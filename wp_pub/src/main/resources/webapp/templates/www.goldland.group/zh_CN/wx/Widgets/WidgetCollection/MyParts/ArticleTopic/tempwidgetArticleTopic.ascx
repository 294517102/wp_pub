<%@ Control Language="C#" AutoEventWireup="true" Debug="true" CodeFile="ArticleTopic.cs"
    Inherits="We7.CMS.Web.Widgets.ArticleTopic" %>
<%@ Register Src="~/Widgets/WidgetCollection/文章列表类/NewsTab.Default/NewsTab.Default.ascx"  TagName="ChannelMenu" TagPrefix="wec" %>
<script type="text/C#" runat="server">
    [ControlDescription(Desc = "Top头条", Author = "wonders")]
    string MetaData;
</script>

<div class="<%= CssClass %> <%=MarginCss %> " <%=SetBoxBorderColor() %>>
        <h3 <%=BackgroundIcon() %>>
            <span><a href="<%=Channel.FullUrl %>">更多>></a></span><em><%=Channel.Name %></em><i><%=Channel.Description %></i>
        </h3>
    
        <% foreach (Article article in Articles)
           { %>
        <h4><a href="<%=article.ContentType==8?article.ContentUrl:article.Url %>" title="<%=ToStr(article.Title) %>" target="_blank"><%=ToStr(article.Title,TitleLength) %></a></h4>
        <p><%=ToStr(article.Description,128) %></p>
        <%} %>

</div>
<%--
tablename:"article"
tabledesc:"文章表"
--%>