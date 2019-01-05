app.controller("payController", function ($scope, $location, cartService, payService) {

    $scope.getUsername = function () {
        cartService.getUsername().success(function (response) {
            $scope.username = response.username;

        });

    };

    //生成二维码
    $scope.createNative = function () {
        //1、接收浏览器地址栏的订单号
        $scope.outTradeNo = $location.search()["outTradeNo"];

        //2、发送请求到后台
        payService.createNative($scope.outTradeNo).success(function (response) {

           if("SUCCESS"==response.result_code){
               //总金额
               $scope.totalFee = (response.totalFee/100).toFixed(2);

               //3、生成二维码
               var qr = new QRious({
                   element:document.getElementById("qrious"),
                   size:250,
                   level:"Q",
                   value:response.code_url
               });
           } else {
               alert("生成二维码失败");
           }
        });



    };

});