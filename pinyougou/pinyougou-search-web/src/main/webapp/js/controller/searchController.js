app.controller("searchController", function ($scope, searchService) {

    //搜索条件对象
    $scope.searchMap = {"keywords":"", "category":"", "brand":"", "spec":{}};

    //搜索
    $scope.search = function () {
        searchService.search($scope.searchMap).success(function (response) {
            $scope.resultMap = response;

        });

    };

    //添加过滤条件
    $scope.addSearchItem = function (key, value) {
        if ("category" == key || "brand" == key) {
            $scope.searchMap[key] = value;
        } else {
            //规格
            $scope.searchMap.spec[key] = value;
        }

        //重新搜索
        //$scope.search();

    };

    //移除过滤条件
    $scope.removeSearchItem = function (key) {
        if ("category" == key || "brand" == key) {
            $scope.searchMap[key] = "";
        } else {
            //规格
            delete $scope.searchMap.spec[key];
        }

    };

});