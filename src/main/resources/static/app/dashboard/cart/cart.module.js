/**
 * Created by bboyvlad on 9/8/16.
 */
'use strict';

var addcart = angular.module('AddCart', ['ngRoute', 'ngMessages', 'ui.utils.masks', 'ngAnimate', 'ngMaterialDatePicker']);

addcart.controller('AddCartController', ['$rootScope','$scope', '$http', '$location', 'LxDatePickerService','LxNotificationService', 'myJdMenu', 'helperFunc', 'shopcartResource', 'locationResource', 'aircraftResource', 'mycaptainResource', '$translate', '$filter',
    function AddCartController($rootScope, $scope, $http, $location, LxDatePickerService, LxNotificationService, myJdMenu, helperFunc, shopcartResource, locationResource, aircraftResource, mycaptainResource, $translate, $filter) {
        $scope.cssClass = 'cart';
        if($body.hasClass('sidebar_secondary_active')) {
            $body.removeClass('sidebar_secondary_active');
        }

        var self = this;
        $scope.sendbutton = false;
        $scope.LinearProgress = false;
        $scope.shopCartsendbutton = false;
        $scope.shopCartLinearProgress = false;
        $scope.startDatePickerId = 'estimateArrival';
        $scope.endDatePickerId = 'estimateDeparture';
        $scope.icon = '../css/icons/airport-flight-info-signal.png';

        //$scope.fcart = {datePicker: { locale: 'es', format: 'YYYY-MM-DD' }, startdatePicker: { dateFormated: null, minDate: moment() }, enddatePicker: { dateFormated: null, minDate:  moment() };
            $scope.fcart = {datePicker: { locale: 'es', format: 'YYYY-MM-DD' }, startdatePicker: { dateFormated: null, minDate: '' }, enddatePicker: { dateFormated: null, minDate:  '' }
        };
        $scope.itemQuantity = "";
        $scope.pPrice = '';

        $scope.$on('lx-date-picker__close-end', function(_event, _dialogId)
        {
            //var self = this;
            if ($scope.endDatePickerId === _dialogId)
            {
                if(moment($scope.fcart.enddatePicker.dateFormated).diff($scope.fcart.startdatePicker.dateFormated, 'days') < 0 ){
                    $scope.fcart.enddatePicker.dateFormated = angular.copy($scope.fcart.startdatePicker.dateFormated);
                    $scope.fcart.estimateDeparture = angular.copy($scope.fcart.startdatePicker.dateFormated);
                    /*console.log($scope.fcart.toSource());
                    console.log(moment($scope.fcart.enddatePicker.dateFormated).diff($scope.fcart.startdatePicker.dateFormated, 'days'));*/
                    $scope.sms1 = $filter('translate')('cart.module.sms1');
                    LxNotificationService.error($scope.sms1 );
                }
            }
        });


        $scope.datePickerCallback = function datePickerCallback(_newdate, action=false)
        {
            if(action==true){
                $scope.fcart.enddatePicker.dateFormated = moment(_newdate).locale($rootScope.dateP.locale).format($rootScope.dateP.format);


                LxDatePickerService.close($scope.endDatePickerId);
                return;
            }
            //console.log("_newdate: "+_newdate);
                $scope.fcart.startdatePicker.dateFormated = moment(_newdate).locale($rootScope.dateP.locale).format($rootScope.dateP.format);
            /*console.log("startdatePicker: "+$scope.fcart.startdatePicker.toSource());
            $scope.fcart.enddatePicker.minDate = angular.copy(moment($scope.fcart.startdatePicker.dateFormated));
            console.log("enddatePicker: "+$scope.fcart.enddatePicker.toSource());*/

            LxDatePickerService.close($scope.startDatePickerId);
        };

        /***************** TO RESET FORMS ********************/
        $scope.master = {
            location: false, estimateArrival: null, time: "", estimateDeparture: null, timeDeparture: "", myaircraft: "", datePicker: { locale: 'es', format: 'YYYY-MM-DD' }, startdatePicker: { dateFormated: new Date()}, enddatePicker: { dateFormated: new Date()}
        };
        $scope.masteritem = "";
        $scope.reset = function() {
            $scope.fcart = angular.copy($scope.master);
            $scope.itemQuantity = angular.copy($scope.masteritem);
            $scope.prepareFligthList = [];
        };

        $scope.$watch('fcart.location', function (newVal, oldVal, scope) {
            /*console.log("newVal:" +newVal+ "oldVal:" +oldVal)
            console.log( angular.isDefined(newVal) );
            console.log(  newVal=="");*/
            if( newVal==""){
                $scope.reset();
            }
        });
        /***************** TO RESET FORMS ********************/

        /***************** OPTIONS LIST ********************/

        $scope.listMyAirCrafts = aircraftResource.usersAircraft();
        $scope.listCaptain = mycaptainResource.query();

        /***************** OPTIONS LIST ********************/

        /****** Location Search ********/
        $scope.selectAjax = {
            loading: false,
            locationOpts: []
        };
        $scope.searchLocation = function searchLocation(newFilter) {
            //console.log(newFilter);
            if (newFilter && newFilter.length >= 4)
            {
                $scope.selectAjax.loading = true;
                locationResource.airportByName({ airportname: newFilter}).$promise.then(
                    function(data)
                    {
                        $scope.selectAjax.locationOpts = data;
                        $scope.selectAjax.loading = false;
                    },
                    function()
                    {
                        $scope.selectAjax.loading = false;
                        $scope.selectAjax.locationOpts = false;
                    });
            }
            else
            {
                $scope.selectAjax.locationOpts = false;
            }
        };


        $scope.productInfo = function productInfo(info) {

            $scope.fcart.push(info.description);
            $scope.pPrice = info.prices[0].price;

        };


        $scope.checkService = [];

        $scope.sync = function sync(bool, item, itemQuantity = false){
            //console.log(bool+"/"+item+"/"+itemQuantity);
            if(bool){
                // add item
                $scope.checkService.push(item);
            } else {
                // remove item
                $scope.servicesTotal = 0;
                for(var i=0 ; i < $scope.checkService.length; i++) {
                    if($scope.checkService[i].id == item.id){
                        if(!itemQuantity){
                            $scope.checkService.splice(i,1);
                        }else{
                            $scope.checkService[i].quantity = itemQuantity;
                            $scope.checkService[i].subtotal = itemQuantity * item.price;
                        }
                    }
                    if(angular.isDefined($scope.checkService[i].subtotal)){
                        $scope.servicesTotal += $scope.checkService[i].subtotal;
                    }
                }
            }
        };

        /* ESTUDIAR COMO MEJORAR */
        /*$scope.$watch("CartForm.$invalid", function(newVal, oldVal, $scope) {
            console.log(newVal+"/"+ oldVal+"/"+ $scope)
            if(newVal==false && oldVal==true){
                $scope.searchServices();
            }
        });*/

        /****************** searchServices ******************/

        $scope.searchServices = function searchServices($event, fields, id) {
            $event.preventDefault();
            var self = this;
            $scope.LinearProgress = helperFunc.toogleStatus($scope.LinearProgress);
            $scope.sendbutton = helperFunc.toogleStatus($scope.sendbutton);

            var toSave = {location : fields.location.id ,  myaircraft : fields.myaircraft.id, landing: fields.estimateArrival,  mycaptain : fields.mycaptain.id };

            var prepareFlight = shopcartResource.prepareFlight({}, toSave);
            prepareFlight.$promise.
            then(
                function (data) {
                    console.log("Productos: " + data.toSource());
                    $scope.LinearProgress = helperFunc.toogleStatus($scope.LinearProgress);
                    $scope.sendbutton = helperFunc.toogleStatus($scope.sendbutton);
                    $scope.prepareFligthList = data;
                    $scope.sms2 = $filter('translate')('cart.module.sms2');
                    LxNotificationService.info($scope.sms2);
                    //$location.path("/dashboard");
                },function (data) {
                    console.log("Error!!" + data);
                    $scope.LinearProgress = helperFunc.toogleStatus($scope.LinearProgress);
                    $scope.sendbutton = helperFunc.toogleStatus($scope.sendbutton);
                }
            )
        };
        /****************** searchServices ******************/


        /****************** addShopCart ******************/

        $scope.addShopCart = function addShopCart($event, fields, id) {
            $event.preventDefault();
            var self = this;
            this.shopCartLinearProgress = helperFunc.toogleStatus(this.shopCartLinearProgress);
            this.shopCartsendbutton = helperFunc.toogleStatus(this.shopCartsendbutton);

            /*var toSave = { location: fields.location.id, myaircraft: fields.myaircraft.id, captain: fields.mycaptain.id, landing: helperFunc.dateToDB(fields.estimateArrival), description: fields.description, generated: false, items: angular.toJson($scope.checkService) };*/
            console.log(fields.toSource());

            var toSave = '{ "location":"'+fields.location.id+'",  "myaircraft":"'+fields.myaircraft.id+'", "captain":"'+fields.mycaptain.id+'", "landing":"'+helperFunc.dateToDB(fields.startdatePicker.dateFormated+" "+fields.time+":00", "YYYY-MM-DD HH:mm:ss", true)+'",  "returndate":"'+helperFunc.dateToDB(fields.enddatePicker.dateFormated+" "+fields.timeDeparture+":00", "YYYY-MM-DD HH:mm:ss", true)+'",  "description":"'+fields.description+'",  "generated":"false",  "items": '+ angular.toJson($scope.checkService) +'}';

            //var addToCart = shopcartResource.addItemCart({}, toSave);
            shopcartResource.addItemCart({}, toSave).$promise.
            then(
                function (data) {
                    console.log("cart: " + data.toSource());
                    //$rootScope.cart = data;
                    $rootScope.cart = shopcartResource.getCartUser();
                    $scope.sms3 = $filter('translate')('cart.module.sms3');
                    LxNotificationService.info($scope.sms3, undefined, true);
                    self.shopCartLinearProgress = helperFunc.toogleStatus(self.shopCartLinearProgress);
                    self.shopCartsendbutton = helperFunc.toogleStatus(self.shopCartsendbutton);
                    $location.path("/dashboard");
                },function (data) {
                    console.log("Error!!" + data);
                    self.shopCartLinearProgress = helperFunc.toogleStatus(self.shopCartLinearProgress);
                    self.shopCartsendbutton = helperFunc.toogleStatus(self.shopCartsendbutton);
                }
            )
        };

        /****************** addShopCart ******************/


    }]);