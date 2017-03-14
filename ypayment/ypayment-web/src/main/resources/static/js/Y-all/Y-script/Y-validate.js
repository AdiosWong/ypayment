
(function($){

define(function(){
    if(Y.Validate) return;
	Y.inherit('Validate','component',{
	    init:function(cfg){
		    this.callBase('init','component',cfg);
		    this.form = cfg.target?$(cfg.target):$('form').eq(0);
            this.all = [];
		    var idRules = cfg.idRules;
		    if(idRules) {
		        for(var id in idRules) {
			        var rules = idRules[id];
					cfg.rules[id] = rules;
					var el = $('#'+id);
					if(!el) {
					    continue;
					}
					this.all.push({el:el,name:el.attr('name'),key:id});
					el.attr('name',id);
			    }
		    }
			var idMessages = cfg.idMessages;
			if(idMessages) {
		        for(var id in idMessages) {
			        var rules = idMessages[id];
					cfg.messages[id] = rules;
			    }
		    }
			var _this = this;
			this.setValidate(cfg);
		},
		addRule:function(el,rule,message,key){
			el = $(el);
			if(key && key != el.attr('name')) {
			    this.all.push({el:el,name:el.attr('name'),key:key});
				el.attr('name',key);
			}
			
			if(rule) {
			    $.extend(rule,{messages:message});
				el.rules('add',rule);
			}
		},
		reSet:function(){
		    for(var i=0;i< this.all.length;i++) {
			    var obj = this.all[i];
				obj.el.attr('name',obj.key);
			}
		},
		setValidate:function(cfg){
		    var oldHandler = cfg.submitHandler;
			var _this = this;
		    cfg.submitHandler = function(){
			    for(var i=0;i< _this.all.length;i++) {
				    var el = _this.all[i].el;
					if(!el) continue;
					el.attr('name',_this.all[i].name);
				}
				if(oldHandler) oldHandler.apply(this,arguments);
			}
			this.validator = this.form.validate(cfg);
		}
	});	
	Y.Validate.defaults = {
        errorClass: 'error-tip',
		errorElement: 'b'
    }	
});


})($);
