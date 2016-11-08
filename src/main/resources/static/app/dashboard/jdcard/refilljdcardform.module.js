/**
 * Created by bboyvlad on 9/8/16.
 */
'use strict';

var refilljdcard = angular.module('reFillJdCard', ['ngRoute', 'ui.utils.masks', 'angularPayments']);

refilljdcard.controller('RefillJdCardController', ['$rootScope','$scope', '$http', '$location', 'myJdMenu', 'LxNotificationService', 'helperFunc','jdCardResource', 'userPaymentResource', 'cardPaymentResource',
    function AddJdCardController($rootScope, $scope, $http, $location, myJdMenu, LxNotificationService, helperFunc, jdCardResource, userPaymentResource, cardPaymentResource) {

        var self = this;
        $scope.sendbutton = false;
        $scope.LinearProgress = false;

        $scope.frfilljdcard = {};
        /***************** TO RESET FORMS ********************/
        $scope.master = {
            jdcardSelected:"", idpaymethod:"", pay_ccsec:"", amount:"",
        };
        $scope.reset = function() {
            $scope.frfilljdcard = angular.copy($scope.master);
        };
        /***************** TO RESET FORMS ********************/
        //$scope.reset();

        $scope.paymethod = [];
        $scope.jdcardOpts = [];
        $scope.getPayments = function getPayments(id) {
            var self = this;

            userPaymentResource.get({id: id}).$promise.
            then(
                function (data) {
                    /*$scope.paymethod = response.data;*/
                    angular.forEach(data, function (key) {
                        //alert(key.paytype +':'+val);
                        if(key.paytype=="JDCARD"){
                            self.jdcardOpts.push(key);
                        }else{
                            self.paymethod.push(key);
                        }
                    });
                }
            )
        };

        $scope.getPayments($rootScope.user.id);

        $scope.refilljdCard = function refilljdCard($event, fields) {
            $event.preventDefault();
            var self = this;
            this.LinearProgress = helperFunc.toogleStatus(this.LinearProgress);
            this.sendbutton = helperFunc.toogleStatus(this.sendbutton);

            //console.log(fields);
            var toSave = '{"idpaymethod":"'+fields.idpaymethod.payid+'","pay_ccsec":"'+fields.pay_ccsec+'","amount":"'+fields.amount+'"}';

            cardPaymentResource.refill({card_id: fields.jdcardSelected.payid}, toSave).$promise.
            then(
                function (data) {
                    console.log("Guardado!!" + data);
                    if (data.message == "conexionError"){
                        LxNotificationService.error('Transacción cancelada! Verifique su conexión a Internet!!!');
                        self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                        self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                        //self.helperFuncBar();
                        return;
                    }

                    LxNotificationService.alert('J&D Card Refill',
                        "Refill Satisfactorio, han sido abonados \r\n $"+ fields.amount +" en su J&D Card...",
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
                    console.log("error!!" + data);
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