<%@ Control Language="C#" AutoEventWireup="true" Debug="true" CodeFile="W_LeaderPic.cs"
    Inherits="We7.CMS.Web.Widgets.W_LeaderPic" %>
<script type="text/C#" runat="server">
    [ControlDescription(Desc = "M栏目图片列表", Author = "wds")]
    string MetaData;
</script>
<div class="<%=Css %>">
    <h1 <%=BackgroundIcon() %>><em><%= Channel!=null ? Channel.Name: "" %></em></h1>
    <ul class="ui-list ui-list-link ui-border-tb">
        <% foreach(Article articles in Articles)
        { %>
        <% if (Articles != null)
        { %>
        <li <%=SetBoxBorderColor() %>>
            <div class="ui-avatar"><a href="<%=articles.Url %>"><span><img src="<%=articles.GetTagThumbnail(ThumbnailTag) %>"></span></a></div>
            <div class="ui-list-info ui-border-t">
                <h4 class="ui-nowrap"><a href="<%=articles.Url %>"><%=ToStr(articles.Title, TitleLength)%></a></h2>
                <p class="ui-nowrap"><%=ToStr(articles.Description, DescriptionLength)%></p>
            </div>
        </li>
        <%} %>
        <%} %>
    </ul>
            
    <div class="clear"></div>
    <div style="overflow:hidden;"><%= Pager.PagedHtml%></div>
</div>