/**
 //肖扬政
 */

define(function(require, exports, module) {
	return function(w,h,content){
	    if(!content && !$('#flexContent').length) return;
	    var swfobject = safeControlSwf;
	    content = content?$(content).eq(0):$('#flexContent');
	    var contentId;
	    if(content.attr('id')) {
	        contentId = content.attr('id');
	    } else {
	        contentId = getControlKey('controlContent');
	        content.attr('id',contentId);
	    }
	    function getControlKey(str){
	    	window.safeControlKey = typeof window.safeControlKey == 'object'?window.safeControlKey:{};
	    	var key = (str || '')+ Math.round(Math.random()*10000);
	    	if(window.safeControlKey[key]) {
	    		key = getControlKey(str);
	    	} else {
	    		window.safeControlKey[key] = true;
	    		return key;
	    	}
	    	return key;
	    }
	    var controlId = 'SecurityComponent' + contentId;
	    var passwordEl = content.next();
	    passwordEl.addClass('fn-shoudValid');
	    var hasFlash = flashChecker();
	    if(hasFlash) {
	     	var swfVersionStr = "9.0.0";
	        var xiSwfUrlStr = "";
	        var flashvars={};
	        flashvars.url="/anon" ;
	        flashvars.width=typeof controlWidth ==="undefined"?w:controlWidth;
	        flashvars.height=typeof controlHeight ==="undefined"?h:controlHeight;
	        flashvars.isPassword=1;
	        var params = {};
	        params.quality = "high";
	        params.bgcolor = "#ffffff";
	        params.allowscriptaccess = "sameDomain";
	        params.allowfullscreen = "true";
	        params.wmode="window";
	        var attributes = {};
	        attributes.id = controlId;
	        attributes.name = controlId;
	        attributes.align = "middle";
	        swfobject.embedSWF(
	         		        "/swf/YJFSecurityComponent.swf", contentId, 
	         		        flashvars.width+2+'', flashvars.height+2+'', 
	         		        swfVersionStr, xiSwfUrlStr, 
	         		        flashvars, params, attributes);
	        swfobject.createCSS('#'+contentId, "display:block;text-align:left;");
			setTimeout(function(){
				
				if($('#'+controlId)[0]) {
					 $('#'+controlId)[0].onblur = function(){
						    setValue();
				     }
				}
			   
				$('button,a,[type=button],[type=submit],.fn-btn').bind('focus mousedown',function(){
			        setValue();
			    });
				
				passwordEl.data('setValue',function(){
					setValue();
				});
			},1);
			
			
		}else{
	     	var safeInfo = $('<a href="http://get.adobe.com/cn/flashplayer/?promoid=JZEFT" class="safe-control">为了您的资金安全，请先安装安全控件,安装成功后请刷新页面</a>');
	     	content.after(safeInfo);
	        noSecurity();
	    }
	    function getSecurityData() {
            var newObject=swfobject.getObjectById(controlId); 
            if(!newObject || !newObject.getSecurityDomainValue) return '';
            return newObject.getSecurityDomainValue();
     }     
              
	    function setValue(){
	    	try {
	    		var securityData = getSecurityData();
			    var el = $('#'+controlId).next(); 
				if(!securityData || securityData === 'invalid') {
				    el.val('');
				} else {
					el.val(securityData);
				}
	    	} catch (e){}
		    
		}	  
		var notSave;
		function noSecurity(){
		    if(!$('#isSafeControl').length) return;
		    var safeInfo = content.next();
            //var pwdIn = $("<input class='fm-text' type='password' style='display:none;' />");
            var pwdIn = passwordEl;
            pwdIn.attr('autocomplete','off');
            //var noSecurity = $("<input type='hidden' name='noSecurity' value='false' />");
            //content.append(noSecurity);
            notSave = true;
            $('#isSafeControl').attr('checked',true).parent().show();
            $('#isSafeControl').click(function(){
                if($(this).attr('checked')) {
                    safeInfo.show();
                   // noSecurity.val('false');
                    pwdIn.hide().val('');
                } else {
                    safeInfo.hide();
                    pwdIn.show();
                    //noSecurity.val('true');
                }
            }); 
            content.hide();
            /*
            pwdIn.bind('keyup change',function(){
                passwordEl.val($(this).val());
            });
            pwdIn.triggerHandler('change');
            */
		}
	          
	     // 判断是否安装了flash
	     function flashChecker()
	     {    
	             var hasFlash=0;         //是否安装了flash
	             var flashVersion=0; //flash版本
	             var isIE=/*@cc_on!@*/0;      //是否IE浏览器
	          
	             if(isIE)
	             {
	               try {
	             	  var swf = new ActiveXObject('ShockwaveFlash.ShockwaveFlash'); 
	                   if(swf) {
	                      hasFlash=1;
	                      VSwf=swf.GetVariable("$version");
	                      flashVersion=parseInt(VSwf.split(" ")[1].split(",")[0]); 
	                   }
	               } catch(e) {
	             	  // 没有flash会抛异常
	               }
	               
	            }else{
	                if (navigator.plugins && navigator.plugins.length > 0)
	                {
	                    var swf=navigator.plugins["Shockwave Flash"];
	                    if (swf)
	                    {
	                       hasFlash=1;
	                       var words = swf.description.split(" ");
	                       for (var i = 0; i < words.length; ++i)
	                       {
	                          if (isNaN(parseInt(words[i]))) continue;
	                          flashVersion = parseInt(words[i]);
	                       }
	                    }
	                }
	            }
	            return hasFlash?true:false;
	     }  		
	}    
              
          
});           
              
              