/**
 * Created by bboyvlad on 9/8/16.
 */
'use strict';

var myaircrafts = angular.module('MyAirCraft', ['ngRoute', 'ngMessages']);

myaircrafts.controller('MyAirCraftsController', ['$rootScope','$scope', '$http', '$location', 'myJdMenu', 'helperFunc', 'aircraftResource', 'LxDialogService', 'LxNotificationService', 'userResource',
    function MyAirCraftsController($rootScope, $scope, $http, $aircraft, myJdMenu, helperFunc, aircraftResource, LxDialogService, LxNotificationService, userResource) {

        var self = this;
        $scope.sendbutton = false;
        $scope.LinearProgress = false;
        $scope.regex = "/[A-Z,\s]+/";

        $scope.faircrafts = {};

        /***************** TO RESET FORMS ********************/
        $scope.master = {
            aircraftmodel: "", tailnumber: ""
        };
        $scope.reset = function() {
            $scope.faircrafts = angular.copy($scope.master);
            $scope.editaircrafts = angular.copy($scope.master);
        };
        /***************** TO RESET FORMS ********************/
        //$scope.reset();

        /****** Aircraft Search ********/
        $scope.selectAjax = {
            loading: false,
            aircraftOpts: []
        };
        $scope.searchAircrafts = function searchAircrafts(newFilter) {
            //console.log(newFilter);
            if (newFilter && newFilter.length > 3)
            {
                $scope.selectAjax.loading = true;
                aircraftResource.aircraftByName({ name: newFilter}).$promise.then(
                    function(data)
                    {
                        $scope.selectAjax.aircraftOpts = data;
                        $scope.selectAjax.loading = false;
                    },
                    function()
                    {
                        $scope.selectAjax.loading = false;
                        $scope.selectAjax.aircraftOpts = false;
                    });
            }
            else
            {
                $scope.selectAjax.aircraftOpts = false;
            }
        };
        /****** Aircraft Search ********/

        /********** editAircraft ********/
        $scope.listAircraft = aircraftResource.usersAircraft();

        $scope.dialogAircraft = "dialogAircraft";
        $scope.editaircrafts = {};

        /***************** TO RESET EDIT FORMS ********************/


        $scope.editReset = function(id) {
            $scope.editMaster = {
                id: id, aircraftmodel: "", tailnumber: ""
            };
            $scope.editaircrafts = angular.copy($scope.editMaster);
        };
        /***************** TO RESET FORMS ********************/
        //$scope.reset();

        $scope.openDialogAircraft = function openDialogAircraft(ef_air)
        {
            LxDialogService.open(this.dialogAircraft);
            console.log(ef_air.id );
            /***************** TO RECALL DATA ON EDIT FORMS ********************/
            $scope.editmaster = {
                id: ef_air.id, aircraftmodel: ef_air, tailnumber: ef_air.tailnumber
            };
            $scope.editaircrafts = angular.copy($scope.editmaster);
            /***************** TO RECALL DATA ON EDIT FORMS ********************/

        }

        $scope.$on('lx-dialog__open-start', function(_event, _dialogId)
        {
            //var self = this;
            if ($scope.dialogAircraft === _dialogId)
            {

            }
        });

        $scope.updateAircraft = function updateAircraft($event, fields) {
            $event.preventDefault();
            var self = this;

            console.log("air id: "+fields.id+ ' / ' + fields.toSource());
            this.LinearProgress = helperFunc.toogleStatus(this.LinearProgress);
            this.sendbutton = helperFunc.toogleStatus(this.sendbutton);

            aircraftResource.updateAircraft({},fields).$promise.
            then(
                function (data) {
                    console.log("Actualizado!!" + data);
                    LxNotificationService.success('Actualización realizada!!!');
                    $scope.listAircraft = aircraftResource.usersAircraft();
                    $scope.reset();
                    //$location.path("/dashboard");
                    LxDialogService.close(self.dialogAircraft);
                    self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                    self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                },function (data) {
                    self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                    self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                    console.log("Error!!" + data);
                }
            );
        }

        /********** editAircraft ********/

        /********** addAircraft ********/
        $scope.addAircraft = function addAircraft($event, fields) {
            $event.preventDefault();
            var self = this;
            this.LinearProgress = helperFunc.toogleStatus(this.LinearProgress);
            this.sendbutton = helperFunc.toogleStatus(this.sendbutton);
            var toSave = '{"aircrafttype":"'+fields.aircraftmodel.id+'","tailnumber":"'+fields.tailnumber+'","name":"'+fields.aircraftmodel.name+'"}';
            userResource.myaircraft({principal_id: $rootScope.user.id}, toSave).$promise.
            then(
                function (data) {
                    console.log("Guardado!!" + data);
                    LxNotificationService.success('Aeronave creada satisfactoriamente!!!');
                    $scope.listAircraft = aircraftResource.usersAircraft();
                    $scope.reset();
                    self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                    self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                },function (data) {
                    self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                    self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                    console.log("Error!!" + data.toSource());
                }
            );
        }
        /********** addAircraft ********/

        /********** deleteAircraft ********/

        $scope.deleteAircraft = function deleteAircraft(data) {
            //$event.preventDefault();
            var self = this;
            this.LinearProgress = helperFunc.toogleStatus(this.LinearProgress);
            this.sendbutton = helperFunc.toogleStatus(this.sendbutton);
            console.log(data.toSource());
            LxNotificationService.confirm('Eliminar Aeronave', 'Por favor confirme que desea eliminar esta Aeronave.',
                {
                    cancel: 'Cancelar',
                    ok: 'Eliminar'
                }, function(answer)
                {
                    if (answer)
                    {
                        aircraftResource.deleteAircraft({aircraftid: data.id}).$promise.
                        then(
                            function (data) {
                                console.log("Borrado!!" + data);
                                LxNotificationService.success('Aeronave, Eliminado satisfactoriamente!!!');
                                $scope.listAircraft = aircraftResource.usersAircraft();
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
        /********** deleteAircraft ********/


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