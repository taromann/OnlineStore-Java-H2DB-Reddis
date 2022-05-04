angular.module('market-front').controller('cartController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:5555/cart/';

    $scope.loadCart = function () {
        $http({
            url: contextPath + 'api/v1/cart/' + $localStorage.springWebGuestCartId,
            method: 'GET'
        }).then(function (response) {
            $scope.cart = response.data;
        });
    };

    $scope.disabledCheckOut = function () {
        alert("Для оформления заказа необходимо войти в учетную запись");
    }

    $scope.clearCart = function () {
        $http.get(contextPath + 'api/v1/cart/' + $localStorage.springWebGuestCartId + '/clear')
            .then(function (response) {
                $scope.loadCart();
            });
    }

    $scope.decrementProduct = function (productId) {
        console.log($scope.selection);
        $http.get(contextPath + 'api/v1/cart/' + $localStorage.springWebGuestCartId + '/decrement/' + productId)
            .then(function (response) {
                $scope.loadCart();
            });
    }

    $scope.checkOut = function () {
        $scope.orderDetails.productIdSet =  $scope.selection; /*копируем в orderDetails массив с омеченными к заказу товарами*/
        $http({
            url: 'http://localhost:5555/core/api/v1/orders',
            method: 'POST',
            data: $scope.orderDetails
        }).then(function (response) {
            $scope.loadCart();
            $scope.orderDetails = null
            $location.path('/orders').replace()
            $scope.$apply();
        });
    };

    //массив выбранных для заказа CartItem
    $scope.selection = [];

    //удаляет/добавляет в массив ip товаров, которые будут заказаны
    $scope.toggleSelection = function toggleSelection(productId) {
        var idx = $scope.selection.indexOf(productId);
        if (idx > -1) {
            $scope.selection.splice(idx, 1);
        } else {
            $scope.selection.push(productId);
        }
    };

    $scope.loadCart();
});