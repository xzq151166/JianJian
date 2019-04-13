/**
 * create by jicogxy
 * 
 * 4/5/17
 */

/*append the fakeloader div when window loads html*/
angular.element(window).bind('load', function($scope,$compile) { 
	angular.element(document.body).append('<div id="fakeloader" class="fakeloader load_visible" ><div class="load_fl load_spinner2"><div class="load_spinner-container load_container1"><div class="load_circle1"></div><div class="load_circle2"></div><div class="load_circle3"></div><div class="load_circle4"></div></div><div class="load_spinner-container load_container2"><div class="load_circle1"></div><div class="load_circle2"></div><div class="load_circle3"></div><div class="load_circle4"></div></div><div class="load_spinner-container load_container3"><div class="load_circle1"></div><div class="load_circle2"></div><div class="load_circle3"></div><div class="load_circle4"></div></div></div></div>');
});

/*create the LoadingService in loadingMadol  to be injected for commonApp module   */
var commonApp = angular.module('commonApp',[]);

commonApp.factory("LoadingService",['$window',function($window){
	var instance = {};
	/*function:show loader  */
	instance.show = function(){
		this.loadcss();
		angular.element(document.querySelector('div#fakeloader')).removeClass('load_visible');
	};
	/*function:hide loader  */
	instance.hide = function(){
		angular.element(document.querySelector('div#fakeloader')).addClass('load_visible');
	};
	/*function:set position of load_spinner div with diff size of window */
	instance.loadcss = function(){
		var winw = $window.innerWidth;
		var winh = $window.innerHeight;
		var loadflw = 20;
		var loadflh = 20;
		angular.element(document.querySelector('div.load_fl')).css({'position':'absolute',
								'left':(winw/2)-(loadflw/2)+'px',
								'top':(winh/2)-(loadflh/2)+'px'});
	};
	return instance;
}]);



