/**
 * Created by bboyvlad on 9/7/16.
 */
'use strict';

var jdApp = angular.module('jdApp', [
    'ngAnimate',
    'ngRoute',
    'ngMessages',
    'ngResource',
    'jdmenu',
    'singup',
    'sms',
    'accountStatus',
    'login',
    'ManageUser',
    'dashboard',
    'PayMethod',
    'AddJdCard',
    'reFillJdCard',
    'AddGrpServ',
    'AddProduct',
    'AddCart',
    'AddGiftJdCard',
    'RedeemGiftJdCard',
    'MyAirCraft',
    'MyCaptain',
    'CardCheck',
    'BankAccount',
    'NotifyPay',
    'CheckPay',
    'BalanceDetails',
    'AdminDeferedPay',
    'DeferedPay',
    'lumx',
    'ui.utils.masks',
    'angularPayments',
    'ngCookies',
    'ngSanitize',
    'pascalprecht.translate',
    'chart.js'

]).run(function($rootScope) {
    $rootScope.user = [];
    //$rootScope.mainPieChart = {labels:[], data:[], payavailable: 0, paylocked:0, paybalance: 0 };
    $rootScope.dateP = { locale: 'es', format: 'YYYY-MM-DD' };
});

/******************************/
/* START SERVICES / FACTORIES */
/******************************/
jdApp.service('helperFunc', ['userResource', 'shopcartResource', 'cardPaymentResource', 'userPaymentResource', '$http', '$rootScope', '$location', '$filter', function (userResource, shopcartResource, cardPaymentResource, userPaymentResource, $http, $rootScope, $location, $filter) {

    this.toogleStatus = function (status) {
        /*console.log('recieved bar: '+status);*/
        status = !status;
        /*console.log('sent bar: '+status);*/
        return status;
    };
    this.mainSetter = function (value) {
        /*console.log('recieved bar: '+value);*/
        //value = !value;
        //console.log('recieved value: '+value.toSource());
        return value;
    };

    this.checkauth = function (tologin=false, roles=null) {
        var loggedUser=userResource.loggedUser();
        loggedUser.$promise.then(function(data) {
            //console.log("in data: " + data.toSource());
            if (!angular.isDefined(data.principal)) {
                if(tologin==true){
                    $location.path("/loginpage");
                    return;
                }
                $location.path("/");
            } else {
                if(roles.indexOf($rootScope.user.authorities[0].authority) !== -1) {
                    $rootScope.cart = shopcartResource.getCartUser();
                    $rootScope.user = data;
                    $rootScope.userDetail = userResource.detailUser();
                    return true;
                }
                return false;

                /* retrieve shop cart, Principal and User details */
            }
        });
    };

    this.dateToDB = function (value, dateFormat="MM/YYYY", unix=false) {
        //console.log('recieved bar: '+value);
        var newDate = "";
        if(unix==true){
            newDate = moment(value, dateFormat).format("x");
            //console.log('Unix to db: '+newDate);
            return newDate;
        }
        newDate = moment(value, dateFormat);
        //console.log('Date to db: '+newDate);
        return newDate;
    };

    this.dateFromDB = function (value, dateFormat="MM/YYYY") {
        //console.log('recieved bar: '+value);
        var dbDate = moment(value).format(dateFormat);
        //console.log('newDate: '+newDate);
        return dbDate;
    };

    this.pieChart = function () {
        $rootScope.mainPieChart = {labels:[], data:[], show: false };

            $rootScope.mainPieChart.labels = ["Available Balance", "Balance Blocked"];
            //$rootScope.mainPieChart.data = [Math.round($rootScope.mainPieChart.payavailable * 100) / 100, Math.round($rootScope.mainPieChart.paylocked * 100) / 100];
            $rootScope.mainPieChart.data = [$rootScope.totalJdCardBalance.payavailable.toFixed(2), $rootScope.totalJdCardBalance.paylocked.toFixed(2)];
            $rootScope.mainPieChart.show = true;

    }

    this.lineChart = function (data) {
        $rootScope.mainLineChart = {labels:[], series:[], payavailable: 0, paylocked:0, paybalance: 0, lineData:[], data:[  ], show: false };

            angular.forEach(data, function (value, key) {
                console.log(value.toSource());
                    console.log("valor es number?: "+angular.isNumber(value.tranamount));
                    if(angular.isNumber(value.tranamount)){
                        console.log("valor es 0?: "+value.tranamount);

                        if(value.tranamount!='0') {
                            console.log("valor es negative?: "+value.tranamount.toString().indexOf('-'));
                            var amount = 0;
                            if(value.tranamount.toString().indexOf('-') !== -1){
                                /************* NEGATIVE VALUE *************/
                                console.log($rootScope.mainLineChart.lineData[$rootScope.mainLineChart.lineData.length -1]);

                                if(angular.isUndefined($rootScope.mainLineChart.lineData[$rootScope.mainLineChart.lineData.length -1])){
                                    amount = value.tranamount;
                                }else{
                                    amount = $rootScope.mainLineChart.lineData[$rootScope.mainLineChart.lineData.length -1] + value.tranamount;
                                }
                                $rootScope.mainLineChart.lineData.push(amount);
                            }else{
                                /************* POSITIVE VALUE *************/
                                if(angular.isUndefined($rootScope.mainLineChart.lineData[$rootScope.mainLineChart.lineData.length -1])){
                                    amount = value.tranamount;
                                }else{
                                    amount = $rootScope.mainLineChart.lineData[$rootScope.mainLineChart.lineData.length -1] + value.tranamount;
                                }
                                $rootScope.mainLineChart.lineData.push(amount);
                            }
                            $rootScope.mainLineChart.labels.push($filter('dateFromDB')(value.trandate, "YYYY-MM-DD"));
                        }
                    }

            });
            $rootScope.mainLineChart.series = ["Available Balance"];
            $rootScope.mainLineChart.data.push($rootScope.mainLineChart.lineData);
            $rootScope.mainLineChart.show = true;

    };

    this.jdCardBalance = function (id=null, chart=false) {
        var self = this;
        if(id!=null){
            $rootScope.jdCardBalance = { payavailable: 0, paylocked:0, paybalance: 0 };
            cardPaymentResource.get({pay_id: id}).$promise.then(
                function (data) {
                    $rootScope.jdCardBalance.payavailable =data.payavailable;
                    $rootScope.jdCardBalance.paylocked =data.paylocked;
                    $rootScope.jdCardBalance.paybalance =data.paybalance;
                }
            );
            return;
        }

        $rootScope.totalJdCardBalance = { payavailable: 0, paylocked:0, paybalance: 0 };
        userPaymentResource.get().$promise.then(
            function (data) {
                //console.log("data paymentsDetails" + data.toSource());
                var items = $filter('orderBy')(data, "payid");
                //var payments = items;
                //console.log("data payments"+payments.toSource());
                angular.forEach(items, function (value, key) {
                    //console.log(value.payavailable);
                    $rootScope.totalJdCardBalance.payavailable += value.payavailable;
                    $rootScope.totalJdCardBalance.paylocked += value.paylocked;
                    $rootScope.totalJdCardBalance.paybalance += value.paybalance;

                });
                if(chart==true){
                    self.pieChart();
                }
            }
        );
    };

    /***************** TO AUTHENTICATE ********************/
    /*this.authenticate = function(credentials, callback) {
        console.log("in authenticate: "+ credentials);

        var headers = credentials ? {authorization : "Basic "
        + btoa(credentials.email + ":" + credentials.pass)
        } : {};

        console.log("in authenticate: "+headers.toSource());

        $http.get('/users/login', {headers : headers}).then(function(response) {
            console.log("in response: "+response.toSource());
            if (response.data.name) {
                $rootScope.authenticated = true;
            } else {
                $rootScope.authenticated = false;
            }
            callback && callback();
        }, function() {
            $rootScope.authenticated = false;
            callback && callback();
        });

    };*/
    /***************** TO AUTHENTICATE ********************/
}]);
jdApp.factory('userResource',  function ($resource) {
    return $resource('/users/manage/:id', {id: "@id"},{
        singUp:{
            method: 'POST',
            url: '/users/create',
            responseType: 'json'
        },
        logIn:{
            method: 'POST',
            url: '/loginpage',
            params:{username: "@username", password: "@password"},
            headers:{'Content-Type': 'application/x-www-form-urlencoded'}
        },
        checkEmail:{
            method: 'POST',
            url: '/users/checkmail/:email',
            params:{email: "@email"},
        },
        loggedUser:{
            method: 'GET',
            url: '/user'
        },
        detailUser:{
            method: 'GET',
            url: '/users/loggedUser'
        },
        manageUpdate:{
            method: 'PUT',
            url: '/users/manage/:principalid',
            params:{principalid: "@principalid"}
        },
        createCoordinates:{
            method: 'GET',
            url: '/users/setcoordinates',
            headers:{'Content-Type': 'application/octet-stream'},
            responseType:'arraybuffer'
        },
        activateCoordinates:{
            method: 'GET',
            url: '/users/coordinateactivate/:token',
            params:{ token: "@token" }
        },
        checkCoordinates:{
            method: 'POST',
            url: '/users/checkcoordinate'/*,
            params:{ token: "@token" }*/
        },
        myaircraft:{
            method: 'POST',
            url: '/users/myaircraft',
            isArray: true
        }
    });
});
jdApp.factory('jdCardResource',  function ($resource) {
    return $resource('/users/jdcard/', {},{
    });
});
jdApp.factory('userPaymentResource',  function ($resource) {
    return $resource('/users/paymethod/', {},{
        get:{
            url: '/users/paymentmethod/',
            method: 'GET',
            isArray: true
        },
        save:{
            method: 'POST'
        },
        update:{
            method: 'PATCH',
            url: '/users/paymethod/',
            isArray: true
        },
        delete:{
            method: 'DELETE',
            url: '/users/paymethod/:paymethod',
            params:{paymethod: "@paymethod"},
            isArray: true
        },
        datetransactionpay:{
            url: '/users/datetransactionpay/',
            method: 'POST',
            isArray: true
        }
    });
});
jdApp.factory('cardPaymentResource',  function ($resource) {
    return $resource('/payments/:pay_id', {pay_id: "@pay_id"},{
        refill:{
            method: 'POST',
            url: '/payments/:card_id/refill',
            params:{card_id: "@card_id"},
        }
    });
});
jdApp.factory('grpServResource',  function ($resource) {
    return $resource('/servgroup/:id', {id: "@id"},{
        showAll:{
            method: 'GET',
            url: '/servgroup/group/:groupid/product/',
            params:{groupid: "@groupid"},
            isArray: true
        },
        deleteProduct:{
            method: 'DELETE',
            url: '/servgroup/group/:groupid/:productid/',
            params:{groupid: "@groupid", productid: "@productid"}
        },
        update: {
            method:'PUT'/*,
             url: '/servgroup/:item',
             params: {item: "@item"}*/
        },
        productAdd: {
            method:'POST',
            url: '/servgroup/product/:groupid/',
            params: {groupid: "@groupid"}
        }
    });
});
jdApp.factory('productsResource',  function ($resource) {
    return $resource('/products/:id', {id: "@id"},{
        update: {
            method:'PUT'
        },
        showProductsGroup:{
            method: 'GET',
            url: '/products/group/:group_id/',
            params:{groupid: "@groupid"},
            isArray: true
        },
        addProductPrice:{
            method: 'POST',
            url: '/products/prices/:productid/',
            params:{ productid: "@productid"}
        },
        getProductPrices:{
            method: 'GET',
            url: '/products/prices/:product_id/',
            params:{ productid: "@productid"},
            isArray: true
        },
        updateProductPrice:{
            method: 'PUT',
            url: '/products/prices/:productid/',
            params:{ productid: "@productid"}
        },
        getProductByTag:{
            method: 'GET',
            url: '/products/productbytag/:tag/',
            params:{ tag: "@tag"},
            isArray: true
        },
    });
});
jdApp.factory('giftCardResource',  function ($resource) {
    return $resource('/giftcard/:id', {id: "@id"},{
        redeem:{
            method: 'POST',
            url: '/giftcard/apply'
        },
        save:{
            method: 'POST',
            url: '/giftcard/'
        }
    });
});
jdApp.factory('locationResource',  function ($resource) {
    return $resource('/location/:id', {id: "@id"},{
        airportByName:{
            method: 'GET',
            url: '/location/airport/:airportname/',
            params:{ airportname: "@airportname"}/*,
             responseType: 'json'*/,
            isArray: true
        }/*,
         airportById:{
         method: 'GET',
         url: '/location/airport/:airport_id/',
         params:{ airport_id: "@airport_id"}/!*,
         responseType: 'json'*!/,
         isArray: true
         }*/
    });
});
jdApp.factory('aircraftResource',  function ($resource) {
    return $resource('/aircraftype/:id', {id: "@id"},{
        aircraftById:{
            method: 'GET',
            url: '/aircraftype/aircraftbyid/:aircraftype_id/',
            params:{ aircraftype_id: "@aircraftype_id"}/*,
             responseType: 'json'*/,
            isArray: true
        },
        aircraftByName:{
            method: 'GET',
            url: '/aircraftype/aircraftbytag/:name',
            params:{ name: "@name"}/*,
             responseType: 'json'*/,
            isArray: true
        },
        usersAircraft:{
            method: 'GET',
            url: '/users/myaircraft/show',
            isArray: true
        },
        updateAircraft:{
            method: 'PATCH',
            url: '/users/myaircraft',
            isArray: true
        },
        deleteAircraft:{
            method: 'DELETE',
            url: '/users/myaircraft/:aircraftid',
             params:{ aircraftid: "@aircraftid"}/*,
             responseType: 'json',
             isArray: true*/
        }
    });
});
jdApp.factory('mycaptainResource',  function ($resource) {
    return $resource('/users/mycaptain/', {},{
        query:{
            url: '/users/mycaptain/show',
            isArray: true
        },
        retrieveMycaptain:{
            method: 'GET',
            url: '/users/mycaptain/:captainId/:principal',
            params:{ captainId: "@captainId", principal: "@principal"}/*,
             responseType: 'json'*/,
            isArray: true
        },
        update:{
            method: 'PATCH',
            url: '/users/mycaptain/'/*,
            params:{ principal: "@principal"},
             responseType: 'json',
             isArray: true*/
        },
        deleteMycaptain:{
            method: 'DELETE',
            url: '/users/mycaptain/:captainId',
            params:{ captainId: "@captainId"}/*,
             responseType: 'json',
             isArray: true*/
        },

    });
});
jdApp.factory('shopcartResource',  function ($resource) {
    return $resource('/users/shopcart/:id', {id: "@id"},{

        prepareFlight:{
            method: 'POST',
            url: '/users/prepareflight/'/*,
             params:{ principal: "@principal"},
             responseType: 'json'*/,
            isArray: true
        },
        addItemCart:{
            method: 'POST',
            url: '/users/shopcart/'/*,
             params:{ principal: "@principal"},
             responseType: 'json'*/,
            isArray: true
        },
        getCartUser:{
            method: 'GET',
            url: '/users/shopcart/show/'/*,
             params:{ principal: "@principal"},
             responseType: 'json'*/,
            isArray: true
        },
        checkOut:{
            method: 'POST',
            url: '/servicerequest/:paymethod_id/:shopcart_id',
            params:{ paymethod_id: "@paymethod_id", shopcart_id: "@shopcart_id"}/*,
             responseType: 'json',
            isArray: true*/
        },
        hardDelete:{
            method: 'DELETE',
            url: '/users/shopcart/:shopcart_id',
            params:{ shopcart_id: "@shopcart_id"}/*,
             responseType: 'json',
             isArray: true*/
        },
        deleteCartItem:{
            method: 'DELETE',
            url: '/users/shopcart/:shopcart_id/:itemcartid',
            params:{ shopcart_id: "@shopcart_id", itemcartid: "@itemcartid"}/*,
             responseType: 'json',
             isArray: true*/
        },

    });
});
jdApp.factory('cardValidateResource',  function ($resource) {
    return $resource('/card/:id', {id: "@id"},{
        cardStatus:{
            method: 'GET',
            url: '/card/status/:fuelCardCode/',
            params:{ fuelCardCode: "@fuelCardCode"}/*,
             responseType: 'json',
             isArray: true*/
        },
        cardBalance:{
            method: 'GET',
            url: '/card/balance/:fuelCardCode/',
            params:{ fuelCardCode: "@fuelCardCode"}/*,
             responseType: 'json',
             isArray: true*/
        }
    });
});
jdApp.factory('bankManageResource',  function ($resource) {
    return $resource('/bank/manage/:id', {id: "@id"},{
        update: {
            method:'PATCH',
             url: '/bank/manage/:id',
             params: {id: "@id"}
        },
        delete: {
            method:'DELETE',
            isArray: true
        },
        getAll:{
            method: 'GET',
            url: '/bank/manage/all',
            isArray: true
        },
        sendpayment:{
            method: 'POST',
            url: '/bank/sendpayment',
            isArray: true
        },
        checkpayment:{
            method: 'POST',
            url: '/bank/checkpayment',
            isArray: true
        },
        showreceived:{
            method: 'GET',
            url: '/bank/showreceived',
            isArray: true
        },
    });
});

