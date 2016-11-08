/**
 * Created by bboyvlad on 9/7/16.
 */
'use strict';

angular.module('jdApp').
config(['$locationProvider', '$routeProvider', '$httpProvider',
    function config($locationProvider, $routeProvider, $httpProvider){
        //$locationProvider.hashPrefix('!');

        /*To Allow Spring Security responds to do a "WWW-Authenticate" header in a 401 response NOT
        SHOWING an authenticatino pop-up */
        $locationProvider.html5Mode(true);
        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';


        $routeProvider.
        when('/', {
            templateUrl: 'partials/index.html',
            controller: 'IndexController'/*,
            controllerAs: 'vm'*/
        }).
        when('/users/sing-up', {
            templateUrl: 'partials/users/singup.html',
            controller: 'singupController'
        })/*.
        when('/users/sms-email', {
            templateUrl: 'partials/users/smsemail.html',
            controller: 'smsController',
            controllerAs: 'vm'
        })*/.
        when('/loginpage', {
            templateUrl: 'partials/users/login.html',
            controller: 'LoginController'/*,
            controllerAs: 'vm'*/
        }).
        when('/users/admin', {
            templateUrl: 'partials/users/manage/admin_users.html',
            controller: 'ManageUserController'
        })
        /*.
        when('/users/account-status', {
            templateUrl: 'partials/users/accountstatus.html',
            controller: 'AccountStatusController',
            controllerAs: 'vm'
        }).
        /*when('/users/showall', {

        })*/
        .
        when('/dashboard', {
            templateUrl: 'partials/dashboard/main.html',
            controller: 'DashController'/*,
            controllerAs: 'vm'*/
        }).
        when('/dashboard/paymentmethod-form', {
            templateUrl: 'partials/dashboard/paymethod/paymethodform.html',
            controller: 'PayMethodController'
        }).
        when('/dashboard/buy/jdcard', {
            templateUrl: 'partials/dashboard/jdcard/addjdcard.html',
            controller: 'AddJdCardController'
        }).
        when('/dashboard/refill/jdcard', {
            templateUrl: 'partials/dashboard/jdcard/refilljdcard.html',
            controller: 'RefillJdCardController'
        }).
        when('/dashboard/groupserv/add', {
            templateUrl: 'partials/generaldef/groupserv/addgroupserv.html',
            controller: 'AddGrpServController'
        }).
        when('/dashboard/products/add', {
            templateUrl: 'partials/generaldef/product/index.html',
            controller: 'AddProductController'
        }).
        when('/dashboard/cart', {
            templateUrl: 'partials/dashboard/cart/cartForm.html',
            controller: 'AddCartController'
        }).
        when('/dashboard/giftcard/buy', {
            templateUrl: 'partials/dashboard/giftcard/addgiftjdcard.html',
            controller: 'AddGiftJdCardController'

        }).
        when('/dashboard/giftcard/redeem', {
            templateUrl: 'partials/dashboard/giftcard/redeemgiftjdcard.html',
            controller: 'RedeemGiftJdCardController'
        }).
        when('/dashboard/aircraft/manage', {
            templateUrl: 'partials/dashboard/aircraft/myaircrafts.html',
            controller: 'MyAirCraftsController'
        }).
        when('/dashboard/captain/manage', {
            templateUrl: 'partials/dashboard/captain/captain.html',
            controller: 'MyCaptainController'
        })


            .otherwise({ redirectTo: '/' });



    }
])/*.run( function($rootScope, $location) {

    // register listener to watch route changes
    $rootScope.$on( "$routeChangeStart", function(event, next, current) {
        console.log("next: "+ next.toSource() +" user: "+angular.isDefined($rootScope.user) +" and user: "+ $rootScope.user.toSource()  );
        if ( angular.isDefined($rootScope.user) && !$rootScope.user=='' ) {
            // no logged user, we should be going to #login
            $location.path( next.$$route.originalPath );
        }else {
            console.log("intento de ir a: "+ next.$$route.templateUrl.toSource() );
            if (
                next.$$route.templateUrl == "partials/index.html" ||
                next.$$route.templateUrl == "partials/users/login.html"||
                next.$$route.templateUrl == "partials/users/singup.html") {
                // already going to #login, no redirect needed
                 //console.log("continuo a: "+ next.$$route.originalPath.toSource() );
                $location.path( next.$$route.originalPath );
            } else {
                // not going to #login, we should redirect now
                $location.path( "/users/log-in" );
            }

        }
    });
})*/;