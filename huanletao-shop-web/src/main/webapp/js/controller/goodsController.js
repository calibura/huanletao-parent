 //控制层 
app.controller('goodsController' ,function($scope,$controller ,$location  ,goodsService,uploadService,itemCatService,typeTemplateService){
	
	$controller('baseController',{$scope:$scope});//继承


    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		goodsService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){			
		goodsService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}

	$scope.checkStatus =  function(name,value){
		var items = $scope.entity.goodsDesc.specificationItems;
		for(var i = 0;i<items.length;i++){
			if(items[i].attributeName == name){
				var index = items[i].attributeValue.indexOf(value);
				if(index >=0){
					return true;
				}
			}
		}
		return false;
	}

	$scope.findGoods = function(){
		var id = $location.search()["id"]
		if(id != null){
			goodsService.findGoods(id).success(function(response){
				$scope.entity = response;
				editor.html($scope.entity.goodsDesc.introduction);
				$scope.entity.goodsDesc.itemImages = JSON.parse($scope.entity.goodsDesc.itemImages);
				$scope.entity.goodsDesc.specificationItems  =JSON.parse($scope.entity.goodsDesc.specificationItems);
				for(var i = 0;i<$scope.entity.items.length;i++){
					$scope.entity.items[i].spec = JSON.parse($scope.entity.items[i].spec);
				}
			})
		}
	}
	
	//查询实体 
	$scope.findOne=function(id){				
		goodsService.findOne(id).success(
			function(response){
				$scope.entity= response;					
			}
		);				
	}
	
	//保存 
	$scope.save=function(){
		$scope.entity.goodsDesc.introduction = editor.html();
		var serviceObject;//服务层对象
		if($scope.entity.goods.id!=null){//如果有ID

			serviceObject=goodsService.update( $scope.entity ); //修改  
		}else{
			serviceObject=goodsService.add( $scope.entity  );//增加
		}				
		serviceObject.success(
			function(response){
				if(response.success){
					$scope.entity = {goods:{},goodsDesc:{itemImages:[],customAttributeItems:[],specificationItems:[]},items:[]};
					$scope.specList = [];
					editor.html("");
					alert(response.message);
                    location.reload();
				}else{
					alert(response.message);
				}
			}		
		);				
	}
	
	 
	//批量删除 
	$scope.dele=function(){			
		//获取选中的复选框			
		goodsService.dele( $scope.selectIds ).success(
			function(response){
				if(response.success){
					$scope.reloadList();//刷新列表
					$scope.selectIds=[];
				}						
			}		
		);				
	}
	
	$scope.searchEntity={};//定义搜索对象 

    $scope.statusName = ["未申请","审核中","审核通过","已驳回"]
	//搜索
	$scope.search=function(page,rows){			
		goodsService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
    $scope.itemCatList = [];
	$scope.findItemCatList = function(){
        itemCatService.findAll().success(function(response){
           for(var i = 0;i<response.length;i++){
               $scope.itemCatList[response[i].id] = response[i].name;
           }
        })
    }




	$scope.upload = function(){
		uploadService.upload().success(function(response){
			if(response.success){
				$scope.imgEntity.url = response.message;
			}
		})
	}

	$scope.saveImg = function(){
		$scope.entity.goodsDesc.itemImages.push($scope.imgEntity);
	}

	$scope.delImg =  function(index){
		$scope.entity.goodsDesc.itemImages.splice(index,1);
	}

	$scope.findByParentId = function(id){
		itemCatService.findByParentId(id).success(function(response){
			$scope.itemCat1List = response;
		})
	}

	$scope.$watch("entity.goods.category1Id",function(newValue,oldValue){
		itemCatService.findByParentId(newValue).success(function(response){
			$scope.itemCat2List = response;
			$scope.itemCat3List = [];
			$scope.brandIds = [];
		})
	})

	$scope.$watch("entity.goods.category2Id",function(newValue,oldValue){
		itemCatService.findByParentId(newValue).success(function(response){
			$scope.itemCat3List = response;
			$scope.brandIds = [];
			$scope.entity.goods.typeTemplateId = "未选择";
		})
	})
	$scope.$watch("entity.goods.category3Id",function(newValue,oldValue){
		itemCatService.findOne(newValue).success(function(response){
			$scope.entity.goods.typeTemplateId = response.typeId;
			$scope.findSpecList($scope.entity.goods.typeTemplateId);
			typeTemplateService.findOne(response.typeId).success(function(response){
					$scope.typeTemplate = response;
					$scope.typeTemplate.brandIds = JSON.parse(response.brandIds);
					$scope.entity.goodsDesc.customAttributeItems = JSON.parse($scope.typeTemplate.customAttributeItems);
			})
		})

	})

	$scope.findSpecList = function(id){
		typeTemplateService.findSpecList(id).success(function(response){
			$scope.specList = response;
		})
	}

	$scope.updateSelectItems = function($event,name,value){
		var object = $scope.searchObject($scope.entity.goodsDesc.specificationItems,name);
		if(object == null){
			var obj = {attributeName:name,attributeValue:[value]}
			$scope.entity.goodsDesc.specificationItems.push(obj);
		}else{
			if($event.target.checked){
				object.attributeValue.push(value);
			}else{
				var index = object.attributeValue.indexOf(value);
				object.attributeValue.splice(index,1);
				if(object.attributeValue.length == 0){
					var oIndex = $scope.entity.goodsDesc.specificationItems.indexOf(object);
					$scope.entity.goodsDesc.specificationItems.splice(oIndex,1);
				}
			}
		}
		$scope.createTableRow();
	}

	$scope.searchObject = function(list,name){

		for(var i = 0;i<list.length;i++){
			if(list[i].attributeName == name){
				return list[i];
			}
		}
		return null;
	}
// [{"网络":"3G","尺寸":"4寸"},{"网络":"3G","尺寸":"5寸"}
// ,{"网络":"4G","尺寸":"4寸"},{"网络":"4G","尺寸":"5寸"}]


	$scope.entity = {goods:{},goodsDesc:{itemImages:[],customAttributeItems:[],specificationItems:[]},items:[]};

	$scope.createTableRow = function(){
        $scope.oldList = JSON.parse(JSON.stringify($scope.entity.items));
		$scope.entity.items = [{spec:{},price:0,num:999,status:0,isDefault:0}];
		var items = $scope.entity.goodsDesc.specificationItems;
		for(var i = 0;i<items.length;i++){
            $scope.entity.items = $scope.createItem(items[i].attributeName,items[i].attributeValue);
        }
		if(items.length == 0 ){
            $scope.entity.items = [];
        }
        for(var i = 0;i<$scope.oldList.length;i++){
           var obj =  $scope.isContainSpec($scope.oldList[i].spec,$scope.entity.items);
            if(obj != null){
                obj.price =  $scope.oldList[i].price;
                obj.num =  $scope.oldList[i].num;
                obj.isDefault =  $scope.oldList[i].isDefault;
                obj.status =  $scope.oldList[i].status;
            }
        }

	}

	$scope.isContainSpec = function(spec,list){
	  var specStr =   JSON.stringify(spec)
	    for(var i = 0 ;i<list.length;i++){
	        var itemStr = JSON.stringify(list[i].spec);
	        if( itemStr== specStr){
	            return list[i];
            }
        }
	    return null;
    }

	$scope.createItem = function(attributeName,attributeValue){
		var list = [];
		for(var i = 0;i<$scope.entity.items.length;i++){
				var oldValue = $scope.entity.items[i];//{spec:{},price:0,num:999,status:0,isDefault:0}
			for(var j = 0;j<attributeValue.length;j++){
				var newValue = JSON.parse(JSON.stringify(oldValue));
				newValue.spec[attributeName] = attributeValue[j];
				list.push(newValue);
			}
		}
		return list;
	}


});	
