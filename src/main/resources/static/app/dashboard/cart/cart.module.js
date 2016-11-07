/**
 * Created by bboyvlad on 9/8/16.
 */
'use strict';

var addcart = angular.module('AddCart', ['ngRoute', 'ngMessages', 'ui.utils.masks']);

addcart.controller('AddCartController', ['$rootScope','$scope', '$http', '$location', 'LxDatePickerService','LxNotificationService', 'myJdMenu', 'helperFunc', 'shopcartResource', 'locationResource', 'aircraftResource',
    function AddCartController($rootScope, $scope, $http, $location, LxDatePickerService, LxNotificationService, myJdMenu, helperFunc, shopcartResource, locationResource, aircraftResource) {
        helperFunc.checkauth(true, ['ROLE_ADMIN', 'ROLE_SYSADMIN']);
        if($body.hasClass('sidebar_secondary_active')) {
            $body.removeClass('sidebar_secondary_active');
        }

        var self = this;
        $scope.sendbutton = false;
        $scope.LinearProgress = false;
        $scope.shopCartsendbutton = false;
        $scope.shopCartLinearProgress = false;

        $scope.fcart = {};
        $scope.pPrice = '';

        /***************** TO RESET FORMS ********************/
        $scope.master = {
            location: "", estimateArrival: "", time: "", myaircraft: ""
        };
        $scope.masteritem = {
            itemQuantity: false
        };
        $scope.reset = function() {
            $scope.fcart = angular.copy($scope.master);
            $scope.itemQuantity = angular.copy($scope.masteritem);
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

        $scope.listMyAirCrafts = aircraftResource.usersAircraft();
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


        /* OnStartDateCloseEvent
        $scope.startDatePickerId = 'estimateArrival';
        $scope.endDatePickerId = 'estimateDeparture'
        $scope.minDate ={};
        $scope.setMaxDepartureDate = function setMaxDepartureDate(_newDate){
            console.log(_newDate.toSource());
            $scope.minDate= _newDate;
            console.log($scope.minDate.toSource());
        }*/
        /*$scope.$on('lx-date-picker__close-start', function(_event, _datePickerId)
        {
            //var self = this;
            if ($scope.startDatePickerId === _datePickerId)
            {
                //LxDialogService.close(self.dialogProduct);
                $scope.listPrices = [];
            }

            if ($scope.endDatePickerId === _datePickerId)
            {
                //LxDialogService.close(self.dialogPrice);
            }
        });*/

        /*$scope.productOpts = [];
        $scope.getproductOpts = function getproductOpts() {
            var self = this;
            $http.get('/products/showall').
            then(
                function (response) {
                    self.productOpts = response.data;

                }
            )
        };*/

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

                for(var i=0 ; i < $scope.checkService.length; i++) {
                    if($scope.checkService[i].id == item.id){
                        if(!itemQuantity){
                            $scope.checkService.splice(i,1);
                        }else{
                            $scope.checkService[i].quantity = itemQuantity;
                        }
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

            var toSave = '{"location":"'+fields.location.id+'", "myaircraft":"'+fields.myaircraft.id+'", "landing":"'+fields.estimateArrival+'"}';

            var prepareFlight = shopcartResource.prepareFlight({}, toSave);
            prepareFlight.$promise.
            then(
                function (data) {
                    console.log("Productos: " + data.toSource());
                    $scope.prepareFligthList = data;
                    LxNotificationService.info('Seleccione los servicio que desea adquirir');
                    $scope.LinearProgress = helperFunc.toogleStatus($scope.LinearProgress);
                    $scope.sendbutton = helperFunc.toogleStatus($scope.sendbutton);
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

            var toSave = '{  "location":"'+fields.location.id+'",  "myaircraft":14, "captain":1, "landing":"'+fields.estimateArrival+'",  "description":"'+fields.description+'",  "generated":"false",  "items": '+ angular.toJson($scope.checkService) +'}';

            var addToCart = shopcartResource.addItemCart({}, toSave);
            addToCart.$promise.
            then(
                function (data) {
                    console.log("cart: " + data.toSource());
                    //$rootScope.cart = data;
                    $rootScope.cart = shopcartResource.getCartUser();
                    LxNotificationService.info('Guardado en el Carro');
                    self.shopCartLinearProgress = helperFunc.toogleStatus(self.shopCartLinearProgress);
                    self.shopCartsendbutton = helperFunc.toogleStatus(self.shopCartsendbutton);
                    //$location.path("/dashboard");
                },function (data) {
                    console.log("Error!!" + data);
                    self.shopCartLinearProgress = helperFunc.toogleStatus(self.shopCartLinearProgress);
                    self.shopCartsendbutton = helperFunc.toogleStatus(self.shopCartsendbutton);
                }
            )
        };

        /****************** addShopCart ******************/

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