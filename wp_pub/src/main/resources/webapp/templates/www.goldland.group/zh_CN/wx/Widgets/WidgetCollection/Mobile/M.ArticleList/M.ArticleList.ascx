<%@ Control Language="C#" AutoEventWireup="true" Debug="true" CodeFile="M.ArticleList.cs"
    Inherits="We7.CMS.Web.Widgets.M_ArticleList" %>
<script type="text/C#" runat="server">
    [ControlDescription(Desc = "MobileListA版块信息列表", Author = "wonders")]
    string MetaData;
</script>

<div class="<%= CssClass %> <%=MarginCss %> mui-card">
    <h3 class="demo-desc" <%=BackgroundIcon() %>>
        <span><a href="<%=Channel.FullUrl %>">更多>></a></span><em><%=Channel.Name %><i>  <%=Channel.Description %></i></em>
    </h3>
    <ul class="mui-table-view">
        <%=SetBoxBorderColor() %>
        <% foreach (Article article in Articles)
        { %>
        <li class="mui-table-view-cell"><a class="mui-navigate-right" href="<%=article.Url %>" title="<%=ToStr(article.Title) %>"><%=ToStr(article.Title,TitleLength) %><span><%=ToDateStr(article.Updated, DateFormat)%></span></a></li>
        <%} %>
    </ul>
</div>