/**

 */


define(function(require, exports, module) {
  
	require('../plugins/jquery.jqtransform.js')($);
	require('../plugins/jquery.dateinput.js')($);
	require('../plugins/jquery.form.js')($);
    require('../plugins/jquery.validate.js')($);
	require('../module/validate.extend.js');
	require('../Y-all/Y-script/Y-base.js');
	require('../comp/template.js');  
	require('../comp/order_ajax.js'); 
	require('../comp/ajaxHandle.js'); 
	
	

    var Site = {
		hover: function(obj){
				obj.hover(function(){
					$(this).addClass('hover')
				}, function(){
					$(this).removeClass('hover')
				})
		},
		init: function(){
			
			if ($('.default_form').length){
				$('.default_form').validate({
					errorClass: 'error-tip',
					errorElement: 'b',
					onkeyup: false
					
				});
				
			}
			
			var dateInputs = $('input[type=date]').add('.widget_date');
			if(dateInputs.length) {
				dateInputs.each(function(i,item){
					var dateconfig = {trigger: true, selectors:true};
					dateconfig.yearRange = [-10,15];
					if($(item).attr('yearRange')) {
						dateconfig.yearRange = eval($(item).attr('yearRange')) || [-10,15];
					}
				    $(item).dateinput(dateconfig);
				});
			    //dateInputs.dateinput({trigger: true, selectors:true, yearRange: [-10, 15]});
			}
			
			
			
			var $menu = $('.main-menu > ul > li'),
				$customForm = $('.jqtransform'),
				$button = $('button.button');
			if ($menu.length > 0){
				Site.hover($menu);
			}
			if ($button.length > 0){
				Site.hover($button);
			}
			if ($customForm.length > 0){
				$customForm.jqTransform();
			}
			
			var menu = $('ul.menu');
			menu.find('.menu_1').each(function(){
				var ul = $(this).children('.sub_menu');
				if(ul.is(':visible')) $(this).addClass('fn-open');
			});
			menu.on('click',' .menu_1', function(){
				if(!$(this).hasClass('fn-open')) {
					$(this).children('ul').slideDown();
					$(this).addClass('fn-open');
				} else {
					$(this).children('ul').slideUp();
					$(this).removeClass('fn-open');
				}
			});
			menu.on('click','.sub_menu',function(e){
				e.stopPropagation();
			});
				
			$.validator.setDefaults({
				errorClass: 'error-tip',
				errorElement: 'b',
				onkeyup: false,
				errorPlacement: function(err,el){
					el.parent().append(err);
				}
			});
			
			
			
		}
	};
    
    /* 只能输入数字 */
	var moneys = $('.mask_money');
	moneys.css('ime-mode', 'disabled').bind('paste', function(){
	    return false;
	});
	$('body').delegate('.mask_money','paste', function(e){
		return false;
	});
	$('body').delegate('.mask_money','keydown', function(e){
	    var key = e.keyCode;
		var val = $(this).val(), charIn;
		var index = getCurserIndex($(this));
		var codes = {
		    8: true, 46 : true, 37: true, 39: true
		};
		if(key == 110 || key == 190) {
		    charIn = ".";
		} else if(key >= 48 && key <= 57) {
			charIn = '' + (key-48);
		} else if(key >= 96 && key <= 105){
		    charIn = '' + (key-96);
		} else if(codes[key]) {
		    return true;
		} else {
		    return false;
		}
		chars = val.split('');
		chars.splice(index.start || 0,(index.end-index.start) || 0,charIn);
		val = chars.join('');
		return isMoney(val);
	});
	$('body').delegate('.mask_money','keyup', function(e){
	    var key = e.keyCode;
		var val = $(this).val();
		if(val && !isMoney(val)) {
		    $(this).val($(this).data('money_lastval') || '');
			return;
		}
		$(this).data('money_lastval',val);
	});

	$('body').delegate('.mask_number','keydown', function(e){
    	var key = e.keyCode;
    	var fnCodes = {
		    8: true, 46 : true, 37: true, 39: true
		};
		if(fnCodes[key]) return;
    	var isNum = (key >= 48 && key <= 57) || (key >= 96 && key <= 105);
    	if(!isNum) return false;
    });

	function isMoney(val){
	    if(typeof val != 'number' && typeof val != 'string') return false;
		val = '' + val;
		return (/^(\d|([1-9]\d+))(\.\d{0,2})?$/).test(val);
	}
	function getCurserIndex(el){
	    el = el[0]?el[0]:el;
		var start, end;
		if(el.selectionStart || el.selectionStart === 0){//非IE浏览器
			start = el.selectionStart;
			end = el.selectionEnd;
		}else{//IE
			if(document.selection) {
			    var range = document.selection.createRange();
			    var length = range.text.length;
				range.moveStart('character', -1*$(el).val().length);
			    end = range.text.length;
				start = end - length;
			}
		}
		return {start: start, end: end};
	}
	
    
	   var submitBtn = $('form').find('button[type=submit]');
	    if(submitBtn.length) {
	    	submitBtn.click(function(e){
	    		    e = e || event;
	    		    e.stopPropagation();
	    	});
	    	submitBtn.parent().click(function(e){
			    if(submitBtn.attr('disabled')) return;
			    if($(this).hasClass('fn-g-btn') || $(this).hasClass('fn-h-btn')) {
				    var _this = this;
				    setTimeout(function(){
				        $(_this).parents('form').submit();
				    },1);
				}
	    	});
	    }
   
	
  /* hide msg_box */
	var msgBox = $('#msg_box');
	if (msgBox.length > 0){
		$('body').click(function(){
			msgBox.hide();
			$('body').unbind('click');
		})
		
	}
	
	//协议toggle
	var btnLicense = $('#btn_license');
		
	if (btnLicense.length > 0){
		btnLicense.click(function(){
			if ($('.fm-license').is(':hidden')){
				$('.fm-license').show();
			}
			else {
				$('.fm-license').hide();
			}
		});
		$('.fm-license .close').click(function(){
			$('.fm-license').hide();
			return false;
		});
	}
	
	
	Site.init();
	
	//动态生成初始input输入提示
    var startInput = $('.start_input');
		if(startInput.length) {
			startInput.each(function(i,item){				
				var tinput = $(item);
				var info = tinput.attr('startValue');
				var off = tinput.offset();
				var over = $('<div class="start_valueEle" for="'+tinput.attr('name')+'">').html("<em style='color:#aaaaaa;font-size:12px;  font-family:'宋体'>"+info || '请输入'+"</em>").css({
					position:'absolute',
					zIndex:10,
					left:off.left+20,
					top:off.top+5
				});
				$('body').append(over);
				
				function hideStart(){
					over.hide();
					tinput.get(0).focus();
				}
				function showStart(){
				    var off = tinput.offset();
					over.css({
					   left:off.left+20, top:off.top+5
					}).show();
				}
				if(tinput.val()) {
					hideStart();
				}
				tinput.bind('focus',hideStart);
				over.click(function(){
				    tinput.trigger('focus');
				});
				tinput.blur(function(){
					var val = $(this).val();
					if(!val) {
						showStart();
					}
				});
			});
		}
	/*避免忘记删除调试代码产生的异常*/
	if(!window.console) {
	    window.console = {log:function(){}}
	}
	return Site;
	
  
  
});