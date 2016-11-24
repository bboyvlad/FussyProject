/**
 * Created by bboyvlad on 9/8/16.
 */
var menu = angular.module('jdmenu', []);
menu.factory('myJdMenu', function() {
    //var myJdMenu = {};
    var myJdMenu = {
        "usermenu":[
            {
                "link":"/users/sing-up",
                "text":"Sing Up"
            },
            {
                "link":"/loginpage",
                "text":"Log In"
            }
        ],
        "useradmin":[
            {
                "link":"/users/admin",
                "text":"Manage Users"
            }
        ],
        "jdcard":[
            {
                "link":"/dashboard/buy/jdcard",
                "text":"Buy J&D Card"
            },
            {
                "link":"/dashboard/refill/jdcard",
                "text":"Refill J&D Card"
            }
        ],
        "giftcard":[
            {
                "link":"/dashboard/giftcard/buy",
                "text":"Buy Gift Card"
            },
            {
                "link":"/dashboard/giftcard/redeem",
                "text":"Redeem Gift Card"
            }
        ],
        "payments":[
            {
                "link":"/dashboard/paymentmethod-form",
                "text":"Add Payment Method"
            },
            {
                "link":"/dashboard/sendpayment",
                "text":"Notify Deposit / Transfer"
            }
        ],
        "bankmanage":[
            {
                "link":"/dashboard/bankmanage",
                "text":"Bank Account Manage"
            },
            {
                "link":"/dashboard/showreceived",
                "text":"Check Payment"
            }
        ],
        "defgen":[
            /*{
                "link":"/dashboard/groupserv/add",
                "text":"Group Service"
            },*/
            {
                "link":"/dashboard/products/add",
                "text":"Products"
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
                "text":"Pilots"
            }
        ],
        "cardvalidate":[
            {
                "link":"/dashboard/cardstatus",
                "text":"Validator"
            }
        ],
        "balance":[
            {
                "link":"/dashboard/balance_details",
                "text":"Balance Details"
            }
        ],
        "servrequest":[
            {
                "link":"/dashboard/defered_payments",
                "text":"Pending Service Requests"
            },
            {
                "link":"/dashboard/admin_pending_payments",
                "text":"Admin Pending Requests"
            }
        ],
        "mainmenu":{
            "main":[
                {
                    "link":"/",
                    "text":"Home"
                },
                /*{
                    "link":"/",
                    "text":"Services"
                },
                {
                    "link":"/",
                    "text":"Products"
                },
                {
                    "link":"/",
                    "text":"Promotions"
                },*/
                {
                    "link":"/",
                    "text":"Contact"
                }
            ]
        }
    };


    myJdMenu.userSection = function(menuOpt){
        myJdMenu.usermenu = menuOpt;
    };
    myJdMenu.userAdminSection = function(menuOpt){
        myJdMenu.useradmin = menuOpt;
    };
    myJdMenu.mainSection = function(menuOpt){
        myJdMenu.mainmenu = menuOpt;
    };
    myJdMenu.jdcardSection = function(menuOpt){
        myJdMenu.jdcard = menuOpt;
    };
    myJdMenu.giftcardSection = function(menuOpt){
        myJdMenu.giftcard = menuOpt;
    };
    myJdMenu.paymentsSection = function(menuOpt){
        myJdMenu.payments = menuOpt;
    };
    myJdMenu.bankmanageSection = function(menuOpt){
        myJdMenu.bankmanage = menuOpt;
    };
    myJdMenu.defgenSection = function(menuOpt){
        myJdMenu.defgen = menuOpt;
    };
    myJdMenu.aircraftSection = function(menuOpt){
        myJdMenu.aircraft = menuOpt;
    };
    myJdMenu.captainSection = function(menuOpt){
        myJdMenu.captain = menuOpt;
    };
    myJdMenu.cardvalidateSection = function(menuOpt){
        myJdMenu.cardvalidate = menuOpt;
    };
    myJdMenu.balanceSection = function(menuOpt){
        myJdMenu.balance = menuOpt;
    };
    myJdMenu.servrequestSection = function(menuOpt){
        myJdMenu.servrequest = menuOpt;
    };

    return myJdMenu;
});
menu.controller('MyJdMenuController', [ '$scope', '$rootScope', '$filter', 'myJdMenu', '$http', 'shopcartResource', 'userResource', '$location', 'LxNotificationService', 'LxDialogService', 'userPaymentResource', 'helperFunc', '$translate', function($scope, $rootScope, $filter, myJdMenu, $http, shopcartResource, userResource, $location, LxNotificationService, LxDialogService, userPaymentResource, helperFunc, $translate) {
    var self = this;
    $scope.sharedMenu = myJdMenu;
    //console.log("menu: "+$scope.sharedMenu.toSource());
    $scope.search ='';
    $scope.productlist = [];
    $scope.jdcardOpts = [];

    $scope.sendbutton = false;
    $scope.LinearProgress = false;

    /*$rootScope.$on('rootScope:emit', function (event, data) {
        console.log(data); // 'Emit!'
        $scope.updateMenu();
    });*/

    $scope.changeLanguage = function changeLanguage(_newValue) {
        //console.log(_newValue);
        $translate.use(_newValue);
    };

    $scope.checkRole = function checkRole(roles) {
        helperFunc.hasRole(roles);
    };

    /* check user for credentials on page refresh */
    var loggedUser=userResource.loggedUser();
    loggedUser.$promise.then(function(data) {
        //console.log("in data: " + data.toSource());
        if (!angular.isDefined(data.principal)) {
            $location.path("/");
        } else {
            /* retrieve shop cart, Principal and User details */
            $rootScope.cart = shopcartResource.getCartUser();
            $rootScope.user = data;
            var detailUser=userResource.detailUser();
            detailUser.$promise.then( function (data) {
                //console.log("data userDetails"+data);
                $rootScope.userDetail = data;
            });
            //$location.path("/dashboard");
        }
    });

    $scope.logout = function() {
        $http.post('/logout', {}).then(function() {
            $rootScope.cart = {};
            $rootScope.user = {};
            $rootScope.userDetail = {};
            $location.path("/");
        });
    }

    $scope.newCoordinates = function newCoordinates() {
        LxNotificationService.confirm('Generar Tarjeta de Coordenadas', 'Necesita una Tarjeta de Coordenadas nueva???.',
            {
                cancel: 'Cancelar',
                ok: 'Aceptar'
            }, function(answer)
            {
                if (answer)
                {
                    window.open('http://jdoilfield.net:8080/users/setcoordinates', '_blank');

                    LxNotificationService.success('Tarjeta creada satisfactoriamente!!!.. en su correo tiene las instrucciones nesarias para su activación');
                }
                else
                {
                    LxNotificationService.error('Operación Cancelada');
                }
            });
    }
    $scope.orderTotal = function orderTotal() {
        $scope.total = 0;
        //console.log("items" + $scope.sCart.items.toSource());
        angular.forEach($scope.sCart.items, function(value){
            //console.log("value" + value.toSource());
            $scope.total= value.totalprice + $scope.total;
            //console.log("total" + $scope.total);
        });
    };


    /* OpenDialogProduct */
    $scope.shopCartDialogId = "shopCartDialogId";
    $scope.shopCartDialog = function shopCartDialog(shopCart)
    {
        $body.removeClass('sidebar_secondary_active');
        //console.log("shopCart" + shopCart.toSource());
        $scope.sCart = shopCart;
        $scope.orderTotal();
        LxDialogService.open($scope.shopCartDialogId);
    };

    /* checkoutDialog */
    $scope.checkoutDialogId = "checkoutDialogId";
    $scope.checkoutDialog = function checkoutDialog(shopCart)
    {
        $body.removeClass('sidebar_secondary_active');
        //console.log("shopCart" + shopCart.toSource());
        $scope.sCart = shopCart;
        $scope.orderTotal();

            userPaymentResource.get().$promise.
            then(
                function (data) {
                    /*$scope.paymethod = response.data;*/
                    angular.forEach(data, function (key) {
                        //alert(key.paytype +':'+val);
                        if(key.paytype=="JDCARD"){
                            $scope.jdcardOpts.push(key);
                        }/*else{
                         self.paymethod.push(key);
                         }*/
                    });
                }
            )

        LxDialogService.open($scope.checkoutDialogId);
    };

    /*$scope.calculateBalance = function calculateBalance() {
        //console.log("payments: "+ $rootScope.userDetail.toSource());
        var detailUser=userResource.detailUser();
        detailUser.$promise.then( function (data) {
            //console.log("data userDetails"+data);
            $rootScope.userDetail = data;
            $scope.items = $filter('orderBy')($rootScope.userDetail.payments, "payid");
            var payments = $scope.items;
            //console.log("data payments"+payments.toSource());
            angular.forEach(payments, function (value, key) {
                console.log(value.payavailable);
                $rootScope.mainPieChart.payavailable +=value.payavailable;
                $rootScope.mainPieChart.paylocked +=value.paylocked;
                $rootScope.mainPieChart.paybalance +=value.paybalance;

            })
            $rootScope.mainPieChart.labels = ["Available Balance", "Balance Blocked"];
            $rootScope.mainPieChart.data = [$rootScope.mainPieChart.payavailable, $rootScope.mainPieChart.paylocked];
            console.log("TOTAL" + $rootScope.mainPieChart.payavailable);
            console.log($rootScope.datePicker.toSource());
        });

    };*/


    $scope.fcheckout={}
    $scope.checkoutShopCart = function checkoutShopCart($event, fields) {
        $event.preventDefault();
        var self = this;
        this.LinearProgress = helperFunc.toogleStatus(this.LinearProgress);
        this.sendbutton = helperFunc.toogleStatus(this.sendbutton);
        console.log($scope.fcheckout.toSource());
        var toSave = '{ ' +
            '"shopcart_id":"'+$scope.sCart.id+'", ' +
            '"paymethod_id":"'+$scope.fcheckout.jdcard.payid+'" ' +
            '}';

        console.log(toSave.toSource());
        var checkoutShopCart = shopcartResource.checkOut({ paymethod_id: $scope.fcheckout.jdcard.payid, shopcart_id: $scope.sCart.id});
        checkoutShopCart.$promise.
        then(
            function (data) {
                //console.log("Guardado!!" + data);
                if(data.message=="Saldo insuficiente"){
                    LxNotificationService.alert('Saldo insuficiente',
                        "Escoja otro metodo de pago o recargue su J&D Card para procesar este pago!",
                        'Ok',
                        function(answer)
                        {
                            self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                            self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                            return;
                        });
                };


                shopcartResource.hardDelete({shopcart_id: $scope.sCart.id}).$promise.
                then(
                    function (data) {
                        $rootScope.cart = shopcartResource.getCartUser();

                    },function (data) {
                        //console.log("Error!!" + data.toSource());
                    }
                );



                LxNotificationService.alert('Service Request',
                    "Su Service Request ha sido Generado satisfactoriamente...",
                    'Ok',
                    function(answer)
                    {
                        //$scope.reset();
                        helperFunc.jdCardBalance(null, true);
                        self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                        self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                        LxDialogService.close($scope.checkoutDialogId);
                        //$location.path("/dashboard");
                    });

            },function (data) {
                self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                console.log("Error!!" + data);
            }
        )
    };
    /*$scope.$watch('vm.search', function () {
        $scope.retrievelist();
    });

    $scope.retrievelist = function retrievelist() {
        if(self.search!=""){
            $http.post("/products/productbytag/" + self.search)
                .then(
                    function (response) {
                        console.log(response.data);
                        $scope.productlist= response.data;
                    }
                )
        }
    };*/

    /*$scope.deleteShopCart = function deleteShopCart(item) {
        $rootScope.cart.splice(item);
        if(self.search!=""){
            $http.post("/products/productbytag/" + self.search)
                .then(
                    function (response) {
                        console.log(response.data);
                        $scope.productlist= response.data;
                    }
                )
        }
    };*/
       /********** deleteItem ********/

    $scope.deleteItem = function deleteItem(cartId, itemId) {
        var self = this;

        console.log(cartId+"/"+itemId);
        LxNotificationService.confirm('Eliminar Item del ShopCart', 'Por favor confirme que desea eliminar este Item...',
            {
                cancel: 'Cancelar',
                ok: 'Eliminar'
            }, function(answer)
            {
                if (answer)
                {
                    //console.log("Borrado!!" + data.toSource());
                    shopcartResource.deleteCartItem({shopcart_id: cartId, itemcartid: itemId}).$promise.
                    then(
                        function (data) {
                            //console.log("Borrado!!" + data);
                            $scope.sCart.items.splice(itemId);
                            LxDialogService.close($scope.shopCartDialogId);
                            LxNotificationService.success('Item, Eliminado satisfactoriamente!!!');
                            //$rootScope.cart = shopcartResource.getCartUser();

                        },function (data) {
                            //console.log("Error!!" + data.toSource());
                        }
                    );
                }
                else
                {
                    LxNotificationService.error('Operación Cancelada');
                }
            });

    }
    /********** deleteItem ********/

    /********** deleteShopCart ********/

    $scope.deleteShopCart = function deleteShopCart(data) {
        var self = this;

        console.log(data.toSource());
        LxNotificationService.confirm('Eliminar ShopCart', 'Por favor confirme que desea eliminar esta Orden Completa...',
            {
                cancel: 'Cancelar',
                ok: 'Eliminar'
            }, function(answer)
            {
                if (answer)
                {
                    console.log("Borrado!!" + data.toSource());
                    shopcartResource.hardDelete({shopcart_id: data.id}).$promise.
                    then(
                        function (data) {
                            //console.log("Borrado!!" + data);
                            LxNotificationService.success('Carro, Eliminado satisfactoriamente!!!');
                            $rootScope.cart = shopcartResource.getCartUser();

                        },function (data) {
                            //console.log("Error!!" + data.toSource());
                        }
                    );
                }
                else
                {
                    LxNotificationService.error('Operación Cancelada');
                }
            });

    }
    /********** deleteShopCart ********/

    //$scope.sharedMenu = myJdMenu;

    $scope.updateMenu = function updateMenu() {
        //alert(this.Opts.item1);
        console.log(myJdMenu.toSource());
        myJdMenu.userSection(myJdMenu.usermenu);
        myJdMenu.userAdminSection(myJdMenu.useradmin);
        myJdMenu.mainSection(myJdMenu.mainmenu);
        myJdMenu.jdcardSection(myJdMenu.jdcard);
        myJdMenu.giftcardSection(myJdMenu.giftcard);
        myJdMenu.paymentsSection(myJdMenu.payments);
        myJdMenu.bankmanageSection = (myJdMenu.bankmanage);
        myJdMenu.defgenSection(myJdMenu.defgen);
        myJdMenu.aircraftSection(myJdMenu.aircraft);
        myJdMenu.captainSection(myJdMenu.captain);
        myJdMenu.cardvalidateSection(myJdMenu.cardvalidate);
        myJdMenu.balanceSection(myJdMenu.balance);
        myJdMenu.servrequestSection(myJdMenu.servrequest);
    };
}]);