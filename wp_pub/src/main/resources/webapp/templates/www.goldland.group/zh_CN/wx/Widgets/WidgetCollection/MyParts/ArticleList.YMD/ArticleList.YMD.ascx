<%@ Control Language="C#" AutoEventWireup="true" Debug="true" CodeFile="ArticleList.YMD.cs"
    Inherits="We7.CMS.Web.Widgets.ArticleList_YMD" %>
<script type="text/C#" runat="server">
    [ControlDescription(Desc = "ListA版块信息列表YMD", Author = "wonders")]
    string MetaData;
</script>
<div class="<%= CssClass %> <%=MarginCss %> ">
        <h3 <%=BackgroundIcon() %>>
            <span><a title="更多" target="_blank" href="<%=Channel.FullUrl %>">更多>></a></span><em><%=Channel.Name %><i>  <%=Channel.Description %></i></em>
        </h3>
        <ul>
            <%=SetBoxBorderColor() %>
            <% foreach (Article article in Articles)
            { %>
            <li><span><%=ToDateStr(article.Updated, DateFormat)%></span><a target="_blank" href="<%=article.Url %>" title="<%=ToStr(article.Title) %>"><%=ToStr(article.Title,TitleLength) %></a></li>
            <%} %>
        </ul>
</div>