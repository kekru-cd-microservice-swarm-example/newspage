//var baseURL = 'http://localhost:8081/api/';
var baseURL = '/api/';

angular.module('NewsApp', []);

angular.module('NewsApp', ['ngRoute'])

    .config(['$routeProvider', '$locationProvider',
        function ($routeProvider, $locationProvider) {
            $routeProvider
                .when('/', {
                    templateUrl: 'startseite.html',
                    controller: 'NewsCtrl'
                })
                .when('/artikel/:artikelid', {
                    templateUrl: 'artikel.html',
                    controller: 'ArtikelCtrl'
                }).otherwise({
                    redirectTo: '/'
                });

            $locationProvider.html5Mode(true);
        }])


    .controller('NewsCtrl', function ($scope, $http) {


        $scope.news = [[]];

        $scope.init = function () {
            $scope.getNews();
            $scope.getForegroundNews();
        }

        $scope.getNews = function () {

            $http({
                method: 'GET',
                url: baseURL + 'newslist'

            }).then(function successCallback(response) {

                $scope.news = response.data;
                console.log(response);

            }, function errorCallback(response) {
                console.log(response);
            });
        }

        $scope.getForegroundNews = function () {
            $http({
                method: 'GET',
                url: baseURL + 'newsforeground'

            }).then(function successCallback(response) {

                $scope.newsForeground = response.data;
                console.log(response);

            }, function errorCallback(response) {
                console.log(response);
            });
        }


    }).controller('ArtikelCtrl', function ($scope, $http, $routeParams) {

        $scope.artikelid = $routeParams.artikelid;

        //Information für den Commentsservice, der über ng-include eingebunden wird und als Unter-Controller auch diese Konfig erhält
        $scope.commentsservice = {};
        $scope.commentsservice.newsId = $scope.artikelid;

        $scope.getArtikel = function () {
            $http({
                method: 'GET',
                url: baseURL + 'newsfull/' + $scope.artikelid

            }).then(function successCallback(response) {

                $scope.artikel = response.data;
                console.log(response);

            }, function errorCallback(response) {
                console.log(response);
            });
        }
    });
