<%@ Control Language="C#" AutoEventWireup="true" Debug="true" CodeFile="ChannelMenuV.DropDown.cs"  Inherits="We7.CMS.Web.Widgets.Nav.ChannelMenuV_DropDown" %>
<script type="text/C#" runat="server">
    [ControlDescription(Desc = "Menu竖向下拉菜单", Author = "wonders")]
    string MetaData;
</script>
<!--[if IE 6]>
<script type="text/javascript">
    DD_belatedPNG.fix('img,a,ul,li,div');
</script>
<![endif]-->
<div class="<%= Css %>">
    <ul id="dropdown_nav">
         <li class="first"><a target="_self" href="/">网站首页</a></li>
        <%for (int i = 0; i < TopChannels.Count; i++)
          { %>
        <li class="top_<%=i %>" onmouseover="showSub(<%=i %>);" onmouseout="hideSub(<%=i %>)">
            <a href="<%=TopChannels[i].FullUrl %>"><%=TopChannels[i].Name %></a>
            <% if (TopChannels[i].HaveSon)
               { %>
            <ul class="sub">
                <%foreach (Channel subChannel in TopChannels[i].SubChannels)
                  { %>
                <li><a href="<%=subChannel.FullUrl %>"><%=subChannel.Name%></a></li>
                <%} %>
            </ul>
            <%}
              } %>
        </li>
    </ul>
    <script type="text/javascript">
        $(".ChannelMenuV_DropDown .sub").hide();
        function showSub(index) {
            $(".ChannelMenuV_DropDown .top_" + index + " .sub").show().parent().addClass("dropon");
        }
        function hideSub(index) {
            $(".ChannelMenuV_DropDown .top_" + index + " .sub").removeClass("dropon").hide().parent().removeClass("dropon");
        }
        $("#dropdown_nav>li").last().children("ul").addClass("lastul");
    </script>
</div>