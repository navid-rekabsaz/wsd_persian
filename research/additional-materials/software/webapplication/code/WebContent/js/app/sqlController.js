clwsdApp.controller('sqlCtrl', function ($scope, $http, $location) {
	$scope.sqlText='';
	$scope.errorMessage='';

	
	$scope.execute = function() {
		
		var res = $http.post('rest/sql', $scope.sqlText);
		res.success(function(data, status, headers, config) {
			if (data.result==true){
				$scope.errorMessage = "DONE!";
			} else if (data.result==false){
				$scope.errorMessage = data.message;
			} else {
				$scope.errorMessage = "Error in request!";
			}
		});
	};
	
});

