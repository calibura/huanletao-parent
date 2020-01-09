app.service("brandService",function($http){
    this.findAll = function(){
       return $http.get("../findAll.do");
    }





    this.save = function (url,entity) {
        return $http.post(url,entity);

    }
    this.findOne = function(id){
       return  $http.post("../findOne.do?id="+id)
    }




    this.dele = function(selectIds){
        return $http.post("../dele.do?ids="+selectIds)
    }

    this.search = function(pageNum,pageSize,keywords){
        return $http.get("../findAll.do?pageNum="+pageNum+"&pageSize="+pageSize+"&keywords="+keywords);
    }

    this.selectBrandList = function(){
        return $http.get("../selectBrandList.do");
    }

})