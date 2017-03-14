/* 
    jquery.selectBank.js

*/
(function($){
    $.fn.selectBranch = function(options){
		var opts = $.extend({}, options);
	    var provinceOpts,cityOpts,branchOpts;
		if(opts.comboOpts) {
		    provinceOpts = $.extend({itemsHeight:180},opts.comboOpts.province || {});
			branchOpts = $.extend({itemsHeight:180},opts.comboOpts.branch || {});
			cityOpts = $.extend({itemsHeight:180},opts.comboOpts.city || {});
		}
		var self = $(this),
			provinceList = self.combobox(provinceOpts),
			cityList = self.combobox(cityOpts),
			branchList = self.combobox(branchOpts);
			cityList.show();
			provinceList.show();
			branchList.show();
		    var bankSel = $.combobox.prototype.toJqObj(opts.bankSelect || "<input>");
		init();
		
		//init
		function init(){
			if(!opts.noInit) {
   			    handleProvinceList();
			}
			//bind province change
			provinceList.change(function(value){
				if (value){
					handleCityList(value);
				}
				else {
					reset();
				}
			});
			
			//bind city change
			cityList.change(function(value){
				if (value){
					handleBranchList(value,bankSel.getValue?bankSel.getValue():bankSel.val());
				}
				
			});
			
			
		}
		
		//reset all
		function reset(){
			cityList.clear();
			branchList.clear();
		}
		
		
		function setProvinceList(areaInfo){
		    var fn = opts.toProvinceItems || getProvinceStore;
		    var store = fn(areaInfo);
			provinceList.clear();
			provinceList.addItem(store);
		}
		function getProvinceStore(areaInfo) {
		    var store = [];
		    $.each(areaInfo,function(i,item){
			    var aitem = item.provinceName;
				store.push(aitem);
			});
			return store;
		}
		function setCityList(areaInfo,provinceName){
		    var fn = opts.toCityItems || getCityStore;
		    var store = fn(areaInfo,provinceName);
			cityList.clear();
			cityList.addItem(store);
		}
		function getCityStore(areaInfo,provinceName) {
		    var store = [];
		    $.each(areaInfo,function(i,item){
			    if(item.provinceName === provinceName) {
			        var citys = item.cityList;
				    $.each(citys,function(j,item){
					    var aitem = {value: item.branchDistrictNo,text: item.cityName};
						store.push(aitem);
					});
					return false;
				}
			});
			return store;
		}
		//handle province list
		function handleProvinceList(callback){
		    if(opts.areaInfo) {
			    setProvinceList(opts.areaInfo);
				if(callback) {
				    callback();
				}
				if(result.afterProvince) {
					result.afterProvince();
				}
				return;
			}
			if(!options.provinceUrl) return;
			$.ajax({
				url: options.provinceUrl,
				dataType: 'json',
				success: function(res){
				    var fn = opts.toProvinceItems ||  toProvinceItems
				    var items = fn(res);;
					provinceList.clear();
					provinceList.addItem(items);
					if(callback) {
						callback();
					}
					if(result.afterProvince) {
					    result.afterProvince();
					}
				},
				error: function(){
				}
			});
		}
		
		function toProvinceItems(res){
		    var items = [];
		    if (res.length){
				for (var i = 0; i < res.length; i++){
					items.push({value: res[i].branchDistrictNo,text: res[i].branchDistrictName});
				}
			}
			return items;
		}
		
		//handle city list
		function handleCityList(code,callback){
		    if(opts.areaInfo) {
			    setCityList(opts.areaInfo,code);
				if(callback) {
				    callback();
				}
				if(result.afterCity) {
					result.afterCity();
				}
				return;
			}
			if(!options.cityUrl) return;
			$.ajax({
				url: options.cityUrl + '?code=' + code,
				dataType: 'json',
				success: function(res){
					var fn = opts.toCityItems ||  toCityItems;
					var items = fn(res);
					cityList.clear();
					cityList.addItem(items);
					if(callback) {
					    callback();
					}
					if(result.afterCity) {
					    result.afterCity();
					}
				},
				error: function(){
				
				}
			});
		} 
		
		function toCityItems(res){
		    var items = [];
		    if (res.length){
				for (var i = 0; i < res.length; i++){
					items.push({value: res[i].branchDistrictNo,text: res[i].areaName});
				}
			}
			return items;
		}
		
		//handle Branch list
		function handleBranchList(code, bankId,callback){
			$.ajax({
				url: options.branchUrl + '?districtNo='+ code +'&bankId='+ bankId,
				dataType: 'json',
				success: function(res){
					var fn = opts.toBranchItems || toBranchItems;
					var items = fn(res);
					
					branchList.clear();
					branchList.addItem(items);
					if(callback) {
					    callback();
					}
					if(result.afterBranch) {
					    result.afterBranch();
					}
					
				},
				error: function(){
				
				}
			});
		} 
		function toBranchItems(res){
		    var items = [];
			for (var i = 0; i < res.length; i++){
				items.push({value: res[i].bankId,text: res[i].branchName});
			}
			return items;
		}
	    var result = {
	        provinceList: provinceList,
		    cityList: cityList,
		    branchList: branchList,
		    getValue: function(){
		        return {province: provinceList.getValue(),city: city.getValue(),branch: branch.getValue()};
		    },
		    getText: function(){
		    	return {province: provinceList.getText(),city: city.getText(),branch: branch.getText()};
		    },
			afterProvince: function(){
			    if(provinceList.getItemList().length>0) {
				    provinceList.setIndex(0);
				}
			},
			afterCity: function(){
			    if(cityList.getItemList().length>0) {
				    cityList.setIndex(0);
				}
			},
			afterBranch: function(){
			    if(branchList.getItemList().length>0) {
				    branchList.setIndex(0);
				}
			},
			setValue: function(info,type){
			    type = type || 'value';
			    if(typeof info != 'object') {
				    return;
				}
				var fn1 = result.afterProvince, fn2 = result.afterCity,fn3 = result.afterBranch;
				result.afterProvince = function(){
				    provinceList.setSelect(info.province,type);
					result.afterProvince = fn1;
				}
				result.afterCity = function(){
				    cityList.setSelect(info.city,type);
					result.afterCity = fn2;
				}
				result.afterBranch = function(){
				    branchList.setSelect(info.branch,type);
					result.afterBranch = fn3;
				}
				handleProvinceList();
			},
			reStart: function() {
				handleProvinceList();
			},
			setAreaInfo: function(areaInfo,first,noInit){
			    opts.areaInfo = areaInfo;
				if(!first || typeof first != 'object') {
				    if(!noInit) {
				        handleProvinceList();
					}
					return;
				}
			    var province,city,branch,type;
				if(first && first.values){
			        province = first.values.province;
				    city = first.values.city;
				    branch = first.values.branch;
			        type = "value";
				} 
				else if(first && first.texts){
			        province = first.texts.province;
				    city = first.texts.city;
				    branch = first.texts.branch;
			        type = "text";
				}
				var fn1 = result.afterProvince,fn2 = result.afterCity,fn3 = result.afterBranch;
				result.afterProvince = function(){
				    if(province) {
					    provinceList.setSelect(province,type);
					}
					result.afterProvince = fn1;
				}
				result.afterCity = function(){
				    if(city) {
				        cityList.setSelect(city,type);
					}
					result.afterCity = fn2;
				}
				result.afterBranch = function(){
				    if(branch) {
				        branchList.setSelect(branch,type);
					}
					result.afterBranch = fn3;
				}
			    handleProvinceList();
			}
	    };
	    return result;
    };
})(jQuery);
