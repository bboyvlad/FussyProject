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
            controller: 'ManageUserController',
            resolve:{
                "check":function($location, helperFunc, LxNotificationService){
                    if(helperFunc.checkauth(true, ['ROLE_ADMIN', 'ROLE_SYSADMIN'])==false){
                        //$event.preventDefault();
                        $location.path("/401");
                        LxNotificationService.error('Get in contact with the System Administartor!!!');
                    }
                }
            }
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
        })
        .
        when('/dashboard/cardstatus', {
            templateUrl: 'partials/dashboard/cardcheck/cardcheck.html',
            controller: 'CardCheckController',
            resolve:{
                "check":function($location, helperFunc, LxNotificationService){
                    if(helperFunc.checkauth(true, ['ROLE_PROVIDER', 'ROLE_ADMIN', 'ROLE_SYSADMIN'])==false){
                        //$event.preventDefault();
                        $location.path("/401");
                        LxNotificationService.error('Get in contact with the System Administartor!!!');
                    }
                }
            }
        }).
        when('/dashboard/paymentmethod-form', {
            templateUrl: 'partials/dashboard/paymethod/paymethodform.html',
            controller: 'PayMethodController',
            resolve:{
                "check":function($location, helperFunc, LxNotificationService){
                    if(helperFunc.checkauth(true, ['ROLE_ADMIN', 'ROLE_SYSADMIN'])==false){
                        //$event.preventDefault();
                        $location.path("/401");
                        LxNotificationService.error('Get in contact with the System Administartor!!!');
                    }
                }
            }
        }).
        when('/dashboard/sendpayment', {
            templateUrl: 'partials/dashboard/notifypayment/notifypaymentform.html',
            controller: 'NotifyPayController',
            resolve:{
                "check":function($location, helperFunc, LxNotificationService){
                    if(helperFunc.checkauth(true, ['ROLE_ADMIN', 'ROLE_SYSADMIN'])==false){
                        //$event.preventDefault();
                        $location.path("/401");
                        LxNotificationService.error('Get in contact with the System Administartor!!!');
                    }
                }
            }
        }).
        when('/dashboard/showreceived', {
            templateUrl: 'partials/dashboard/checkpayment/checkpaymentform.html',
            controller: 'CheckPayController',
            resolve:{
                "check":function($location, helperFunc, LxNotificationService){
                    if(helperFunc.checkauth(true, ['ROLE_ADMIN', 'ROLE_SYSADMIN'])==false){
                        //$event.preventDefault();
                        $location.path("/401");
                        LxNotificationService.error('Get in contact with the System Administartor!!!');
                    }
                }
            }
        }).
        when('/dashboard/bankmanage', {
            templateUrl: 'partials/dashboard/bankmanage/bankaccount.html',
            controller: 'BankAccountController',
            resolve:{
                "check":function($location, helperFunc, LxNotificationService){
                    if(helperFunc.checkauth(true, ['ROLE_ADMIN', 'ROLE_SYSADMIN'])==false){
                        //$event.preventDefault();
                        $location.path("/401");
                        LxNotificationService.error('Get in contact with the System Administartor!!!');
                    }
                }
            }
        }).
        when('/dashboard/balance_details', {
            templateUrl: 'partials/dashboard/balance/balance.html',
            controller: 'BalanceDetailsController',
            resolve:{
                "check":function($location, helperFunc, LxNotificationService){
                    if(helperFunc.checkauth(true, ['ROLE_ADMIN', 'ROLE_SYSADMIN'])==false){
                        //$event.preventDefault();
                        $location.path("/401");
                        LxNotificationService.error('Get in contact with the System Administartor!!!');
                    }
                }
            }
        }).
        when('/dashboard/admin_pending_payments', {
            templateUrl: 'partials/dashboard/admin_pending/admin_pendingform.html',
            controller: 'AdminDeferedPayController',
            resolve:{
                "check":function($location, helperFunc, LxNotificationService){
                    if(helperFunc.checkauth(true, ['ROLE_ADMIN', 'ROLE_SYSADMIN'])==false){
                        //$event.preventDefault();
                        $location.path("/401");
                        LxNotificationService.error('Get in contact with the System Administartor!!!');
                    }
                }
            }
        }).
        when('/dashboard/defered_payments', {
            templateUrl: 'partials/dashboard/deferedpayment/deferedpaymentform.html',
            controller: 'DeferedPayController',
            resolve:{
                "check":function($location, helperFunc, LxNotificationService){
                    if(helperFunc.checkauth(true, ['ROLE_ADMIN', 'ROLE_SYSADMIN'])==false){
                        //$event.preventDefault();
                        $location.path("/401");
                        LxNotificationService.error('Get in contact with the System Administartor!!!');
                    }
                }
            }
        }).
        when('/dashboard/buy/jdcard', {
            templateUrl: 'partials/dashboard/jdcard/addjdcard.html',
            controller: 'AddJdCardController',
            resolve:{
                "check":function($location, helperFunc, LxNotificationService){
                    if(helperFunc.checkauth(true, ['ROLE_ADMIN', 'ROLE_SYSADMIN'])==false){
                        //$event.preventDefault();
                        $location.path("/401");
                        LxNotificationService.error('Get in contact with the System Administartor!!!');
                    }
                }
            }
        }).
        when('/dashboard/refill/jdcard', {
            templateUrl: 'partials/dashboard/jdcard/refilljdcard.html',
            controller: 'RefillJdCardController',
            resolve:{
                "check":function($location, helperFunc, LxNotificationService){
                    if(helperFunc.checkauth(true, ['ROLE_ADMIN', 'ROLE_SYSADMIN'])==false){
                        //$event.preventDefault();
                        $location.path("/401");
                        LxNotificationService.error('Get in contact with the System Administartor!!!');
                    }
                }
            }
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
            controller: 'AddCartController',
            resolve:{
                "check":function($location, helperFunc, LxNotificationService){
                    if(helperFunc.checkauth(true, ['ROLE_ADMIN', 'ROLE_SYSADMIN'])==false){
                        //$event.preventDefault();
                        $location.path("/401");
                        LxNotificationService.error('Get in contact with the System Administartor!!!');
                    }
                }
            }
        }).
        when('/dashboard/giftcard/buy', {
            templateUrl: 'partials/dashboard/giftcard/addgiftjdcard.html',
            controller: 'AddGiftJdCardController',
            resolve:{
                "check":function($location, helperFunc, LxNotificationService){
                    if(helperFunc.checkauth(true, ['ROLE_ADMIN', 'ROLE_SYSADMIN'])==false){
                        //$event.preventDefault();
                        $location.path("/401");
                        LxNotificationService.error('Get in contact with the System Administartor!!!');
                    }
                }
            }

        }).
        when('/dashboard/giftcard/redeem', {
            templateUrl: 'partials/dashboard/giftcard/redeemgiftjdcard.html',
            controller: 'RedeemGiftJdCardController',
            resolve:{
                "check":function($location, helperFunc, LxNotificationService){
                    if(helperFunc.checkauth(true, ['ROLE_ADMIN', 'ROLE_SYSADMIN'])==false){
                        //$event.preventDefault();
                        $location.path("/401");
                        LxNotificationService.error('Get in contact with the System Administartor!!!');
                    }
                }
            }
        }).
        when('/dashboard/aircraft/manage', {
            templateUrl: 'partials/dashboard/aircraft/myaircrafts.html',
            controller: 'MyAirCraftsController',
            resolve:{
                "check":function($location, helperFunc, LxNotificationService){
                    if(helperFunc.checkauth(true, ['ROLE_ADMIN', 'ROLE_SYSADMIN'])==false){
                        //$event.preventDefault();
                        $location.path("/401");
                        LxNotificationService.error('Get in contact with the System Administartor!!!');
                    }
                }
            }
        }).
        when('/dashboard/captain/manage', {
            templateUrl: 'partials/dashboard/captain/captain.html',
            controller: 'MyCaptainController',
            resolve:{
                "check":function($location, helperFunc, LxNotificationService){
                    if(helperFunc.checkauth(true, ['ROLE_ADMIN', 'ROLE_SYSADMIN'])==false){
                        //$event.preventDefault();
                        $location.path("/401");
                        LxNotificationService.error('Get in contact with the System Administartor!!!');
                    }
                }
            }
        })

        /******** ERRORS *********/
            .
            when('/401', {
                templateUrl: 'partials/errors/401.html'
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