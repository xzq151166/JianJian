angular.module('testApp',['commonApp'])
            .controller('myCtrl',function($scope,$timeout,LoadingService){
	
	$scope.doSubmit = function(){

		console.log("start dosubmit...");

		LoadingService.show();//show the loading overlay

		//模拟http请求发送时延
		$timeout(function(){
			console.log('end dosubmit ');

			LoadingService.hide();//hide the loading overlay

		},3000);
	};

});
