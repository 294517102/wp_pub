<%@ Control Language="C#" AutoEventWireup="true" Debug="true" CodeFile="ArticleView.Default.cs"
    Inherits="We7.CMS.Web.Widgets.ArticleView_Default" %>
<script type="text/C#" runat="server">
    [ControlDescription(Desc = "Mobile文章详情", Author = "wonders")]
    string MetaData;
</script>
<div class="<%=Css %>" <%=SetBoxBorderColor() %>>
    <!--标题-->
    <h3><%=ThisArticle.Title%></h3>
    
    <!--附属信息-->
    <div class="article_info">
        <span><%=ThisArticle.Source%></span>　<%=ThisArticle.Author%>　<%=ToDateStr(ThisArticle.Updated, DateFormat)%>
    </div>
    
    <!--正文内容-->
    <div id="fontzoom articleContent" class="article_content">
            <%=ThisArticle.Content %>
            <div class="page_css">
                <span class="pagecss" id="pe100_page_contentpage"></span>
            </div>
            <div style="color:#333; font-size:12px;">本文已被阅读 <%=ThisArticle.Clicks %> 次</div>

    </div>
    <!--正文内容-->
            <!--附件-->
            <%if (IsShowAtta)
              { %>
            <div class="articleAttachments">
                <h3>附件列表</h3>
                <ul>
                    <%
                  foreach (Attachment att in Attachments)
                  { %>
                    <li>>><a href="<%=att.DownloadUrl %>"><%=att.FileName%></a><span><a href="<%=att.DownloadUrl %>">下载附件</a></span></li>
                    <%} %>
                </ul>
            </div>
            <%} %>
    
        
    <!--上下篇-->
    <div class="article_page">
        <ul>
            <li><span>上一篇：</span>
                <%
                    if (PreviousArticle != null)
                    {
                %>
                <a target="_self" href="<%=PreviousArticle.Url %>">
                    <%=ToStr(PreviousArticle.Title, TitleLength)%></a>
                <%}
                    else
                    { %>
                <span>没有了！</span>
                <%}%>
            </li>
            <li class="next"><span>下一篇：</span>
                <%
                    if (NextArticle != null)
                    {
                %>
                <a target="_self" href="<%=NextArticle.Url %>">
                    <%=ToStr(NextArticle.Title, TitleLength)%></a>
                <%}
                    else
                    { %>
                <span>没有了！</span>
                <%}%>
            </li>
        </ul>
    </div>
        
    <!--相关信息-->
    <div class="article_about">
        <h3>相关内容</h3>
        <ul>
            <%
                if (RelevantArticles != null && RelevantArticles.Count > 0)
                {
                    foreach (Article article in RelevantArticles)
                    {                    
            %>
            <li><a href="<%=article.Url %>" target="_self"><%=ToStr(article.Title, TitleLength)%></a><span><%=ToDateStr(article.Updated,DateFormat) %></span></li>
            <%}
                }
                else
                {
            %>
            <font style="color:#cc0000;">没有相关内容</font>
            <%}
            %>
        </ul>
    </div>
    
</div>
<script type="text/javascript" language="javascript">

    // $('#articleContent img').imageResize();

    //更改字体大小
    var status0 = '';
    var curfontsize = 10;
    var curlineheight = 18;


    //设置附件显示
    if ($(".ArticleViewAll .articleAttachments ul li").length == 0)
        $(".ArticleViewAll .articleAttachments").hide();
</script>
<%--
tablename:"article"
tabledesc:"文章表"
--%>