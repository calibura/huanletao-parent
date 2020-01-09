app.service("itemService",function($http){
    this.itemCat=function(entity){
        return $http.post("",entity)
    }
})