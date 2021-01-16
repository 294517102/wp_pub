﻿<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ Register Src="/Widgets/WidgetCollection/MyParts/TopInfo/TopInfo.ascx" TagName="TopInfo" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/SiteHeader/SiteHeader.ascx" TagName="SiteHeader" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/ChannelMenuV.DropDown/ChannelMenuV.DropDown.ascx" TagName="ChannelMenuV_DropDown" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/ChannelMenu.CurrLocation/ChannelMenu.CurrentLocation.ascx" TagName="ChannelMenu_CurrentLocation" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/Sidebar.ChannelNav/Sidebar.ChannelNav.ascx" TagName="Sidebar_ChannelNav" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/ArticleList.YMD/ArticleList.YMD.ascx" TagName="ArticleList_YMD" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/PicList/PicList.ascx" TagName="PicList" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/Footer.Default/Footer.Default.ascx" TagName="Footer_Default" TagPrefix="wew" %>

<html>
<head runat="server">
    <title></title>
    <link href="/Admin/VisualTemplate/Style/VisualDesign.LayoutsBasics.css" rel="stylesheet" type="text/css">
<link href="/Widgets/Themes/theme/Style.css" type="text/css" rel="stylesheet" class="themestyle" id="themestyle"><script src="/Widgets/Scripts/jquery.pack.js" type="text/javascript" class="jquerypack" id="jquerypack"></script><script src="/Widgets/Scripts/jquery.peex.js" type="text/javascript" class="jquerypeex" id="jquerypeex"></script><script src="/Widgets/Scripts/Plugins/Common.js" type="text/javascript" class="commonPlugin" id="commonPlugin"></script><link href="/_skins/temp02/Style/UxStyle.css" type="text/css" rel="stylesheet"></head>
<body style="background-color:rgb(245, 242, 217);background-position:center top;background-repeat:no-repeat; background-attachment:scroll;">
    <div id="pagecontainer" style="width:100%px;margin:0 auto">
        <we7design:we7zoneplaceholder id="bodyplaceholder" runat="server"><we7design:we7layout runat="server" id="we7layout_149795256537894">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_149795256537894_cloumn1">
 <wew:TopInfo control="TopInfo" filename="/Widgets/WidgetCollection/MyParts/TopInfo/TopInfo.ascx" id="TopInfo_149795261488830" cssclass="TopInfo" runat="server"></wew:TopInfo></we7design:we7layoutcolumn>
</we7design:we7layout><we7design:we7layout runat="server" id="we7layout_149795256069089">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_149795256069089_cloumn1">
 <we7design:we7layout runat="server" id="we7layout_149795256769895">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_149795256769895_cloumn1" style="" cssclass="layout">
 <wew:siteheader control="SiteHeader" filename="/Widgets/WidgetCollection/MyParts/SiteHeader/SiteHeader.ascx" id="SiteHeader_147886619479267" cssclass="SiteHeader" runat="server"></wew:siteheader></we7design:we7layoutcolumn>
</we7design:we7layout></we7design:we7layoutcolumn>
</we7design:we7layout><we7design:we7layout runat="server" id="we7layout_147841508879151">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147841508879151_cloumn1">
 <wew:ChannelMenuV_DropDown control="ChannelMenuV_DropDown" filename="/Widgets/WidgetCollection/MyParts/ChannelMenuV.DropDown/ChannelMenuV.DropDown.ascx" id="ChannelMenuV_DropDown_147841522084582" cssclass="ChannelMenuV_DropDown" runat="server"></wew:ChannelMenuV_DropDown></we7design:we7layoutcolumn>
</we7design:we7layout><we7design:we7layout runat="server" id="we7layout_147841514889983">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147841514889983_cloumn1" style="" cssclass="layout_content">
 <we7design:we7layout runat="server" id="we7layout_147841528247064">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147841528247064_cloumn1">
 <wew:ChannelMenu_CurrentLocation control="ChannelMenu_CurrentLocation" filename="/Widgets/WidgetCollection/MyParts/ChannelMenu.CurrLocation/ChannelMenu.CurrentLocation.ascx" id="ChannelMenu_CurrentLocation_147841534194954" cssclass="ChannelMenu_CurrentLocation" runat="server"></wew:ChannelMenu_CurrentLocation></we7design:we7layoutcolumn>
