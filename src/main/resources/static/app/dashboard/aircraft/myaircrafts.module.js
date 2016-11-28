/**
 * Created by bboyvlad on 9/8/16.
 */
'use strict';

var myaircrafts = angular.module('MyAirCraft', ['ngRoute', 'ngMessages']);

myaircrafts.controller('MyAirCraftsController', ['$rootScope','$scope', '$http', '$location', 'myJdMenu', 'helperFunc', 'aircraftResource', 'LxDialogService', 'LxNotificationService', 'userResource', '$translate', '$filter',
    function MyAirCraftsController($rootScope, $scope, $http, $aircraft, myJdMenu, helperFunc, aircraftResource, LxDialogService, LxNotificationService, userResource, $translate, $filter) {
        $scope.cssClass = 'aircraft';
        $scope.icon = '../css/icons/plane-flying.png';
        var self = this;
        $scope.sendbutton = false;
        $scope.LinearProgress = false;
        $scope.regex = "/[A-Z,\s]+/";
        $scope.search = { mysearch:'' };
        $scope.faircrafts = {};
        $scope.aviationOpts = [
            { key: "1", value: "COMERCIAL" },{ key: "2", value: "GENERAL" }
        ];

        /***************** TO RESET FORMS ********************/
        $scope.master = {
            aircraftmodel: "", tailnumber: "", aviationtype: {key:"", value:""} ,name:"" ,active:false
        };
        $scope.reset = function() {
            $scope.faircrafts = angular.copy($scope.master);
            $scope.editaircrafts = angular.copy($scope.master);
            $scope.AircraftModelId = "";
        };
        /***************** TO RESET FORMS ********************/
        //$scope.reset();

        /****** SetAircraftModelId ********/
        $scope.AircraftModelId = "";
        $scope.setAircraftModelId = function setAircraftModelId(_newValue) {
            console.log(_newValue.toSource());
            if(angular.isDefined(_newValue.aircraftype)){
                $scope.AircraftModelId = _newValue.aircraftype;
                return;
            }
            $scope.AircraftModelId = _newValue.id;
        };
        /****** SetAircraftModelId ********/

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
                id: id, aircraftmodel: "", tailnumber: "", aviationtype: {key:"", value:""} ,name:"" ,active:false
            };
            $scope.AircraftModelId = "";
            $scope.editaircrafts = angular.copy($scope.editMaster);
        };
        /***************** TO RESET FORMS ********************/

        $scope.toEditAvType = function toEditAvType(data) {
            switch (data) {
                case "1":
                    return { key: "1", value: "COMERCIAL" };
                    break;
                case "2":
                    return { key: "2", value: "GENERAL" };
                    break;
                default:
                    return { key: "1", value: "COMERCIAL" };
            }
        };

        $scope.openDialogAircraft = function openDialogAircraft(ef_air)
        {
            LxDialogService.open(this.dialogAircraft);
            console.log(ef_air.toSource());

            /***************** TO RECALL DATA ON EDIT FORMS ********************/
            $scope.editmaster = {
                id: ef_air.id, aircraftmodel: ef_air, tailnumber: ef_air.tailnumber, aviationtype: $scope.toEditAvType(ef_air.aviationtype.key) , name:ef_air.name , active:ef_air.active
            };
            $scope.AircraftModelId = ef_air.aircraftype;
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

            var toSave = {
                id: fields.id, aircrafttype: $scope.AircraftModelId , tailnumber: fields.tailnumber, aviationtype:fields.aviationtype.key, name:fields.name , active:fields.active
            };
            console.log(toSave.toSource());

            aircraftResource.updateAircraft(toSave).$promise.
            then(
                function (data) {
                    console.log("Updated!!" + data);
                    $scope.sms1 = $filter('translate')('aircraft.module.sms1');
                    LxNotificationService.success($scope.sms1);
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
            /*var toSave = '{"aircrafttype":"'+fields.aircraftmodel.id+'","tailnumber":"'+fields.tailnumber+'","name":"'+fields.aircraftmodel.name+'", aviationtype: {key:ef_air.aviationtype, value:ef_air.aviationtype} , name:ef_air.name , active:ef_air.active}';*/

            var toSave = {aircrafttype: fields.aircraftmodel.id, tailnumber: fields.tailnumber, aviationtype:fields.aviationtype.key, name:fields.name , active:fields.active};

            userResource.myaircraft({principal_id: $rootScope.user.id}, toSave).$promise.
            then(
                function (data) {
                    console.log("Saved!!" + data.toString());
                    if(data.message == "Error en la operacion"){
                        $scope.sms2 = $filter('translate')('aircraft.module.sms2');
                        LxNotificationService.error($scope.sms2);
                        self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                        self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                        //self.helperFuncBar();
                        return;
                    }
                    $scope.sms3 = $filter('translate')('aircraft.module.sms3');
                    LxNotificationService.success($scope.sms3);
                    $scope.listAircraft = aircraftResource.usersAircraft();
                    $scope.reset();
                    self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                    self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                },function (data) {
                    self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                    self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                    console.log("Error!!" + data.toString());
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
            $scope.sms4 = $filter('translate')('aircraft.module.sms4');
            LxNotificationService.confirm($scope.sms4, $scope.sms5,
                {
                    cancel: $filter('translate')('aircraft.module.btn1'),
                    ok: $filter('translate')('aircraft.module.btn2')
                }, function(answer)
                {
                    if (answer)
                    {
                        aircraftResource.deleteAircraft({aircraftid: data.id}).$promise.
                        then(
                            function (data) {
                                console.log("Erased!!" + data.toString());
                                if(data.message == "Error en la operation"){
                                    $scope.sms6 = $filter('translate')('aircraft.module.sms6');
                                    LxNotificationService.error($scope.sms6);
                                    self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                                    self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                                    //self.helperFuncBar();
                                    return;
                                }
                                $scope.sms7 = $filter('translate')('aircraft.module.sms7');
                                LxNotificationService.success($scope.sms7);
                                $scope.listAircraft = aircraftResource.usersAircraft();
                                //$scope.faircrafts = [];
                                //$location.path("/dashboard");
                                self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                                self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                            },function (data) {
                                self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                                self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                                console.log("Error!!" + data.toString());
                            }
                        );
                    }
                    else
                    {
                        $scope.sms8 = $filter('translate')('aircraft.module.sms8');
                        LxNotificationService.error($scope.sms8);
                        this.LinearProgress = helperFunc.toogleStatus(this.LinearProgress);
                        this.sendbutton = helperFunc.toogleStatus(this.sendbutton);
                    }
                });

        }
        /********** deleteAircraft ********/

    }]);