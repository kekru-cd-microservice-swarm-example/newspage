angular.module('NewsApp', []);

angular.module('NewsApp')    

.controller('newsCtrl', function ($scope, $http) {

    $scope.baseURL = 'http://localhost:8081/api/';
    $scope.news = [[]];

    $scope.init = function(){
        $scope.getNews();
        $scope.getCarouselNews();
    }

    $scope.getNews = function(){
        
        $http({
            method: 'GET',
            url: $scope.baseURL + 'newslist'

        }).then(function successCallback(response) {

            $scope.news = response.data;
            console.log(response);

        }, function errorCallback(response) {
            console.log(response);
        });
    }

    $scope.getCarouselNews = function(){
        $http({
            method: 'GET',
            url: $scope.baseURL + 'newscarousel'

        }).then(function successCallback(response) {

            $scope.newsCarousel = response.data;
            console.log(response);

        }, function errorCallback(response) {
            console.log(response);
        });
    }
});
