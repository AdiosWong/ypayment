define(function (require) {
	require('../../../comp/init.js');

	var isMobile = false;
	$("#phoneNumber").keyup(function(){
		if($(this).val()!=''){
			$('#clearPhoneNo').addClass('clear-ico');
		}else {
			$('#clearPhoneNo').removeClass('clear-ico');
		}
	}).focus(function(){
		if($(this).val()!=''){
			$('#clearPhoneNo').addClass('clear-ico');
		}else {
			$('#clearPhoneNo').removeClass('clear-ico');
		}
	}).blur(function(){
		checkPhoneNo();
		setTimeout(function(){
			$('#clearPhoneNo').removeClass('clear-ico');
		},1);
	});
	
	$('div.recharge-list li').click(function(){
		$('div.recharge-list li').removeClass('current');
	    $(this).addClass('current');
	    checkPhoneNo();
	    if (isMobile) {
	        $('#mobileNo').hide();
	    }else {
	    	$('#mobileNo').show();
	        return false;
	    }
	});
	function checkPhoneNo(){
		isMobile = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(14[0-9]{1})|(17[0-9]{1}))+\d{8})$/.test($('input[name=phoneNumber]').val());
		if (isMobile) {
			$('#mobileNo').hide();
		} else {
			$('#mobileNo').show();
		}
	};
	
	$('#clearPhoneNo').click(function(){
		$("#phoneNumber").attr("value","");
		$(this).removeClass('clear-ico');
		return false;
	});
	
});