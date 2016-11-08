/**
 * Created by bboyvlad on 9/7/16.
 */
'use strict';

var mycaptain = angular.module('MyCaptain', ['ngRoute', 'jdmenu', 'ngMessages']);

mycaptain.controller('MyCaptainController', ['$rootScope','$scope', '$http', '$location', 'myJdMenu', 'LxNotificationService', 'helperFunc', 'mycaptainResource',
    function MyCaptainController($rootScope, $scope, $http, $location, myJdMenu, LxNotificationService, helperFunc, mycaptainResource) {
        var self = this;

        $scope.fmycaptain = {};
        $scope.sendbutton = false;
        $scope.LinearProgress = false;
        $scope.listCaptain = mycaptainResource.query();

        /***************** TO RESET FORMS ********************/
        $scope.master = {
            id: "", name: "", license: "", dateofbirth: "", address: "", city: "", country: "", phone: "", email: "", active: ""
        };
        $scope.reset = function() {
            $scope.fmycaptain = angular.copy($scope.master);
            $scope.feditcaptain = angular.copy($scope.master);
        };
        /***************** TO RESET FORMS ********************/


        /********** saveMyCaptain ********/

        $scope.myCaptainCreate = function myCaptainCreate($event, fields) {
            $event.preventDefault();
            var self = this;
            this.LinearProgress = helperFunc.toogleStatus(this.LinearProgress);
            this.sendbutton = helperFunc.toogleStatus(this.sendbutton);

            var myCaptainSave = mycaptainResource.save({}, fields);
            myCaptainSave.$promise.
            then(
                function (data) {
                    console.log("Guardado!!" + data.toSource());
                    $scope.listCaptain = mycaptainResource.query();
                    $scope.reset();

                    //$rootScope.userInfo = data;
                    LxNotificationService.success('Capitan, creado satisfactoriamente!!!');
                    self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                    self.sendbutton = helperFunc.toogleStatus(self.sendbutton);

                }
            );

        };

        /********** saveMyCaptain ********/


        /********** editCaptain ********/
        //$scope.listCaptain = userResource.myaircraft({principal_id: $rootScope.user.id});

        $scope.dialogCaptain = "dialogCaptain";
        $scope.feditcaptain = {};

        /***************** TO RESET EDIT FORMS ********************/


        $scope.editReset = function(id) {
            $scope.editMaster = {
                id: id, name: "", license: "", dateofbirth: "", address: "", city: "", country: "", phone: "", email: "", active: ""
            };
            $scope.feditcaptain = angular.copy($scope.editMaster);
        };
        /***************** TO RESET FORMS ********************/
        //$scope.reset();

        $scope.openDialogCaptain = function openDialogCaptain(ef_cap)
        {
            LxDialogService.open(this.dialogCaptain);
            console.log(ef_cap.id );
            /***************** TO RECALL DATA ON EDIT FORMS ********************/
            $scope.editmaster = {
                id: ef_cap.id, name: ef_cap.name, license: ef_cap.license, dateofbirth: ef_cap.dateofbirth, address: ef_cap.address, city: ef_cap.city, country: ef_cap.country, phone: ef_cap.phone, email: ef_cap.email, active: ef_cap.active
            };
            $scope.feditcaptain = angular.copy($scope.editmaster);
            /***************** TO RECALL DATA ON EDIT FORMS ********************/

        }

        $scope.$on('lx-dialog__open-start', function(_event, _dialogId)
        {
            //var self = this;
            if ($scope.dialogCaptain === _dialogId)
            {

            }
        });

        $scope.updateCaptain = function updateCaptain($event, fields) {
            $event.preventDefault();
            var self = this;

            console.log(fields[0].id+ ' / ' + fields[0]);
            this.LinearProgress = helperFunc.toogleStatus(this.LinearProgress);
            this.sendbutton = helperFunc.toogleStatus(this.sendbutton);

            mycaptainResource.update({}, fields[0]).$promise.
            then(
                function (data) {
                    console.log("Actualizado!!" + data);
                    LxNotificationService.success('Actualización realizada!!!');
                    $scope.listCaptain = mycaptainResource.query();
                    $scope.reset();
                    //$location.path("/dashboard");
                    LxDialogService.close(self.dialogCaptain);
                    self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                    self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                },function (data) {
                    self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                    self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                    console.log("Error!!" + data);
                }
            );
        }

        /********** editCaptain ********/


        /********** deleteCaptain ********/

        $scope.deleteCaptain = function deleteCaptain(data) {
            //$event.preventDefault();
            var self = this;
            this.LinearProgress = helperFunc.toogleStatus(this.LinearProgress);
            this.sendbutton = helperFunc.toogleStatus(this.sendbutton);
            console.log(data.toSource());
            LxNotificationService.confirm('Eliminar Capitan', 'Por favor confirme que desea eliminar este Capitan.',
                {
                    cancel: 'Cancelar',
                    ok: 'Eliminar'
                }, function(answer)
                {
                    if (answer)
                    {
                        mycaptainResource.delete({id: data.id}).$promise.
                        then(
                            function (data) {
                                console.log("Borrado!!" + data);
                                LxNotificationService.success('Capitan, Eliminado satisfactoriamente!!!');
                                $scope.listCaptain = mycaptainResource.query();
                                //$scope.faircrafts = [];
                                //$location.path("/dashboard");
                                self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                                self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                            },function (data) {
                                self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                                self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                                console.log("Error!!" + data.toSource());
                            }
                        );
                    }
                    else
                    {
                        LxNotificationService.error('Disagree');
                        this.LinearProgress = helperFunc.toogleStatus(this.LinearProgress);
                        this.sendbutton = helperFunc.toogleStatus(this.sendbutton);
                    }
                });

        }
        /********** deleteCaptain ********/


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
    }]);

/*TO COMPARE FIELDS VALUES*/
/*
 var compareTo = function() {
 return {
 require: "ngModel",
 scope: {
 otherModelValue: "=compareTo"
 },
 link: function(scope, element, attributes, ngModel) {

 ngModel.$validators.compareTo = function(modelValue) {
 return modelValue == scope.otherModelValue;
 };

 scope.$watch("otherModelValue", function() {
 ngModel.$validate();
 });
 }
 };
 };

 singup.directive("compareTo", compareTo);*/
