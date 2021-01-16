(function($) {
	$.fn.xdlines = function(options) {
		var opts = $.extend({}, $.fn.xdlines.defaults, options);
		var $this = $(this);
		for(var k = 0; k < $this.length; k++) {
			var $obj = $this.eq(k);

			$obj.css({ wordWrap: 'break-word' })
			var str = $obj.text();
			str = str.replace(/(^\s*)|(\s*$)/g, "");
			var l = str.length;
			var txt = '';
			var lineHeight; //单行高度
			var linesNum = opts.max; //需要的行数

			for(var i in str) {
				txt += str[i];
				$obj.text(txt);
				var nowh = parseInt($obj.css('height')); //当前高度
				if(i == 0) {
					lineHeight = nowh * linesNum;
				}

				if(nowh > lineHeight) {
					var txt = txt.substring(0, txt.length - 3) + "...";
					$obj.text(txt);
					break;
				}
			}
		}
	};

	$.fn.xdlines.defaults = {
		max: 1
	}

})(jQuery)
