/**
 * Created by bboyvlad on 9/7/16.
 */
'use strict';

var sms = angular.module('sms', ['ngRoute']);

    sms.controller('smsController', ['$rootScope', '$scope', '$http', '$location', 'myJdMenu',
        function smsController($rootScope, $scope, $http, $location, myJdMenu) {

            var self = this;
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
                "useradmin":false,
                "jdcard":false,
                "giftcard": false,
                "payments":false,
                "defgen":false,
                "aircraft":false,
                "captain":false,
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
            };


        $scope.resendEmail = function resendEmail() {
            alert("Confirmation Email has been Resend!");
        }
}]);