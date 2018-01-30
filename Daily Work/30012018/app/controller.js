var app = angular.module('myFirstApp', []);

app.controller('studentController', function ($scope) {
   $scope.student = {
    details: [{
        firstname : "Dheeraj", lastname
    :
        "Kamath", fees
    :
        100, subjects
    :
        [{name: "Physics", marks: 99}, {name: "Chemistry", marks: 100}]
    }
,
    {
        firstname : "Chinmayi", lastname
    :
        "Teggi", fees
    :
        50, subjects
    :
        [{name: "Physics", marks: 75}, {name: "Chemistry", marks: 68}]
    }
,
    {
        firstname : "Adithya", lastname
    :
        "Bhat", fees
    :
        75, subjects
    :
        [{name: "Physics", marks: 59}, {name: "Chemistry", marks: 10}]
    }]
};
});
