angular.module('myApp',['commonApp'])
    .controller('SignUpController',function($scope,$http,$timeout,LoadingService){
        $scope.userdata=  {};

        $scope.submitForm = function(){
            console.log($scope.userdata);
            if($scope.SignUpForm.$invalid)//这里曾经出现错误：SignUpForm是form表单的名字
                console.log('错误');
            else
                console.log('成功');
        }

        $scope.save = function() {
            var fd = new FormData();
            var file = document.querySelector('input[type=file]').files[0];
            fd.append('file', file);
            LoadingService.show();//show the loading overlay
            $http({
                method:'POST',
                url:"excel/upload",
                data: fd,
                headers: {'Content-Type':undefined},
                transformRequest: angular.identity
            }).success( function ( response )
                {
                    LoadingService.hide();
                    //上传成功的操作
                    $scope.download(response);
                });

        }
        
        $scope.download = function (response) {
             var fileUrl = response.data;
            $http({
                url: 'excel/download',
                method: "POST",//接口方法
                params:{fileUrl:fileUrl},
                headers: {
                    'Content-type': 'application/json'
                },
                responseType: 'arraybuffer'
            }).success(function (data, status, headers, config) {
                var blob = new Blob([data], {type: "application/vnd.ms-excel"});
                var objectUrl = URL.createObjectURL(blob);
                var a = document.createElement('a');
                document.body.appendChild(a);
                a.setAttribute('style', 'display:none');
                a.setAttribute('href', objectUrl);
                var filename="匹配表.xlsx";
                a.setAttribute('download', filename);
                a.click();
                URL.revokeObjectURL(objectUrl);
            }).error(function (data, status, headers, config) {

            });

        }
    })
    .directive('compare',function(){
        var o = {};
        o.strict='AE';
        o.scope = {
            orgText:'=compare'
        }
        o.require='ngModel';
        o.link=function(sco,ele,att,con){
            con.$validators.compare=function(v){
                return v == sco.orgText;
            }
            sco.$watch('orgText',function(){
                con.$validate();
            })
        }
        return o;
    })
