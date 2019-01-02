app.service("cartService", function ($http) {
    this.getUsername = function () {
        return $http.get("cart/getUsername.do?t=" + Math.random());

    };

    this.findCartList = function () {
        return $http.get("cart/findCartList.do?t=" + Math.random());

    };

    this.addItemToCartList = function (itemId, num) {
        return $http.get("cart/addItemToCartList.do?itemId=" +itemId + "&num=" + num + "&t=" + Math.random());

    };
});