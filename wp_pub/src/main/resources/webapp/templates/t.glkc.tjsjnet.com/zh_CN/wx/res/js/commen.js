/**
 * Created by sandmer on 2018/4/10.
 */
$.init();

$.config = {router: false}
var swiper1 = new Swiper('.index_banner_box .swiper-container', {
    pagination: '.index_banner_box .swiper-pagination',
    paginationClickable: true,
    autoplayDisableOnInteraction : false,
//        effect:'fade',
    speed:500,
    loop:true,
    autoplay:3000
});
var swiper2 = new Swiper('.index_box2 .swiper-container', {
    pagination: '.index_box2 .swiper-pagination',
    paginationClickable: true,
    autoplayDisableOnInteraction : false,
    speed:500,
    loop:true,
    autoplay:3000,
});
(function($){$.fn.xdlines=function(options){var opts=$.extend({},$.fn.xdlines.defaults,options);var $this=$(this);for(var k=0;k<$this.length;k++){var $obj=$this.eq(k);$obj.css({wordWrap:"break-word"});var str=$obj.text();str=str.replace(/(^\s*)|(\s*$)/g,"");var l=str.length;var txt="";var lineHeight;var linesNum=opts.max;for(var i in str){txt+=str[i];$obj.text(txt);var nowh=parseInt($obj.css("height"));if(i==0){lineHeight=nowh*linesNum}if(nowh>lineHeight){var txt=txt.substring(0,txt.length-3)+"...";$obj.text(txt);break}}}};$.fn.xdlines.defaults={max:1}})(jQuery);

