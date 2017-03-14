/**
 * @fileoverview 对ajax请求进行预处理
 * @author 未知数，xyangzheng@yiji.com
 * @created  2014-08-05
 * @updated  
 */
(function($){

define(function(require, exports, module) {
    require('../Y-all/Y-script/Y-window.js');
	
	if($.alreadySetAjaxHandle) return;
	$.alreadySetAjaxHandle = true;
	
	var ajax = $.ajax;
	var urlhistorys = {};
    $.ajax = function(param){
	    param = typeof param === 'string'?{url: param}:param;
	    param = $.extend(true,{},{dataType: 'json'}, param, arguments[1] || {});
	    if(!param.original) {//若配置original，不做预处理
			if(pretreatment(param) === false) return;
		}
		ajax.apply(this,arguments);
		urlhistorys[param.url] = new Date();
    }
	
	function pretreatment(param){//请求的预处理
	    if(isReSubmit(param.url, param.leastInterval) && param.allowContinuity === false) {//防连续重复提交
		    return false;
		}
		if(param.loadding) {//ajax进行中特效
		    var loadding = showLoadding(param.loadding,param.loaddingPlacement);
		}
		if(param.button) {//启用防提交按钮重复点击
		    setDisable(param.button, param.submitText);
			setTimeout(function(){
			    setAble(param.button);
			},5000);
		}
		var complete = param.complete;
		param.complete = function(){
			if(param.button) setAble(param.button);
			if(param.loadding) {
			    if(loadding.data('wnd')) {
					loadding.data('wnd').close();
					loadding.data('wnd', null);
				}
				loadding.remove();
			}
			if(typeof complete === 'function') complete.apply(this,arguments);
		}
		
		if(param.responseHandle !== false) {//默认启用响应预处理
			var success = param.success;
			var error = param.error;
			param.success = function(res){
				if(param.button) setAble(param.button);
				if(responseHandle(res) === false) return;
				if(typeof success === 'function') success.apply(this,arguments);
			}
			param.error = function(e){
				if(errorHandle(e) === false) return;
			    if(typeof error === 'function') error.apply(this,arguments);
			}
		}
	}
	function responseHandle(res){//响应的预处理
		if(typeof res === 'srting') {
			
		}
	}
	function errorHandle(e){//请求出现异常的预处理
		
	}
	
	function isReSubmit(url, leastInterval){
		if(urlhistorys[url]) {
		    var lastTime = urlhistorys[url];
			if(new Date().getTime() - lastTime.getTime() < (leastInterval || 500)) {
			    return true;
			}
		}
		return false;
	}
	
	function showLoadding(loadding, loaddingPlacement){
	    var el;
		if(loadding === true) {
		    el = $('<div>').addClass('loadding');
		}
		else el = $(loadding);
		if(loaddingPlacement) loaddingPlacement(loadding);
		else {
			var wnd = Y.create('Window',{
				content: el,
				simple: true
			});
			wnd.show();
			el.data('wnd', wnd);
		}
		return el;
	}
	
	function setDisable(btn, submitText){
		btn = $(btn);
		if(!btn.data('selfClone')) {
			var clone = btn.clone().removeAttr('id');
			if(!clone.children().length) {
			    clone.attr('disabled', 'disabled').html(submitText || '提交中...').click(function(){return false;});
			}
			else {
				clone.children().removeAttr('id').attr('disabled', 'disabled').html(submitText || '提交中...');
			}
			btn.hide().after(clone);
			btn.data('selfClone',clone);
		}
	}
	function setAble(btn){
		btn = $(btn);
		if(btn.data('selfClone')) {
			btn.data('selfClone').remove();
			btn.data('selfClone', null);
			btn.show();
		}
	}
});

})(jQuery);