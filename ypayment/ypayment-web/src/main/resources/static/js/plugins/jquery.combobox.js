
define(function(){
	$.fn.combobox = function(opintion){
	    var opts = $.extend({},opintion || {}); 
		opts.renderTo = this;
		var combo = new $.combobox(opts);
		combo.show();
		return combo;
	}
    function combo(opintion){
	    var opts = $.extend({},$.combobox.defaults,opintion);
		opts.style = $.extend({},$.combobox.defaults.style,opintion.style);
	    this.base.call(this,opts);
	}
	$.combobox = combo;
	$.extend(combo.prototype,$.box.prototype,{
	    base: $.box,
	    init: function(){
		    var _this = this;
			var opts = this.opts;       
		    var combo = $('<span>').addClass('select-combo');
			var main = $('<input>').attr({type:'text',name: opts.name}).addClass('select-input');
			var list = $('<ul>').css({position:'absolute',zIndex:6000,overflow:'hidden',display:'none'}).addClass('select-list');
			if(opts.itemsHeight) {
			    list.css({height: opts.itemsHeight,overflow:'auto'});
			}
			var img = $('<img>').addClass(opts.imgCss).css({cursor:'pointer'}).attr({'src':opts.selectImg,alt:'X',title:'展开'});
			img.bind('error',function(){
			    $(this).css({width:16,height:24});
			});
			var store = [];
			$.each(this.opts.store,function(i,item){
			    var arecord;
			    if(typeof item == 'string') {
				    arecord = {text: item,value: item};
				} else if(typeof item == 'object'){
				    arecord = item;
				}
				store.push(arecord);
			});
			this.store = store;
			$.each(store,function(i,item){
			    var aselect = $('<li>').addClass(item.css || opts.itemCss).html(item.text);
				aselect.data({'index':i,value:item.value});
				list.append(aselect);
			});
			combo.append(main).append(img);
			$('body').append(list);
			opts.content = combo;
			this.selectImg = img;
			this.list = list;
			this.main = main;
			this.combo = combo;
			this.img = img;
			combo.click(function(ev){
			    if(ev && _this.list.isShow) {
                   ev.stopPropagation();
                }
			});
			img.click(function(){
			    $(document).click();
			    if(!list.isShow) {
				    _this.showList();
					_this.getItemList().show();
				} else {
				    _this.hideList();
				}
			});
			$(document).click(function(){
			    if(_this.isDistroy) {
				    if(arguments && arguments.callee) {
					    $(document).unbind('click',arguments.callee);
					}
				    return;    
				};
			    if(!_this.list.isShow) return;
				setTimeout(function(){
				    _this.hideList();
				},10);   
			});
			_this.getItemList().click(function(){
			    var _self = $(this);
			    _this.setSelect(_self);
				_this.hideList();
			});
			_this.getItemList().bind('mouseover',function(){
			    var _self = $(this);
				if(_this.selectItem != _self) {
					_this.setLight(_self);
				}
			});
			main.blur(function(){
				if($(this).val() === _this.selectText || !opts.mustSelect) {
				    return;
				}
				if(_this.selectItem && _this.opts.mustSelect) {
				    main.val(_this.selectText);
				} else {
				    main.val('');
				}
			});
			main.keyup(function(ev){
			    var key = ev.keyCode || ev.witch;
				if(key == 13 || key == 38 || key == 40) {
				    return;
				}
			    var txt = main.val();
			    var itemList = _this.quary(txt);
				_this.getItemList().data({'isShow':false}).hide();
				$.each(itemList,function(i,item){
				    item.data({'isShow':true}).show();
				});
			    if(itemList.length) {
				    if(!_this.list.isShow) {
					    _this.showList();
					}
					_this.setLight(itemList[0]);
			    } else {
				    if(_this.list.isShow) {
					    _this.hideList();
					}
				}
			});
			main.keypress(function(ev){
			    var key = ev.keyCode || ev.witch;
				if(key == 13) {
				    if(_this.list.lightIndex || _this.list.lightIndex == 0) {
					    _this.setSelect(_this.list.lightIndex);
						_this.hideList();
					}
				} else if(key == 38 || key == 40){
				    var allItems = _this.getItemList();
					var items = [];
					allItems.each(function(i,item){
					    if($(item).css('display')!='none') {
						    items.push($(item));
						}
					});
				    if(items.length > 0 && key == 38) {
					    var setLight = function(index){
						    if(index<0) return;
						    if(_this.getItem(index).css('display')!='none'){
							    _this.setLight(index);
								return;
							} else {
							    setLight(index-1);
							}
						}
					    setLight(_this.list.lightIndex-1);
					}
					if(items.length > 0 && key == 40) {
					    var setLight = function(index){
						    if(index >= _this.getItemList().length) return;
						    if(_this.getItem(index).css('display')!='none'){
							    _this.setLight(index);
								return;
							} else {
							    setLight(index+1);
							}
						}
					    setLight(_this.list.lightIndex+1);
					}
				}
			});
			
			if(!opts.editAble) {
			    main.unbind('keyup');
				main.attr('readonly','readonly');
			}
			if(opts.change) {
			    this.change(opts.change);
			}
			if(opts.select) {
			    this.select(opts.select);
			}
			this.base.prototype.init.call(this);
		},
		show: function(){
		    this.base.prototype.show.call(this);
			var opts = this.opts;
			if(opts.autoFill) {
			   this.combo.css({width: '100%',height:'100%'});
			   this.img.css({height: '100%'});
			   this.main.css({width: this.combo.innerWidth() - this.img.outerWidth(),height:'100%'});
			} else if(opts.autoSize) {
			   this.doSize();
			} else {
			    this.main.css({width: this.container.innerWidth()-this.img.outerWidth(),height: this.container.innerHeight()-this.img.outerHeight()});
			}
		},
		doSize: function(){
		    var maxSize = {width: 0,height: 0};
			var _this = this;
		    this.getItemList().each(function(i,item){
			    var str = $(item).html();
				var strSize = _this.getStrSize(str);
				if(strSize.width > maxSize.width) {
				    maxSize.width = strSize.width;
				}
				if(strSize.height > maxSize.height) {
				    maxSize.hieght = strSize.height;
				}
			});
			if (maxSize.width != 0) {
			    this.main.css({width: maxSize.width + 6});
			}
			this.combo.css({width:this.main.outerWidth()+this.img.outerWidth(),height: this.main.outerHeight()});
			this.base.prototype.doSize.call(this);
		},
		showList: function(){
		    this.list.css({width: this.combo.width(),left: this.combo.offset().left,top: this.combo.offset().top + this.combo.outerHeight()});
		    this.main.focus();
		    this.list.show();
			var _this = this;
			var setLight = function(index){
			    if(index >= _this.getItemList().length) return;
			    if(_this.getItem(index).css('display')!='none'){
				    _this.setLight(index);
				    return;
			    } else {
				    setLight(index+1);
			    }
			}
			setLight(0);
			this.list.isShow = true;
		},
		hideList: function(){
		    if(this.list.lightIndex || this.list.lightIndex == 0) {
			    this.getItem(this.list.lightIndex).removeClass(this.opts.lightCss);
				this.list.lightIndex = null;
			}
		    this.list.hide();
			this.list.isShow = false;
		},
		getItemList: function(){
		    var itemList = this.list.children('li');
			return itemList;
		},
		setSelect: function(param,type,noEv){
		    var opts = this.opts;
		    var item = (typeof param == 'object' && type !== 'value')?param : this.getItem(param,type);
			if(!item) return false;
			if(this.selectItem !== item) {
			    if(this.selectItem) {
				    this.selectItem.removeClass(opts.selectCss);
			    }
			    item.addClass(opts.selectCss);
		    }
			var lastValue = this.selectValue;
		    this.selectIndex = item.data('index');
			this.selectValue = item.data('value');
			this.selectText = item.html();
		    this.selectItem = item;
			this.main.val(item.html());
			if(noEv) return item;
			var _this = this;
			if(this.selects) {
				$.each(this.selects,function(i,item){
					item.call(_this,_this.selectValue,_this.selectText,_this.selectItem);
				});
			}
			if(this.changes && lastValue != this.selectValue) {
				$.each(this.changes,function(i,item){
					item.call(_this,_this.selectValue,_this.selectText,_this.selectItem);
				});
			}
			return item;
		},
		setValue: function(value){
		    this.setSelect(value,'value');
		},
		setIndex: function(index){
		    this.setSelect(index,'index');
		},
		setText: function(text){
			this.setSelect(text,'text');
		},
		getText: function(){
			return this.selectText;
		},
		getValue: function(){
		    return this.selectValue;
		},
		setLight: function(param){
		    var opts = this.opts;
		    var item,index;
		    if(typeof param == 'object') {
			    item = param;
				index = this.getIndex(param);
		    } else {
		        item = this.getItem(param,'index');
			    index = param;
		    }
			item.addClass(opts.lightCss);
			if(this.list.lightIndex || this.list.lightIndex == 0 && index != this.list.lightIndex) {
			    this.getItem(this.list.lightIndex).removeClass(opts.lightCss);
			}
			this.list.lightIndex = index;
		},
		addItem: function(param,value,type){
		    var item;
			var _this = this;
		    if(typeof param == 'string') {
			    item = {text: param,value: param};
			}  else if($.isArray(param)){
			    $.each(param,function(i,aparam){
				    _this.addItem(aparam);
				});
			    if(value) {
			    	_this.setSelect(value,type || 'value');
			    }
				return;
			} else if(typeof param == 'object') {
			    item = param;
			}
			var aselect = $('<li>').html(item.text);
			aselect.addClass(this.opts.itemCss);
			if(item.css) {
			    aselect.addClass(item.css);
			}
			aselect.data({'index':this.list.children().length,value:item.value});
			this.list.append(aselect);
			aselect.bind('mouseover',function(){
			    var _self = $(this);
				if(_this.selectItem != _self) {
					_this.setLight(_self);
				}
			});
			aselect.click(function(){
			    var _self = $(this);
			    _this.setSelect(_self);
				_this.hideList();
			});
			if(this.opts.autoSize) {
			    this.doSize();
			}
		},
		clear: function(){
		    this.list.empty();
			this.main.val('');
			this.selectValue = null;
			this.selectIndex = null;
			this.selectItem = null;
			this.selectText = null;
		},
		getItem: function(param,type){
		    var result;
		    this.getItemList().each(function(i,item){
			    var mask;
				if((typeof param == 'number' && type !== 'value') || type === 'index') {
				    mask = i;
				} else if(type === 'text') {
					mask = $(item).html();
				} 
				else {
				    mask = $(item).data('value');
				}
			    if(mask == param) {
				    result = $(item);
				    return false;
				}
			});
			return result;
		},
		getIndex: function(obj){
		    var result;
		    this.getItemList().each(function(i,item){
			    if(item == obj.get(0)) {
				    result = i;
				    return false;
				}
			});
			return result;
		},
		change: function(fn){
		    if(!this.changes) {
			    this.changes = [];
			}
		    this.changes.push(fn);
		},
		select: function(fn) {
		    if(!this.selects) {
			    this.selects = [];
			}
		    this.selects.push(fn);
		},
		compare : function(str,exp/*类似于SQL中的模糊查询字符串*/, i/*是否区分大小写*/) {
            var str = str;
            i = i == null ? false : i;
            if (exp.constructor == String) {
               var s=exp.replace(/\./g,"\\.");//by xyz 把.替换为\.
               /*将表达式中的‘%’替换成‘.’，但是‘[%]’表示对‘%’的转义，所以做特殊处理*/
                s = s.replace(/%/g, function(m, i) {
                    if (i == 0 || i == s.length - 1) {
                    return ".*";
                    }
                    else {
                        if (s.charAt(i - 1) == "[" && s.charAt(i + 1) == "]") {
                            return m;
                        }
                    return ".*";
                    }
                });

                /*将表达式中的‘[_]’、‘[%]’分别替换为‘_’、‘%’*/

                s = s.replace(/\[_\]/g, "_").replace(/\[%\]/g, "%");

                /*对表达式处理完后构造一个新的正则表达式，用以判断当前字符串是否和给定的表达式相似*/

                var regex = new RegExp("^.*" + s, i ? "" : "i");
                return regex.test(str);
            }
            return false;
        },
		quary: function(str){
		    var itemList = [];
			var _this = this;
			$.each(this.getItemList(),function(i,item){
			    var txt = $(item).html();
				if(_this.compare(txt,str)) {
				    itemList.push($(item));
				}
			});
			return itemList;
		}
		
	});
    $.combobox.defaults = {
		disabled: false,
		editAble: true,
		container: 'span',
		store: [],
		name: 'select',
		autoSize: true,
		mustSelect: true,
		itemCss: 'select-item',
		selectCss: 'select-selected',
		lightCss:'select-light',
		selectImg:'img/combo-select.jpg',
		css:'select',
		imgCss:'select-arrow',
        container:'span'
	}
});