/**
 * Created by bboyvlad on 9/8/16.
 */
'use strict';

var addjdcard = angular.module('AddJdCard', ['ngRoute', 'ngMessages', 'ui.utils.masks', 'angularPayments']);

addjdcard.controller('AddJdCardController', ['$rootScope','$scope', '$http', '$location', 'myJdMenu', 'helperFunc', 'LxNotificationService', 'jdCardResource', 'userPaymentResource',
    function AddJdCardController($rootScope, $scope, $http, $location, myJdMenu, helperFunc, LxNotificationService, jdCardResource, userPaymentResource) {
        helperFunc.checkauth(true, ['ROLE_ADMIN', 'ROLE_SYSADMIN']);
        var self = this;
        $scope.sendbutton = false;
        $scope.LinearProgress = false;


        /***************** TO RESET FORMS ********************/
        $scope.master = {
            idpaymethod: "", pay_ccsec: "", cardname: "", amount: ""
        };
        $scope.reset = function() {
            $scope.fjdcard = angular.copy($scope.master);
        };
        /***************** TO RESET FORMS ********************/
        $scope.reset();

        $scope.regex = "[A-Z\\s]+";

        $scope.paymethod = [];
        $scope.getPayments = function getPayments(id) {
            var self = this;

            userPaymentResource.get({id: id}).$promise.
                then(
                    function (data) {
                        /*$scope.paymethod = response.data;*/
                        angular.forEach(data, function (key) {
                            //alert(key.pay_type +':'+val);
                            if(key.paytype=="JDCARD"){
                                //self.jdcardOpts.push(key);
                            }else{
                                self.paymethod.push(key);
                            }
                        });
                    }
            )
        };

        $scope.getPayments($rootScope.user.id);

        $scope.addjdCard = function addjdCard($event, fields) {
            $event.preventDefault();
            var self = this;
            this.LinearProgress = helperFunc.toogleStatus(this.LinearProgress);
            this.sendbutton = helperFunc.toogleStatus(this.sendbutton);

            var toSave = '{"idpaymethod":"'+fields.idpaymethod.payid+'","pay_ccsec":"'+fields.pay_ccsec+'","cardname":"'+fields.cardname+'","amount":"'+fields.amount+'"}';

            var buyJdCard = jdCardResource.save({id: $rootScope.user.id}, toSave);
            buyJdCard.$promise.
            then(
                function (data) {
                    console.log("Guardado!!" + data.toSource());
                    if (data.message == "conexionError"){
                        LxNotificationService.error('Transacción cancelada! Verifique su conexión a Internet!!!');
                        self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                        self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                        //self.helperFuncBar();
                        return;
                    }
                    if (data.message == "Tarjeta Invalida"){
                        LxNotificationService.error('Transacción cancelada! Verifique su metodo de Pago!!!');
                        self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                        self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                        //self.helperFuncBar();
                        return;
                    }

                    LxNotificationService.alert('J&D Card Creada',
                        "Ya puede empezar a usar su,\r\nJ&D Card...",
                        'Ok',
                        function(answer)
                        {
                            $scope.reset();
                            self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                            self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                            $location.path("/dashboard");
                        });

                },function (data) {
                    self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                    self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                    console.log("Guardado!!" + data);
                }
            )
        }

        $scope.userOpts = {
            "usermenu":[
                {
                    "link":"/users/sing-up",
                    "text":"Registrate"
                },
                {
                    "link":"/loginpage",
                    "text":"Log In"
                }
            ],
            "useradmin":[
                {
                    "link":"/users/admin",
                    "text":"Gestionar Usuarios"
                }
            ],
            "jdcard":[
                {
                    "link":"/dashboard/buy/jdcard",
                    "text":"Comprar J&D Card"
                },
                {
                    "link":"/dashboard/refill/jdcard",
                    "text":"Refill J&D Card"
                }
            ],
            "giftcard":[
                {
                    "link":"/dashboard/giftcard/buy",
                    "text":"Comprar Gift Card"
                },
                {
                    "link":"/dashboard/giftcard/redeem",
                    "text":"Reclamar Gift Card"
                }
            ],
            "payments":[
                {
                    "link":"/dashboard/paymentmethod-form",
                    "text":"Agregar Metodo de pago"
                }
            ],
            "defgen":[
                {
                    "link":"/dashboard/groupserv/add",
                    "text":"Grupo de Servicios"
                },
                {
                    "link":"/dashboard/products/add",
                    "text":"Productos"
                }
            ],
            "aircraft":[
                {
                    "link":"/dashboard/aircraft/manage",
                "text":"Aeronaves"
            }
        ],
            "captain":[
            {
                "link":"/dashboard/captain/manage",
                "text":"Capitanes"
            }
        ],            "mainmenu":{
                "main":[
                    {
                        "link":"/",
                        "text":"Home"
                    },
                    {
                        "link":"/",
                        "text":"Servicios"
                    },
                    {
                        "link":"/",
                        "text":"Productos"
                    },
                    {
                        "link":"/",
                        "text":"Promociones"
                    },
                    {
                        "link":"/",
                        "text":"Contacto"
                    }
                ]
            }
        };

        $scope.sharedMenu = myJdMenu;

        $scope.updateMenu = function () {
            //alert(this.Opts.item1);
            myJdMenu.userSection(this.userOpts.usermenu);
            myJdMenu.userAdminSection(this.userOpts.useradmin);
            myJdMenu.mainSection(this.userOpts.mainmenu);
            myJdMenu.jdcardSection(this.userOpts.jdcard);
            myJdMenu.giftcardSection(this.userOpts.giftcard);
            myJdMenu.paymentsSection(this.userOpts.payments);
            myJdMenu.defgenSection(this.userOpts.defgen);
            myJdMenu.aircraftSection(this.userOpts.aircraft);
            myJdMenu.captainSection(this.userOpts.captain);
        };
    }]);