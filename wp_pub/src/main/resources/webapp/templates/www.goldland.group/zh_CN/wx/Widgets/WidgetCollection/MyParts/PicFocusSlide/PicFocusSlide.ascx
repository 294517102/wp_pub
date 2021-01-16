<%@ Control Language="C#" AutoEventWireup="true" Debug="true" CodeFile="PicFocusSlide.cs"
    Inherits="We7.CMS.Web.Widgets.Pic.PicFocusSlide" %>
<script type="text/C#" runat="server">
    [ControlDescription(Desc = "单图渐隐焦点图", Author = "wonders")]
    string MetaData;
</script>

<!--<script type="text/javascript" src="<%=FileUrl %>Js/jquery.pack.js"></script>-->
<!--<script type="text/javascript" src="<%=FileUrl %>Js/jQuery.blockUI.js"></script>-->
<script type="text/javascript" src="../Widgets/WidgetCollection/MyParts/PicFocusSlide/Js/jquery.SuperSlide.js"></script>

<div class="<%= Css %>">
    <div id="wrapper">
        <div id="slideBox" class="slideBox">
            <div class="hd">
                <ul><% for (int i = 0; i < Articles.Count; i++)
                    { %>
                    <li><%=i+1%></li>
                <%} %></ul>
            </div>
            <div class="bd">
                <ul><% for (int i = 0; i < Articles.Count; i++){ %>
                    <li><a href="<%=Articles[i].Url %>" title="<%=ToStr(Articles[i].Title)%>" target="_blank"><img src="<%=Articles[i].GetTagThumbnail(ThumbnailTag) %>" alt="" /><p><%=ToStr(Articles[i].Title, TitleLength)%></p></a></li>
                <%} %></ul>
            </div>
        </div>
        <script type="text/javascript">jQuery(".slideBox").slide( { mainCell:".bd ul",autoPlay:true} );</script>
    </div>
</div>
