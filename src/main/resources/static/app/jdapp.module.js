/**
 * Created by bboyvlad on 9/7/16.
 */
'use strict';

var jdApp = angular.module('jdApp', [
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
    'lumx',
    'ui.utils.masks',
    'angularPayments'

]).run(function($rootScope) {
    $rootScope.user = [];
});

/******************************/
/* START SERVICES / FACTORIES */
/******************************/
jdApp.service('helperFunc', ['userResource', '$http', '$rootScope', function (userResource, $http, $rootScope) {

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
        loggedUser:{
            method: 'GET',
            url: '/user'
        },
        detailUser:{
            method: 'GET',
            url: '/users/loggedUser'
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
    return $resource('/users/paymentmethod/', {},{
        get:{
            method: 'GET',
            isArray: true
        },
        save:{
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
            url: '/users/myaircraft/show'/*,
             params:{ name: "@name"},
             responseType: 'json'*/,
            isArray: true
        },
        updateAircraft:{
            method: 'PATCH',
            url: '/users/myaircraft'/*,
             params:{ name: "@name"},
             responseType: 'json',
            isArray: true*/
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
            url: '/users/mycaptain/show'/*,
            isArray: false*/
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
            url: '/users/mycaptain/:captainId/:principal',
            params:{ captainId: "@captainId", principal: "@principal"}/*,
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

    });
});
/********************/
/* START DIRECTIVES */
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
/********************/
/* START CONTROLLER */
/********************/
jdApp.controller('IndexController', ['$rootScope','$scope', '$http', '$location', 'LxDialogService', 'LxNotificationService', 'myJdMenu',
    function IndexController($rootScope, $scope, $http, $location, LxDialogService, LxNotificationService, myJdMenu) {
        var vm = this;

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
            "payments":false,
            "defgen":false,
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