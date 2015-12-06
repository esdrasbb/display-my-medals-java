
var app = angular.module('medalapp', [
    'ngCookies',
    'ngResource',
    'ngSanitize',
    'ngRoute'
]);

app.config(function ($routeProvider) {
    $routeProvider.when('/medals', {
        templateUrl: 'views/list.html',
        controller: 'ListCtrl'
    }).when('/medals/display', {
        templateUrl: 'views/display.html',
        controller: 'DisplayCtrl'
    }).when('/classes', {
        templateUrl: 'views/classes/list.html',
        controller: 'ListClassCtrl'
    }).when('/classes/create', {
        templateUrl: 'views/classes/create.html',
        controller: 'CreateClassCtrl'
    }).when('/students', {
        templateUrl: 'views/students/list.html',
        controller: 'ListStudentCtrl'
    }).when('/students/create', {
        templateUrl: 'views/students/create.html',
        controller: 'CreateStudentCtrl'
    }).when('/students/add-class', {
        templateUrl: 'views/students/add.html',
        controller: 'AddClassCtrl'
    }).otherwise({
        redirectTo: '/medals'
    })
});

app.controller('HeaderController', function ($scope, $location) {
    $scope.isActive = function (viewLocation) {
        return $location.path().indexOf(viewLocation) == 0;
    };
});

app.controller('ListCtrl', function ($scope, $http) {
    $http.get('/api/v1/medals').success(function (data) {
        $scope.medals = data;
    }).error(function (data, status) {
        console.log('Error ' + data)
    })
});

app.controller('DisplayCtrl', function ($scope, $http) {
    $http.get('/api/v1/classes').success(function (data) {
        $scope.classes = data;
    }).error(function (data, status) {
        console.log('Error ' + data)
    })

    $http.get('/api/v1/students').success(function (data) {
        $scope.students = data;
    }).error(function (data, status) {
        console.log('Error ' + data)
    })
});

app.controller('ListClassCtrl', function ($scope, $http) {
    $http.get('/api/v1/classes').success(function (data) {
        $scope.classes = data;
    }).error(function (data, status) {
        console.log('Error ' + data)
    })
});

app.controller('CreateClassCtrl', function ($scope, $http, $location) {
    $scope.class = {
    };
    $('input[name="daterange"]').daterangepicker({
        singleDatePicker: true,
        showDropdowns: true,
        locale: {
              format: 'DD-MM-YYYY'
        }
    });

    $scope.createClass = function () {
        $http.post('/api/v1/classes', $scope.class).success(function (data) {
            $location.path('/classes');
        }).error(function (data, status) {
            console.log('Error ' + data)
        })
    }
});

app.controller('ListStudentCtrl', function ($scope, $http) {
    $http.get('/api/v1/students').success(function (data) {
        $scope.students = data;
    }).error(function (data, status) {
        console.log('Error ' + data)
    })
});

app.controller('CreateStudentCtrl', function ($scope, $http, $location) {
    $scope.student = {
    };

    $scope.createStudent = function () {
        $http.post('/api/v1/students', $scope.student).success(function (data) {
            $location.path('/student');
        }).error(function (data, status) {
            console.log('Error ' + data)
        })
    }
});

app.controller('AddClassCtrl', function ($scope, $http, $location) {
    $http.get('/api/v1/students').success(function (data) {
        $scope.students = data;
    }).error(function (data, status) {
        console.log('Error ' + data)
    })

    $scope.studentSelected = {
    };
    $scope.classSelected = {
    };
    $scope.classes = {
    };

    $scope.getClasses = function () {
        var studentId = String($scope.studentSelected.id);
        $http.get('/api/v1/students/classes/' + studentId).success(function (data) {
            $scope.classes.length = 0;
            $scope.classes = data;
        }).error(function (data, status) {
            console.log('Error ' + data)
        })
    }

    $scope.addClass = function () {
        data = $scope.studentSelected.id + "#" + $scope.classSelected.id

        $http.post('/api/v1/class', data).success(function (data) {
            $location.path('/student');
        }).error(function (data, status) {
            console.log('Error ' + data)
        })
    }
});