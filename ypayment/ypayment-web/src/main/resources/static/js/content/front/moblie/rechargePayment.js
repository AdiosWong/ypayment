define(function (require) {
require('../../../comp/init.js');
    
	var form = $('#rechargePayForm');
	var sub_btn = $('button.sub-btn');
	
	form.validate({
		errorElement: 'p',
        errorClass: 'psw-error',
        ignore: '.not-check',
        errorPlacement: function (err, element) {
        	$('.payMsg').empty();
			$('.payMsg').append(err);
        },
	    rules: {
	    	token: {
	    		required: true,
	    		customRemote: {
	                url: '/recharge/checkToken.json?dateTag=' + new Date().getTime(),
	                data: {
	                	token:function(){
	                    	return $('[name=token]').val();
	                    }
	                },
	                type: 'POST',
	                customError: function (element, res) {
	                    if (res.code != 1) {
	                        return res.message
	                    }
	                }
	              }
	    	},
	    	password: {
	        	required: true,
	        	minlength: 8,
	        	customRemote: {
	                url: '/recharge/checkPayPwd.json?dateTag=' + new Date().getTime(),
	                data: {
	                	payPwd:function(){
	                    	return $('[name=password]').val();
	                    }
	                },
	                type: 'POST',
	                customError: function (element, res) {
	                    if (res.code != 1) {
	                        return res.message
	                    }
	                }
	              }
	    	}
	    },
	    messages: {
		    token :{
	    		required: '请勿重复提交'
	    	},
	    	password: {
	    		required: '请输入您的支付密码',
	    		minlength: '支付密码长度必须大于或等于8位'
	    	}
	    },
	    success: function() {
	    	var availableAmount = parseFloat($("#availableAmount").val());
	    	var payAmount = parseFloat($("#payAmount").val());
	    	var payPact = $('input[name=paymentType]').val().split("_");
	    	if (payPact[0] == 'balance' && availableAmount < payAmount) {
	    		$('.payMsg').empty();
				$('.payMsg').append("<p class='psw-error'>您的账户余额不足</p>");
	    		sub_btn.removeClass('bg-blue').addClass('bg-gary');
	        	sub_btn.attr("disabled", true); 
	    	} else {
	    		sub_btn.removeClass('bg-gary').addClass('bg-blue');
		    	sub_btn.attr("disabled", false); 
	    	}
        },
        highlight: function() {
        	sub_btn.removeClass('bg-blue').addClass('bg-gary');
        	sub_btn.attr("disabled", true); 
        },
	    onkeyup: false,
	    onblur: false,
	    submitHandler : function(from) {
	    	$(from).find(".sub-btn").attr("disabled", true); 
	    	from.submit(); 
		}
	  });
	
	$(document).ready(function(){
		sub_btn.attr("disabled", true);
	});
	
	 $('.sub-btn').click(function(){
		 $("#payPact").val($('input[name=paymentType]').attr("value"));
		 form.submit();
	 });
	
	 $('li.more').click(function(){
	    $('div.bank-list').toggleClass('fn-hide');
	    $('i.more-ico').toggleClass('more-ico2');
	  });
	  
	  $('div.bank-list').find('ul li').click(function(){
	    if (!$('div.bank-list').hasClass('fn-hide')) {
	      $('input[name=password]').attr("value","");
	      $('.payMsg').empty();
	      $('dl.payway').text($(this).text());
	      $('input[name=paymentType]').attr("value",$(this).find("input:last").attr("name"));
	      $(this).append($('i.current-ico'));
	      $('div.bank-list').addClass('fn-hide');
	      $('i.more-ico').removeClass('more-ico2');
	    };
	  });
	  
	$('input[name=password]').keyup(function(){
		if($(this).val()!=''){
			$('#clearPsw').addClass('clear-ico');
		}else {
			$('#clearPsw').removeClass('clear-ico');
		}
	}).focus(function(){
		if($(this).val()!=''){
			$('#clearPsw').addClass('clear-ico');
		}else {
			$('#clearPsw').removeClass('clear-ico');
		}
	}).blur(function(){
		setTimeout(function(){
			$('#clearPsw').removeClass('clear-ico');
		},1);
	});
	
	$('#clearPsw').click(function(){
		$('input[name=password]').attr("value","");
		$(this).removeClass('clear-ico');
		return false;
	});
});