'use strict'

moduleUsuario.controller('usuarioNewController', ['$scope', '$http', '$location', 'toolService', '$routeParams', '$window',
    function ($scope, $http, $location, toolService, $routeParams, $window) {

        $scope.totalPages = 1;
        $scope.btnNew = true;
      

        $scope.goBack = function () {
            $window.history.back();
        };

        $scope.nuevo = function () {
            $scope.btnNew = false;

            var json = {
                dni: $scope.dni,
                nombre: $scope.nombre,
                ape1: $scope.ape1,
                ape2: $scope.ape2,
                login: $scope.login,
                pass: $scope.pass,
                id_tipoUsuario: 2
            };

            $http({
                method: 'POST',
                url: '/json?ob=usuario&op=create',
                params: {json: JSON.stringify(json)}
            }).then(function (response) {
                $scope.status = response.status;
                $scope.ajaxDataUsuarios = response.data.message;
                $scope.resultado = "Creado con éxito";
            }, function (response) {
                $scope.status = response.status;
                $scope.ajaxDataUsuarios = response.data.message || 'Request failed';
                $scope.resultado = "No se pudo crear";
            });
        };

        $scope.isActive = toolService.isActive;



    }



]);





