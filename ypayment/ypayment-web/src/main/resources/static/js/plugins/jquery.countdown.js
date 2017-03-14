

define(function(){
    $.countdown = countdown;
	$.fn.countdown = function(opintion){
	    var opts = $.extend({},opintion || {}); 
		opts.renderTo = this;
		var timer = new $.countdown(opts);
		timer.show();
		return timer;
	}
    function countdown(opintion){
	    var opts = $.extend({},$.countdown.defaults,opintion);
		opts.style = $.extend({},$.countdown.defaults.style,opintion.style);
	    this.base.call(this,opts);
	}
	$.countdown = countdown;
	$.extend(countdown.prototype,$.box.prototype,{
	    base: $.box,
		init: function(){
		    var opts = this.opts;
			if(opts.beControl) {
			    var beControl = this.toJqObj(opts.beControl);
				beControl.attr('disabled','disabled');
				this.beControl = beControl;
			}
		    var time = opts.time;
		    var str = opts.message;
			str = str.replace(/\{0\}/g,"<b style='width:30px;'>" + time + "</b>");
		    opts.content = $("<span>" + str + "</span>");
			var strSize = this.getStrSize(opts.message);
			var _this = this;
		    var timer = setInterval(function(){
		        time -= opts.space;
			    if(time < 0) {
				    time = 0;
		        }
			    var timeStr= ''+time;
			    if(timeStr.length == 1) {
			    	if($.browser.msie) {
			    		timeStr = "  " + timeStr;
			    	} else {
			    		timeStr = "  " + timeStr;
			    	}
			    }
				opts.content.find('b').html(timeStr);
				if(time <= 0) {
				    if(typeof opts.timeover == "function") {
					    opts.timeover.call(_this);
					}
					if(_this.beControl) {
				        _this.beControl.removeAttr('disabled');
			        }
					var overStr = opts.overMessage;
			        overStr = overStr.replace(/\{0\}/g,"<b style='width:30px;'>" + time + "</b>");
		            opts.content.html(overStr);
					if(opts.autoDisappear) {
					    _this.close();
					}
					clearInterval(timer);
				}
		    },opts.space*1000);
			this.base.prototype.init.call(this);
		},
		show :function(){
			this.base.prototype.show.call(this);
			if(this.opts.numberCss) {
			    this.opts.content.find('b').addClass(this.opts.numberCss);
			}
		}
	});
    $.countdown.defaults = {
	    message: '还剩{0}秒',
		overMessage: '时间到了{0}',
		numberCss:'countdown-num',
		autoSize: true,
		time: 60,
		space: 1,
		container: 'span',
		css: 'countdownEle',
		autoDisappear:true
	}
	$.countdown.init = function(option){
	    var eles = $('.fn-countdown');
		if(!eles.length) return;
		eles.each(function(i,item){
		    var self= $(item);
			var next = self.next();
			var container;
			if(next && next.hasClass('countdownEle')) {
			    container = next.addClass('fn-g-btn');
			    
			} else {
			    var className = (option && option.className)?option.className:'fn-g-btn';
				container =  $('<span>').attr('id',"countdownEle"+i)
				             .addClass('countdownEle').addClass(className);
			}
			self.after(container.hide());
			self.click(function(){
				var countdown = container.countdown($.extend({
				   autoSize:true,
				   message:'{0}秒后可再发送',
				   show:function(){
				       self.hide();
					   container.show();
				   },close:function(){
				       self.show();
					   container.hide();
				   }
				},$.parseJSON(self.attr('option')) || {}));
				self.data('countdown',countdown);
				if(!option) return;
				var sendMsg,url,input,name;
				if(typeof option == 'function') {
				    sendMsg = option;
				} else {
				    sendMsg = option.sendMsg;
					url = option.url;
					input = option.input;
					name = option.name;
				}
				
				if(sendMsg)
				{
				    sendMsg(afterSend);
				}
				else if(url && input) {
				    var val = typeof input=='object'?input.eq(i).val():input;
					var parname = name || input.eq(i).attr('name');
					$.ajax({
					    url:url,
						data:{parname:val},
						dataType:'json',
						success:function(res){
						    afterSend(res);
						}
					});
				}
				function afterSend(res) {
				    if(res.code != 1) {
						countdown.close();
						var error = $('<b>').addClass('error-tip').html(res.message);
						container.after(error);
						setTimeout(function(){
						    $(document).bind('click',function(){
							    error.remove();
							    $(document).unbind('click',arguments.callee);
						    });
						},10);
						
					}
				}
			});
		});
	}
});