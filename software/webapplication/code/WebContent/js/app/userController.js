clwsdApp.controller('userCtrl', function ($scope, $http, $location) {
	$scope.load = function() {
 		$scope.errorMessage = "";
 			
 		$http.get('rest/user/checkpassword?annotatorid='+$location.search()['annotatorid']+'&passwrd='+$location.search()['password']).success(function(data) {
              if (data!=true){
            	  window.location="index.html";  
              }
        });
                  
	};
});

