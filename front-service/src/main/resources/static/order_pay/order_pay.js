angular.module('market-front').controller('orderPayController', function ($scope, $http, $location, $localStorage, /*чтобы работать с параметрами как /order_pay/:orderId*/$routeParams) {

    /*  запрашиваем с бэка информацию о заказе*/
    $scope.loadOrder = function () {
        $http({
            url: 'http://localhost:5555/core/api/v1/orders/' + $routeParams.orderId,
            method: 'GET'
        }).then(function (response) {
            $scope.order = response.data; /* получаем детали о заказе*/
            $scope.renderPaymentButtons();/* отрисовываем кнопку платежа*/
        });
    };

    //обработка нажатия кнопки
    $scope.renderPaymentButtons = function() {
        paypal.Buttons({
            //создание черновика заказа
            createOrder: function(data, actions) {
                return fetch('http://localhost:5555/core/api/v1/paypal/create/' + $scope.order.id, {
                    method: 'post',
                    headers: {
                        'content-type': 'application/json'
                    }
                }).then(function(response) {
                    return response.text();
                });
            },

            //далее появляется запрос на подтверждение черновика заказа

            // при подтверждении черновика заказа
            onApprove: function(data, actions) {
                //отправляем запрос о том что будем проводить платеж
                return fetch('http://localhost:5555/core/api/v1/paypal/capture/' + data.orderID, {
                    method: 'post',
                    headers: {
                        'content-type': 'application/json'
                    }
                }).then(function(response) {
                    $location.path('/orders').replace()
                    $scope.$apply();
                });


            },

            // при неподтверждении черновика заказа
            onCancel: function (data, actions) {
                console.log("Order canceled: " + data);
            },

            //при ошибке
            onError: function (data, actions) {
                alert(data + ' ! ' + data + ' ! ' + data);
            }
        }).render('#paypal-buttons');
    }

    $scope.goToOrders = function () {
        $location.path('/orders');
    }

    $scope.loadOrder();
});