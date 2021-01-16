<%@ Control Language="C#" AutoEventWireup="true" Debug="true" CodeFile="PagedArticleList.Default.cs"
    Inherits="We7.CMS.Web.Widgets.PagedArticleList_Default" %>
<script type="text/C#" runat="server">
    [ControlDescription(Desc = "ArticleList分页信息列表纵行", Author = "wonders")]
    string MetaData;
</script>

<div class="<%=Css %>">
    <div class="TitleImage"><img src="<%=Channel.TitleImage %>"></div>
    <h3 <%=BackgroundIcon() %>><em><%= Channel!=null ? Channel.Name: "" %></em></h3>
    <div class="Description"><%= Channel.Description%></div>
    <div class="article_list ">
        <ul <%=SetBoxBorderColor() %>>
            <% foreach (Article article in Articles)
               { %>
            <li><a target="_self" href="<%=article.Url %>">
                <%
                   string title = ToStr(article.Title, TitleLength);
                   if (!string.IsNullOrEmpty(KeyWord))
                       title = title.Replace(KeyWord, "<em>" + KeyWord + "</em>");
                %>
                <%=title%></a><span><%=ToDateStr(article.Updated,DateFormat) %></span>
            </li>
            <%} %>
        </ul>
        <div class="clear"></div>
        <%= Pager.PagedHtml%>
    </div>
</div>
<%--
tablename:"article"
tabledesc:"文章表"
--%>