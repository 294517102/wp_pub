
/*首页导航*/
jQuery("#nav").slide({
    type: "menu",// 效果类型，针对菜单/导航而引入的参数（默认slide）
    titCell: ".nLi", //鼠标触发对象
    targetCell: ".sub", //titCell里面包含的要显示/消失的对象
    effect: "slideDown", //targetCell下拉效果
    delayTime: 300, //效果时间
    triggerTime: 0, //鼠标延迟触发时间（默认150）
    returnDefault: true //鼠标移走后返回默认状态，例如默认频道是“预告片”，鼠标移走后会返回“预告片”（默认false）
});
$('.rightboxdesc').xdlines({
    max:2
})
    
(function(){
    var bp = document.createElement('script');
    var curProtocol = window.location.protocol.split(':')[0];
    if (curProtocol === 'https'){
   bp.src = 'https://zz.bdstatic.com/linksubmit/push.js';
  }
  else{
  bp.src = 'http://push.zhanzhang.baidu.com/push.js';
  }
    var s = document.getElementsByTagName("script")[0];
    s.parentNode.insertBefore(bp, s);
})();
