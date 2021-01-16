<%@ Control Language="C#" AutoEventWireup="true" Debug="true" CodeFile="PagedArticleList.Default.cs"
    Inherits="We7.CMS.Web.Widgets.PagedArticleList_Default" %>
<div class="<%=Css %>">
    <h3 <%=BackgroundIcon() %>><em><%= Channel!=null ? Channel.Name: "" %></em></h3>
    <div class="Description"><%= Channel.Description%></div>

    <ul class="mui-table-view mui-table-view-striped mui-table-view-condensed" style="min-height:200px;">
    <% foreach (Article article in Articles)
       { %>
        <li class="mui-table-view-cell">
                <a href="<%=article.Url %>">
                <div class="mui-table">
                    <div class="mui-table-cell mui-col-xs-10">
                        <h4 class="mui-ellipsis-2">
                            <%
                               string title = ToStr(article.Title, TitleLength);
                               if (!string.IsNullOrEmpty(KeyWord))
                                   title = title.Replace(KeyWord, "<em>" + KeyWord + "</em>");
                            %><%=title%>
                        </h4>
                        <h5><%=ToStr(article.SubTitle) %></h5>
                        <p class="mui-h6 mui-ellipsis"><%=ToStr(article.Description,132) %></p>
                    </div>
                    <div class="mui-table-cell mui-col-xs-2 mui-text-right">
                        <span class="mui-h5"><%=ToDateStr(article.Updated,DateFormat) %></span>
                    </div>
                </div>
                </a>
        </li>
        <%} %>
    </ul>
    <div class="clear"></div>
    <%= Pager.PagedHtml%>
</div>
<%--
tablename:"article"
tabledesc:"文章表"
--%>