clwsdApp.controller('questionCtrl', function ($scope, $http) {
                    $scope.errormessage = "";
                    
                    $scope.load = function(annotatorid) {
                    //$http.get('question/?annotatorid='+annotatorid).success(function(data) {
                    //      $scope.question = data;
                    //});
                    
                    
                    $scope.clusters= {
                    'Fowler' : ['Pune', 'Mumbai', 'Nagpur', 'Akola'],
                    'Twain' : ['Montgomery', 'Birmingham'],
                    'Poe' : ['Springfield', 'Chicago']
                    };
                    
                    $scope.id=1;
                    $scope.all_ques=1000;
                    $scope.answered_ques=0;
                    $scope.annotatorname="Unknown";
                    $scope.wordname="MyWord";
                    $scope.sentence="a cool random sentence";
                    };
                    });