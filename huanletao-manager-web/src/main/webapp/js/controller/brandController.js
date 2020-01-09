app.controller("brandController",function($scope,$http,$controller,brandService){
    $controller("baseController",{$scope:$scope});

    $scope.findAll = function(){
        brandService.findAll().success(function(response){
            $scope.list = response;
        })
    }
    $scope.keywords = "";




    $scope.save = function () {
        var url;
        if($scope.entity.id == null){
            url = "../add.do";
        }else{
            url = "../update.do";
        }
        brandService.save(url,$scope.entity).success(function(response){
            if(response.success){
                $scope.reloadList();
            }else{
                alert(response.message);
            }
        })

    }
    $scope.findOne = function(id){
        brandService.findOne(id).success(function(response){
            $scope.entity = response;
        })
    }



    $scope.dele = function(){
        brandService.dele($scope.selectIds).success(function(response){
            if(response.success){
                $scope.selectIds = [];
                $scope.reloadList();
            }else{
                alert(response.message);
            }
        })
    }

    $scope.search = function(pageNum,pageSize){
        brandService.search(pageNum,pageSize,$scope.keywords).success(function(response){
            $scope.list = response.rows;
            $scope.paginationConf.totalItems = response.total;
            $scope.selectIds = [];
        })
    }
})