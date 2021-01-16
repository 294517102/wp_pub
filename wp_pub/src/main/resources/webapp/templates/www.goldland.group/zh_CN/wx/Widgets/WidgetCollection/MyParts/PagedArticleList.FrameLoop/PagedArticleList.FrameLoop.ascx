<%@ Control Language="C#" AutoEventWireup="true" Debug="true" CodeFile="PagedArticleList.FrameLoop.cs"
    Inherits="We7.CMS.Web.Widgets.PagedArticleList_FrameLoop" %>
<script type="text/C#" runat="server">
    [ControlDescription(Desc = "ArticleList分页信息列表块状", Author = "wonders")]
    string MetaData;
</script>

<div class="<%=Css %>">
    <h3 <%=BackgroundIcon() %>><em><%= Channel!=null ? Channel.Name: "" %></em></h3>
    <div class="Description"><%= Channel.Description%></div>
    <div class="article_list ">
        <ul <%=SetBoxBorderColor() %>>
            <% foreach (Article article in Articles)
               { %>
            <li><h4><a href="<%=article.Url %>">
                <%
                   string title = ToStr(article.Title, TitleLength);
                   if (!string.IsNullOrEmpty(KeyWord))
                       title = title.Replace(KeyWord, "<em>" + KeyWord + "</em>");
                %>
                <%=title%></a> / <i><%=ToStr(article.SubTitle) %></i><span><a href="<%=article.Url %>">查看详情>></a></span></h4>
                <p><%=ToStr(article.Description,1000) %></p>
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