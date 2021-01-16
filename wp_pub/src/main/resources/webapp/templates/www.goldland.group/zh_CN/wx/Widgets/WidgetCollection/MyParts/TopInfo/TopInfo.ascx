<%@ Control Language="C#" AutoEventWireup="true" Inherits="We7.CMS.WebControls.BaseControl" %> 
 <script type="text/C#" runat="server"> 
   [ControlDescription(Desc = "TOP头部日期及链接",Author="system")] 
   [ControlGroupDescription(Label = "头部日期及链接", Icon = "头部日期及链接", Description = "头部日期及链接", DefaultType = "TopInfo")] 
 string MetaData; 
 </script> 
<!--htmlWidgetStart-->
<div class="<%= CssClass %>">
    <div class="TopContent">
        <div class="topdate">
            <script language=JavaScript>
            var d, s = "";
            var x = new Array("星期日", "星期一", "星期二","星期三","星期四", "星期五","星期六");
            d = new Date();
            s+="今天是："+ d.getFullYear()+"年"+(d.getMonth() + 1)+"月"+d.getDate()+"日　";
            s+=x[d.getDay()];
            document.writeln(s);
            </script>
        </div>
        
        <div class="toplink">
            <!-- <span>></span><a href="#">微信公众号</a> -->
            <div class="weixin" onmouseover="this.className = 'weixin on';" onmouseout="this.className = 'weixin';">
                <a href="javascript:;"></a>
                <div class="weixin_nr">
                    <div class="arrow"></div>
                    <img src="/_skins/temp02/images/eweima.png" width="110" height="110" />
                    关注官方微信
                </div>
            </div>

            <span>></span><a href="/contact/2.aspx">联系我们</a>
        </div>
    </div>
</div>
<!--HtmlWidgetEnd-->