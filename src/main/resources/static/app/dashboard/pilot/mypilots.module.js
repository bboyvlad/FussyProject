/**
 * Created by bboyvlad on 9/8/16.
 */
'use strict';

var pilot = angular.module('pilot', ['ngRoute', 'ngMessages']);

pilot.controller('pilotController', ['$rootScope','$scope', '$http', '$location', 'myJdMenu', 'helperFunc', 'grpServResource', 'LxDialogService', 'LxNotificationService',
    function pilotsController($rootScope, $scope, $http, $location, myJdMenu, helperFunc, grpServResource, LxDialogService, LxNotificationService) {
        $scope.cssClass = 'pilot';
        var self = this;
        $scope.sendbutton = false;
        $scope.LinearProgress = false;

        $scope.fpilots = {};

        /***************** TO RESET FORMS ********************/
        $scope.master = {
            groupcode: "", description: "", detailsdesc: ""
        };
        $scope.reset = function() {
            $scope.fpilots = angular.copy($scope.master);
            $scope.editFieldsGS = angular.copy($scope.master);
        };
        /***************** TO RESET FORMS ********************/
        //$scope.reset();

        $scope.regex = "/[A-Z,\s]+/";



        /********** editPilot ********/
        $scope.listGS = grpServResource.query();

        $scope.dialogPilot = "dialogPilot";
        $scope.editFieldsGS = {};

        /***************** TO RESET EDIT FORMS ********************/


        $scope.editReset = function(id, groupcode) {
            $scope.editMaster = {
                id: id, groupcode: groupcode, description: "", detailsdesc: ""
            };
            $scope.editFieldsGS = angular.copy($scope.editMaster);
        };
        /***************** TO RESET FORMS ********************/
        //$scope.reset();

        $scope.openDialogPilot = function openDialogPilot(gs)
        {
            LxDialogService.open(this.dialogPilot);
            console.log(gs.id );
            /***************** TO RECALL DATA ON EDIT FORMS ********************/
            $scope.editmaster = {
                id: gs.id, groupcode: gs.groupcode, description: gs.description, detailsdesc: gs.detailsdesc
            };
            $scope.editFieldsGS = angular.copy($scope.editmaster);
            /***************** TO RECALL DATA ON EDIT FORMS ********************/
            //$scope.reset();
            /*self.editFieldsGS = [
                {
                    id: gs.id,
                    groupcode: gs.groupcode,
                    description: gs.description,
                    detailsdesc: gs.detailsdesc
                }
            ];*/
            /*this.editFieldsGS = [
                {
                    key: gs.key,
                    name: gs.name
                }
            ];*/
        }

        $scope.$on('lx-dialog__open-start', function(_event, _dialogId)
        {
            //var self = this;
            if ($scope.dialogPilot === _dialogId)
            {

            }
        });

        $scope.updatePilot = function updatePilot($event, fields) {
            $event.preventDefault();
            var self = this;

            console.log(fields[0].id+ ' / ' + fields[0]);
            this.LinearProgress = helperFunc.toogleStatus(this.LinearProgress);
            this.sendbutton = helperFunc.toogleStatus(this.sendbutton);

            grpServResource.update({id: fields[0].id}, fields[0]).$promise.
            then(
                function (data) {
                    console.log("Updated!!" + data);
                    LxNotificationService.success('Update successful!!!');
                    $scope.listGS = grpServResource.query();
                    $scope.reset();
                    //$location.path("/dashboard");
                    LxDialogService.close(self.dialogPilot);
                    self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                    self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                },function (data) {
                    self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                    self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                    console.log("Error!!" + data);
                }
            )
        }

        /********** editPilot ********/

        /********** addPilot ********/
        $scope.addPilot = function addPilot($event, fields) {
            $event.preventDefault();
            var self = this;
            this.LinearProgress = helperFunc.toogleStatus(this.LinearProgress);
            this.sendbutton = helperFunc.toogleStatus(this.sendbutton);

            grpServResource.save({}, fields).$promise.
            then(
                function (data) {
                    console.log("Saved!!" + data);
                    LxNotificationService.success('Pilot, created successfully!!!');
                    $scope.listGS = grpServResource.query();
                    $scope.reset();
                    self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                    self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                },function (data) {
                    self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                    self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                    console.log("Error!!" + data.toSource());
                }
            )
        }
        /********** addPilot ********/

        /********** deletePilot ********/

        $scope.deletePilot = function deletePilot(data) {
            //$event.preventDefault();
            var self = this;
            this.LinearProgress = helperFunc.toogleStatus(this.LinearProgress);
            this.sendbutton = helperFunc.toogleStatus(this.sendbutton);
            console.log(data.toSource());
            LxNotificationService.confirm('Eliminar Pilot', 'Por favor confirme que desea eliminar este grupo de servicio.',
                {
                    cancel: 'Cancelar',
                    ok: 'Eliminar'
                }, function(answer)
                {
                    if (answer)
                    {
                        grpServResource.delete({id: data.id}).$promise.
                        then(
                            function (data) {
                                console.log("Borrado!!" + data);
                                LxNotificationService.success('Pilot, Erased successfully!!!');
                                $scope.listGS = grpServResource.query();
                                $scope.fpilots = [];
                                //$location.path("/dashboard");
                                self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                                self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                            },function (data) {
                                self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                                self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                                console.log("Error!!" + data.toSource());
                            }
                        )
                    }
                    else
                    {
                        LxNotificationService.error('Disagree');
                        this.LinearProgress = helperFunc.toogleStatus(this.LinearProgress);
                        this.sendbutton = helperFunc.toogleStatus(this.sendbutton);
                    }
                });

        }
        /********** deletePilot ********/


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
                "text":"Aircrafts"
            }
        ],
            "captain":[
            {
                "link":"/dashboard/captain/manage",
                "text":"Captaines"
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