jdApp.factory('serviceRequestResource',  function ($resource) {
    return $resource('/servicerequest/:paymethod_id/:shopcart_id', {paymethod_id: "@paymethod_id", shopcart_id: "@shopcart_id"},{
        prepareTicket: {
            method:'GET',
            url: '/servicerequest/manage/prepareticket/:servicerequest',
            params: {servicerequest: "@servicerequest"}
        },
        generateTicket: {
            method:'POST',
            url: '/servicerequest/manage/generateticket'
        },
        reverseServicesRequests: {
            method:'PATCH',
            url: '/servicerequest/reverse/:servicerequest',
            params: {servicerequest: "@servicerequest"},
            isArray: true
        },
        showServicesRequests: {
            method:'GET',
            url: '/servicerequest/manage/all'
        },
        showServicesRequestsPending: {
            method:'GET',
            url: '/servicerequest/manage/pending',
            isArray: true
        },
        showServicesRequestsOpen: {
            method:'GET',
            url: '/servicerequest/manage/open'
        },
        showServicesRequestsClose: {
            method:'GET',
            url: '/servicerequest/manage/close'
        },
        userServicesRequestsPending: {
            method:'GET',
            url: '/servicerequest/pending',
            isArray: true
        },
        ServicesRequestsFinalized: {
            method:'GET',
            url: '/servicerequest/finalized',
            isArray: true
        },
    });
});
/********************/
/* START DIRECTIVES  / FILTERS*/
/********************/
var capitalize = function() {
    return {
        require: 'ngModel',
        link: function(scope, element, attrs, modelCtrl) {
            var capitalize = function(inputValue) {
                if (inputValue == undefined) inputValue = '';
                var capitalized = inputValue.toUpperCase();
                if (capitalized !== inputValue) {
                    modelCtrl.$setViewValue(capitalized);
                    modelCtrl.$render();
                }
                return capitalized;
            }
            modelCtrl.$parsers.push(capitalize);
            capitalize(scope[attrs.ngModel]); // capitalize initial value
        }
    };
};

jdApp.directive("capitalize", capitalize);

/*TO COMPARE FIELDS VALUES*/
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

jdApp.directive("compareTo", compareTo);

jdApp.filter("tdcmask", function () {
    return function (value) {
        if(angular.isDefined(value)){

            value = value.slice(0, 4)+"********"+value.slice(12, 16);
            return value
        }
    }
});

jdApp.filter("dateFromDB", function () {
    return function (value, dateFormat="MM/YYYY") {
        //console.log('recieved bar: '+value);
        var dbDate = moment(value).format(dateFormat);
        //console.log('newDate: '+newDate);
        return dbDate;
    }
});

/********************/
/* START CONTROLLER */
/********************/
jdApp.controller('IndexController', ['$rootScope','$scope',
    function IndexController($rootScope, $scope) {
        $scope.cssClass = 'index';
        var vm = this;



        $rootScope.userDetail = {};


        //console.log(moment());
    }]);