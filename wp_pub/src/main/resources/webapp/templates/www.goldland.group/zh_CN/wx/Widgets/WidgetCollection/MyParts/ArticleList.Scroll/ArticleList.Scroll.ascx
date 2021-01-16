<%@ Control Language="C#" AutoEventWireup="true" Debug="true" CodeFile="ArticleList.Scroll.cs"
    Inherits="We7.CMS.Web.Widgets.ArticleList_Scroll" %>
<script type="text/C#" runat="server">
    [ControlDescription(Desc = "ListA版块滚动信息", Author = "wonders")]
    string MetaData;
</script>

<!-- 滚动新闻JS -->
    <script type="text/javascript" src="/_data/js/jquery.min.js"></script>
    <script type="text/javascript">
        function timer(opj){
            $(opj).find('ul').animate({
                marginTop : "-40px"  
                },500,function(){  
                $(this).css({marginTop : "0px"}).find("li:first").appendTo(this);  
            })  
        }
        $(function(){ 
            var num = $('.notice_active').find('li').length;
            if(num > 1){
               var time=setInterval('timer(".notice_active")',3500);
                $('.gg_more a').mousemove(function(){
                    clearInterval(time);
                }).mouseout(function(){
                    time = setInterval('timer(".notice_active")',3500);
                }); 
            }
            
        });
    </script>
 <!-- 滚动新闻JS -->
   
<div class="<%= CssClass %> <%=MarginCss %> ">
    <h3><%=Channel.Name %></h3>
    <div class="notice_active">
        <ul>
            <%=SetBoxBorderColor() %>
            <% foreach (Article article in Articles)
            { %>
            <li><span><%=ToDateStr(article.Updated, DateFormat)%></span><a target="_blank" href="<%=article.Url %>" title="<%=ToStr(article.Title) %>"><%=ToStr(article.Title,TitleLength) %></a></li>
            <%} %>
        </ul> 
    </div>
    <div class="gg_more">
         <a target="_blank" href="<%=Channel.FullUrl %>">更多>></a>
    </div>
</div>