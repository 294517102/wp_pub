<%@ Control Language="C#" AutoEventWireup="true" Debug="true" CodeFile="ChannelMenu.CurrLocation.cs" Inherits="We7.CMS.Web.Widgets.ChannelMenu_CurrLocation" %>
<script type="text/C#" runat="server">
    [ControlDescription(Desc = "ShowPath当前位置", Author = "系统")]
    [RemoveParameter("Tag")]
    string MetaData;
    protected string GetLocation()
    {
        if (CurrentChannel != null && !string.IsNullOrEmpty(CurrentChannel.FullPath))
        {
            return CurrentChannel.FullPath.Substring(1).Replace("/", "<span>&gt;</span>");
        }
        return "";    
    }
</script>
<div class="<%=CssClass %>" <%=BackgroundIcon() %>>
    您现在的位置：首页<span>></span><em><%=GetLocation()%></em>
</div>