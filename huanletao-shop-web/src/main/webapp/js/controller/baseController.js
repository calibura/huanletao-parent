app.controller("baseController",function($scope){
    $scope.paginationConf = {
        //当前页
        currentPage: 1,
        // 总条数
        totalItems: 10,
        // 每页显示的数量
        itemsPerPage: 10,
        // 可选的每页数量
        perPageOptions: [10, 20, 30, 40, 50],
        // 切换页面之后触发的事件
        onChange: function () {
            $scope.reloadList();
        }
    }

    $scope.reloadList = function(){
        $scope.search($scope.paginationConf.currentPage,$scope.paginationConf.itemsPerPage);
    }

    $scope.selectIds = [];
    $scope.selectAll = function($event){
        if($event.target.checked){
            $scope.selectIds = [];
            for(var i = 0;i<$scope.list.length;i++){
                $scope.selectIds.push($scope.list[i].id);
                $scope.list[i].selected = true;
            }
        }else{
            $scope.selectIds = [];
            for(var i = 0;i<$scope.list.length;i++){
                $scope.list[i].selected = false;
            }
        }
    }

    $scope.updateSelectIds = function($event,id){
        if($event.target.checked){
            $scope.selectIds.push(id);
        }else{
            //移除
            //获取id对应的索引
            var index = $scope.selectIds.indexOf(id);
            //根据索引移除  index 删除的开始位置 deletecount 朝后面删几个
            $scope.selectIds.splice(index,1);
        }
        if($scope.selectIds.length == $scope.list.length){
            $scope.isSelectAll = true;
        }else{
            $scope.isSelectAll = false;
        }
    }
})