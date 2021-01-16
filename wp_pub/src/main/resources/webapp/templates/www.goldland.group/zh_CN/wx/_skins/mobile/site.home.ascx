<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ Register Src="/Widgets/WidgetCollection/Mobile/MbHeader/MbHeader.ascx" TagName="MbHeader" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/Mobile/SliderBanner/SliderBanner.ascx" TagName="SliderBanner" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/Mobile/IconNavGroup/IconNavGroup.ascx" TagName="IconNavGroup" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/Mobile/M.ArticleList/M.ArticleList.ascx" TagName="M_ArticleList" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/Mobile/Mobile_footer1/Mobile_footer.ascx" TagName="Mobile_footer" TagPrefix="wew" %>

<html>
<head runat="server">
    <meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=2.0, initial-scale=1, user-scalable=no">
    <title></title>
	<meta name="apple-mobile-web-app-capable" content="no">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<!--标准mui.css-->
	<link rel="stylesheet" href="/_skins/mobile/css/mui.css">
	<link rel="stylesheet" href="/_skins/mobile/css/app.css">
    <script src="/_skins/mobile/js/mui.js"></script>

    <!-- 引用WapBanner -->
    <script src="/_skins/mobile/lib/zepto.min.js"></script>
    <script src="/_skins/mobile/js/frozen.js"></script>
    <!-- 引用WapBanner -->
	
    <link href="/Admin/VisualTemplate/Style/VisualDesign.LayoutsBasics.css" rel="stylesheet" type="text/css">
<link href="/Widgets/Themes/theme/Style.css" type="text/css" rel="stylesheet" class="themestyle" id="themestyle"><script src="/Widgets/Scripts/jquery.pack.js" type="text/javascript" class="jquerypack" id="jquerypack"></script><script src="/Widgets/Scripts/jquery.peex.js" type="text/javascript" class="jquerypeex" id="jquerypeex"></script><script src="/Widgets/Scripts/Plugins/Common.js" type="text/javascript" class="commonPlugin" id="commonPlugin"></script><link href="/_skins/mobile/Style/UxStyle.css" type="text/css" rel="stylesheet"></head>
<body>
    <div id="pagecontainer">
        <we7design:we7zoneplaceholder id="bodyplaceholder" runat="server"><we7design:we7layout runat="server" id="we7layout_149919306229961">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_149919306229961_cloumn1" style="margin-bottom:50px" cssclass="">
 <wew:MbHeader control="MbHeader" filename="/Widgets/WidgetCollection/Mobile/MbHeader/MbHeader.ascx" id="MbHeader_149919311505563" cssclass="MbHeader" runat="server"></wew:MbHeader></we7design:we7layoutcolumn>
</we7design:we7layout><we7design:we7layout runat="server" id="we7layout_152389278653015">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_152389278653015_cloumn1">
 <wew:SliderBanner id="SliderBanner_152389279546923" icon="" bordercolor="" tag="WapBanner" cssclass="SliderBanner" pagesize="3" filename="/Widgets/WidgetCollection/Mobile/SliderBanner/SliderBanner.ascx" runat="server"></wew:SliderBanner></we7design:we7layoutcolumn>
</we7design:we7layout><we7design:we7layout runat="server" id="we7layout_149077776697474">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_149077776697474_cloumn1">
 <wew:IconNavGroup id="IconNavGroup_149918983385860" cssclass="" filename="/Widgets/WidgetCollection/Mobile/IconNavGroup/IconNavGroup.ascx" runat="server"></wew:IconNavGroup></we7design:we7layoutcolumn>
