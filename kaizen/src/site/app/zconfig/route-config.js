(function () {
    //constants
    angular.module("AppModule")
            .constant("systemConfig", {
                apiUrl:
                        location.hostname === 'localhost'
                        ? "http://localhost:8090"
                        : location.protocol + "//" + location.hostname + (location.port ? ':' + location.port : '')
            });




    angular.module("AppModule")
            .config(function ($routeProvider) {
                $routeProvider

                        .when("/", {
                            templateUrl: "app/system/login/login.html",
                            controller: "LoginController"
                        })

                        .when("/admin", {
                            templateUrl: "app/system/home/home.html"
                        })

                        .when("/news", {
                            templateUrl: "app/system/home/news.html",
                            controller: "IndexController"
                        })

                        //  Transaction
                        .when("/transaction/job", {
                            templateUrl: "app/transaction/job/job.html",
                            controller: "JobController"
                        })
                        .when("/transaction/kaizen", {
                            templateUrl: "app/transaction/kaizen/kaizen.html",
                            controller: "KaizenController"
                        })
                        .when("/transaction/kaizen-manager-view", {
                            templateUrl: "app/transaction/manager/manager-view.html",
                            controller: "KaizenManagerViewController"
                        })
                        .when("/transaction/kaizen-committee-view", {
                            templateUrl: "app/transaction/committee/committee-view.html",
                            controller: "KaizenCommitteeViewController"
                        })

                        //master
                        .when("/master/employee-registration", {
                            templateUrl: "app/master/employee/employee-registration.html",
                            controller: "employeeController"
                        })
                        .when("/master/user-registration", {
                            templateUrl: "app/master/user/user-registration.html",
                            controller: "userController"
                        })
                        .when("/master/create-target", {
                            templateUrl: "app/master/target/target.html",
                            controller: "targetController"
                        })
                        .when("/master/create-topkaizen-monthly", {
                            templateUrl: "app/master/topKaizen/create-topkaizen-monthly.html",
                            controller: "TopkaizenController"
                        })
                        .when("/master/department", {
                            templateUrl: "app/master/department/department.html",
                            controller: "departmentController"
                        })
                        .when("/master/kaizens", {
                            templateUrl: "app/master/kaizens/kaizen-view.html",
                            controller: "KaizenViewController"
                        })
                        .when("/master/delete-kaizen", {
                            templateUrl: "app/master/kaizens/delete-kaizen.html",
                            controller: "KaizenViewController"
                        })
                        .when("/master/send-mail", {
                            templateUrl: "app/master/mail/mail.html",
                            controller: "mailController"
                        })
                        .when("/master/branch", {
                            templateUrl: "app/master/branch/branch.html",
                            controller: "branchController"
                        })
                        .when("/master/currency", {
                            templateUrl: "app/master/currency/currency.html",
                            controller: "currencyController"
                        })
                        .when("/master/news", {
                            templateUrl: "app/master/newsFeed/news-feed.html",
                            controller: "newsFeedController"
                        })



                        //reports
                        .when("/reports/summary", {
                            templateUrl: "app/reports/summary.html",
                            controller: "SummaryController"
                        })
                        .when("/reports/annual-achievement", {
                            templateUrl: "app/reports/annual-achievement.html",
                            controller: "SummaryController"
                        })
                        .when("/reports/evaluated-details", {
                            templateUrl: "app/reports/manager-evaluated.html",
                            controller: "SummaryController"
                        })
                        .when("/reports/monthly-details", {
                            templateUrl: "app/reports/monthly-details.html",
                            controller: "SummaryController"
                        })
                        .when("/reports/top5-kaizen", {
                            templateUrl: "app/reports/top5-kaizen.html",
                            controller: "SummaryController"
                        })
                        .when("/reports/top10-kaizen", {
                            templateUrl: "app/reports/top10-kaizen.html",
                            controller: "SummaryController"
                        })
                        .when("/reports/top-kaizen-monthly", {
                            templateUrl: "app/reports/top-kaizen-monthly.html",
                            controller: "SummaryController"
                        })
                        .when("/reports/cost-saving", {
                            templateUrl: "app/reports/cost-saving.html",
                            controller: "SummaryController"
                        })


                        .otherwise({
                            redirectTo: "/"
                        });
            });

    angular.module("AppModule")
            .run(function ($rootScope, $location, $cookieStore, $http) {
                // keep user logged in after page refresh
                $rootScope.globals = $cookieStore.get('globals') || {};
                if ($rootScope.globals.currentUser) {
                    $http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.globals.currentUser.authdata; // jshint ignore:line
                }

                $rootScope.$on('$locationChangeStart', function (event, next, current) {
                    // redirect to login page if not logged in
                    if ($location.path() !== '/' && !$rootScope.globals.currentUser) {
                        $location.path('/');
                    }
                });
            });

    angular.module("AppModule")
            .controller("IndexController", function ($scope, $rootScope, $location, systemConfig, $http, $base64) {
                // log out when refresh
                $scope.$watch('UserMode', function (mode) {
                    if (!mode) {
                        window.onbeforeunload = function () {
                            return 'Want to unload?';
                        };
                        $location.path("/");
                        $rootScope.error = null;
                    }
                });


                //log out 
                $scope.logout = function () {
                    $rootScope.value = null;
                    $location.path("/");
                };

                var url = systemConfig.apiUrl + "/api/news";
                $http.get(url)
                        .success(function (data, status, headers) {
                            $rootScope.newsFeeds = data;
                        })
                        .error(function (data, status, headers) {

                        });

                $scope.selectNews = function (news) {
                    $scope.file = "./images/" + news.file;
                    window.open($scope.file);
                };


            });


}());