var app = angular.module('exam', []);

app.controller('examctrl', function ($scope) {

    $scope.randomNum1 = Math.floor(Math.random() * 10 + 1);
    $scope.randomNum2 = Math.floor(Math.random() * 20 + 1);

    var moods = ["Sad", "Happy", "Depressed", "Suprised", "Angry"];

    $scope.mood = moods[Math.floor(Math.random() * 4)];

    $scope.groceries = [
        {item: "Tomatoes", purchased : true},
        {item: "Bananas", purchased : true},
        {item: "Milk", purchased : true},
        {item: "Soya", purchased : false}
    ];

    $scope.getList = function () {
      return $scope.showList ? 'example1.html' : "example2.html";
    }

    $scope.someNumber = Math.floor(Math.random() * 3);

    $scope.blur = 0;

    $scope.disableButton = false;
});