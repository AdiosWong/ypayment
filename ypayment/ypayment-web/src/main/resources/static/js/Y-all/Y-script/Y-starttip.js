
(function($){

define(function(require, exports, module){
    require('../Y-script/Y-window.js');
    
	Y.inherit('StartTip','Tip',{//定位的startTip有缺陷 如果页面变动 使input位置改变 但是tip不会触发事件跟着改变
	    doInit: function(cfg){
			cfg.renderTo = 'body';
		    this.callBase('doInit', 'Tip', cfg);
			var target = this.target;
			if(!target) {
			   Y.handlerError('StartTip类必须配置target');
			   return;
			}
			var _this = this;
			this.bind(target, 'focus', function(e){
			    _this.hide(cfg.speed);
			});
			this.bind(target, 'blur', function(e){
			    if($(this).val($.trim($(this).val())).val())return;
				 _this.show(cfg.speed);
			});
			this.bind(this.el, 'click', function(e){
			    _this.hide(cfg.speed);
				target.focus();
			});
			this.doShow(0);
		},
		doShow: function(speed){
		    this.callBase('doShow','Window',speed);
			var cfg = this.cfg;
			if(cfg.showInfo){
			    if(typeof cfg.showInfo == 'function'){
				  	this.el.html(cfg.showInfo.call(this.target));
				}else{
					this.el.html(cfg.showInfo);
				}
			}
			this.rePosition();
			var _this = this;
			setTimeout(function(){_this.target.blur()},5);
		},
		rePosition:function(){
			var XY = this.getPosition();
			if(this.target.val())this.hide(0);
			this.el.css({
				"fontSize":this.target.height(),
				width:'auto',
				height:'auto',
				left: XY.x,
				top: XY.y
			});
			return this;
		}
	});
	Y.StartTip.defaults = $.extend({},Y.Tip.defaults,{
		autoDisappear: false,
		css: 'wnd-tip-starttip',
		renderTo : 'body',
		padding: 2,
		spacing : 0,
		speed: 0,
		align: 'bottom'
	});
	
});
	
})($);