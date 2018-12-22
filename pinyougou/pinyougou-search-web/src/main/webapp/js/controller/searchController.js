app.controller("searchController", function ($scope, searchService) {

    //搜索条件对象
    $scope.searchMap = {"keywords":""};

    //搜索
    $scope.search = function () {
        searchService.search($scope.searchMap).success(function (response) {
            $scope.resultMap = response;

        });

    };

});