<%@ Control Language="C#" AutoEventWireup="true" Debug="true" CodeFile="W_LeaderPic.cs"
    Inherits="We7.CMS.Web.Widgets.W_LeaderPic" %>
<script type="text/C#" runat="server">
    [ControlDescription(Desc = "W-领导照片列表", Author = "wds")]
    string MetaData;
</script>
<div class="<%=Css %>">
    <h1 <%=BackgroundIcon() %>><em><%= Channel!=null ? Channel.Name: "" %></em></h1>
    <div class="W_LeaderPic_Box">
        <% foreach(Article articles in Articles)
        { %>
        <% if (Articles != null)
        { %>
        <dl <%=SetBoxBorderColor() %>>
            <dt><a target="_blank" href="<%=articles.Url %>"><img width="90" height="120" alt="<%=articles.Title %>" src="<%=articles.GetTagThumbnail(ThumbnailTag) %>" /></a></dt>
            <dd>
                <h2><a target="_blank" href="<%=articles.Url %>"><%=ToStr(articles.Title, TitleLength)%></a><span><%=ToStr(articles.SubTitle)%></span></h2>
                <p><%=ToStr(articles.Description, DescriptionLength)%></p>
                <a target="_blank" href="<%=articles.Url %>" class="more">详情介绍>></a>
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