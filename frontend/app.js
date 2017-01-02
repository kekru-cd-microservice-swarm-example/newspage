angular.module('NewsApp', []);

angular.module('NewsApp', ['ngRoute'])    
.config(['$routeProvider', '$locationProvider',
  function($routeProvider, $locationProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'startseite.html',
        controller: 'NewsCtrl',
        controllerAs: 'news'
      })
      .when('/artikel/:id', {
        templateUrl: 'artikel.html',
        controller: 'ArtikelCtrl',
        controllerAs: 'artikel'
      });

    $locationProvider.html5Mode(true);
}])
.controller('NewsCtrl', function ($scope, $http) {

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
}).controller('ArtikelCtrl', function ($scope, $http) {
    
    
});
