<%@ Control Language="C#" AutoEventWireup="true" Debug="true" CodeFile="ArticleView.Default.cs"
    Inherits="We7.CMS.Web.Widgets.ArticleView_Default" %>
<script type="text/C#" runat="server">
    [ControlDescription(Desc = "文章详情所有", Author = "系统")]
    string MetaData;
</script>
<div class="<%=Css %>" <%=SetBoxBorderColor() %>>
    <!--标题-->
    <h1><%=ThisArticle.Title%></h1>
    <h2><%=ThisArticle.SubTitle%></h2>
    
    <!--附属信息-->
    <div class="article_info">
        <%=ThisArticle.Source%>　<%=ThisArticle.Author%>　　<%=ToDateStr(ThisArticle.Updated, DateFormat)%>　　浏览 <%=ThisArticle.Clicks %> 次
    </div>
    
    <!--正文内容-->
    <div id="fontzoom articleContent" class="article_content">
            <%=ThisArticle.Content %>
            <div class="page_css">
                <span class="pagecss" id="pe100_page_contentpage"></span>
            </div>
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
    
<!--分享-->
    <div class="artilcle_share">
        <!-- JiaThis Button BEGIN -->
        <div class="jiathis_style_24x24">
            <a class="jiathis_button_weixin"></a>
            <a class="jiathis_button_tsina"></a>
            <a class="jiathis_button_tqq"></a>
            <a class="jiathis_button_cqq"></a>
            <a class="jiathis_button_qzone"></a>
            <a class="jiathis_button_renren"></a>
            <a class="jiathis_button_qingbiji"></a>
            <a class="jiathis_button_yixin"></a>
            <a class="jiathis_button_douban"></a>
            <a href="http://www.jiathis.com/share" class="jiathis jiathis_txt jtico jtico_jiathis" target="_blank"></a>
            <a class="jiathis_counter_style"></a>
        </div>
        <script type="text/javascript" src="http://v3.jiathis.com/code/jia.js" charset="utf-8"></script>
        <!-- JiaThis Button END -->
    </div>  
        
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
        <h3>您也可能感兴趣</h3>
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
    function fontZoomA() {
        if (curfontsize > 8) {
            document.getElementById('fontzoom').style.fontSize = (--curfontsize) + 'pt';
            document.getElementById('fontzoom').style.lineHeight = (--curlineheight) + 'pt';
        }
    }
    function fontZoomB() {
        if (curfontsize < 64) {
            document.getElementById('fontzoom').style.fontSize = (++curfontsize) + 'pt';
            document.getElementById('fontzoom').style.lineHeight = (++curlineheight) + 'pt';
        }
    }

    //设置附件显示
    if ($(".ArticleView_Default .articleAttachments ul li").length == 0)
        $(".ArticleView_Default .articleAttachments").hide();
</script>
<%--
tablename:"article"
tabledesc:"文章表"
--%>