(function($,undefined){
	$.fn.myplugin = function(){
		$(".parentNode>h3").click(function(){
			$(".subNode>li").hide("slow");	
		}); 
	};
}(jQuery));