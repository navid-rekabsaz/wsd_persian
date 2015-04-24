clwsdApp.controller('questionCtrl', function ($scope, $http) {
	$scope.errormessage = "";
    $scope.question = {};
                    
	$scope.load = function(annotatorid) {
		//$http.get('question/?annotatorid='+annotatorid).success(function(data) {
        //      $scope.question = data;
		//});
        
                    var fowler = {
                    id: 1,
                        name: "Fowler"
                    };
                    var twain = {
                    id: 2,name: "Twain"
                    };
                    var poe = {
                    id: 3,name: "Poe"
                    };
        $scope.question.clusters=[twain, fowler, poe];
                    
        $scope.question.all_ques=1000;
        $scope.question.answered_ques=0;
        $scope.question.annotatorname="Unknown";
        $scope.question.wordname="MyWord";
        $scope.question.sentence="a cool random sentence";
    };
});