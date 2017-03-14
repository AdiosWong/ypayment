define(function (require) {
require('../../comp/init.js');
    
    var form = $('#platformForm');
    form.validate({
        errorElement: 'p',
        errorClass: 'err-tip',
        ignore: '.not-check',
        errorPlacement: function (error, element) {
            element.parents('.item').append(error);
        },
        rules: {
        	platformType: {
                required: true
            },
            platformName: {
                required: true
            },
            status: {
            	required: true
            },
            sms: {
            	required: true
            },
            paymentTypeList: {
            	required: true
            },
            tradeBizProductCode: {
            	required: true
            },
            partnerId: {
            	required: true,
            	number: true
            }
        },
        messages: {
        	platformType: {
                required: '平台类型不能为空'
            },
            platformName: {
                required: '平台名称不能为空'
            },
            status: {
            	required: '请选择平台状态'
            },
            sms: {
            	required: '请选择是否短信通知'
            },
            paymentTypeList: {
            	required: '请选择缴费类型'
            },
            tradeBizProductCode: {
            	required: '交易收费码不能为空'
            },
            partnerId: {
            	required: '商户ID不能为空',
            	number: '商户ID由数字组成'
            }
        },
        submitHandler: function (from) {
        	from.submit();
        },
        onkeyup: false
    });

    $('#platformSubmit').click(function () {
    	form.submit();
        return false;
    });

});