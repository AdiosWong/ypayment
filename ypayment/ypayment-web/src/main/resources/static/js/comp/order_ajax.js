/**
 * @fileoverview 
 * @author xiaoyangzheng
 * @created 
 * @updated 
 */


define(function(require, exports, module) {
	
	$.orderAjax = function(cfg){
	    var results = [], successes = [];
		var order = typeof cfg.order === 'string'?[cfg.order]:cfg.order;
	    $.each(order,function(i,item){
		    var option = $.extend({},cfg.defaults || {},item);
			var success = option.success, error = option.error;
			$.extend(option,{
			    success: function(res){
				    try {
					    success.call(this, res);
					} catch(e){}
					handle({success: true, url: option.url, response: res});
				}, 
				error: function(){
					try {
					    error.call(this);
					} catch(e){}
				    handle({success: false, url: option.url});
				}
			});
			$.ajax(option);
		});
		
		function handle(result){
		    results.push(result);
			if(result.success) successes.push(result.response);
			if(results.length == order.length) {
			    if(cfg.complete) cfg.complete(results);
			}
			if(successes.length == order.length) {
			    if(cfg.success) cfg.success(results);
			}
		}
		
	}
	
});