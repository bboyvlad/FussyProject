/**
 * Created by bboyvlad on 9/8/16.
 */
'use strict';

var cardcheck = angular.module('CardCheck', ['ngRoute', 'ngMessages', 'ui.utils.masks', 'angularPayments']);

cardcheck.controller('CardCheckController', ['$rootScope','$scope', '$http', '$location', 'myJdMenu', 'helperFunc', 'LxNotificationService', 'LxDialogService',  'cardValidateResource',
    function AddJdCardController($rootScope, $scope, $http, $location, myJdMenu, helperFunc, LxNotificationService, LxDialogService, cardValidateResource) {
        $scope.cssClass = 'cardcheck';
        helperFunc.checkauth(true, ['ROLE_PROVIDER', 'ROLE_ADMIN', 'ROLE_SYSADMIN']);
        var self = this;
        $scope.sendbutton = false;
        $scope.LinearProgress = false;
        $scope.cardCheckImg = '../css/img/finance-graphic.png';
        $scope.fcardcheck = {};
        $scope.dialogValidate = 'dialogValidate';

        /***************** TO RESET FORMS ********************/
        $scope.master = {
            switches: "false", jdcardcode: ""
        };
            $scope.reset = function() {
            $scope.fcardcheck = angular.copy($scope.master);
        };
        $scope.reset();
        /***************** TO RESET FORMS ********************/

        $scope.CheckCard = function CheckCard($event, validate)
        {
            $event.preventDefault();
            //console.log(validate.toSource() );
            if(validate.switches=="false"){
                cardValidateResource.cardStatus({fuelCardCode: validate.jdcardcode}).
                $promise.then(
                    function (data) {
                        $scope.cardBalance = false;
                        $scope.cardStatus = data;
                    }
                );
            }else if(validate.switches=="true"){
                cardValidateResource.cardBalance({fuelCardCode: validate.jdcardcode}).
                $promise.then(
                    function (data) {
                        $scope.cardStatus = false;
                        $scope.cardBalance = data;
                    }
                );
            }
            LxDialogService.open(this.dialogValidate);
        }

        $scope.$on('lx-dialog__open-start', function(_event, _dialogId)
        {
            //var self = this;
            if ($scope.dialogCaptain === _dialogId)
            {

            }
        });

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
        ],
            "cardvalidate":[
                {
                    "link":"/dashboard/cardstatus",
                    "text":"Validador"
                }
            ],
            "mainmenu":{
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
            myJdMenu.cardvalidateSection(this.userOpts.cardvalidate);
        };
    }]);