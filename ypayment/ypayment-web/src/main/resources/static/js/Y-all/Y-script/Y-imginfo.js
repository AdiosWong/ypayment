(function($){
define(function(require, exports, module){
    Y.inherit('ImgInfo','component',{
	    init : function(cfg){
			var content = cfg.content;
			if(!content) return;
			content = $(content);
			var _this = this;
			if(cfg.liveSts){
				content.live('mouseenter', function(){
					var $this = $(this);
					_this.mouseenterHandle(this);
				});
				content.live('mouseleave', function(){
					_this.mouseleaveHandle(this);
				});
			}else{
				content.bind('mouseenter', function(){
					var $this = $(this);
					_this.mouseenterHandle(this);
				});
				content.bind('mouseleave', function(){
					_this.mouseleaveHandle(this);
				});
			}
			this.callBase('init','component',cfg);
		},
		mouseenterHandle:function(ele){
			var cfg = this.cfg;
			var obj = $(ele);
			if(!ele.wrapDiv){
				var div = ele.wrapDiv = $("<div style='overflow:hidden;'>").width(obj.width()).height(obj.height());
				obj.wrap(div);
				var height = "100";
				if(typeof cfg.height == 'string'){
					if(/\%/.test(cfg.height)){
						height = obj.height() * parseFloat(cfg.height) / 100;
					}else height = parseFloat(cfg.height);
				}else if(typeof cfg.height == 'function'){
					height = cfg.height.call(ele);
				}
				var html = obj.attr("title");
				if(typeof cfg.key == 'string'){
					html = obj.attr(cfg.key);
				}else if(typeof cfg.height == 'function'){
					html = cfg.key.call(ele);
				}
				var showDiv = ele.showDiv = $("<div>").width(obj.width())
								.height(height)
								.html(html)
								.css({
									marginTop: 0
								}).addClass(cfg.class)
								.animate({
									marginTop: -height
								}, cfg.speed);
				showDiv.bind('mouseenter mousemove',function(){
					this.enterSts = true;
				});
				showDiv.mouseout(function(){
					this.enterSts = false;
					var _this = this;
					setTimeout(function(){
						if(_this.showSts)return;
						
						$(_this).stop(true,true).animate({
								marginTop:0
							},cfg.speed);
					},10);
				});			
				obj.after(showDiv);
			}else{
				if(ele.showDiv[0].enterSts)return;
				ele.showDiv .stop(true,true)
							.animate({
								marginTop:-ele.showDiv.height()
							},cfg.speed);
			}
			ele.showDiv[0].showSts = true;
		},
		mouseleaveHandle:function(ele){
			var cfg = this.cfg;
			if(!ele.showDiv)return;
			ele.showDiv[0].showSts = false;
			setTimeout(function(){
				if(ele.showDiv[0].enterSts)return;
				ele.showDiv.stop(true,true)
							.animate({
								marginTop:0
							},cfg.speed);
			},10);
		}
    });
	Y.ImgInfo.defaults = $.extend({},{
		liveSts: true,//如果选择live模式 context需要JQ选择器字符串形式
		content: 'img',//需要弹层的对象
		height: '20%',//弹层高度 也可以是函数 this代表img
		class: 'imgInfo',//弹层样式
		key: 'title',//显示图片的某属性 也可以传递函数 this代表当前img标签
		speed: 500 //动画速度
	});
});
})($);
