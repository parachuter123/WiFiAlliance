(function($,undefined){
	$.fn.minimap = function(options){
		var minimap = this;
		var $window = $(window);
		var fn = function(){};
		var show = true;
		
		var defaults = {
	            heightRatio : 0.6,
	            widthRatio : 0.05,
	            offsetHeightRatio : 0.035,
	            offsetWidthRatio : 0.035,
	            position : "right",
	            touch: true,
	            smoothScroll: true,
	            smoothScrollDelay: 200,
	            onPreviewChange: fn
	     };
	     var settings = $.extend({},defaults, options);
	     var position = ["right", "left"];
	     
	     	var miniElement = minimap.clone();
	     	miniElement.find('.minimap.noselect').remove();
	        miniElement.find('.miniregion').remove();
	        miniElement.addClass('minimap noselect');
	        // remove events & customized cursors
	        miniElement.children().each(function() {$(this).css({'pointer-events': 'none'});});

	        var region = $('<div class="miniregion"> </div>');

	        $($('body')[0]).append(region);
	        $($('body')[0]).append(miniElement);
	        
	};
})(jQuery);