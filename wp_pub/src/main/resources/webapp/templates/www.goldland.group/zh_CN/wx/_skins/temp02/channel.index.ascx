<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ Register Src="/Widgets/WidgetCollection/MyParts/TopInfo/TopInfo.ascx" TagName="TopInfo" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/SiteHeader/SiteHeader.ascx" TagName="SiteHeader" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/ChannelMenuV.DropDown/ChannelMenuV.DropDown.ascx" TagName="ChannelMenuV_DropDown" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/scroll_banner/scroll_banner.ascx" TagName="scroll_banner" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/ArticleList.Scroll/ArticleList.Scroll.ascx" TagName="ArticleList_Scroll" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/Search2015/Search2015.ascx" TagName="Search2015" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/ChannelMenu.CurrLocation/ChannelMenu.CurrentLocation.ascx" TagName="ChannelMenu_CurrentLocation" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/Sidebar.ChannelNav/Sidebar.ChannelNav.ascx" TagName="Sidebar_ChannelNav" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/ArticleList.YMD/ArticleList.YMD.ascx" TagName="ArticleList_YMD" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/PagedArticleList.Default/PagedArticleList.Default.ascx" TagName="PagedArticleList_Default" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/Footer.Default/Footer.Default.ascx" TagName="Footer_Default" TagPrefix="wew" %>

<html>
<head runat="server">
    <title></title>
    <link href="/Admin/VisualTemplate/Style/VisualDesign.LayoutsBasics.css" rel="stylesheet" type="text/css">
<link href="/Widgets/Themes/theme/Style.css" type="text/css" rel="stylesheet" class="themestyle" id="themestyle"><script src="/Widgets/Scripts/jquery.pack.js" type="text/javascript" class="jquerypack" id="jquerypack"></script><script src="/Widgets/Scripts/jquery.peex.js" type="text/javascript" class="jquerypeex" id="jquerypeex"></script><script src="/Widgets/Scripts/Plugins/Common.js" type="text/javascript" class="commonPlugin" id="commonPlugin"></script><link href="/_skins/temp02/Style/UxStyle.css" type="text/css" rel="stylesheet"></head>
<body style="background-color:#f5f1da;background-position:center top;background-repeat:no-repeat; background-attachment:scroll;">
    <div id="pagecontainer" style="width:100%px;margin:0 auto">
        <we7design:we7zoneplaceholder id="bodyplaceholder" runat="server"><we7design:we7layout runat="server" id="we7layout_147814426541884">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147814426541884_cloumn1">
 <wew:TopInfo control="TopInfo" filename="/Widgets/WidgetCollection/MyParts/TopInfo/TopInfo.ascx" id="TopInfo_147815829649467" cssclass="TopInfo" runat="server"></wew:TopInfo></we7design:we7layoutcolumn>
</we7design:we7layout><we7design:we7layout runat="server" id="we7layout_149784749084810">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_149784749084810_cloumn1" style="" cssclass="topbg">
 <we7design:we7layout runat="server" id="we7layout_149784749677785">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_149784749677785_cloumn1" style="" cssclass="layout">
 <wew:SiteHeader control="SiteHeader" filename="/Widgets/WidgetCollection/MyParts/SiteHeader/SiteHeader.ascx" id="SiteHeader_149784752862096" cssclass="SiteHeader" runat="server"></wew:SiteHeader></we7design:we7layoutcolumn>
</we7design:we7layout><we7design:we7layout runat="server" id="we7layout_149615624552010">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_149615624552010_cloumn1" style="" cssclass="">
 <wew:channelmenuv_dropdown control="ChannelMenuV_DropDown" filename="/Widgets/WidgetCollection/MyParts/ChannelMenuV.DropDown/ChannelMenuV.DropDown.ascx" id="ChannelMenuV_DropDown_147814427067210" cssclass="ChannelMenuV_DropDown" runat="server"></wew:channelmenuv_dropdown><wew:scroll_banner control="scroll_banner" filename="/Widgets/WidgetCollection/MyParts/scroll_banner/scroll_banner.ascx" id="scroll_banner_149786860447984" cssclass="scroll_banner" runat="server"></wew:scroll_banner><we7design:we7layout runat="server" id="we7layout_150470101249948">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_150470101249948_cloumn1" style="background:#f9f9f9" cssclass="">
 <we7design:we7layout runat="server" id="we7layout_150470103932064">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_150470103932064_cloumn1" style="" cssclass="layout">
 <we7design:we7layout runat="server" id="we7layout_150470105198668">
 <we7design:we7layoutcolumn float="left" width="700" widthunit="px" runat="server" id="we7layout_150470105198668_cloumn1" style="" cssclass="">
 <wew:ArticleList_Scroll id="ArticleList_Scroll_150470106871178" icon="" marginleft10="False" margintop10="False" bordercolor="" isshow="False" tags="" pagesize="8" ownerid="{db76ad54-c5f1-4c04-9273-c62c68ddca8a}" cssclass="huadong" includechildren="True" dateformat="MM/dd" titlelength="60" filename="/Widgets/WidgetCollection/MyParts/ArticleList.Scroll/ArticleList.Scroll.ascx" runat="server"></wew:ArticleList_Scroll></we7design:we7layoutcolumn>
  <we7design:we7layoutcolumn float="left" width="400" widthunit="px" runat="server" id="we7layout_150470105198668_cloumn2" style="" cssclass="">
 <wew:Search2015 id="Search2015_149786868787692" iconwap="" iconhomepage="" iconcollection="" iconrss="" cssclass="Search2015" searchpage="" iconlogin="" filename="/Widgets/WidgetCollection/MyParts/Search2015/Search2015.ascx" runat="server"></wew:Search2015></we7design:we7layoutcolumn>
