/**
 * Created by bboyvlad on 9/8/16.
 */
'use strict';

var addgiftcard = angular.module('AddGiftJdCard', ['ngRoute', 'ngMessages', 'ui.utils.masks', 'angularPayments']);

addgiftcard.controller('AddGiftJdCardController', ['$rootScope','$scope', '$http', '$location', 'myJdMenu', 'helperFunc', 'LxNotificationService', 'giftCardResource', 'userPaymentResource',
    function AddJdCardController($rootScope, $scope, $http, $location, myJdMenu, helperFunc, LxNotificationService, giftCardResource, userPaymentResource) {
        helperFunc.checkauth(true, ['ROLE_ADMIN', 'ROLE_SYSADMIN']);
        var self = this;
        $scope.sendbutton = false;
        $scope.LinearProgress = false;

        $scope.fgiftcard = {};
        $scope.regex = "[A-Z\\s]+";

        $scope.paymethod = [];
        $scope.jdcardOpts = [];

        /***************** TO RESET FORMS ********************/
        $scope.master = {
            switches: "false", idpaymethod: "", paycardsec: "", tdcamount: "", jdcardmethod: "", jdcamount: "", recipient_email: "", recipient_name: "", recipient_message: "" };
            $scope.reset = function() {
            $scope.fgiftcard = angular.copy($scope.master);
        };
        $scope.reset();
        /***************** TO RESET FORMS ********************/

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

        //alert($rootScope.user.id);
        $scope.getPayments($rootScope.userDetail.id);

        $scope.addGiftCard = function addGiftCard($event, fields) {
            $event.preventDefault();
            var self = this;
            this.LinearProgress = helperFunc.toogleStatus(this.LinearProgress);
            this.sendbutton = helperFunc.toogleStatus(this.sendbutton);
            console.log(fields.toSource());
            if(fields.switches=="false"){

                var toSave = '{ ' +
                '"recipient_email":"'+fields.recipient_email+'", ' +
                '"recipient_message":"'+fields.recipient_message+'", ' +
                '"recipient_name":"'+fields.recipient_name+'", ' +
                '"paymethod":"'+ fields.idpaymethod.payid +'", ' +
                '"amount":'+fields.tdcamount +', ' +
                '"paycardsec":"'+fields.paycardsec +'" ' +
                '}';
            }else{
                var toSave = '{ ' +
                '"recipient_email":"'+fields.recipient_email+'", ' +
                '"recipient_message":"'+fields.recipient_message+'", ' +
                '"recipient_name":"'+fields.recipient_name+'", ' +
                '"paymethod":"'+ fields.jdcardmethod.payid+'", ' +
                '"amount":'+fields.jdcamount+
                '}';
            }

            console.log(toSave.toSource());
            var buyGiftCard = giftCardResource.save({}, toSave);
            buyGiftCard.$promise.
            then(
                function (data) {
                    console.log("Guardado!!" + data);
                    if (data.message == "conexionError"){
                        LxNotificationService.error('Transacción cancelada! Verifique su conexión a Internet!!!');
                        self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                        self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                        //self.helperFuncBar();
                        return;
                    }else if(data.message == "Saldo insuficiente"){
                        LxNotificationService.error('Transacción cancelada! No cuenta con sufiente saldo!!!');
                        self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                        self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                        //self.helperFuncBar();
                        return;
                    }

                    LxNotificationService.alert('Gift Card Enviada',
                        "El Destinatario Recibira en su correo las instrucciones para,\r\nReclamar su Gift Card...",
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