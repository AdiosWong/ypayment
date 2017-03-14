// JavaScript Document
(function($){

define(function(require, exports, module){
    require("../Y-script/Y-window.js");
	if(Y.ImgPlayer){
		return;
	}
    Y.inherit('ImgPlayer','Window',{
		addImg:function(ele){
			var img = this.img;
			var _this = this;
			this.eleArr.add(ele);
			$('body').delegate(ele,'click',function(){
				_this.doClick(this);
			});
		},
		setTimes:function(time){
			this.cfg.times = time || this.cfg.times || 1000;
		},
		startPaly:function(){
			this.doStartPaly();
			this.fire('startPaly');
		},
		doStartPaly:function(){
			var cfg = this.cfg, _this = this;
			if(this.timer)clearInterval(this.timer);
			this.timer = setInterval(function(){
				_this.doGotoShow(++cfg.index);
			},cfg.times || 1000);
		},
		stopPaly:function(){
			this.doStopPaly();
			this.fire('stopPaly');
		},
		doStopPaly:function(){
			if(this.timer)clearInterval(this.timer);
		},
		doClick:function(ele){
			var img = this.img;
			if(this.eleArr.index(ele) == -1)this.eleArr.add(ele);
			img.attr('src',$(ele).attr('src'));
			this.ele = ele;
			this.cfg.index = this.eleArr.index(ele);
			this.load();
		},
	    doInit: function(cfg){
		    var _this = this; 
			var img = this.img = $("<img>");
			this.eleArr = $(cfg.eleArr);
			$('body').delegate(cfg.eleArr,'click',function(){
				_this.doClick(this);
			})
			img.mousedown(function(e){ //鼠标拖动图片
				e.preventDefault();
				_this.doStopPaly();
				this.downSts=true;
				img.css({
					cursor:'crosshair'
				});
				e = e || event;
				this.lastX = e.pageX;
				this.lastY = e.pageY;
				_this.stopPropagation(e);
			});
			img.mouseup(function(){
				this.downSts = false;
				img.css({
					cursor:'pointer'
				});
			});
			$(document).mouseup(function(){
				if(!img[0]) return;
				img[0].downSts = false;
				img.css({
					cursor: 'pointer'
				});
			});
			var parent;
			img.mousemove(function(e){
				e.preventDefault();
				prevBtn.removeClass('prev-photo-hover');
				nextBtn.removeClass('next-photo-hover');
				_this.stopPropagation(e);
				if(!this.downSts)return;
				e = e || event;
				var x = e.pageX - this.lastX;
				var y = e.pageY - this.lastY;
				this.lastX = e.pageX;
				this.lastY = e.pageY;
				parent = img.parent();
				parent.css({
					left: parseInt(parent.css('left')) + x,
					top: parseInt($(parent).css('top')) + y
				});
			});
			var cwBtn, acwBtn, biggerBtn, smallerBtn ,prevBtn ,nextBtn, fullBtn;
			if(!cfg.mainDiv){
				$('body').append(cfg.mainDiv = $('<div class="photo-browse" style="display:none;"><div class="main-photo"></div><div class="mod-area" align="center"><p class="path-title"></p><p class="info-title"></p><a href="javascript:;" class="magnify"></a><a href="javascript:;" class="lessen"></a><a href="javascript:;" class="rota-l"></a><a href="javascript:;" class="rota-r"></a><a href="javascript:;" class="full"></a></div><a href="javascript:;" class="prev-photo">上一张</a><a href="javascript:;" class="next-photo">下一张</a><a href="javascript:;" class="close-photo close">关闭</a></div>'));
			}
			cfg.mainDiv.find('p').hide();
			cfg.mainDiv.find('a').hide();
			cfg.mainDiv = $(cfg.mainDiv);
			cfg.mainDiv.find(cfg.imgDiv).append(img);
			if(cfg.cwAbled) {
			    cwBtn = cfg.cwBtn = cfg.mainDiv.find(cfg.cwBtn).show();
				cwBtn.click(function(e){
					_this.doStopPaly();
					_this.transform(90);
					_this.stopPropagation(e);
				});
				 acwBtn = cfg.acwBtn = cfg.mainDiv.find(cfg.acwBtn).show();
				acwBtn.click(function(e){
					_this.doStopPaly();
					_this.transform(-90);
					_this.stopPropagation(e);
				});
			}
			if(cfg.zoomAbled) {
			    biggerBtn = cfg.biggerBtn = cfg.mainDiv.find(cfg.biggerBtn).show();
				biggerBtn.click(function(e){
					_this.doStopPaly();
					_this.zoom(0.1);
					_this.stopPropagation(e);
				});
				smallerBtn = cfg.smallerBtn = cfg.mainDiv.find(cfg.smallerBtn).show();
				smallerBtn.click(function(e){
					_this.doStopPaly();
					_this.zoom(-0.1);
					_this.stopPropagation(e);
				});
			}
			if(cfg.fullAbled) {
			    fullBtn = cfg.fullBtn = cfg.mainDiv.find(cfg.fullBtn).show();
				fullBtn.attr('title','全屏');
				fullBtn.click(function(e){
					_this.doStopPaly();
					_this.fullHandler(this);
					_this.stopPropagation(e);
				});
			}
			if(cfg.prevBtn) {
				prevBtn = cfg.prevBtn = cfg.mainDiv.find(cfg.prevBtn).show();
				prevBtn.click(function(e){
					_this.doStopPaly();
					_this.doShow(-1);
					_this.stopPropagation(e);
				});
			}
			if(cfg.nextBtn) {
				nextBtn = cfg.nextBtn = cfg.mainDiv.find(cfg.nextBtn).show();
				nnn = nextBtn
				nextBtn.click(function(e){
					_this.doStopPaly();
					_this.doShow('+1');
					_this.stopPropagation(e);
				});
			}
			if(cfg.wheelSts){//中键事件
				function get(e){
					e = e || event;
					_this.event = e;
					var target = e.target || e.srcElement;
					if(target && (target.nodeName == 'IMG' || target.localName == 'IMG')){
						_this.imgOverSts = true;
					}else{
						_this.imgOverSts = false;
					}
					var wheelDelta = e.wheelDelta || e.detail;
					if(Math.abs(wheelDelta) >= 120){
						if(wheelDelta > 0){
							_this.zoom(0.1);
						}else{
							_this.zoom(-0.1);
						}
					}else{//firefox
						if(wheelDelta < 0){
							_this.zoom(0.1);
						}else{
							_this.zoom(-0.1);
						}
					}
					if(e.stopPropagation) {
						e.stopPropagation();
					} else {
						e.cancelBubble = true;
					}

					if (e.preventDefault) {
						e.preventDefault();
					} else {
						e.returnValue = false;
					}
				}
				var wheelType = navigator.userAgent.indexOf("Firefox") > 0 ? 'DOMMouseScroll' : 'mousewheel';
				var eventEle = $(cfg.mainDiv)[0];
				if(document.addEventListener){
					eventEle.addEventListener(wheelType,function(e){
						get(e); 
					});
				}else{
					eventEle.attachEvent('onmousewheel',function(e){
						get(e); 
					});
				}
			}
			if(cfg.moveCutSts){//左右分屏
				$(cfg.mainDiv).bind('mousemove',function(e){
					e.preventDefault();
					if(!this.overClickSts) return;
					e = e || event;
					var x = e.pageX;
					var left = $(this).offset().left;
					var width = $(this).width();
					if(x - left > width / 2){
						this.clickType = 'next';
						nextBtn.addClass('next-photo-hover');
						prevBtn.removeClass('prev-photo-hover');
					}else{
						this.clickType = 'prev';
						prevBtn.addClass('prev-photo-hover');
						nextBtn.removeClass('next-photo-hover');
					}
				});
				$(cfg.mainDiv).bind('click',function(e){
					_this.doStopPaly();
					e.preventDefault();
					if(!this.overClickSts) return;
					e = e || event;
					if(this.clickType == 'next'){
						_this.doShow('+1');
					}else{
						_this.doShow(-1);		
					}
					_this.stopPropagation(e);
					this.overClickSts = true;
				});
				$(cfg.mainDiv).bind('mousedown',function(e){
					e.preventDefault();
				});
				$(cfg.mainDiv).bind('mouseover',function(e){
					this.overClickSts = true;
				});
				$(cfg.mainDiv).bind('mouseout',function(e){
					this.overClickSts = false;
					prevBtn.removeClass('prev-photo-hover');
					nextBtn.removeClass('next-photo-hover');
				});
			}
			$('.close').click(function(e){
				_this.doStopPaly();
				_this.stopPropagation(e);
				_this.hideBefore();
			})
			if(cfg.titleEle)cfg.titleEle = cfg.mainDiv.find(cfg.titleEle).show();
			if(cfg.pathEle)cfg.pathEle = cfg.mainDiv.find(cfg.pathEle).show();
			cfg.mainDiv.find('.close').show();
			this.itemImg = img;
			cfg.content = cfg.mainDiv;
			this.callBase('doInit','Window',cfg);
		},
		transform:function(num){// num 转的角度 90 -90
			this.doTransform(num);
			this.fire('transform');
			return this;
		},
		doTransform:function(num){
			var img = this.itemImg;
			num -= num % 90;
			var degree = img.data('degree') || 0;
			degree += num;
			degree += 360;
			degree %= 360;
			this.setTransform(degree);
		},
		setTransform:function(degree){
			var img = this.itemImg;
			var dict = {'0': 'transform0','90': 'transform90', '180': 'transform180', '270': 'transform270'};
	        var cls = dict[degree];
	        img.removeClass(dict[90]).removeClass(dict[180]).removeClass(dict[270]).removeClass(dict[0]);
	        if(cls){
				img.addClass(cls);
				img.css({filter:'progid:DXImageTransform.Microsoft.BasicImage(rotation=' + degree / 90 + ')'})
			}
			img.data('degree',degree);
			this.moveCenter();
		},
		setZoomScale:function(a,b){
			var cfg = this.cfg;
			if(a)cfg.minScale = a;
			if(b)cfg.maxScale = b;
			return this;
		},
		zoom:function(num){// num是缩放的 比例 0.1放大 -0.1缩小
			this.doZoom(num);
			this.fire('zoom');
			return this;
		},
		doZoom:function(num){
			var img = this.itemImg;
			var cfg = this.cfg;
			var scale = img.data('scale') || 1;
			var newScale = ((scale + num) * 100 | 0) / 100;
			if(newScale > cfg.maxScale || newScale < cfg.minScale) return;
			this.setZoom(newScale, -num);
		},
		fullHandler:function(ele){
			var cfg = this.cfg;
			var _parent = cfg.mainDiv;
			if(!ele)ele = {};
			if(!ele.fullType)ele.fullType = "+";
			if(ele.fullType == "+"){
				ele.fullType = "-";
				$(ele).attr('title','取消全屏');
				this.full();
			}else{
				ele.fullType = "+";
				$(ele).attr('title','全屏');
				this.cancelFull();
			}
		},
		full:function(){
			this.doFull();
			this.fire('full');
			return this;
		},
		doFull:function(){
			var cfg = this.cfg;
			var _parent = cfg.mainDiv;
			this.defaultWidth = _parent[0].clientWidth;
			this.defaultHeight = _parent[0].clientHeight;
			_parent.width($(window).width()-20);
			_parent.height($(window).height()-20);
			this.moveCenter(1);
		},
		cancelFull:function(){
			this.doCancelFull();
			this.fire('cancelFull');
			return this;
		},
		doCancelFull:function(){
			var cfg = this.cfg;
			var _parent = cfg.mainDiv;
			_parent.width(this.defaultWidth);
			_parent.height(this.defaultHeight);
			this.moveCenter(0, _parent);
		},
		doShow:function(num){// num 可为'+1'字符串 向后1张, -1 向前1张, 1跳到第一张
			this.doGotoShow(num);
			this.fire('doShow');
			return this;
		},
		doGotoShow:function(num){
			var img = this.itemImg;
			var cfg = this.cfg;
			var length = $(cfg.eleArr).length;
			if(length <= 1) return;
			num += '';
			var _this = this;
			if(/\D/.test(num)){
				cfg.index = eval(cfg.index + num);
			}else{
				cfg.index = num;
			}
			cfg.index += length;
			cfg.index %= length;
			var ele = this.ele = $(cfg.eleArr)[cfg.index];
			img.fadeOut(200,function(){
				img.attr({
					src: $(ele).attr('src')
				});
				_this.load();
				img.fadeIn(300,function(){
					//_this.load();
				});
			})
			
		},
		hideBefore:function(){//隐藏前把全屏的还原
			var cfg = this.cfg;
			var fullBtn = cfg.fullBtn;
			this.thisShowSts = false;
			if(this.defaultWidth){
				this.el.css({
					width: this.defaultWidth,
					height: this.defaultHeight
				});
			}
			if(fullBtn && fullBtn[0]){
				fullBtn[0].fullType = "+";
				fullBtn.attr('title', '全屏');
			}
			this.hide();
			return this;
		},
		stopPropagation:function(e){//阻止冒泡
			var cfg = this.cfg;
			if($(cfg.mainDiv)[0]){
				$(cfg.mainDiv)[0].overClickSts = false;
			}
			e = e || window.event;
			if(e){
				e.stopPropagation();
			}
		},
		setImg:function(ele){//设置要显示的img 或者要跳转的位置
			var cfg = this.cfg;
			var _this = this;
			if(typeof ele != "object"){
				this.doShow(ele);
			}else{
				var arr = $(cfg.eleArr);
				var img = this.itemImg;
				if(ele[0])_this = ele[0];
				for(var i = 0 ; i < arr.length ; i++){
					if(arr[i] == ele){
						cfg.index = i;
						break;
					}
				}
				this.ele = ele;
				img.fadeOut(20,function(){
					img.attr({
						src: $(ele).attr('src')
					});
					_this.load();
					img.fadeIn(30);
				});
			}
			return this;
		},
		load:function(){//初始化
			this.doLoad();
			this.fire('load');
			return this;
		},
		doLoad:function(){
			var img = this.itemImg;
			img.parent().width(img.width());
			this.setZoom(0);
			this.setTransform(0);
			this.showInfo();
		},
		showInfo:function(){//显示某些信息
			var cfg = this.cfg;
			var ele = this.ele || $(cfg.eleArr)[cfg.index];
			if(cfg.titleEle && cfg.titleInfo){
				if(typeof cfg.titleInfo == 'function'){
					$(cfg.titleEle).html(cfg.titleInfo.call(ele));
				}else{
					$(cfg.titleEle).html($(ele).attr(cfg.titleInfo || 'title'));
				}
			}
			if(cfg.pathEle && cfg.pathInfo){
				if(typeof cfg.pathInfo == 'function'){
					$(cfg.pathEle).html(cfg.pathInfo.call(ele));
				}else{
					$(cfg.pathEle).html($(ele).attr(cfg.pathInfo || 'title'));
				}
			}
		},
		getTransform:function(){
			var img = this.itemImg;
			return img.data('degree');
		},
		setZoom: function(scale,s){
		    var w,h;
			var img = this.itemImg;
			if(!scale){
				img.removeAttr('width').removeAttr('height');
				img.data('scale',1);
				return;
			}
			var width = img.width(),height = img.height();
		    w = width * scale / (scale + s);
			h = height * scale / (scale + s);
			var parent = img.parent();
			var lastLeft = parseInt(parent.css('left')), lastTop = parseInt(parent.css('top'));
			if(this.imgOverSts){// 鼠标在图片上的时候 以鼠标位置放大缩小
				var x = this.event.pageX , y = this.event.pageY ;
				if(x == undefined){
					x = this.event.clientX + $(window).scrollLeft();
					y = this.event.clientY + $(window).scrollTop();
				}
				var left = x - img.offset().left, top = y - img.offset().top;
				var cssLeft , cssTop;
				var scaleLeft = left / width, scaleTop = top / height;
				lastLeft -= (w - width) * scaleLeft ;
				lastTop -= (h - height) * scaleTop ;
				parent.css({
					left: lastLeft,
					top: lastTop
				});
			}else{
				parent.css({
					left: lastLeft - (w - width) / 2,
					top: lastTop - (h - height) / 2
				});
			}
			img.attr({
			   width: w,
			   height: h
			});
			img.data('scale', scale);
		},
		getZoom:function(){
			var img = this.itemImg;
			return img.data('scale');
		},
		moveCenter:function(sts, ele, speed){//把图片居中 sts全屏 ele取消全屏对象
			this.doMoveCenter.apply(this,Array.prototype.slice.call(arguments));
			this.fire('moveCenter');
			return this;
		},
		doMoveCenter:function(sts, ele, speed){
			var cfg = this.cfg;
			speed = speed || 0;
			if(!this.thisShowSts){
				this.callBase('show', 'Window', speed);
				this.thisShowSts = true;
			}
			var img = this.itemImg;
			var parent = img.parent();
			var _img = new Image();
			_img.src = img.attr('src');
			var width = $(img).width();
			var height = $(img).height();
			var _this = this;
			if(!width){
				_img.onload = function(){
					width = $(img).width();
					height = $(img).height();
					setCss();
				}
			}else{
				setCss();
			}
			function setCss(){
				var pw = $(cfg.mainDiv).width();
				var ph = $(cfg.mainDiv).height();
				parent.css({
					position: 'relative',
					left: (pw - width) / 2,
					top: (ph - height) / 2 	
				});
				_this.reWndSize(sts,ele);
			}
			
		},
		reWndSize:function(sts,ele){//全屏的处理
			if(sts){//全屏
				this.el.css({
					left: 10,
					top: $(window).scrollTop() + 10
				});
			}else if(ele){//取消全屏
				this.el.css({
					left: ($(window).width()- ele.width()) / 2,
					top: $(window).scrollTop() + ($(window).height() - ele.height()) / 2
				});
			}	
		}
	});
	Y.ImgPlayer.defaults = $.extend({}, Y.Window.defaults, {
		simple: true,
		eleArr: '',//jq选择字符串 是为了配合live 必须
		renderTo: 'body',
		minScale: 1,
		maxScale: 2,
		
		mainDiv: '',//整体对象 如果没有则会默认生成 如果未配置maindiv 下面的对象都为默认
		imgDiv: '.main-photo',//放图片的div
		cwAbled:true,
		cwBtn: '.rota-r',//右旋
		acwBtn: '.rota-l',//
		zoomAbled:true,
		biggerBtn: '.magnify',//放大
		smallerBtn: '.lessen',
		fullAbled:true,
		fullBtn: '.full',//全屏
		prevBtn: '.prev-photo',//上一张
		nextBtn: '.next-photo',
		titleEle: '.info-title',//第二个显示信息的P
		titleInfo: 'title',//显示img的某属性
		pathEle: '.path-title',// 第一个显示信息的p
		pathInfo: 'path',//显示的信息 可配置函数 this为当前显示的页面中的img
		
		times:1000,
		wheelSts: true,//鼠标中键
		moveCutSts: true//左右分屏
	});	
});
  
})($);
