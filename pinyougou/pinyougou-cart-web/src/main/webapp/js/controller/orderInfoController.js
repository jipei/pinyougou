app.controller("orderInfoController", function ($scope, cartService, addressService) {

    $scope.getUsername = function () {
        cartService.getUsername().success(function (response) {
            $scope.username = response.username;

        });

    };

    //获取购物车列表
    $scope.findCartList = function () {
        cartService.findCartList().success(function (response) {
            $scope.cartList = response;

            //计算商品的总价和总数量
            $scope.totalValue = cartService.sumTotalValue(response);
        });

    };

    //获取当前登录用户的收件人地址列表
    $scope.findAddressList = function () {
        addressService.findAddressList().success(function (response) {
            $scope.addressList = response;

        });

    };


});