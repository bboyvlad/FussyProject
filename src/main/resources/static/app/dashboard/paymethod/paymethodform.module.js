/**
 * Created by bboyvlad on 9/8/16.
 */
'use strict';

var paymethod = angular.module('PayMethod', ['ngRoute', 'ngMessages', 'angularPayments']);

paymethod.controller('PayMethodController', ['$rootScope','$scope', '$http', '$location', 'LxDatePickerService', 'myJdMenu', 'helperFunc', 'LxNotificationService', 'LxDialogService', 'userPaymentResource', 'userResource', 'cardPaymentResource',
    function PayMethodController($rootScope, $scope, $http, $location, $LxDatePickerService, myJdMenu, helperFunc, LxNotificationService, LxDialogService, userPaymentResource, userResource, cardPaymentResource) {

        var self = this;
        $scope.sendbutton = false;
        $scope.LinearProgress = false;

        $scope.faddpaym = {};

        /***************** COORDINATE CARD ********************/
        $scope.userDetail = userResource.detailUser().$promise
        .then(
            function (data) {
                if(data.coordinates==null || data.coordinates.id==""){
                    //console.log(data.coordinates.id + " / " + data.coordinates.active);
                    LxNotificationService.confirm('Crear Tarjeta de Coordenadas', 'Para agregar un Metodo de pago debe tener una Tarjeta de Coordenadas.',
                        {
                            cancel: 'Cancelar',
                            ok: 'Crear'
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
                }else if(data.coordinates.active==false){
                    LxNotificationService.info('Usted ya creo su Tarjeta de Coordenadas, pero no la ha activado... en su correo tiene las instrucciones nesarias para su activación, ó puede generar una nueva desde el menu de usuario!...');
                }
            }
        );

        var randomString = function(type) {
            if(type==true){
                var text = "";
                var possible = "ABCDE";
                for(var i = 0; i < 1; i++) {
                    text += possible.charAt(Math.floor(Math.random() * possible.length));
                }
                return text;
            }else{

                var text = "";
                var possible = "12345";
                for(var i = 0; i < 1; i++) {
                    text += possible.charAt(Math.floor(Math.random() * possible.length));
                }
                return text;
            }
        }
        /*************  ASKING FOR CORDINATES **************/
        $scope.dialogCoordinates = "dialogCoordinates";
        $scope.openDialogCoordinates = function openDialogCoordinates()
        {
            LxDialogService.open(this.dialogCoordinates);

        }

        $scope.$on('lx-dialog__open-start', function(_event, _dialogId)
        {
            //var self = this;
            if ($scope.dialogCoordinates === _dialogId)
            {
                $scope.coord1 = {
                    key: randomString(true) + randomString(false)
                };
                $scope.coord2 = {
                    key: randomString(true) + randomString(false)
                };

            }
        });

        //console.log($scope.userDetail.toSource());
        /***************** COORDINATE CARD ********************/

        /***************** TO RESET FORMS ********************/
        $scope.master = {
            paytype: "", payacctnum: "", paycardname: "", payvalid: new Date()/*, paystatus: ""*/
        };
        $scope.reset = function() {
            $scope.faddpaym = angular.copy($scope.master);
            $scope.feditpaym = angular.copy($scope.master);
        };
        /***************** TO RESET FORMS ********************/
        //$scope.reset();

        $scope.regex = "[A-Z\\s]+";
        $scope.methodOpts = [
            {
                key: "CARD",
                name: "CARD"
            }
        ];

        $scope.datePicker = {
            input:
            {
                date: new Date(),
                dateFormatted: moment().locale('es').format('LL')
            }
        };


        /********** addpayMethod ********/
        $scope.payMethodCreate = function payMethodCreate($event, fields, id) {
            $event.preventDefault();
            var self = this;


            var coordinates = '{"crdOne":"'+$scope.coord1.key+'","valOne":"'+$scope.coord1.val+'","crdTwo":"'+$scope.coord2.key+'","valTwo":"'+$scope.coord2.val+'"}';
            userResource.checkCoordinates({}, coordinates).$promise.then(
                function (data) {
                    console.log(data.toSource());
                    if(data.message=="Autorized"){
                        LxDialogService.close($scope.dialogCoordinates);
                        $scope.LinearProgress = helperFunc.toogleStatus($scope.LinearProgress);
                        $scope.sendbutton = helperFunc.toogleStatus($scope.sendbutton);

                        var url = '/users/add/paymentmethod/'+ id;
                        var toSave = '{"paytype":"'+fields.paytype.key+'","payacctnum":"'+fields.payacctnum+'","paycardname":"'+fields.paycardname+'","payvalid":"'+fields.payvalid+'","paystatus":"'+fields.paystatus+'"}';
                        userPaymentResource.save({id: $rootScope.user.id}, toSave).$promise.
                        then(
                            function (data) {
                                console.log("Guardado!!" + data.toSource());
                                LxNotificationService.alert('Metodo de Pago Creado',
                                    "Su tarjeta ha sido agregada satisfactoriamente,\r\na sus metodos de pago...",
                                    'Ok',
                                    function(answer)
                                    {
                                        $scope.reset();
                                        self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                                        self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                                        $location.path("/dashboard");
                                    });
                            },function (data) {
                                self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                                self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                                console.log("Error!!" + data);
                            }
                        )

                    }else if(data.message=="Unautorized"){
                        window.alert(" Datos Invalidos!!! ");
                    }else if(data.message=="Coordinate card inactive"){
                        window.alert(" Aun no ha activado su tarjeta!!! ");
                    }
                }
            );


            /**/
        }
        /********** addpayMethod ********/

        /********** editpayMethod ********/
        $scope.listPayMethod = userPaymentResource.get();

        $scope.dialogPayMethod = "dialogPayMethod";
        $scope.feditpaym = {};

        /***************** TO RESET EDIT FORMS ********************/


        $scope.editReset = function(id) {
            $scope.editMaster = {
                id: id, paytype: "", payacctnum: "", paycardname: "", payvalid: new Date()/*, paystatus: ""*/
            };
            $scope.feditpaym = angular.copy($scope.editMaster);
        };
        /***************** TO RESET FORMS ********************/
        //$scope.reset();

        $scope.openDialogPayMethod = function openDialogPayMethod(ef_paym)
        {
            LxDialogService.open(this.dialogPayMethod);
            console.log(ef_paym.payid );
            /***************** TO RECALL DATA ON EDIT FORMS ********************/
            $scope.editmaster = {
                id: ef_paym.payid, paytype: ef_paym.paytype, payacctnum: ef_paym.payacctnum, paycardname: ef_paym.paycardname, payvalid: new Date(ef_paym.payvalid)
            };
            $scope.feditpaym = angular.copy($scope.editmaster);
            /***************** TO RECALL DATA ON EDIT FORMS ********************/

        }

        $scope.$on('lx-dialog__open-start', function(_event, _dialogId)
        {
            //var self = this;
            if ($scope.dialogPayMethod === _dialogId)
            {

            }
        });

        $scope.updatePayMethod = function updatepayMethod($event, fields) {
            $event.preventDefault();
            var self = this;

            console.log(fields.id+ ' / ' + fields);
            this.LinearProgress = helperFunc.toogleStatus(this.LinearProgress);
            this.sendbutton = helperFunc.toogleStatus(this.sendbutton);

            cardPaymentResource.update({pay_id: fields.id}, fields).$promise.
            then(
                function (data) {
                    console.log("Actualizado!!" + data);
                    LxNotificationService.success('Actualización realizada!!!');
                    $scope.listPayMethod = userPaymentResource.get();
                    $scope.reset();
                    //$location.path("/dashboard");
                    LxDialogService.close(self.dialogPayMethod);
                    self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                    self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                },function (data) {
                    self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                    self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                    console.log("Error!!" + data);
                }
            );
        }

        /********** editpayMethod ********/

        /********** deletePayMethod ********/

        $scope.deletePayMethod = function deletePayMethod(data) {
            //$event.preventDefault();
            var self = this;
            this.LinearProgress = helperFunc.toogleStatus(this.LinearProgress);
            this.sendbutton = helperFunc.toogleStatus(this.sendbutton);
            console.log(data.toSource());
            LxNotificationService.confirm('Eliminar Metodo de Pago', 'Por favor confirme que desea eliminar esta Metodo de Pago.',
                {
                    cancel: 'Cancelar',
                    ok: 'Eliminar'
                }, function(answer)
                {
                    if (answer)
                    {
                        userPaymentResource.delete({id: data.id}).$promise.
                        then(
                            function (data) {
                                console.log("Borrado!!" + data);
                                LxNotificationService.success('Metodo de Pago, Eliminado satisfactoriamente!!!');
                                $scope.listPayMethod = userPaymentResource.get();
                                $scope.faircrafts = [];
                                //$location.path("/dashboard");
                                self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                                self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                            },function (data) {
                                self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                                self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                                console.log("Error!!" + data.toSource());
                            }
                        );
                    }
                    else
                    {
                        LxNotificationService.error('Disagree');
                        this.LinearProgress = helperFunc.toogleStatus(this.LinearProgress);
                        this.sendbutton = helperFunc.toogleStatus(this.sendbutton);
                    }
                });

        }
        /********** deletePayMethod ********/


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