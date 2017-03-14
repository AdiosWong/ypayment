define(function(require, exports, module) {
	
	require('../../comp/init.js');
	
	$("#contractNo").change(function(){
		var txt=$(this).find("option:selected").text();
		var contractNo=$(this).val();
		if (contractNo=='') {
			alert("请选择需要缴费的卡号");
			return false;
		}
		
		$.ajax({
	         url: '/test/payment/getPayables.json?contractNo='+contractNo,
	         dataType:'json',
	         async:false,
	         success: function (map) {
	        	 var payable="";
	         	 for (var key in map){
	         		 if (key == 'totalAmount') {
	         			 var amount = "<div class='item-box fn-clear mt10'>"
	         				 		+"<div class='item'>"
	         				 		+"<label class='lab'>应交金额：</label>"
	         				 		+"<div class='txt'>"+map[key]+" (元)</div>"
	         				 		+"</div></div>";
	         			 $(".totalPayables").html(amount);
	         			 $("#amount").val(map[key]);
	         			continue;
	         		 }
	         		payable=payable+"<div class='item-box fn-clear mt10'><div class='item'>"
    					+"<label class='lab'>年月：</label> "
    					+"<div class='txt'>"+key+"</div>"
    					+"</div><div class='item'>"
    					+"<label class='lab'>欠费金额：</label>"
    					+"<div class='txt'>"+map[key]+ " (元)</div>"
    					+"</div></div>";
	         	 }
	         	 $(".payable").html(payable);
	         }
	     });
	});
});