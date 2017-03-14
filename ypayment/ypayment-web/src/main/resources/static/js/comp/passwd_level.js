/**
 * @fileoverview tab组件，支持click, hover事件
 * @author yangle | yorsal.coms
 * @created  2012-05-09
 * @updated  2012-05-09
 */


define(function(require, exports, module) {
  
  //demo 所有的JS按照这种模式来写
  var that = function(pwd){
	
		var p1 = (pwd.search(/[a-zA-Z]/)!=-1) ? 1 : 0;
		var p2 = (pwd.search(/[0-9]/)!=-1) ? 1 : 0;
		var p3 = (pwd.search(/[^A-Za-z0-9_]/)!=-1) ? 1 : 0;
		var obj = $('.fm-note');
		var pa = p1 + p2 + p3;
		if(pa == 1){
			obj.find('i')[0].className = 'h';
			obj.find('strong')[0].className = 'h';
			obj.find('strong').html('低');
		}else if(pa == 2){
			obj.find('i')[0].className = 'o';
			obj.find('strong')[0].className = 'o';
			obj.find('strong').html('中');
		}else if(pa == 3){
			obj.find('i')[0].className = 'g';
			obj.find('strong')[0].className = 'g';
			obj.find('strong').html('高');
		}
		
	}
  

  
  
  
  return that; 
  
  
  
});