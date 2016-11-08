/**
 * Created by bboyvlad on 9/8/16.
 */
var menu = angular.module('jdmenu', []);
menu.factory('myJdMenu', function() {
    var myJdMenu = {};
    myJdMenu = {
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
    myJdMenu.defgenSection = function(menuOpt){
        myJdMenu.defgen = menuOpt;
    };
    myJdMenu.aircraftSection = function(menuOpt){
        myJdMenu.aircraft = menuOpt;
    };
    myJdMenu.captainSection = function(menuOpt){
        myJdMenu.captain = menuOpt;
    };

    return myJdMenu;
});
menu.controller('MyJdMenuController', [ '$scope', '$rootScope', 'myJdMenu', '$http', 'shopcartResource', 'userResource', '$location', 'LxNotificationService', 'LxDialogService', 'userPaymentResource', 'helperFunc', function($scope, $rootScope, myJdMenu, $http, shopcartResource, userResource, $location, LxNotificationService, LxDialogService, userPaymentResource, helperFunc) {
    var self = this;
    $scope.sharedMenu = myJdMenu;
    //console.log("menu: "+$scope.sharedMenu.toSource());
    $scope.search ='';
    $scope.productlist = [];

    $scope.sendbutton = false;
    $scope.LinearProgress = false;

    var loggedUser=userResource.loggedUser();
    loggedUser.$promise.then(function(data) {
        console.log("in data: " + data.toSource());
        if (!angular.isDefined(data.principal)) {
            $location.path("/");
        } else {
            $rootScope.cart = shopcartResource.getCartUser();
            $rootScope.user = data;
            //$location.path("/dashboard");
        }
    });
    //$rootScope.cart= shopcartResource.getCartUser();


    $scope.logout = function() {
        $http.post('/logout', {}).then(function() {
            //$rootScope.authenticated = false;
            $location.path("/");
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
        //$scope.orderTotal();

        $scope.jdcardOpts = [];
        /*$scope.getPayments = function getPayments(id) {
            var self = this;*/

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
        /*};*/

        LxDialogService.open($scope.checkoutDialogId);
    };

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
                console.log("Guardado!!" + data);
                LxDialogService.close($scope.checkoutDialogId);
                /*if (data.message == "conexionError"){
                    LxNotificationService.error('Transacción cancelada! Verifique su conexión a Internet!!!');
                    self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                    self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                    //self.helperFuncBar();
                    return;
                }*/

                LxNotificationService.alert('Service Request',
                    "Su Service Request ha sido Generado satisfactoriamente...",
                    'Ok',
                    function(answer)
                    {
                        $scope.reset();
                        self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                        self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                        //$location.path("/dashboard");
                    });

            },function (data) {
                self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                console.log("Guardado!!" + data);
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
    /********** deleteShopCart ********/

    $scope.deleteShopCart = function deleteShopCart(data) {
        //$event.preventDefault();
        var self = this;
       /* this.LinearProgress = helperFunc.toogleStatus(this.LinearProgress);
        this.sendbutton = helperFunc.toogleStatus(this.sendbutton);*/
        console.log(data.toSource());
        LxNotificationService.confirm('Eliminar ShopCart', 'Por favor confirme que desea eliminar esta Orden Completa.',
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
                            console.log("Borrado!!" + data);
                            LxNotificationService.success('Item, Eliminado satisfactoriamente!!!');
                            $rootScope.cart = shopcartResource.getCartUser();
                            /*$scope.faircrafts = [];
                             //$location.path("/dashboard");
                             self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                             self.sendbutton = helperFunc.toogleStatus(self.sendbutton);*/
                        },function (data) {
                            /*self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                             self.sendbutton = helperFunc.toogleStatus(self.sendbutton);*/
                            console.log("Error!!" + data.toSource());
                        }
                    );
                }
                else
                {
                    LxNotificationService.error('Disagree');
                    /*this.LinearProgress = helperFunc.toogleStatus(this.LinearProgress);
                    this.sendbutton = helperFunc.toogleStatus(this.sendbutton);*/
                }
            });

    }
    /********** deleteShopCart ********/

    //$scope.sharedMenu = myJdMenu;

    $scope.updateMenu = function updateMenu() {
        //alert(this.Opts.item1);
        //console.log(myJdMenu.jdcard);
        myJdMenu.userSection(myJdMenu.usermenu);
        myJdMenu.userAdminSection(myJdMenu.useradmin);
        myJdMenu.mainSection(myJdMenu.mainmenu);
        myJdMenu.jdcardSection(myJdMenu.jdcard);
        myJdMenu.giftcardSection(myJdMenu.giftcard);
        myJdMenu.paymentsSection(myJdMenu.payments);
        myJdMenu.defgenSection(myJdMenu.defgen);
        myJdMenu.aircraftSection(myJdMenu.aircraft);
        myJdMenu.captainSection(myJdMenu.captain);
    };
}]);