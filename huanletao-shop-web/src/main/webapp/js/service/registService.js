app.service("registService",function($http){
    this.regist = function(entity){
       return  $http.post("/user/regist.do",entity)
    }
})