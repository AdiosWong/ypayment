
(function($){

define(function(require, exports, module){
    require('../Y-script/Y-window.js');
    
	Y.inherit('SearchTip','Tip',{
	    doInit: function(cfg){
		    this.callBase('doInit','Tip',cfg);
		    this.target = $(cfg.target);
		    var target = this.target;
			if(!target) {
			   Y.handlerError('TranslateTip类必须配置target');
			   return;
			}
			var _this = this;
			this.bind(target,'keyup',function(e){
				_this.setItim(this);
			},cfg.bindType);
			this.bind(target,'keydown',function(e){ 
				e = e || event;
				var keyCode = e.keyCode || e.which || e.charCode;
				if(_this.selectEle)_this.selectEle.removeClass(cfg.overCss);
				if(keyCode == 40){
					if(_this.el.html() == '')_this.setItim(target,true);
					_this.hover((_this.selectEle && _this.selectEle.next()[0] && _this.selectEle.next()) || _this.el.find(cfg.item + ":first"));
					_this.setValue(_this.selectEle);
				}else if(keyCode == 38){
					if(_this.el.html() == '')_this.setItim(target,true);
					_this.hover((_this.selectEle && _this.selectEle.prev()[0] && _this.selectEle.prev()) || _this.el.find(cfg.item + ":last"));
					_this.setValue(_this.selectEle);
				}else if(keyCode == 13){
					_this.clear();
					if(!_this.selectEle){
						return;
					}
					_this.select(_this.selectEle[0]);
				}else{
					_this.hover(_this.selectEle);
				}
			},cfg.bindType);
			this.bind(target, 'focus click', function(e){
				isNotBlur = false;
				if(_this.notShow){
					return _this.hide();		
				}
				_this.makeList($.trim($(this).val()));
				_this.show(cfg.speed);
			},cfg.bindType);
			var isNotBlur = false;
			this.bind(target, 'blur', function(e){
				_this.clickSts = false;
				if(isNotBlur)return;
			    _this.hide(cfg.speed);
			},cfg.bindType);
			this.el.append();
			var ul = this.ul = $('<ul>');
			this.el.append(ul);
			ul.addClass(this.cfg.css);
			ul.mousedown(function(){
				isNotBlur = true;
			});
			$(ul).delegate("." + cfg.css + " " + cfg.item , 'mousedown' , function(){
				_this.select(this);
			});
			$(ul).delegate("." + cfg.css + " " + cfg.item , 'mouseover' , function(){
				_this.hover($(this));
			});
			$(ul).delegate("." + cfg.css, 'mouseleave' ,function(){
				_this.selectEle = null;
				_this.notShow = false;
				_this.hide();
			});
		},
		clear:function(){//清空 隐藏
			this.el.html('');
			this.hide(this.cfg.speed);
		},
		setItim:function(ele,sts){//设置子级
			var val = $.trim($(ele).val());
			if(!sts && this.last == val)return this.show(this.cfg.speed);
			this.selectEle = null;
			this.last = val;
			var arr = this.makeList(val);
			if(arr.length)this.show(this.cfg.speed);
			else this.hide(this.cfg.speed);
		},
		hover:function(ele){//覆盖样式处理
			if(!ele)return;
			if(this.selectEle)this.selectEle.removeClass(this.cfg.overCss);
			this.selectEle = ele.addClass(this.cfg.overCss);
		},
		select:function(ele){//选择事件
			this.clickSts = true;
			this.target.val(this.last = ele.data);
			this.hide(this.cfg.speed);
			this.fire('select' , $(this) , this.data);
			this.selectEle = null;
		},
		setValue:function(obj){//给input赋值
			var val = (obj && obj[0] && obj[0].data) || obj;
			this.target.val(this.last = val);
		},
		makeList:function(s){//生成查询的列表
			var cfg = this.cfg;
			var m = Math.abs(cfg.showNumbers);
			var data = cfg.data;
			var fontColor = cfg.fontColor;
			var ul = this.ul.html('');
			var Arr = [];
			for(var i = 0 ; i < data.length ; i++){
				var cell = data[i];
				var index = cell.indexOf(s);
				if(index != -1){
					if(!Arr[index]){
						Arr[index] = [];
					}
					Arr[index].push(cell);
				}
			}
			var count = 0;
			ul.css({
				height:'auto',
				'overflow-y':''
			})
			for(i = 0 ; i < Arr.length ; i++){
				var r = Arr[i];
				if(r){
					for(var j = 0 ; j < r.length ; j++){
						if(s && !m)return Arr;
						cell = r[j];
						var row = cell.split(s);
						var div = $("<li>");
						div.width(this.target.outerWidth()).css('margin','');
						div.addClass(cfg.itemCss);
						div[0].data = cell;
						div.html(row.join('<font style="color:' + fontColor + ';">' + s + '</font>'));
						ul.append(div);
						m--;
						count ++ ;
					}
				}
			}	
			if(count > 10){
				ul.css({
					height:200,
					'overflow-y':'scroll'
				})
			}
			return Arr;
		},
		doShow: function(speed){
			this.el.css('padding',0);
		    this.callBase('doShow', 'Window', speed);
		    var XY = this.getPosition();
			$('body').append(this.el);
			this.el.css({
				left: XY.x,
				top: XY.y
			});
		},
		getPosition: function(){
			var cfg = this.cfg;
			var ele = this.target;
			var pos = ele.offset();
		 	return {x: pos.left,y: pos.top + cfg.spacing + ele[0].offsetHeight};
		}
	});
	
	Y.SearchTip.defaults = $.extend({},Y.Tip.defaults,{
		autoDisappear: false,
		css: 'select-combo-tip',
		renderTo: 'body',
		spacing:5,
		speed: 0,
		el:'ul',
		item:'li',
		data: [],
		overCss: 'select-light-tip',
		itemCss: 'select-item-tip',
		bindType: 'bind',
		showNumbers:10
	});
	
});
  
})($);