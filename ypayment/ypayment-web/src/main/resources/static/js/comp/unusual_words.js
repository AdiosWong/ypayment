/*
 * 生僻字
 * modify by yangle | yorsal.com 2012-04-09
 */
define(function(require, exports, module) {	 
	require('../plugins/jquery.window.js');
	
	var that = function unusualWords(){
		var data = {a:"奡靉叆",c:"旵玚棽琤翀珵楮偲赪瑒篪珹捵茝鷐铖宬査嶒",b:"仌昺竝霦犇愊贲琲礴埗別骉錶",d:"耑昳菂頔遆珰龘俤叇槙璗惇",g:"玍冮芶姏堽粿筦嘏釭",f:"仹汎沨昉璠雰峯洑茀渢棻棻頫",e:"峩",h:"郃浛訸嗃瓛翃隺鋐滈翚翯竑姮葓皜袆淏皞翙銲鉷澒澔閤婳黃峘鸻鈜褘锽谹嫮",k:"凱堃蒯鹍崑焜姱衎鵾愷鎧",j:"冏泂劼莙濬暕珒椈珺璟競煚傑玦鑑瑨瑨琎勣寯烱浕斚倢瑴畯雋傢峤",m:"劢忞旻旼濛嫚媺铓鋩洺媌媔祃牻慜霂楙媄瑂",l:"玏呂俍冧倞琍綝壘孋瓅璘粦琍麗樑秝鍊崚链镠皊箖菻竻鸰琭瓈騄浬瑠嶺稜欐昽",n:"婻寗嫟秾迺柟薿枏",q:"玘佺耹踆骎啟蒨慬勍嵚婍璆碏焌駸綪锜荍釥嶔啓",p:"芃玭玶罴毰珮蘋慿弸掽逄砯",s:"屾昇妽珅姼甡湦骦塽挻甦鉥燊遂陞莦湜奭佀聖骕琡",r:"汭瑈瑢讱镕婼叡蒻羢瀼",t:"沺凃禔慆弢颋譚曈榃湉珽瑱橦镋渟黇頲畑媞鰧",w:"卍彣炆溦娬韡暐偉湋妏硙珷娒",y:"乂冘弌贠伝伃杙沄旸玙玥垚訚堯溁嫈澐颺熤儀赟祎瑀湧燚嬿鋆嫄愔贇彧崟韻龑颙晹媖顒禕羕炀弇湲霙嫕浥飏峣曣億雲愔洢暘钖垟詠燿鹓歈貟瑩燏暎畇娫矞祐溳崯颍煬靷谳異軏繄",x:"仚旴忺炘昍烜爔斅豨勲敩虓鈃禤燮瑄晞賢翾譞諕璿琇晛焮珣晅郤禼皛哓肸谞迿咲婞昫缐姁猇欻箮翛暁",z:"烝梽喆禛誌曌衠淽枬詟炤昝珘赒"};
		var tpl = '<div class="unfamiliar" id="unfamiliarBox" style="width: 400px; border:1px solid #999; background:#fff; padding:5px; z-index: 8; position: absolute; left: 410px; top: 255px;"><div class="unfamiliar-spells"><a title="a" class="unfamiliar-spell selected" href="javascript:;">a</a><a title="b" class="unfamiliar-spell" href="javascript:;">b</a><a title="c" class="unfamiliar-spell" href="javascript:;">c[ch]</a><a title="d" class="unfamiliar-spell" href="javascript:;">d</a><a title="e" class="unfamiliar-spell" href="javascript:;">e</a><a title="f" class="unfamiliar-spell" href="javascript:;">f</a><a title="g" class="unfamiliar-spell" href="javascript:;">g</a><a title="h" class="unfamiliar-spell" href="javascript:;">h</a><a title="i" class="unfamiliar-spell" href="javascript:;">i</a><a title="j" class="unfamiliar-spell" href="javascript:;">j</a><a title="k" class="unfamiliar-spell" href="javascript:;">k</a><a title="l" class="unfamiliar-spell" href="javascript:;">l</a><a title="m" class="unfamiliar-spell" href="javascript:;">m</a><a title="n" class="unfamiliar-spell" href="javascript:;">n</a><a title="o" class="unfamiliar-spell" href="javascript:;">o</a><a title="p" class="unfamiliar-spell" href="javascript:;">p</a><a title="q" class="unfamiliar-spell" href="javascript:;">q</a><a title="r" class="unfamiliar-spell" href="javascript:;">r</a><a title="s" class="unfamiliar-spell" href="javascript:;">s[sh]</a><a title="t" class="unfamiliar-spell" href="javascript:;">t</a><a title="u" class="unfamiliar-spell" href="javascript:;">u</a><a title="v" class="unfamiliar-spell" href="javascript:;">v</a><a title="w" class="unfamiliar-spell" href="javascript:;">w</a><a title="x" class="unfamiliar-spell" href="javascript:;">x</a><a title="y" class="unfamiliar-spell" href="javascript:;">y</a><a title="z" class="unfamiliar-spell" href="#">z[zh]</a></div><div class="unfamiliar-words"><a style="z-index:10;" class="unfamiliar-word" href="#">奡</a><a style="z-index:10;" class="unfamiliar-word" href="#">靉</a><a style="z-index:10;" class="unfamiliar-word" href="#">叆</a></div></div>';
		var input = $('#realName'),
			evtLink = $('#openUnfamiliarSpells');
		
		if (evtLink.length > 0 && input.length > 0){ //有生僻字功能则开启
			evtLink.click(function(e){
				e.stopPropagation();
				if ($('#unfamiliarBox').length === 0){
					$('body').append(tpl);
					var offset = $('#openUnfamiliarSpells').offset();
					$('#unfamiliarBox').css('left', offset.left  + 'px');
					$('#unfamiliarBox').css('top', offset.top + 15  + 'px');
					$('#unfamiliarBox a').css('margin-right', '5px');
				}
				else {
					$('#unfamiliarBox').show();
				}
				
			});
			
				
			
			$(window).resize(function(){
				var offset = $('#openUnfamiliarSpells').offset();
				$('#unfamiliarBox').css('left', offset.left  + 'px');
			});
			
			
			$('#unfamiliarBox .unfamiliar-spells a').live('mouseover', function(){
				$('#unfamiliarBox .unfamiliar-spells a').removeClass('selected');
				$(this).addClass('selected');
				var html = '', i, str = data[$(this).attr('title')];
				
				if (str){
					for (i = 0; i < str.length; i++){
						html += '<a style="z-index:10;margin-right:5px;" class="unfamiliar-word" href="#">'+ str.charAt(i) +'</a>';
					}
					$('#unfamiliarBox .unfamiliar-words').html(html);
					
				}
				
				
			});	
			
			$('#unfamiliarBox .unfamiliar-words a').live('click', function(e){
				e.preventDefault();
				input.val(input.val() + $(this).html());
				input.select();
				
			});
			
			$(document).click(function(e){
		
				if ($(e.target).attr('id') !== 'openUnfamiliarSpells'){
					$('#unfamiliarBox').hide();
				}
					
			});
			
		}
		else {
			return;
		}
	}
	
	return that;
	
  
});