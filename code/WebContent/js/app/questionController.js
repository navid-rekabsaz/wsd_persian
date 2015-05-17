clwsdApp.controller('questionCtrl', function ($scope, $http, $location) {
	$scope.question = {};
	$scope.loadTime;
	$scope.answerTime;
	
 	$scope.load = function() {
 		$scope.errorMessage = "";
 		
 		$http.get('rest/question/getnewquestion?annotatorid='+$location.search()['annotatorid']).success(function(data) {
              $scope.question = data;
              $scope.loadTime=Date.now();
              $scope.question.translations=[];
      		  $scope.question.selectedtranslationids=[];
 		});
                  
 		
 		/*
 		cluster1= { 'id':1, 'name':'Fowler', 'translations':[{ 'id':1, 'name':'Pune'}, { 'id':2, 'name':'Mumbai'}, { 'id':3, 'name':'Nagpur'}, { 'id':4, 'name':'Akola'}]};
 		cluster2= { 'id':2, 'name':'Twain', 'translations':[{ 'id':5, 'name':'Montgomery'}, { 'id':6, 'name':'Birmingham'}]};
 		cluster3= { 'id':3, 'name':'Poe', 'translations':[{ 'id':7, 'name':'Springfield'}, { 'id':8, 'name':'Chicago'}]};
 		                      
        $scope.question.clusters= [];
        $scope.question.clusters.push(cluster1);
        $scope.question.clusters.push(cluster2);
        $scope.question.clusters.push(cluster3);
                    
        $scope.question.id=1;
        $scope.question.allQuestionsNumber=1000;
        $scope.question.answeredQuestionsNumber=0;
        $scope.question.annotatorName="Unknown";
        $scope.question.wordName="MyWord";
    	$scope.question.sentence="a cool random sentence";
    	*/
	};
	
	$scope.saveanswer = function() {
		
		$scope.answerTime=Date.now();
		
		var dataObj = {
			annotatorId : $location.search()['annotatorid'],
			questionId : $scope.question.id,
			translationIds : $scope.question.selectedtranslationids,
			loadTime : $scope.loadTime,
			answerTime : $scope.answerTime
		};
		
		var res = $http.post('rest/question', dataObj);
		res.success(function(data, status, headers, config) {
			if (data.result==true){
				$scope.load();
			} else if (data.result==false){
				$scope.errorMessage = data.message;
			} else {
				$scope.errorMessage = "Error in request!";
			}
		});
	};
	
	$scope.OnClusterChange = function () {  
		$scope.question.translations=[];
		$scope.question.selectedtranslationids=[];
		
		for (var i = 0; i < $scope.question.clusters.length; i++) {
		    if ($scope.question.clusters[i].id==$scope.question.clusterid) {
		    	$scope.question.translations=$scope.question.clusters[i].translations;
		    	break;
		    }
		}
     }
	
});

