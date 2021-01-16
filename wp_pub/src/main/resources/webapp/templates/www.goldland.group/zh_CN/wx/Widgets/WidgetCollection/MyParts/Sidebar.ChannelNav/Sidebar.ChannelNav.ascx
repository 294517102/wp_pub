<%@ Control Language="C#" AutoEventWireup="true" CodeFile="Sidebar.ChannelNav.cs" Inherits="We7.CMS.Web.Widgets.Sidebar_ChannelNav" %>
<script type="text/C#" runat="server">
    [ControlDescription(Desc = "Menu侧栏二级导航", Author = "系统")]
    string MetaData;
    protected string getlocation()
    {
        string[] taget = CurrentChannel.FullPath.Split('/');
        return taget[taget.Length - 2];
    }
</script>
<div class="<%= Css %>">
    <h2 <%=BackgroundIcon() %>>
       <%if (CurrentChannel.ParentID != "{00000000-0000-0000-0000-000000000000}")
          { %> 
            <em><%=getlocation() %></em>
        <%}else {%>
            <em><%= CurrentChannel.Name%></em>
        <%} %></h2>
    <ul <%=SetBoxBorderColor() %>>
       <% if (ChannelChildren != null && ChannelChildren.Count > 0){ %>
            <% foreach (Channel ch in ChannelChildren){ %>
                <li><a target="_self" href="<%= ch.FullUrl%>"><%= ch.Name %></a></li>           
            <% } %>
        <% } %>
    </ul>
</div>