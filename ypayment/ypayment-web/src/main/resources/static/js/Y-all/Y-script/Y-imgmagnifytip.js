
(function($){

define(function(require, exports, module){
    require('../Y-script/Y-tip.js');
   
	Y.inherit('ImgMagnifyTip','Tip',{//图片在旁边放大处理
	    doInit: function(cfg){
			cfg.renderTo = 'body';
		    this.callBase('doInit', 'Tip', cfg);
			var target = this.target;
			if(!target) {
			   Y.handlerError('ImgMagnifyTip类必须配置target');
			   return;
			}
			var _this = this;
			this.bind(target, 'mouseenter', function(e){
				clearTimeout(_this.timer);
				_this.el.html('').append($(this).clone()
											.width(_this.width = $(this).width() * cfg.magnify)
											.height(_this.height = $(this).height() * cfg.magnify));
			    _this.show(cfg.speed);
				
			});
			
			this.bind(target, 'mouseleave', function(e){
				_this.timer = setTimeout(function(){
					_this.hide(cfg.speed);
				},200);
			});
			this.bind(this.el, 'mouseleave', function(e){
			    _this.timer = setTimeout(function(){
				  	_this.hide(cfg.speed);
				},200);
			});
			this.bind(this.el, 'mouseenter', function(e){
			    clearTimeout(_this.timer);
			});
		},
		doShow: function(speed){
		    this.callBase('doShow','Window',speed);
			this.rePosition();
			this.isDoSize = true;
		},
		rePosition:function(){
			var XY = this.getPosition();
			var ele = this.target;
			var w = ele.width(),h = ele.height();
			if(XY.x + w + this.width > $(window).width()){
			   XY.x -= this.width + 50;
			}else{
			   XY.x += w;
			}
			if(XY.y + this.height > $(window).height() + $(window).scrollTop()){
			   XY.y -= this.height + 10;
			}
			this.el.css({
				left: XY.x,
				top: XY.y
			});
			return this;
		},
		getPosition:function(){
			var ele = this.target;
			var pos = ele.offset();
		 	return {x:pos.left,y: pos.top};
		}
	});
	
	Y.ImgMagnifyTip.defaults = $.extend({},Y.Tip.defaults,{
		autoDisappear:false,
		el:'div',
		content:'<div>',
		magnify:2
	});
});
  
})($);