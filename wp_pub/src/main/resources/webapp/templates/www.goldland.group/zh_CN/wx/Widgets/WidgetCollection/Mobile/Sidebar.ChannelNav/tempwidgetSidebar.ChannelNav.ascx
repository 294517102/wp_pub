<%@ Control Language="C#" AutoEventWireup="true" CodeFile="Sidebar.ChannelNav.cs" Inherits="We7.CMS.Web.Widgets.Sidebar_ChannelNav" %>
<script type="text/C#" runat="server">
    [ControlDescription(Desc = "Mobile子栏目导航图标", Author = "系统")]
    string MetaData;
    protected string getlocation()
    {
        string[] taget = CurrentChannel.FullPath.Split('/');
        return taget[taget.Length - 2];
    }
</script>
<div class="<%= Css %>">
    <h3 <%=BackgroundIcon() %>>
       <%if (CurrentChannel.ParentID != "{00000000-0000-0000-0000-000000000000}")
          { %> 
        <span><%=getlocation() %></span>
        <%}else {%>
            <span><%= CurrentChannel.Name%></span>
        <%} %>
    </h3>

    <ul class="ui-justify ui-whitespace" <%=SetBoxBorderColor() %>>
    <% if (ChannelChildren != null && ChannelChildren.Count > 0){ %>
        <% foreach (Channel ch in ChannelChildren){ %>
            <li><a href="<%= ch.FullUrl%>"><div class="icon_bg"><img src="<%= ch.TitleImage%>"></div><p><%= ch.Name %></p></a></li>
        <% } %>
    <% } %>
    </ul>

</div>