</we7design:we7layout><we7design:we7layout runat="server" id="we7layout_147841529121454">
 <we7design:we7layoutcolumn float="left" width="250" widthunit="px" runat="server" id="we7layout_147841529121454_cloumn1" style="" cssclass="">
 <we7design:we7layout runat="server" id="we7layout_147895516343416">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147895516343416_cloumn1">
 <wew:Sidebar_ChannelNav id="Sidebar_ChannelNav_147895520274333" icon="/_skins/temp02/images/header_tit06.jpg" bordercolor="" margintop10="True" cssclass="Sidebar_ChannelNav" ownerid="" filename="/Widgets/WidgetCollection/MyParts/Sidebar.ChannelNav/Sidebar.ChannelNav.ascx" runat="server"></wew:Sidebar_ChannelNav><we7design:we7layout runat="server" id="we7layout_147886679827897">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147886679827897_cloumn1">
 <wew:ArticleList_YMD id="ArticleList_YMD_147886680258028" icon="/_skins/temp02/images/header_tit05.jpg" marginleft10="False" margintop10="True" bordercolor="" isshow="False" tags="" pagesize="5" ownerid="{89fbf068-cdfe-446c-9d1f-cf8269315d9a}" cssclass="ArticleRight" includechildren="True" dateformat="MM/dd" titlelength="34" filename="/Widgets/WidgetCollection/MyParts/ArticleList.YMD/ArticleList.YMD.ascx" runat="server"></wew:ArticleList_YMD><we7design:we7layout runat="server" id="we7layout_147886679531275">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147886679531275_cloumn1">
 <wew:ArticleList_YMD id="ArticleList_YMD_147886683573823" icon="/_skins/temp02/images/header_tit05.jpg" marginleft10="False" margintop10="True" bordercolor="" isshow="False" tags="" pagesize="5" ownerid="{57626e8b-ab72-416c-a678-94e16e33bd68}" cssclass="ArticleRight" includechildren="True" dateformat="MM/dd" titlelength="34" filename="/Widgets/WidgetCollection/MyParts/ArticleList.YMD/ArticleList.YMD.ascx" runat="server"></wew:ArticleList_YMD></we7design:we7layoutcolumn>
</we7design:we7layout></we7design:we7layoutcolumn>
</we7design:we7layout></we7design:we7layoutcolumn>
</we7design:we7layout></we7design:we7layoutcolumn>
 <we7design:we7layoutcolumn float="left" width="30" widthunit="px" runat="server" id="we7layout_147841529121454_cloumn2" style="" cssclass="">
 </we7design:we7layoutcolumn>
  <we7design:we7layoutcolumn float="left" width="780" widthunit="px" runat="server" id="we7layout_147841529121454_cloumn3" style="" cssclass="">
 <wew:PicList id="PicList_147842168780116" margintop10="True" thumbnailtag="Company" dateformat="MM/dd" includechildren="True" descriptionlength="60" icon="/_skins/temp02/images/header_tit02.png" bordercolor="" tags="" isshow="False" pager-requestpageindex="pi" pager-pagerdivclass="page_css page_line" cssclass="PicListA" pager-pagesize="18" pager-pagerspanclass="pagecss" ownerid="" titlelength="30" pager-vmtemplatefilename="/Widgets/WidgetCollection/文章列表类/PagedArticleList.Default/vm/pager.vm" pagesize="10" filename="/Widgets/WidgetCollection/MyParts/PicList/PicList.ascx" runat="server"></wew:PicList></we7design:we7layoutcolumn>
</we7design:we7layout></we7design:we7layoutcolumn>
</we7design:we7layout><we7design:we7layout runat="server" id="we7layout_147841515163296">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147841515163296_cloumn1">
 <wew:Footer_Default control="Footer_Default" filename="/Widgets/WidgetCollection/MyParts/Footer.Default/Footer.Default.ascx" id="Footer_Default_147841523202096" cssclass="Footer_Default" runat="server"></wew:Footer_Default></we7design:we7layoutcolumn>
</we7design:we7layout></we7design:we7zoneplaceholder>
    </div>
<wew:topinfo control="TopInfo" filename="/Widgets/WidgetCollection/MyParts/TopInfo/TopInfo.ascx" id="TopInfo_147841521055986" cssclass="TopInfo" runat="server"></wew:topinfo></body>
</html>