var app = angular.module("myApp", []);

app.controller('myCtrl', ['$scope', function ($scope) {
    $scope.gmail = {
        username : "",
        email : ""
    };

    $scope.onGoogleLogin = function(){
        console.log("Hey there!");
        alert("Response Received -1!");
        var params = {
            'clientid' : '566422633916-ok8e6irsevt9aud1k41cf4riuo6focat.apps.googleusercontent.com',
            'cookiepolicy' : 'single_host_origin',
            'callback' : function (result) {
                if(result['status']['signed_in']){
                    var request = gapi.client.plus.people.get(
                        {
                            'userId' : 'me'
                        }
                    );
                    request.execute(function (resp) {
                        $scope.$apply(function(){

                            $scope.gmail.username = resp.displayName;
                            $scope.gmail.email = resp.emails[0].value;
                            $scope.g_image = resp.image.url;
                            console.log(resp.displayName);
                        });
                    });
                }else{
                    console.log(result['status']['signed_in']);
                }
            },
            'approvalprompt' : 'force',
            // 'scope' : 'https://www.googleapis.com/auth/plus.login https://www.googleapis.com/auth/userinfo.email'
            'scope': 'https://www.googleapis.com/auth/plus.login https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/plus.me https://www.googleapis.com/auth/plus.profiles.read https://www.googleapis.com/auth/userinfo.profile'
        };

        gapi.auth.signIn(params);
    };
}]);