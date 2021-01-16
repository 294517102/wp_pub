<%@ Control Language="C#" AutoEventWireup="true" Debug="true" CodeFile="PicList.cs"
    Inherits="We7.CMS.Web.Widgets.PicList" %>
<script type="text/C#" runat="server">
    [ControlDescription(Desc = "PicList图片循环列表", Author = "wonders")]
    string MetaData;
</script>
<div class="<%=Css %>">
    <h3 <%=BackgroundIcon() %>><em><%= Channel!=null ? Channel.Name: "" %></em></h3>
    <ul <%=SetBoxBorderColor() %>>
        <% foreach(Article articles in Articles)
        { %>
        <% if (Articles != null)
        { %>
            <li><a target="_blank" href="<%=articles.Url %>">
                <img width="90" height="120" alt="<%=articles.Title %>" src="<%=articles.GetTagThumbnail(ThumbnailTag) %>" />
                <p><%=ToStr(articles.Title, TitleLength)%></p>
            </a></li>
        <%} %>
        <%} %>
    </ul>   
    <div class="clear"></div>
    <%= Pager.PagedHtml%>
</div>   
        
<%--
tablename:"articles"
tabledesc:"文章表"
--%>