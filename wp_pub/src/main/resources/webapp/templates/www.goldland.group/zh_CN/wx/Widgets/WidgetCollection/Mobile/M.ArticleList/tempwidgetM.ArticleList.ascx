<%@ Control Language="C#" AutoEventWireup="true" Debug="true" CodeFile="M.ArticleList.cs"
    Inherits="We7.CMS.Web.Widgets.M_ArticleList" %>
<script type="text/C#" runat="server">
    [ControlDescription(Desc = "MobileListA版块信息列表", Author = "wonders")]
    string MetaData;
</script>
<div class="<%= CssClass %> <%=MarginCss %> ">
    <h3 class="demo-desc" <%=BackgroundIcon() %>>
        <span><a target="_self" href="<%=Channel.FullUrl %>">更多>></a></span><em><%=Channel.Name %><i>  <%=Channel.Description %></i></em>
    </h3>
    <ul class="ui-list ui-list-text ui-list-active ui-list-cover ui-border-tb">
        <%=SetBoxBorderColor() %>
        <% foreach (Article article in Articles)
        { %>
        <li class="ui-border-t"><p><a href="<%=article.Url %>" title="<%=ToStr(article.Title) %>"><%=ToStr(article.Title,TitleLength) %><span><%=ToDateStr(article.Updated, DateFormat)%></span></a></p></li>
        <%} %>
    </ul>
</div>