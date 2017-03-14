
(function($){

define(function(){
	Y.inherit('ProgressBar','box',{
	    doInit: function(cfg){ 		
		    var _this = this;
		    cfg.renderTo = cfg.renderTo || cfg.target || 'body';
            var el = $('<span>').addClass('progressbar-progressbar');
			cfg.el = el;
			this.completed = $('<span>').addClass('progressbar-completed').css({width:'0%',height:'100%'});
			this.main = $('<span>').addClass('progressbar-main').css({width:'100%',height:'100%'});
			this.info = $('<b>').addClass('progressbar-percent');
			if(!cfg.showPercent) this.info.hide();
			el.append(this.main.append(this.completed)).append(this.info);
			this.callBase('doInit','box',cfg);
		},
		doShow: function(speed){
		    this.el.css('display','inline-block');
		    this.callBase('doShow','box',speed);
			this.start();
			if(this.cfg.showPercent) {
			    this.main.css('width',this.el.innerWidth()-this.info.show().outerWidth()-6);
			}
		},
		start:function(){
		    var cfg = this.cfg;
		    this.completed.stop().css('width','0%');
			this.isComplete = false;
			this.percent = 0;
			this.info.html('<span style="visibility:hidden;">100%</span>');
			var _this = this;
			if(cfg.url) {
			    _this.timer = setInterval(function(){
				    _this.inquire(function(percent){
					    _this.setPercent(percent);
					});
				},cfg.time || 2000);
			}
		},
		setPercent:function(percent,callback){
		    if(this.isComplete) return;
			var _this = this;
		    var number = parseFloat(percent);
			if(isNaN(number)) return;
			if(number >=100) {
			    this.percent = 100;
				this.complete();
				return;
			}
			if(number === this.percent) {
			    return;
			}
			this.completed.stop(true,true);
			this.completed.animate({width: number + '%'},this.cfg.time,function(){
			    if(callback) callback();
				_this.info.html(number + '%');
			});
			this.percent = number;
			this.fire('change',number);
		},
		complete: function(time){
		    if(this.isComplete) return;
		    var cfg = this.cfg;
			var _this = this;
		    this.completed.animate({width: '100%'},time || 500,function(){
			    _this.fire('complete');
			    if(cfg.autoDisappear) {
				    _this.close();
				}
				_this.info.html('100%');
			});
			this.percent = 100;
			this.isComplete = true;
			if(this.timer) clearInterval(this.timer);
		},
		inquire:function(callback){
		    var cfg = this.cfg;
			if(!cfg.url) return;
			dataType = cfg.dataType || 'text';
			var _this = this;
			$.ajax({
			    url : cfg.url,
				dataType: dataType,
				success: function(res){
				    var percent;
				    if(dataType == 'text') {
					    percent = res;
					} else {
					    if(res.code == 1) {
						    percent = res.percent;
						}
					}
					if(cfg.dataHandler) {
					    percent = cfg.dataHandler(res);
					}
					if(callback) callback(percent);
					_this.fire('inquire',res);
				} 
			});
		}
		
		
	});
	Y.ProgressBar.defaults = {
	    time: 2000,
		width: 100,
		autoShow: true,
		autoDisappear: true
	}
});

})($);