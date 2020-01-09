app.controller("registController",function($scope,registService){
    $scope.regist = function(){
    registService.regist($scope.entity).success(function(response){
        alert(response.message);
    })
    }
})