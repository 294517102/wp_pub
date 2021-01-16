<%@ Control Language="C#" AutoEventWireup="true" Debug="true" CodeFile="PicNewsList.cs"
    Inherits="We7.CMS.Web.Widgets.PicNewsList" %>
<script type="text/C#" runat="server">
    [ControlDescription(Desc = "PicNewsList图片新闻列表", Author = "wonders")]
    string MetaData;
</script>
<div class="<%=Css %>">
    <h1 <%=BackgroundIcon() %>><em><%= Channel!=null ? Channel.Name: "" %></em></h1>
    <div class="PicNewsList_Box">
        <% foreach(Article articles in Articles)
        { %>
        <% if (Articles != null)
        { %>
        <dl <%=SetBoxBorderColor() %>>
            <dt><a target="_blank" href="<%=articles.Url %>" title="点击查看"><img alt="<%=articles.Title %>" src="<%=articles.GetTagThumbnail(ThumbnailTag) %>" /></a></dt>
            <dd>
                <h2><a target="_blank" href="<%=articles.Url %>"><%=ToStr(articles.Title, TitleLength)%></a><span><%=ToDateStr(articles.Updated, DateFormat)%></span></h2>
                <p><%=ToStr(articles.Description, DescriptionLength)%></p>
            </dd>
        </dl>
        <%} %>
        <%} %>
    </div>
            
<div class="clear"></div>
<%= Pager.PagedHtml%>
</div>
            
        
<%--
tablename:"articles"
tabledesc:"文章表"
--%>