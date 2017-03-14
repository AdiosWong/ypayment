/*
图表组件，注意需加载FusionCharts方可运作
*/
(function($){

define(function(require, exports, module){
    require('../Y-script/Y-string.js');
    Y.inherit('Chart','box',{
	    doInit: function(cfg){
		    var _this = this;
		    if(typeof FusionCharts !== 'function') {
			    Y.handlerError('未加载FusionCharts');
				return false;
			}
			cfg.renderTo = cfg.renderTo || cfg.target || '';
		    this.callBase('doInit','box',cfg);
			Y.Chart[this.key] = function(index){
			    var data = cfg.data;
			    _this.fire('barclick',data[index]);
			}
			cfg.chartConfig = $.extend({},Y.Chart.defaults.chartConfig,cfg.chartConfig);
		},
		doRender: function(){
		    this.callBase('doRender','box');
			this.renderChart();
		},
		renderChart: function(){
		    var cfg = this.cfg;
		    this.el.empty();
			var width = cfg.chartWidth,height = cfg.chartHeight;
			if(cfg.autoFill) {
			    width = this.renderTo.innerWidth();
				height = this.renderTo.innerHeight();
			}
			var path = cfg.swfPath, type = "FCF_" + cfg.chartType.replace(/\..*/,'');
			var elId = "Y_chart_elid_" + this.key;
			var chartId = "Y_chart_id_"+ this.key;
			this.el.attr('id',elId);
			var chart = new FusionCharts(path + type + ".swf",chartId,width,height);
			chart.setDataXML(this.dataToXml());
			chart.render(elId);
			this.chart = chart;
		},
		setChartType: function(type){
		    this.cfg.chartType = type;
			this.renderChart();
		},
		setChartData: function(data){
		    this.cfg.data = data;
			this.renderChart();
		},
		dataToXml: function(){
		    var _this = this;
		    var cfg = this.cfg;
			var data = cfg.data;
			var str = Y.StringBuilder();
			str.append("<graph");
			for(var i in cfg.chartConfig) {
			    str.append(" " +i + "='"+cfg.chartConfig[i]+"'");
			}
			str.append(">");
			if(cfg.chartType.substring(0,2) =="MS") {
			} else {
			    $.each(data,function(i,item){
				    str.append("<set");
					for(var name in item) {
					    str.append(" "+name+"='"+item[name]+"'");
					}
				    if(cfg.barclick) {
				        str.append(" link='JavaScript:Y.Chart[\""+_this.key+"\"](\""+i+"\")'");
				    }
				    str.append(" />");
				});
			}
			str.append("<trendlines></trendlines><styles><definition>");
			for(var i in cfg.chartStyle) {
			    //str.append(" "+i+"='"+cfg.chartStyle[i]+"'");
			}
			str.append("<style name='myCaptionFont' type='font' font='黑体' size='14' color='414042' bold='0' underline='0'/>");
			str.append("<style name='myYFont' type='font' size='13' bold='0' /></definition><application>");
			str.append("<apply toObject='Caption' styles='myCaptionFont' />");
			str.append("<apply toObject='YAxisName' styles='myYFont' /></application></styles></graph>");
			return str.toString();
		},
		doDestroy: function(){
		    Y.Chart[this.key]= null;
			this.chart = null;
			this.el.empty();
		    this.callBase('doDestroy','box');
		}
    });
	
    Y.Chart.defaults = {
	    chartConfig: {
			animation: '0',
			showValues: '1',
			showhovercap: '1',
			baseFontSize: "12",
			caption:'',
			showShadow: '0'
		},
		swfPath: '../../lib/FusionChartsFree/',
		chartType: 'Column3D',
		autoShow: true,
		autoFill: true
	}
});

})($);