</we7design:we7layout><we7design:we7layout runat="server" id="we7layout_149075790519361">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_149075790519361_cloumn1">
 <wew:M_ArticleList id="M_ArticleList_14907579130474" ownerid="{b935dca7-eeb1-4232-92b8-b22235211d2d}" pagesize="6" tags="" titlelength="50" dateformat="[MM-dd]" includechildren="True" marginleft10="False" icon="/_skins/mobile/images/header_tit01.jpg" cssclass="ArticleListAuto" isshow="False" bordercolor="" margintop10="True" filename="/Widgets/WidgetCollection/Mobile/M.ArticleList/M.ArticleList.ascx" runat="server"></wew:M_ArticleList><we7design:we7layout runat="server" id="we7layout_149918355495332">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_149918355495332_cloumn1">
 <wew:M_ArticleList id="M_ArticleList_149918357199831" icon="/_skins/mobile/images/header_tit01.jpg" marginleft10="False" includechildren="True" margintop10="True" bordercolor="" isshow="False" pagesize="6" ownerid="{db76ad54-c5f1-4c04-9273-c62c68ddca8a}" cssclass="ArticleListAuto" dateformat="[MM-dd]" titlelength="50" tags="" filename="/Widgets/WidgetCollection/Mobile/M.ArticleList/M.ArticleList.ascx" runat="server"></wew:M_ArticleList><we7design:we7layout runat="server" id="we7layout_149918359527617">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_149918359527617_cloumn1">
 <wew:M_ArticleList id="M_ArticleList_149918359789753" icon="/_skins/mobile/images/header_tit01.jpg" marginleft10="False" includechildren="True" margintop10="True" bordercolor="" isshow="False" pagesize="6" ownerid="{6afa9239-a5a5-421a-a786-340744dcd405}" cssclass="ArticleListAuto" dateformat="[MM-dd]" titlelength="50" tags="" filename="/Widgets/WidgetCollection/Mobile/M.ArticleList/M.ArticleList.ascx" runat="server"></wew:M_ArticleList><we7design:we7layout runat="server" id="we7layout_149918362020796">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_149918362020796_cloumn1">
 <wew:M_ArticleList control="M_ArticleList" filename="/Widgets/WidgetCollection/Mobile/M.ArticleList/M.ArticleList.ascx" id="M_ArticleList_149918365234664" cssclass="ArticleListAuto" runat="server" icon="/_skins/mobile/images/header_tit01.jpg" marginleft10="False" margintop10="True" bordercolor="" isshow="False" tags="" pagesize="6" ownerid="{158b9843-5bcb-4c79-a87f-f16b39740c80}" includechildren="True" dateformat="[MM-dd]" titlelength="50"></wew:M_ArticleList><wew:M_ArticleList id="M_ArticleList_149918363541929" icon="/_skins/mobile/images/header_tit01.jpg" marginleft10="False" margintop10="True" bordercolor="" isshow="False" tags="" pagesize="6" ownerid="{fa7f018b-927a-4e60-9636-cebc79570ebd}" cssclass="ArticleListAuto" includechildren="True" dateformat="[MM-dd]" titlelength="50" filename="/Widgets/WidgetCollection/Mobile/M.ArticleList/M.ArticleList.ascx" runat="server"></wew:M_ArticleList></we7design:we7layoutcolumn>
</we7design:we7layout><we7design:we7layout runat="server" id="we7layout_149918361864333">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_149918361864333_cloumn1">
 </we7design:we7layoutcolumn>
</we7design:we7layout></we7design:we7layoutcolumn>
</we7design:we7layout></we7design:we7layoutcolumn>
</we7design:we7layout></we7design:we7layoutcolumn>
</we7design:we7layout><we7design:we7layout runat="server" id="we7layout_149075757704280">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_149075757704280_cloumn1">
 <wew:Mobile_footer id="Mobile_footer_149077730436358" cssclass="Mobile_footer" filename="/Widgets/WidgetCollection/Mobile/Mobile_footer1/Mobile_footer.ascx" runat="server"></wew:Mobile_footer></we7design:we7layoutcolumn>
</we7design:we7layout></we7design:we7zoneplaceholder>
    </div>
</body>
</html>