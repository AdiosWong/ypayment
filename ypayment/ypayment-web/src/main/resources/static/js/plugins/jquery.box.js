

define(function(){

    function box(opintion){
        this.firstOpts = opintion;
		this.opts = $.extend({},opintion);
		this.init();
	}
	$.box = box;
	box.prototype = {
	    init: function(){
		    var container = $("<"+(this.opts.container || 'div') + ">");
			if(this.opts.style) {
			    container.css(this.opts.style);
			}
			if(this.opts.css) {
			    container.addClass(this.opts.css);
			}
			if(this.opts.content) {
			    var obj = this.toJqObj(this.opts.content);
				if(obj.parent().length) {
					if(this.opts.contentClone) {
						obj = obj.clone();
					} else {
						obj.detach();
					}
				}
				this.item = obj;
			} else {
			    this.item = $('<div>');
			}
			container.hide();
			this.container = container;
			if(this.opts.init) {
			    this.opts.init.call(this);
			}
		},
		render: function(){
		    if(this.isRender || this.isDistoy) return;
			this.container.append(this.item);
			if(!this.opts.renderTo) return;
			this.renderTo = this.toJqObj(this.opts.renderTo);
			if(!this.renderTo.length) return;
			this.renderTo.append(this.container);
			this.isRender = true;
			if(this.opts.render) this.opts.render.call(this);
		},
		show: function(){
		    this.render();
			if(this.opts.beforeshow) {
			    var shoundshow = this.opts.beforeshow.call(this);
				if(shoundshow === false) {
				    return;
				}
			}
			if(this.showwing) return;
			this.container.show();
			var size = this.calSize();
			this.container.css({width: size.width,height: size.height});
			this.isShow = true;
			this.showwing = true;
			if(this.opts.show) {
			    this.opts.show.call(this);
			}
		},
		hide: function(){
		    this.container.hide();
			this.showwing = false;
			if(this.opts.hide) {
			    this.opts.hide.call(this);
			}
		},
		close: function(){
		    if(this.isDistroy) {
			    return;
			}
			if(this.opts.beforeclose) {
			    var shoundClose=this.opts.beforeclose.call(this);
				if(shoundClose === false) {
				    return;
				}
			}
			if(this.opts.closeAction == 'hide') {
			    this.hide();
			} else {
			    this.distroy();
			}
			if(this.opts.close) {
			    this.opts.close.call(this);
			}
		},
		distroy: function(){
		    this.container.remove();
			this.isDistroy = true;
			if(this.opts.distroy) this.opts.distroy.call(this);
		},
		calSize: function(){
		    if(this.opts.autoFill) {
			    return {width: '100%',height: '100%'}
			}
		    if(this.opts.autoSize) {
			    if(this.opts.container && this.opts.container != 'div') {
				    return {width: 'auto',height: 'auto'};
			    }
			    return {width: this.item.outerWidth(),height: this.item.outerHeight()};
			}
			return {width: this.opts.width || 'auto',height: this.opts.height || 'auto'};
		},
		doSize: function(){
		    if(this.opts.container && this.opts.container != 'div') {
			    this.container.css({width: 'auto',height: 'auto'});
				return;
			}
		    this.container.css({width: this.item.outerWidth(),height: this.item.outerHeight()+2});
		},
		toJqObj: function(param){
		    var obj;
		    if(typeof param == "string") {
	            var jobj = $(param);
		        if(jobj.length) {
		            obj = jobj;
		        } else {
		            strSize = this.getStrSize(param);
		            obj=$('<div>').css({'width': this.opts.width || strSize.width,height: this.opts.height || strSize.height}).html(param);
		        }    
	        } else {
	            obj = param;
	        }
		    return obj;
	    },
		getStrSize: function(param){
		    var strSize = this.opts.style['font-size'] || 10;
			var strSpacing = this.opts.style['letter-spacing'] || 2;
			var width = 0;
			param = param.split('');
			for(var i = 0;i < param.length;i++ ) {
			    if((/^[\u4e00-\u9fa5]$/).test(param[i])) {
				        width += strSize;
						width += strSpacing;
			    } else {
				    width += strSize/2;
			    }

			}
			return {width: width,height: strSize};
		}
	}
});