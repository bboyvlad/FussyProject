/**
 * Created by bboyvlad on 9/7/16.
 */
'use strict';

var user = angular.module('ManageUser', ['ngRoute', 'jdmenu', 'ngMessages']);

user.controller('ManageUserController', ['$rootScope','$scope', '$http', '$location', 'myJdMenu', 'LxNotificationService', 'helperFunc', 'userResource', 'LxDialogService',
    function ManageUserController($rootScope, $scope, $http, $location, myJdMenu, LxNotificationService, helperFunc, userResource, LxDialogService) {
        $scope.cssClass = 'manage';
        var self = this;

        $scope.fuser = {};
        $scope.search = { mysearch:'' };
        $scope.sendbutton = false;
        $scope.LinearProgress = false;

        /*$scope.resetSearch = function (Val) {
            if(Val===""){
                $scope.search = { mysearch:'Buscar Usuario' };
            }
        }*/

        /***************** TO RESET FORMS ********************/
        $scope.master = {
            name:"", lastname:"", email:"", role:""
        };
        $scope.reset = function() {
            $scope.fuser = angular.copy($scope.master);
            $scope.fuseredit = angular.copy($scope.master);
        };
        /***************** TO RESET FORMS ********************/

        $scope.profileOpts = [
            { key: "U", name: "USER" },
            { key: "A", name: "ADMIN" },
            { key: "S", name: "SYSADMIN" },
            { key: "P", name: "PROVIDER" },
            { key: "G", name: "GUEST" },
            { key: "M", name: "MANAGER" },
            { key: "O", name: "OPER" },
            { key: "PA", name: "PARTNER" },
            { key: "SU", name: "SUPER" }
        ];

        /********** addUser ********/
        $scope.userCreate = function userCreate($event, fields) {
            $event.preventDefault();
            var self = this;
            this.LinearProgress = helperFunc.toogleStatus(this.LinearProgress);
            this.sendbutton = helperFunc.toogleStatus(this.sendbutton);

            var userSave = userResource.singUp({}, fields);
            userSave.$promise.
            then(
                function (data) {
                    //console.log("Guardado!!" + data.toSource());
                    LxNotificationService.success('Usuario creado satisfactoriamente!!!');
                    $scope.listUser = userResource.query();
                    $scope.reset();
                    self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                    self.sendbutton = helperFunc.toogleStatus(self.sendbutton);

                }
            );

        };
        /********** addUser ********/


        /********** editUser ********/
        $scope.listUser = userResource.query();

        $scope.dialogUser = "dialogUser";
        $scope.fuseredit = {};

        /***************** TO RESET EDIT FORMS ********************/


        $scope.editReset = function(id) {
            $scope.editMaster = {
                id: id, name:"", lastname:"", email:"", role:""
            };
            $scope.fuseredit = angular.copy($scope.editMaster);
        };
        /***************** TO RESET FORMS ********************/
        //$scope.reset();
        $scope.toEditRole = function toEditRole(data) {
            console.log(data);
            switch (data) {
                case "ROLE_USER":
                    return { key: "U", name: "USER" };
                    break;
                case "ROLE_ADMIN":
                    return { key: "A", name: "ADMIN" };
                    break;
                case "ROLE_SYSADMIN":
                    return { key: "S", name: "SYSADMIN" };
                    break;
                case "ROLE_PROVIDER":
                    return { key: "P", name: "PROVIDER" };
                    break;
                case "ROLE_GUEST":
                    return { key: "G", name: "GUEST" };
                    break;
                case "ROLE_MANAGER":
                    return { key: "M", name: "MANAGER" };
                    break;
                case "ROLE_OPER":
                    return { key: "O", name: "OPER" };
                    break;
                case "ROLE_PARTNER":
                    return { key: "PA", name: "PARTNER" };
                    break;
                case "ROLE_SUPER":
                    return { key: "SU", name: "SUPER" };
                    break;
                default:
                    return { key: "G", name: "GUEST" };
            }
        };

        $scope.openDialogUser = function openDialogUser(ef_user)
        {
            LxDialogService.open(this.dialogUser);
            //console.log("ef_user: "+ ef_user.roles[0].code );
            /***************** TO RECALL DATA ON EDIT FORMS ********************/

            $scope.editmaster = {
                id: ef_user.id, name:ef_user.name, lastname:ef_user.lastname, email:ef_user.email, role: $scope.toEditRole(ef_user.roles[0].code)
            };
            $scope.fuseredit = angular.copy($scope.editmaster);
            /***************** TO RECALL DATA ON EDIT FORMS ********************/

        }

        $scope.$on('lx-dialog__open-start', function(_event, _dialogId)
        {
            //var self = this;
            if ($scope.dialogUser === _dialogId)
            {

            }
        });

        $scope.updateUser = function updateUser($event, fields) {
            $event.preventDefault();
            var self = this;

            //console.log(fields[0].id+ ' / ' + fields[0]);
            this.LinearProgress = helperFunc.toogleStatus(this.LinearProgress);
            this.sendbutton = helperFunc.toogleStatus(this.sendbutton);
            var toSave = {
                name:fields.name, lastname:fields.lastname, password:fields.password, role: fields.role.key
            };

            userResource.manageUpdate({principalid: fields.id},  toSave).$promise.
            then(
                function (data) {
                    console.log("Actualizado!!" + data);
                    LxNotificationService.success('Actualización realizada!!!');
                    $scope.listUser = userResource.query();
                    $scope.reset();
                    //$location.path("/dashboard");
                    LxDialogService.close($scope.dialogUser);
                    self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                    self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                },function (data) {
                    self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                    self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                    console.log("Error!!" + data);
                }
            );
        }
        /********** editUser ********/

        /********** deleteUser ********/

        $scope.deleteUser = function deleteUser(data) {
            //$event.preventDefault();
            var self = this;
            this.LinearProgress = helperFunc.toogleStatus(this.LinearProgress);
            this.sendbutton = helperFunc.toogleStatus(this.sendbutton);
            //console.log(data.toSource());
            LxNotificationService.confirm('Eliminar Usuario', 'Por favor confirme que desea eliminar este Usuario.',
                {
                    cancel: 'Cancelar',
                    ok: 'Eliminar'
                }, function(answer)
                {
                    if (answer)
                    {
                        userResource.delete({id: data.id}).$promise.
                        then(
                            function (data) {
                                //console.log("Borrado!!" + data);
                                LxNotificationService.success('Usuario, Eliminado satisfactoriamente!!!');
                                $scope.listUser = userResource.query();
                                self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                                self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                            },function (data) {
                                self.LinearProgress = helperFunc.toogleStatus(self.LinearProgress);
                                self.sendbutton = helperFunc.toogleStatus(self.sendbutton);
                                //console.log("Error!!" + data.toSource());
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
        /********** deleteUser ********/


    }]);