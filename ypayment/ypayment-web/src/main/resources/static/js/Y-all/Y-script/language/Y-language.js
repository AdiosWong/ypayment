/**
 * @fileoverview 语言包加载文件
 * @author xiaoyangzheng
 * @modify 
 * @requires
 * @description   
 * @created  2014-12-15
 * @updated  
 */

define(function(require, exports, module){
    Y.language = Y.language || {};
    $.extend(Y.language, {
        name: '',
        getPact: function(name){
            if(!this.name) return;
            if(!name) return this.pact[this.name];
            return this.pact[this.name][name] || {};
        },
        pact: {}
    });
	require('../language/Y-language-zh.js');
	require('../language/Y-language-en.js');
});
