
(function($){

define(function(require, exports, module){
    require('../Y-script/Y-window.js');
    
	Y.inherit('TranslateTip','Tip',{
	    doInit: function(cfg){
		    this.callBase('doInit', 'Tip', cfg);
			var target = this.target;
			if(!target) {
			   Y.handlerError('TranslateTip类必须配置target');
			   return;
			}
			var translateType = cfg.translateType,
				_this = this;
			if(typeof translateType == "string"){
				translateType = this[translateType] || this.fixedTurn;
			}else{
				Y.handlerError('TranslateTip类配置的translateType类型错误');
			    return;
			}
			
			this.bind(target, 'keyup', function(e){
				_this.doTranslate(translateType);
			});
			this.bind(target, 'blur', function(e){
			    _this.hide(cfg.speed);
			});
		},
		doTranslate:function(translateType){
			var cfg = this.cfg,
				val = this.target.val(),
				html = translateType.call(this,val);
				
			if(!html)return this.hide(cfg.speed);
			this.el.html(html);
			this.show(cfg.speed,this);
		},
		digitUppercase:function(n){
			var fraction = ['角', '分'];
			var digit = ['零', '壹', '贰', '叁', '肆','伍', '陆', '柒', '捌', '玖'];
			var unit = [['元', '万', '亿'],['', '拾', '佰', '仟']];
			var head = n < 0? '欠': '';
			n = Math.abs(n);
			var s = '';
			for (var i = 0; i < fraction.length; i++) {
				var arr = (''+n).split('.');
				var num;
				if(arr.length <= 1) num = digit[0];
				else num = digit[arr[1].charAt(i) || 0];
				s += (num + fraction[i]).replace(/零./, '');
			}
			s = s || '整';
			n = Math.floor(n);
			for (var i = 0; i < unit[0].length && n > 0; i++) {
				var p = '';
					for (var j = 0; j < unit[1].length && n > 0; j++) {
					p = digit[n % 10] + unit[1][j] + p;
					n = Math.floor(n / 10);
				}
				s = p.replace(/(零.)*零$/, '').replace(/^$/, '零')+ unit[0][i] + s;
			}
			return head + s.replace(/(零.)*零元/, '元').replace(/(零.)+/g, '零').replace(/^整$/, '零元整');
		},
		//定制功能
		customMade:function(s){// 4个xxxx 在一块的格式化 为了向前兼容 所留
			var length = this.cfg.length;
			var count = (length + 4) / 4 | 1;
			var str = Array(count + 1).join('xxxx ').slice(0,length + count - 1);
			s = s.replace(/\s/g,'').slice(0,length);
			this.target.val(s);
			this.cfg.fixedString = str;
			return this.getTranslate(s);
		},
		fixedTurn:function(s){//默认的格式化方式
			var length = this.cfg.fixedString.replace(/\s/g,'').length;
			s = s.replace(/\s/g,'').slice(0,length);
			this.target.val(s);
			return this.getTranslate(s);
		},
		getTranslate:function(val){//获取格式化字符串 只替换x字母
			var cfg = this.cfg;
			var index = 0;
			return cfg.fixedString.replace(/./g,function(a){
				if(index >= val.length){
					if(!cfg.showMore)return '';
					else return a;
				}
				if(a != 'x')return a;
				return val.charAt(index++);
			});
		},
		reverse:function(str){//颠倒字符串
			return ('' + str).split('').reverse().join('');
		},
		fmoney:function(s){//数字的格式化 处理 比字符串多个.
			var cfg = this.cfg;
			cfg.fixedString = cfg.fixedString || 'xxx,xxx,xxx,xxx,xxx,xxx,xxx,xxx';
			cfg.showMore = false;
			var r = s.split(".")[1];
			s = this.reverse(s.split(".")[0].replace(/\s/g,''));   
			var t = this.getTranslate(s);
			t = this.reverse(t);
			if(r)return t+ "." + r;  
			else return  t;
		},
		doShow: function(speed,_this){
		    this.callBase('doShow','Window',speed);
		    this.layout();
		}
	});
	Y.TranslateTip.defaults = $.extend({},Y.Tip.defaults,{
		autoDisappear: false,
		css: 'wnd-tip-translatetip',
		translateType: '',//默认转换方式
		renderTo : 'body',
		spacing : 0 ,
		speed : 0 ,
		align: 'top',
		length: 16 , // xxxx xxxx xxxx xxxx 特殊配置 x的个数
		fixedString:'',//固定格式 xxx xx xx 只有x是被替换的 其他不变
		showMore:true,//位数不够多时 是否显示多余的默认的替代符号
		bindType: 'bind'
	});
	
});
  
})($);