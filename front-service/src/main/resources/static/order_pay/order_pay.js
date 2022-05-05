angular.module('market-front').controller('orderPayController', function ($scope, $http, $location, $localStorage, /*чтобы работать с параметрами как /order_pay/:orderId*/$routeParams) {

    /*  запрашиваем  информацию о заказе*/
    $scope.loadOrder = function () {
        $http({
            url: 'http://localhost:5555/core/api/v1/orders/' + $routeParams.orderId,
            method: 'GET'
        }).then(function (response) {
            $scope.order = response.data; /* получаем детали о заказе*/
            $scope.renderPaymentButtons();/* отрисовываем кнопки платежа*/
        });
    };

    //обработка нажатия кнопки
    $scope.renderPaymentButtons = function () {
        paypal.Buttons({
            //создание черновика заказа
            createOrder: function (data, actions) {
                return fetch('http://localhost:5555/payment/api/v1/paypal/create/' + $scope.order.id, {
                    method: 'post',
                    headers: {
                        'content-type': 'application/json'
                    }
                }).then(function (response) {
                    return response.text();
                });
            },

            //далее появляется запрос на подтверждение черновика заказа

            // при подтверждении черновика заказа
            onApprove: function (data, actions) {
                //отправляем запрос о том что будем проводить платеж
                return fetch('http://localhost:5555/payment/api/v1/paypal/capture/' + data.orderID, {
                    method: 'post',
                    headers: {
                        'content-type': 'application/json'
                    }
                }).finally(
                    $scope.goToOrders
                );


            },

            // при неподтверждении черновика заказа
            onCancel: function (data, actions) {
                $http({
                    url: 'http://localhost:5555/core/api/v1/orders/' + $scope.order.id + '/status',
                    method: 'POST',
                    params: {
                        status: 'CANCELLED',
                    }
                }).finally(
                    $scope.goToOrders
                );
            },

            //при ошибке
            onError: function (data, actions) {
                $http({
                    url: 'http://localhost:5555/core/api/v1/orders/' + $scope.order.id + '/status',
                    method: 'POST',
                    params: {
                        status: 'ERROR_WHILE_REGISTRATION',
                    }
                }).finally(
                    $scope.goToOrders
                )
            }
        }).render('#paypal-buttons');
    }

    $scope.goToOrders = function () {
        $location.path('/orders'),
        $scope.$apply()
        $window.location.reload();
    }

    $scope.loadOrder();
});