</we7design:we7layout></we7design:we7layoutcolumn>
</we7design:we7layout></we7design:we7layoutcolumn>
</we7design:we7layout></we7design:we7layoutcolumn>
</we7design:we7layout></we7design:we7layoutcolumn>
</we7design:we7layout><we7design:we7layout runat="server" id="we7layout_147814426381590">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147814426381590_cloumn1">
 <we7design:we7layout runat="server" id="we7layout_147814488611465">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147814488611465_cloumn1" style="" cssclass="layout_content">
 <we7design:we7layout runat="server" id="we7layout_147814491829580">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147814491829580_cloumn1">
 <wew:ChannelMenu_CurrentLocation control="ChannelMenu_CurrentLocation" filename="/Widgets/WidgetCollection/MyParts/ChannelMenu.CurrLocation/ChannelMenu.CurrentLocation.ascx" id="ChannelMenu_CurrentLocation_147814495505786" cssclass="ChannelMenu_CurrentLocation" runat="server"></wew:ChannelMenu_CurrentLocation><we7design:we7layout runat="server" id="we7layout_147913355435384">
 <we7design:we7layoutcolumn float="left" width="250" widthunit="px" runat="server" id="we7layout_147913355435384_cloumn1" style="" cssclass="">
 <we7design:we7layout runat="server" id="we7layout_149784712691161">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_149784712691161_cloumn1">
 <wew:Sidebar_ChannelNav id="Sidebar_ChannelNav_14978471381277" icon="/_skins/temp02/images/header_tit06.jpg" bordercolor="" margintop10="True" cssclass="Sidebar_ChannelNav" ownerid="" filename="/Widgets/WidgetCollection/MyParts/Sidebar.ChannelNav/Sidebar.ChannelNav.ascx" runat="server"></wew:Sidebar_ChannelNav></we7design:we7layoutcolumn>
</we7design:we7layout><we7design:we7layout runat="server" id="we7layout_147895534870141">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147895534870141_cloumn1">
 <wew:ArticleList_YMD id="ArticleList_YMD_147895538174256" icon="/_skins/temp02/images/header_tit05.jpg" marginleft10="False" margintop10="True" bordercolor="" isshow="False" tags="" pagesize="5" ownerid="{b4c35ed2-e90f-4d66-b7a2-d695b9e576b3}" cssclass="ArticleRight" includechildren="True" dateformat="MM/dd" titlelength="34" filename="/Widgets/WidgetCollection/MyParts/ArticleList.YMD/ArticleList.YMD.ascx" runat="server"></wew:ArticleList_YMD></we7design:we7layoutcolumn>
</we7design:we7layout><we7design:we7layout runat="server" id="we7layout_147895534322233">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147895534322233_cloumn1">
 <wew:ArticleList_YMD id="ArticleList_YMD_14789553535172" icon="/_skins/temp02/images/header_tit05.jpg" marginleft10="False" margintop10="True" bordercolor="" isshow="False" tags="" pagesize="5" ownerid="{21878620-7c85-4da0-be2e-984276537bb2}" cssclass="ArticleRight" includechildren="True" dateformat="MM/dd" titlelength="34" filename="/Widgets/WidgetCollection/MyParts/ArticleList.YMD/ArticleList.YMD.ascx" runat="server"></wew:ArticleList_YMD></we7design:we7layoutcolumn>
</we7design:we7layout></we7design:we7layoutcolumn>
 <we7design:we7layoutcolumn float="left" width="30" widthunit="px" runat="server" id="we7layout_147913355435384_cloumn2" style="" cssclass="">
 </we7design:we7layoutcolumn>
  <we7design:we7layoutcolumn float="left" width="780" widthunit="px" runat="server" id="we7layout_147913355435384_cloumn3" style="" cssclass="">
 <we7design:we7layout runat="server" id="we7layout_147913493714760">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147913493714760_cloumn1">
 <wew:PagedArticleList_Default id="PagedArticleList_Default_147814487908142" margintop10="True" includechildren="True" dateformat="yyyy-MM-dd" tags="" bordercolor="" icon="/_skins/temp02/images/header_tit02.png" isshow="False" titlelength="80" pager-pagesize="15" pager-requestpageindex="pi" cssclass="PagedArticleList_Default" pager-pagerdivclass="page_css page_line" ownerid="" pager-vmtemplatefilename="/Widgets/WidgetCollection/文章列表类/PagedArticleList.Default/vm/pager.vm" pager-pagerspanclass="pagecss" filename="/Widgets/WidgetCollection/MyParts/PagedArticleList.Default/PagedArticleList.Default.ascx" runat="server"></wew:PagedArticleList_Default></we7design:we7layoutcolumn>
</we7design:we7layout></we7design:we7layoutcolumn>
</we7design:we7layout></we7design:we7layoutcolumn>
</we7design:we7layout></we7design:we7layoutcolumn>
</we7design:we7layout><we7design:we7layout runat="server" id="we7layout_147814491248248">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147814491248248_cloumn1">
 <wew:Footer_Default id="Footer_Default_14781606225829" cssclass="Footer_Default" filename="/Widgets/WidgetCollection/MyParts/Footer.Default/Footer.Default.ascx" runat="server"></wew:Footer_Default></we7design:we7layoutcolumn>
</we7design:we7layout></we7design:we7layoutcolumn>
</we7design:we7layout><we7design:we7layout runat="server" id="we7layout_149615623827028">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_149615623827028_cloumn1">
 </we7design:we7layoutcolumn>
</we7design:we7layout></we7design:we7zoneplaceholder>
    </div>
</body>
</html>