<%@ Control Language="C#" AutoEventWireup="true" Inherits="We7.CMS.WebControls.BaseControl" %> 
 <script type="text/C#" runat="server"> 
   [ControlDescription(Desc = "全屏滚动图",Author="system")] 
 [ControlGroupDescription(Label = "全屏滚动图", Icon = "全屏滚动图", Description = "全屏滚动图", DefaultType = "scroll_banner")] 
 string MetaData; 
 </script> 
<!--htmlWidgetStart-->
<!--<script type="text/javascript" src="/_skins/201608A/js/jquery.js" ></script>--> 
<!--<script type="text/javascript" src="/_skins/201608A/js/jquery.caroufredsel-6.0.4-packed.js" ></script>-->
<script type="text/javascript" src="../../Widgets/WidgetCollection/MyParts/scroll_banner/js/focus.js" ></script>
<div class="allscroll">
<!--滚动图-->
<div id="banner_show" class="banner_show">
    <!--焦点图-->
    <a class="bannger_inbox" href="#"><img width="1920" src="/_skins/temp02/images/banner01.jpg" alt="" /></a>
    <a class="bannger_inbox" href="#"><img width="1920" src="/_skins/temp02/images/banner02.jpg" alt="" /></a>
    <a class="bannger_inbox" href="#"><img width="1920" src="/_skins/temp02/images/banner03.jpg" alt="" /></a>
    <!--焦点图-->
    <!--左右箭头-->
    <div class="banner_pre_next">
      <a class="banner_btn_left" href="javascript:;">上一个</a>
      <a class="banner_btn_right" href="javascript:;">下一个</a>
    </div>
    <!--左右箭头-->
</div>
<!--滚动图-->
</div>   
 <!--HtmlWidgetEnd-->