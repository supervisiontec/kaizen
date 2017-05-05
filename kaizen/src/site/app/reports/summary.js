(function () {
    angular.module("AppModule")
            .factory("SummaryFactory", function (systemConfig, $http) {
                var factory = {};

                //load summary
                factory.loadSummary = function (year, callback) {
                    var url = systemConfig.apiUrl + "/summary/" + year;
                    $http.get(url)
                            .success(function (data, status, headers) {
                                callback(data);
                            })
                            .error(function (data, status, headers) {

                            });
                };
                // get monthWise details
                factory.getMonthWiseDetails = function (year, callback) {
                    var url = systemConfig.apiUrl + "/month-wise-details/" + year;
                    $http.get(url)
                            .success(function (data, status, headers) {
                                callback(data);
                            })
                            .error(function (data, status, headers) {

                            });
                };

                //load manager view and eveluated kaizen details
                factory.loadEveluatedDetails = function (callback) {
                    var url = systemConfig.apiUrl + "/view-count";
                    $http.get(url)
                            .success(function (data, status, headers) {
                                callback(data);
                            })
                            .error(function (data, status, headers) {

                            });
                };

                return factory;
            });

    angular.module("AppModule")
            .controller("SummaryController", function ($timeout, $scope, $window, SummaryFactory, $filter) {

                $scope.yearList = [];
                $scope.monthList = [];

                $scope.countList = [];

                $scope.print = function (summary) {
                    $scope.printMode = 'true';

                    $timeout(function () {
                        $window.print();
                        $scope.printMode = 'false';
                    }, 500);

                };

                $scope.changeYear = function (year) {
                    SummaryFactory.loadSummary(year
                            , function (data) {
                                $scope.summaryList = data;
                            }
                    , function (data) {
//                        Notification.error(data);
                    });
                };

                $scope.getMonthWiseDetails = function (year) {
                    SummaryFactory.getMonthWiseDetails(year
                            , function (data) {
                                $scope.monthWiseList = data;
                            }
                    , function (data) {
//                        Notification.error(data);
                    });
                };

                $scope.changeMonth = function (month) {
                    $scope.tempList = [];
                    angular.forEach($scope.countList, function (data) {
                        var month1 = $filter('date')(data[0], 'MM');
                        if (month1 === month) {
                            $scope.tempList.push(data);
                        }
                    });

                };

                $scope.getMonthlyCount = function (month, department) {
                    var label = 0;

                    angular.forEach($scope.monthWiseList, function (data) {

                        var month1 = data[0];
                        if (month === month1) {
                            if (department === data[1]) {
                                label = data[3];
                                return;
                            }
                        }
                    });
                    console.log(label);
                    return label;
                };

                $scope.totalKizanCount = function (department) {
                    var totalKizan = 0;

                    angular.forEach($scope.monthWiseList, function (data) {
                        if (department === data[1]) {
                            totalKizan += data[3];
                            return;
                        }

                    });
                    return totalKizan;
                };


                $scope.init = function () {
                    for (var j = new Date().getFullYear(); j > 2005; j--)
                    {
                        $scope.yearList.push(j);
                    }

                    SummaryFactory.loadEveluatedDetails(
                            function (data) {
                                $scope.countList = data;
                            });

                };

                $scope.init();

            });



}());


