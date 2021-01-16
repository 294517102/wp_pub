<%@ Control Language="C#" AutoEventWireup="true" CodeFile="PicGroupScrool.cs" Inherits="We7.CMS.Web.Widgets.PicGroupScrool" %>
<script type="text/C#" runat="server">
    [ControlDescription(Desc = "PicGroupScrool图片滚动", Author = "wonders")]
    string MetaData;
</script>
<!-- <script src="/Widgets/WidgetCollection/MyParts/PicGroupScrool/js/jquery.min.js"></script>
 -->
<div class="<%=Css %>">
    <h2 <%=BackgroundIcon() %>><span><a href="<%=Channel.FullUrl %>" target="_blank">更多>></a></span><em><%=Channel.Name??"图文推荐" %></em><i><%=Channel.Description %></i></h2>
    <ul class="rollBox">
        <div class="LeftBotton" onmousedown="ISL_GoUp()" onmouseup="ISL_StopUp()" onmouseout="ISL_StopUp()"><a style="cursor:pointer;"></a></div>
        <div class="Cont" id="ISL_Cont">
            <div class="ScrCont">
                <div id="List1">
                    <% foreach (Article article in Articles)
            {%>
                <li><a target="_self" title="<%=article.Title %>" href="<%=article.Url %>">
            <img  alt="<%=article.Title %>" src="<%=article.GetTagThumbnail(ThumbnailTag) %> "><p><%=ToStr(article.Title, TitleLength)%></p></a></li>    
                <%} %>
                   
                </div>
                <div id="List2"></div>
            </div>
        </div>
        <div class="RightBotton" onmousedown="ISL_GoDown()" onmouseup="ISL_StopDown()" onmouseout="ISL_StopDown()"><a style="cursor:pointer;"></a></div>
    </ul>
</div>

<script language="javascript" type="text/javascript">
                <!--    //--><![CDATA[//><!--
    //图片滚动列表 mengjia 070816
    var Speed = 0.01; //速度(毫秒)
    var Space = 5; //每次移动(px)
    var PageWidth = 248; //翻页宽度
    var fill = 0; //整体移位
    var MoveLock = false;
    var MoveTimeObj;
    var Comp = 0;
    var AutoPlayObj = null;
    GetObj("List2").innerHTML = GetObj("List1").innerHTML;
    GetObj('ISL_Cont').scrollLeft = fill;
    GetObj("ISL_Cont").onmouseover = function () { clearInterval(AutoPlayObj); }
    GetObj("ISL_Cont").onmouseout = function () { AutoPlay(); }
    AutoPlay();
    function GetObj(objName) { if (document.getElementById) { return eval('document.getElementById("' + objName + '")') } else { return eval('document.all.' + objName) } }
    function AutoPlay() { //自动滚动
        clearInterval(AutoPlayObj);
        AutoPlayObj = setInterval('ISL_GoDown();ISL_StopDown();', 4000); //间隔时间
    }
    function ISL_GoUp() { //上翻开始
        if (MoveLock) return;
        clearInterval(AutoPlayObj);
        MoveLock = true;
        MoveTimeObj = setInterval('ISL_ScrUp();', Speed);
    }
    function ISL_StopUp() { //上翻停止
        clearInterval(MoveTimeObj);
        if (GetObj('ISL_Cont').scrollLeft % PageWidth - fill != 0) {
            Comp = fill - (GetObj('ISL_Cont').scrollLeft % PageWidth);
            CompScr();
        } else {
            MoveLock = false;
        }
        AutoPlay();
    }
    function ISL_ScrUp() { //上翻动作
        if (GetObj('ISL_Cont').scrollLeft <= 0) { GetObj('ISL_Cont').scrollLeft = GetObj('ISL_Cont').scrollLeft + GetObj('List1').offsetWidth }
        GetObj('ISL_Cont').scrollLeft -= Space;
    }
    function ISL_GoDown() { //下翻
        clearInterval(MoveTimeObj);
        if (MoveLock) return;
        clearInterval(AutoPlayObj);
        MoveLock = true;
        ISL_ScrDown();
        MoveTimeObj = setInterval('ISL_ScrDown()', Speed);
    }
    function ISL_StopDown() { //下翻停止
        clearInterval(MoveTimeObj);
        if (GetObj('ISL_Cont').scrollLeft % PageWidth - fill != 0) {
            Comp = PageWidth - GetObj('ISL_Cont').scrollLeft % PageWidth + fill;
            CompScr();
        } else {
            MoveLock = false;
        }
        AutoPlay();
    }
    function ISL_ScrDown() { //下翻动作
        if (GetObj('ISL_Cont').scrollLeft >= GetObj('List1').scrollWidth) { GetObj('ISL_Cont').scrollLeft = GetObj('ISL_Cont').scrollLeft - GetObj('List1').scrollWidth; }
        GetObj('ISL_Cont').scrollLeft += Space;
    }
    function CompScr() {
        var num;
        if (Comp == 0) { MoveLock = false; return; }
        if (Comp < 0) { //上翻
            if (Comp < -Space) {
                Comp += Space;
                num = Space;
            } else {
                num = -Comp;
                Comp = 0;
            }
            GetObj('ISL_Cont').scrollLeft -= num;
            setTimeout('CompScr()', Speed);
        } else { //下翻
            if (Comp > Space) {
                Comp -= Space;
                num = Space;
            } else {
                num = Comp;
                Comp = 0;
            }
            GetObj('ISL_Cont').scrollLeft += num;
            setTimeout('CompScr()', Speed);
        }
    }
    //--><!]]>
</script>

<%--<script type="text/javascript">
var dhs = new dhscroll();
dhs.scrollDOM = document.getElementById("scroll0");
dhs.scrollCDOM = document.getElementById("scroll20");
dhs.showwidth = 330;
dhs.getsw();
dhs.go("left",true);
</script>--%>