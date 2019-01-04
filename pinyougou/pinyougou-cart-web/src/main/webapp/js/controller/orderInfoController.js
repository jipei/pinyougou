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

            //查询默认地址
            for (var i = 0; i < response.length; i++) {
                var address = response[i];
                if("1"==address.isDefault){
                    $scope.address = address;
                    break;
                }
            }
        });

    };

    //选择地址
    $scope.selectAddress = function (address) {
        $scope.address = address;

    };

    //判断当前地址是否是选择的地址
    $scope.isSelectedAddress = function (address) {
        return $scope.address == address;

    };

    //支付方式；默认为微信付款
    $scope.order = {"paymentType":"1"};

    //选择支付方式
    $scope.selectPaymentType = function (type) {
        $scope.order.paymentType = type;

    };

});