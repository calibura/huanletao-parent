app.controller("indexController",function($scope,indexService){
    $scope.getName = function(){
        indexService.getName().success(function(response){
            $scope.name = response.name;
        })
    }
})