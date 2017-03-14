
function $_ex_window(){

  //扩展window组件,根据配置显示一个页面小窗体,在jquery对象上渲染一个遮罩，同时产生一个浮动窗体,具体配置请见$.littlewnd
    $.fn.window = function(options){
	    var opts = $.extend({},{
		    renderTo: options.renderTo || this.eq(0)
		},options);
        var wnd = new $.littlewnd(opts);
        if(!opts.hidden) wnd.show();
        return wnd;
    }
  //显示一个简单的模拟模态弹窗,用以模拟alert弹窗，但不会像alert一样引起中断,参数分别为 标题；文本；点击确定的回调函数
    $.fn.msgbox = function(title,msg,config){
	    var fn,opts = {};
		
		if(typeof config == 'function') {
		    fn = config;
		} else if(typeof config == 'object') {
		    fn = config.callback || config.fn;
			$.extend(opts,config);
		}
		var wndStyle = {};
		if(!$.browser.msie) {
		   wndStyle.width = 'auto';
		}
        var wnd = new $.littlewnd($.extend({
            title: title || '',
			modal: true,
			renderTo: 'body',
			container: 'span',
			wndStyle: wndStyle,
			minWidth: 300,
			close:function(){
			    if(fn) {
                    fn();
                }
			},
            content: $("<span>").append(
			    $("<span>").css({'display':'block'}).html(msg).addClass('msgbox-msg')).append(
				    $("<span>").css({'text-align':'center','display':'block'}).append('<br/>').append(
					    $("<span>").append(
						    $('<span>').html('确定')).addClass('fn-h-btn').click(function(){
                              
                              wnd.close();
                            })
			        )
				),
            autoSize: true
        },opts));
        wnd.show();
        return this;
    }
	
  /*书签组件，基于window组件
  扩展配置：
     autoDisappear：bool 点击任意位置关闭，默认为true
	 align：字符串 显示的位置方式，有八个值 left,top,right,buttom,left top,right top,left bottom,right bottom
	 spacing：数值 与目标的间隔
  */
  
  $.fn.bookmark = function(opintion,config){
      var info; 
	  var opts = $.extend({},$.fn.bookmark.defaults);
      if(typeof opintion == 'string') {
	      info = $.littlewnd.prototype.toJqObj(opintion);
	  } else {
	      info = $.littlewnd.prototype.toJqObj(opintion.content);
		  $.extend(opts,opintion);
		  opts.style = $.extend({},$.fn.bookmark.defaults.style,opintion.style || {});
	  }
	  info.addClass(opts.markCss);
	  var _this = this;
	  function getXY(align){
	      var spacing = opts.spacing;
		  var pos = $(_this).offset();
	      var eleLeft = pos.left,eleTop = pos.top,eleWidth = $(_this).outerWidth(),eleHeight = $(_this).outerHeight();
		  var left,top;
	      switch(align) {
		      case 'top': 
			      left = eleLeft,top = eleTop - info.height() - spacing;
			  break;
			  case 'bottom': 
			      left = eleLeft,top = eleTop + eleHeight + spacing;
			  break;
			  case 'left': 
			      left = eleLeft - info.width() - spacing,top = eleTop;
			  break;
			  case 'right': 
			      left = eleLeft + eleWidth + spacing,top = eleTop;
			  break;
			  case 'left top': 
			  case 'top left':
			      left = eleLeft - info.width() - spacing,top = eleTop - info.height() - spacing;
			  break;
			  case 'right bottom': 
			  case 'bottom right': 
			      left = eleLeft + eleWidth + spacing,top = eleTop + eleHeight + spacing;
			  break;
			  case 'left bottom': 
			  case 'bottom left':
			      left = eleLeft - info.width() - spacing,top = eleTop + eleHeight + spacing;
			  break;
			  case 'right top': 
			  case 'top right':
			      left = eleLeft + eleWidth + spacing,top = eleTop - info.height() - spacing;
			  break;
		  }
		  return {x: left,y: top}
	  }
	  var XY = getXY(opts.align);
	  var wnd = $(this).window($.extend({
	      left: XY.x,top: XY.y,
	      bodyStyle: opts.style,
		  noheader: true,
		  autoSize: true,
		  content: info,
		  modal: false,
		  wndEle: 'span',
		  renderTo:'body',
		  simple: opts.simple,
		  minWidth: 16,
		  wndCss:'wnd-bookmark',
		  bodyCss: 'wnd-bookmark-body',
		  distroy: function(){
		     if(opts.autoDisappear) {
			     $(document).unbind('mousedown',closewnd);
			 }
		  }
	  },config || {}));
      wnd.show();
	  wnd.item.blur(function(){
	      closewnd();
	  });
	  info.mousedown(function(ev){
	      if(ev) {
              ev.stopPropagation();
          }
	  });
	  if(opts.autoDisappear) {
	      $(document).mousedown(closewnd);
	  }
	  function closewnd(){
	      wnd.close();
	  }
	  return wnd;
  }
  $.fn.bookmark.defaults = {
      style: {
	      'color': '#ff0000',
	      //'background': '#ffff00',
          'font-size': 12,
          'letter-spacing': 1,
		  'border': 'none'
	  },
	  markCss: 'bookmark-main',
	  align:'right bottom',
	  spacing: 2,
	  simple: true,
	  autoDisappear: true
  }
  
  //tooltip组件，基于书签组件
  $.fn.tooltip = function(content,config){
      var opts;
	  if(typeof content == 'object') {
	      opts = $.extend({},$.fn.tooltip.defaults,content);
		  if(content.style) {
		      opts.style = $.extend({},$.fn.tooltip.defaults.style,content.style || {});
		  }
		  
	  } else {
	      opts = $.extend({content:content},$.fn.tooltip.defaults);
	  }
	  var _this = $(this);
	  var wnd;
	  config = $.extend({},config || {},{
	      show: function(){
		      var  _this = this;
			  this.wnd.mouseenter(function(){
			      _this.canClose = false;
			  });
		      this.wnd.mouseleave(function(){
			      _this.canClose = true;
			      _this.close();
			  });
		  },
	      beforeclose: function(){
		     return this.canClose;
		  }
	  });
	  
	  this.bind('mouseenter',function(){
	      if(!wnd) {
		      wnd = _this.bookmark(opts,$.extend({
	              closeAction:'hide',
		          wndEle: 'span',
				  bodyEle: 'span',
				  wndCss:'tootip-wnd',
		          bodyCss: 'tooltip-body'
	          },config));
	          wnd.removeManager();
		  } else
		      wnd.show();
	  });
	  this.bind('mouseleave',function(){
	      if(wnd) {
		      if(content.delay) {
			      setTimeout(function(){
				     wnd.close();
				  },content.delay);
			  } else {
			      wnd.close();
			  }
	      }
	  });
  }
  $.fn.tooltip.defaults = {
      style: {
	      'color': '#000000',
          'font-size': 12,
          'letter-spacing': 1,
		  'border': 'none'
	  },
	  markCss:'tooltip-main',
	  align: 'right bottom',
	  spacing: 2
  }
 

    function littlewindow(opintion){
	    var firstOpts = $.extend({},opintion);
		if(!opintion.left && (!opintion.wndStyle || !opintion.wndStyle.left)) {
		    this.centerX = true;
        }
        if(!opintion.top && (!opintion.wndStyle || !opintion.wndStyle.top)) {
		    this.centerY = true;
        }
        if(opintion.simple && typeof opintion.contentClone=='undefined') {
		    opintion.contentClone = false;
        }		
		this.firstOpts = firstOpts;
        this.opts = opintion;
		this.listeners = new Array();//用于dom事件的管理,以便组件销毁时移除所有dom事件，避免意外错误及内存泄露
        this.init();
		var noManager = this.opts.noManager===true || this.opts.noManager===false?this.opts.noManager: $.littlewnd.defaults.noManager;
		if($.littlewnd.manager && !noManager) {
            this.setManager($.littlewnd.manager);
        }
    }
    $.littlewnd = littlewindow;
    littlewindow.prototype = {
        init: function(){
            var defaults = $.littlewnd.defaults;
            var wndStyle = $.extend({},defaults.wndStyle,this.opts.wndStyle || {});
            var headerStyle = $.extend({},defaults.headerStyle,this.opts.headerStyle || {});
            var bodyStyle = $.extend({},defaults.bodyStyle,this.opts.bodyStyle || {});
            var opts = $.extend({},defaults,this.opts);
            $.extend(opts.overStyle,defaults.overStyle,this.opts.overStyle || {});
			var renderTo = this.toJqObj(opts.renderTo);
			var item = this.toJqObj(opts.content);
            if(item.parent().length>0) {
                if(opts.contentClone) {
                    item = item.clone(true,true);
                } else {
					this.contentParent = item.parent();
					item.detach();
                }
                item.show();
            }
			if(opts.removeLocation) {
				if(item.css('position') && item.css('position') != 'static' ) {
			        item.css({left:'',top:'',position:''});
				}
			}
			var position = 'absolute';
			wndStyle.left = opts.left?opts.left: wndStyle.left || 300;
			wndStyle.top = opts.top?opts.top: wndStyle.top || 300;
			if(opts.simple) {//最简单方式生成组件，content内容即为组件UI，不在外部包裹任何容器
			    $.extend(this,{
                    container: renderTo,
                    body: item,
                    wnd: item,
                    item: item,
                    opts: opts
                });
				this.wnd.css(wndStyle);
				this.wnd.css('position',position);
				$.extend(opts,{
				    noheader: true,
					noStyle: true
				});
				return this.wnd;
			}
			
			if(opts.noStyle) {
			    $.extend(opts,{
				    wndCss: 'wnd-wnd-no',
		            bodyCss: 'wnd-body-no',
		            headerCss: 'wnd-header-no',
		            closeBtnCss: 'wnd-close-no'
				});
			}
            var $header = $("<div>").append($('<h4>').html(opts.title).addClass('fn-left'));
            var $body = $("<"+(opts.bodyEle || "div")+">").css('display','block');
            var $closeBtn = $("<a>").attr({href:'javascript:void(0)'}).css({border: 'none'}).html('x');
            var $wnd = $("<"+(opts.wndEle || opts.container || "div")+">");
            //$closeBtn.css({'float': 'right','cursor': 'pointer','textAlign': 'center','padding': '3px 3px 0px 0px'});
            if(opts.closeBtn) {
                $closeBtn.empty();
                $closeBtn.append(typeof opts.closeBtn=="object"?opts.closeBtn: $(opts.closeBtn));
            }
            var _this = this;
            $header.append($closeBtn);
            $header.css(headerStyle);
            $body.css(bodyStyle);
			wndStyle.width = opts.width?opts.width: wndStyle.width;
            wndStyle.height = opts.height?opts.height: wndStyle.height;
            $wnd.css(wndStyle);
			this.addClass($header,opts.headerCss);
			this.addClass($body,opts.bodyCss);
			this.addClass($wnd,opts.wndCss);
			this.addClass($closeBtn,opts.closeBtnCss);
            $wnd.css('position',position);
            $wnd.hide();
            $.extend(this,{
                container: renderTo,
                header: $header,
                body: $body,
                wnd: $wnd,
                closeBtn: $closeBtn,
                item: item,
                opts: opts
            });
            return $wnd;
        },
        liveDrag: function(ele,closeEle){//该函数激活拖拽
            if(this.dragAble === true) {
                return;
            }
            var _this = this;
			var closeBtn = closeEle || this.closeBtn;
			if(closeBtn) {
			    this.bind(closeBtn,'mousedown',function(ev){//用以阻止事件冒泡,保证在关闭按钮上不会触发拖拽
                   if(ev) {
                       ev.stopPropagation();
                   }
                });
			}
			var dragEle = ele || this.header;
            this.bind(dragEle,'mousedown',mousedownev);
            function mousedownev(ev){
                if(ev) {
                    _this.startX = ev.pageX;
                    _this.startY = ev.pageY;
                }
                else {
                    _this.startX = event.offsetX;
                    _this.startY = event.offsetY;
                }
                _this.startLeft = _this.wnd.offset().left;
                _this.startTop = _this.wnd.offset().top;
                //var rect = _this.getRect();
                //_this.wndrect = rect;
                _this.isDragging = true;
				dragEle.css('cursor','pointer');
            }
            function mousemoveev(ev){
                if(!_this.isDragging) return;
                var x,y;
                if(ev) {
                    x = ev.pageX;
                    y = ev.pageY;
                }
                else {
                    x = event.offsetX;
                    y = event.offsetY;
                }
                var xp = x - _this.startX,yp = y - _this.startY;
                var left = _this.startLeft + xp;
                var top = _this.startTop + yp;
                //_this.wndrect.css({left: left,top: top});
				_this.wnd.css({left: left,top: top});
            }
            function mouseupev(ev){
                if(!_this.isDragging) return;
                var x,y;
                if(ev) {
                    x = ev.pageX;
                    y = ev.pageY;
                }
                else {
                    x = event.offsetX;
                    y = event.offsetY;
                }
                var xp = x - _this.startX,yp = y - _this.startY;
                var left = _this.startLeft + xp;
                var top = _this.startTop + yp;
                //_this.wndrect.css({left: left,top: top});
                //_this.wndrect.remove();
                _this.wnd.css({left: left,top: top});
                _this.isDragging = false;
				dragEle.css('cursor','');
            }
			this.bind($(document),'mousemove',mousemoveev);
			this.bind($(document),'mouseup',mouseupev);
			dragEle.attr('title','可拖动');
            this.dragAble = true;
        },
        render: function(){
            if(this.isRender) return;
            if(!this.opts.noheader) {
                this.wnd.append(this.header);
            }
			this.container.append(this.wnd);
			if(!this.opts.simple) {
			    this.wnd.append(this.body);
                this.body.append(this.item);
			}
            
            if(!this.opts.noheader) {
                if(this.opts.dragAble) {
                    this.liveDrag();
                }
            } else {
                if(this.opts.dragAble && this.opts.dragEle) {
                    var dragEle = typeof this.opts.dragEle=="object"?this.opts.dragEle: this.wnd.find(this.opts.dragEle);
                    this.liveDrag(dragEle,closeEle);
                }
            }
            var _this = this;
            var closeEle;
            var optEle = this.opts.closeEle;
            if(!optEle) {
                closeEle = this.closeBtn;
            } else {
                closeEle = this.opts.noheader?(typeof optEle=="object"?optEle: this.wnd.find(optEle)): this.closeBtn;
            }
			if(closeEle) {
			    closeEle.attr('title','关闭');
			    _this.bind(closeEle,'click',function(ev){
                     _this.close();
                });
			}
            
			if(this.opts.modal) {
			    if(_this.over && _this.container.get(0)==$('body').get(0)) {
				    var docuSize = {width:$(document).width(),height:$(document).height()};
					function windowresizeev(){
					    if(!_this.bodyResizing) {
						    _this.bodyResizing = true;
							setTimeout(function(){
							    if(_this.isDistroy) return;
							    setSize();
								var off = _this.wnd.offset();
								var rect = {top:off.top,left:off.left,width:_this.wnd.outerWidth(),height:_this.wnd.outerHeight()};
								var wndH = $(window).height(),wndW = $(window).width();
								if(rect.top > wndH - rect.height) {
								    var top = wndH - rect.height;
									if(top < 0) top =0;
								    _this.wnd.css('top',top);
								}
								if(rect.left > wndW - rect.width) {
								    var left = wndW - rect.width;
									if(left < 0) left =0;
								    _this.wnd.css('left',left);
								}
								setTimeout(function(){
								    setSize();
								},1);
							},300);
						}
					    //_this.over.css({width:$(window).width(),height:height = $(window).height()});
			        }
			        _this.bind($(window),'resize',windowresizeev);
					_this.windowresizeev = windowresizeev;
					function setSize(){
					    var wndH = $(window).height(),wndW = $(window).width();
						var overH = wndH > docuSize.height?wndH:docuSize.height;
						var overW = wndW > docuSize.width?wndW:docuSize.width;
						_this.over.css({'width':overW,'height':overH});
					    _this.bodyResizing = false;
					}
				}
			}
			this.isRender = true;
			if(this.opts.render) this.opts.render.call(this);
        },
		showOver: function(){
		    var over = $("<div>");
            over.css(this.opts.overStyle);
            var left = this.container.offset().left,top=this.container.offset().top;
            var width = this.container.outerWidth(),height=this.container.outerHeight();
            if(this.container.get(0)==$('body').get(0)) {
                width = $(document).outerWidth();
                height = $(document).outerHeight();
                top = 0,left = 0;
            }
            var overIndex = this.manager?this.manager.getTop().zIndex - 1: parseInt(this.wnd.css('zIndex')) - 1;
            over.css({width: width,height: height,left: left,top: top,position: 'absolute',zIndex: overIndex});
            over.hide();
			if($.browser.msie && ($.browser.version == "6.0") && !$.support.style) {
			    over.append($('<iframe>').attr('src','').css({
				    width:'100%',
					height:'100%',
					opacity:0.01
				}));
				
			}
            this.container.append(over);
            over.show();
            this.over = over;
            this.isModal = true;
		},
        show: function(){
		    if(this.isDistroy) return;
            if(this.opts.modal) {
                this.showOver();
            }
            this.render();
			if(this.opts.beforeshow) {
			    var shoundShow = this.opts.beforeshow.call(this);
				if(shoundShow===false) return;
			}
            var _this = this;
            this.wnd.show(1,function(){
                if(_this.isShow) {
                     if(_this.manager) {
                         _this.manager.toFront(_this);
                     }
                    // _this.wnd.css('zIndex',parseInt(_this.over.css('zIndex'))+1);
                     if(_this.opts.show) {
                         _this.opts.show.call(_this);
                     }
                     return;
                }
				if(_this.centerX) {
				    var left = ($(window).width() - _this.wnd.width())/2 +($(document).scrollLeft() || 0) ;
					_this.wnd.css('left',left);
				}
				if(_this.centerY) {
				    var top = ($(window).height() - _this.wnd.height())/2 +($(document).scrollTop() || 0) ;
					_this.wnd.css('top',top);
				}
				_this.isShow = true;
				if(!_this.opts.simple && _this.opts.autoSize) {
				    _this.doSize();
				} 
				if(_this.opts.show) {
                    _this.opts.show.call(_this);
                }
            });
            
        },
		hide: function(){
		    if(this.isDistroy) return;
		    this.wnd.hide();
			if(this.isModal && this.over) {
                this.over.remove();
				this.over=null;
            }
		},
		append: function(ele){
		    this.body.append(ele);
		},
        close: function(){
		    if(this.isDistroy) return;
            if(this.opts.beforeclose) {
               var shoundClose =  this.opts.beforeclose.call(this);
               if(shoundClose===false) {
                   return;
               }
            }
            if(this.opts.closeAction && this.opts.closeAction=='hide') {
                this.hide();
            }
            else {
                this.distroy();
            }
			if(this.opts.close) {
			   this.opts.close.call(this);
			}
        },
        distroy: function(){
		    if(this.isDistroy) return;
			if(this.isModal && this.over) {
                this.over.remove();
				this.over=null;
            }
            $.each(this.listeners,function(i,item){//注销所有dom事件
			    if(item.type && item.type == 'live') item.obj.die(item.ev,item.handler);
				else item.obj.unbind(item.ev,item.handler);
			});
			if(this.contentParent && this.opts.contentRecovery) {
				this.item.hide();
			    this.contentParent.append(this.item);
			}
			if(this.manager) {
                this.manager.remove(this);
				this.manager=null;
            }
			if(!this.opts.simple) this.wnd.remove();
			else this.wnd.hide();
            this.isDistroy=true;
			var props = ['item','wnd','header','closeBtn','body'];
			for(var i=0;i<props.length;i++) {
			    if(this[props[i]]) delete this[props[i]];
			}
			if(this.opts.distroy) {
                this.opts.distroy.call(this);
            }
        },
        getRect: function(){
            var zIndex = this.manager?this.manager.getTop().zIndex + 1: this.wnd.css('zIndex') + 1;
            var wnd = this.wnd;
            var left = wnd.offset().left,top = wnd.offset().top,width = wnd.outerWidth(),height = wnd.outerHeight();
            var rect = $("<div>").css({
                left: left,
                top: top,
                height: height,
                width: width,
                border: 'dotted Black 1px',
                position: 'absolute',
                zIndex: zIndex,
                background: '#666666',
                opacity: 0.2
            });
            this.container.eq(0).append(rect);
            return rect;
        },
        setManager: function(manager){
            this.manager = manager;
            var _this = this;
            var topone = manager.getTop();
            if(topone.wnd) {
               this.wnd.css('zIndex',topone.zIndex + 2);
           }
           manager.add(this);
           this.bind(this.wnd,'mousedown',function(){
               manager.toFront(_this);
           });
        },
		removeManager: function(){
		    if(this.manager) {
			    this.manager.remove(this);
				this.manager = null;
			}
		},
        toJqObj: function(param){
		    var obj;
		    if(typeof param == "string") {
	            var jobj = $(param);
		        if(jobj.length) {
		            obj = jobj;
		        } else {
		            obj=$('<span>').html(param);
		        }    
	        } else {
	            obj = param;
	        }
			obj.css('visibility','visible');
		    return obj;
	    },
		doSize: function(){
		    var _this = this;
		    var itemWidth = _this.item.outerWidth(),itemHeight =_this.item.outerHeight();
            var headerHeight;
            if(_this.opts.noheader) {
                headerHeight = 0;
            } else {
                headerHeight = _this.header.outerHeight();
            }
			var minWidth = _this.opts.minWidth || 100;
            var width = itemWidth > minWidth?itemWidth:minWidth;
			_this.body.css({height: itemHeight});
            var height = _this.body.outerHeight() + headerHeight;
            _this.wnd.css({width: width,height: height});
		},
		addClass: function(obj,name){
		    $.each(name.split(' '),function(i,item){
			    obj.addClass(item);
			});
		},
		bind: function(obj,ev,handler,type){
		    this.listeners.push({obj:obj,ev:ev,handler:handler,type:type});
			if(type && type == 'live') {
			    obj.live(ev,handler);
			} else {
			    obj.bind(ev,handler);
			}
		}
    }
    $.littlewnd.defaults = {
           title: '',//默认标题
           content: '',//主体部分的默认内容
           dragAble: true,
           modal: true,
           contentClone: false,
		   removeLocation: true,
		   contentRecovery:true,
		   wndCss: 'n-fn-layer',
		   bodyCss: 'wnd-body',
		   headerCss: 'f-title fn-mb15 fn-clear',
		   closeBtnCss: 'fn-right',
		   noStyle: false,
           wndStyle: {//窗体默认配置
               overflow: 'hidden',
               zIndex: 5000
           },
           headerStyle: {//标题栏的默认配置
               width: '100%',
               height: 25,
			   margin: 0 
           },
           bodyStyle: {//窗体主体部分的默认配置
               width: '100%'
           },
           overStyle: {
               opacity: 0.5,
               background: '#666666'
           }
        }
        	
        
        
        /* 该组件用于小窗体组件的管理,当非模态形式显示多个小窗体时,管理窗体的层,使小窗体在被点击时居于最前
           同时使得模态窗体遮罩能将所有窗体组件遮住,该组件由小窗体组件自动调用,不建议主动调用其方法.
           支持以下方法: 
           getTop: 返回zIndex最高的窗体组件对象及其zIndex和索引
           getHeigher: 返回比传入参数层数高的所有小窗体
           getIndex: 根据小窗体对象返回其索引
           remove: 从管理器中移除小窗体组件
           add: 将小窗体组件添加到管理器
           toFront: 将一个小窗体显示到最前
         */
        $.littlewnd.manager = $.extend(new Array(),{
            getTop: function(){
                var topOne = {index: 0,wnd: null,zIndex: 0};
                for(var i = 0;i<this.length;i++ )
                {
                    var zIndex = parseInt(this[i].wnd.css('zIndex'));
                    if(zIndex > topOne.zIndex) {
                        topOne.zIndex = zIndex;
                        topOne.wnd = this[i];
                        topOne.index = i;
                    }
                }
                return topOne;
            },
            getHigher: function(wnd){
                if(!wnd) return [];
                var zIndex = wnd.wnd.css('zIndex');
                var higherWnds = [];
                $.each(this,function(i,item){
                    var aindex = item.wnd.css('zIndex');
                    if(aindex > zIndex) {
                        higherWnds.push(item);  
                    }
                })
                return higherWnds;
            },
            getIndex: function(wnd){
                if(!wnd) return;
                var index;
                for(var i = 0;i<this.length;i++ ) {
                    if(wnd==this[i]) {
                        return i;
                    }
                }
                return -1;
            },
            remove: function(wnd){
                if(!wnd || !wnd.manager) return;
                var index = this.getIndex(wnd);
                this.splice(index,1);
				wnd.manager=null;
            },
            add: function(wnd)
			{
			    if(!wnd) return;
			    var topone = this.getTop();
				var newIndex;
				if(!topone.wnd) {
				    newIndex = 5000;
				} else {
				    newIndex = topone.zIndex + 2;
					if(wnd.over) {
					    newIndex = newIndex >= parseInt(wnd.over.css('zIndex'))?newIndex:parseInt(wnd.over.css('zIndex')) + 1;
					}
				    wnd.wnd.css('zIndex',newIndex);
				}
                this.push(wnd);
            },
            toFront: function(wnd){
                if(!wnd) return;
                var topone = this.getTop();
                var higherWnds = this.getHigher(wnd);
                $.each(higherWnds,function(i,item){
                    var newIndex = parseInt(item.wnd.css('zIndex')) - 2;
                    item.wnd.css('zIndex',newIndex);
                    if(item.over) item.over.css('zIndex',newIndex-1);
                });
                wnd.wnd.css('zIndex',topone.zIndex);
            }
        });
}
if(!$.littlewnd) {
    if(typeof define == 'function') {
        define($_ex_window);
    } else {
        $_ex_window();
    }
}
