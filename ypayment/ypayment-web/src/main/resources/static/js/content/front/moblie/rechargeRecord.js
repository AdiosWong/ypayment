define(function (require) {
	require('../../../comp/init.js');

	var pageInfo = {
		pageNum : 1,
		isloading: false,
		isend : false
	};
	
	function getPaymentRecord(){
		$.ajax({
			  url:'/recharge/rechargeRecord.json',
			  data:{
				  pageNum:pageInfo.pageNum
			  },
			  type:'POST',
			  dataType: 'json',
			  success: function(result){
				  if(result.code == 1){
					  var datalist = result.paymentOrderList;
					  pageInfo.isend = (datalist.currentPage == datalist.totalPage);
					  pageInfo.pageNum = datalist.currentPage+1;
					  addrecords(datalist.pageResults,pageInfo.isend);
					  pageInfo.isloading = false;
				  }else{
					  alert(result.message);
					  pageInfo.isloading = false;
				  }
			  },
			  error:function(){
				  alert(res.message);
				  pageInfo.isloading = false;
			  }
		  });
	}
	
	/**
	 * 页面加载
	 */
	$(document).ready(function(){
		getPaymentRecord();
	}).find('body').css('overflow-y','scroll');
	
	/**
	 * 滑动翻页
	 */
	$(document).scroll(function(e) {
		var scroTop = $(window).scrollTop();
		var winheight = $(window).height();
		var docheight = $('body').height();
		  if((scroTop+winheight>=docheight)&&!pageInfo.isend&&!pageInfo.isloading){
			  pageInfo.isloading = true;
			  getPaymentRecord();
		  }
	});
	
	var addrecords = function(data,isend){
		for(var i=0;i<data.length;i++){
			var item = data[i];
			var style, statusDes;
			if(data[i].paymentExtStatus == "SUCCESS") {
				style = 'fn-green';
				statusDes = '充值成功';
			} else if(data[i].paymentExtStatus == "FAIL") {
			    style = 'fn-red';
			    statusDes = '充值失败';
			} else {
				style = 'fn-blue2';
				statusDes = '处理中';
			}
			var thedl = $('<dl>'), thedt = $('<dt>').addClass('fn-gray');
			
			thedl.append('充值'+item.payable+'元-'+item.userCode).append($('<span>').text(item.paymentAmount.toFixed(2)+'元').addClass(style));
			thedt.append(item.startTime.substring(5,16)).append($('<span>').text(statusDes));
			$('div.record-list ul').append($('<li>').append(thedl).append(thedt));
		}
		if(isend){
			pageInfo.isend = true;
			$('div.record-list ul').append($('<li>').text('已经到底!'));
		}
	}
});