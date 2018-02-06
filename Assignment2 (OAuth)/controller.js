var app = angular.module("body", ["ngRoute"]);

app.config(['$routeProvider', function($routeProvider) {
    $routeProvider.

    when('/secondpage', {
        templateUrl: 'secondpage.html'
    }).

    when('/viewStudents', {
        templateUrl: 'viewStudents.html'
    }).

    otherwise({
        redirectTo: '/'
    });

}]);

app.controller('formCtrl', function ($http, $scope, $location, $window) {

    // myFunction = function () {
    //     if($scope.useremail == null || $scope.useremail == "" || $scope.password == "" || $scope.password == null){
    //         alert("Please enter all the details correctly!");
    //     }else{
    //         if($scope.useremail == "dheeraj@gmail.com" && $scope.password == "12345678"){
    //             alert("Login Successfull!");
    //             $window.location.href = '/secondpage';
    //         }else{
    //             alert("Incorrect email or password");
    //         }
    //     }
    //
    // };

    Object.size = function(obj) {
        var size = 0, key;
        for (key in obj) {
            if (obj.hasOwnProperty(key)) size++;
        }
        return size;
    };

    validate = function(){
        $http.get("http://localhost:63342/LogAnalyzer/src/database.json")
            .success(function (response) {
                var persondetails = response.person;

                // Get the size of an object
                var size = Object.size(persondetails);

                for(var i = 0; i < size; i++){
                    var email = persondetails[i].emailID;
                    var password = persondetails[i].password;
                    var loginFlag = false;
                    console.log(email);

                    if($scope.useremail == null || $scope.useremail == "" || $scope.password == "" || $scope.password == null){
                        alert("Please enter all the details correctly!");
                    }else{
                        if($scope.useremail == email && $scope.password == password){
                            $window.location.href = './secondpage.html';
                            loginFlag = true;
                            break;
                        }
                    }
                }

                if(loginFlag == false){
                    alert('Incorrect email/password!');
                }
            });
    };

    login = function () {

    }


});



