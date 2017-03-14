(function($){
define(function(require, exports, module){
    Y.inherit('Jrotate','component',{
	    init : function(cfg){
			if(!cfg.renderTo) return ;
			cfg.renderTo = $(cfg.renderTo);
			if(cfg.class){
				cfg.renderTo.addClass(cfg.class);
			}
			cfg.showNumber = cfg.showNumber || 0 ;
			cfg.showNumber += '';
			this.callBase('init','component',cfg);
			this.makeDefaultList(Math.max(cfg.startLength,cfg.showNumber.length),cfg.startNumber);
			this.setNumber(cfg.showNumber);
		},
		makeDefaultList:function(len,num){
			if(len < 1)return ;
			var cfg = this.cfg;	
			num = num || 0;
			num += '';
			while(num.length < len){
				num = '0' + num;
			}
			var cfg = this.cfg;
			for(var i = 0 ; i < len ; i++){
				if( cfg.renderTo.children('div').length>=cfg.maxLength)return;
				cfg.renderTo.prepend(this.makeList(num.charAt(i)));
			}
		},
		setNumber:function(num){
			if(isNaN(num))return;
			var cfg = this.cfg;	
			var str = num + '';
			var length = str.length;
			var len = cfg.renderTo.children('div').length;
			this.makeDefaultList(Math.max(cfg.startLength,length) - len);
			length = Math.max(length,len,cfg.startLength);
			while(num.length < length){
				num = '0' + num;
			}
			var _this = this;
			cfg.renderTo.children('div').each(function(index){
				index = length - index - 1;
				var char = num.charAt(index);
				if(this.showIndex == char) return;
				var obj = {ele:this,index:index};
				setTimeout(function(){_this.gotoIndex(obj.ele,char,1)},5);
			});
		},
		gotoIndex:function(ele,index,sts){
			ele = $(ele);
			var h = ele.children('div:first').height();
			var cfg = this.cfg;
			ele[0].showIndex = index;
			ele.animate({
				scrollTop  : index * h
			} , sts && cfg.speed);
		},
		makeList:function(num){
			num = num || 0;
			var _this = this;
			var ul = $("<div>");
			for(var i = 0 ; i < 10 ; i++){
				var li = $('<div>');
				ul.append(li.html(i));
			}
			setTimeout(function(){_this.gotoIndex(ul,num,0)},2);
			return ul;
		}
    });
	Y.Jrotate.defaults = $.extend({},{
		renderTo: '#div',//显示效果的对象
		startNumber: 1211,
		showNumber: 12121,
		maxLength : 0,
		startLength : 4,
		class: 'jrotate',//样式
		speed: 500 //动画速度
	});
});
})($);
