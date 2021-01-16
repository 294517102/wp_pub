<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ Register Src="/Widgets/WidgetCollection/MyParts/TopInfo/TopInfo.ascx" TagName="TopInfo" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/SiteHeader/SiteHeader.ascx" TagName="SiteHeader" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/Search2015/Search2015.ascx" TagName="Search2015" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/ChannelMenuV.DropDown/ChannelMenuV.DropDown.ascx" TagName="ChannelMenuV_DropDown" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/ChannelMenu.CurrLocation/ChannelMenu.CurrentLocation.ascx" TagName="ChannelMenu_CurrentLocation" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/Sidebar.ChannelNav/Sidebar.ChannelNav.ascx" TagName="Sidebar_ChannelNav" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/ArticleList.YMD/ArticleList.YMD.ascx" TagName="ArticleList_YMD" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/PagedArticleList.FrameLoop/PagedArticleList.FrameLoop.ascx" TagName="PagedArticleList_FrameLoop" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/Footer.Default/Footer.Default.ascx" TagName="Footer_Default" TagPrefix="wew" %>

<html>
<head runat="server">
    <title></title>
    <link href="/Admin/VisualTemplate/Style/VisualDesign.LayoutsBasics.css" rel="stylesheet" type="text/css">
<link href="/Widgets/Themes/theme/Style.css" type="text/css" rel="stylesheet" class="themestyle" id="themestyle"><script src="/Widgets/Scripts/jquery.pack.js" type="text/javascript" class="jquerypack" id="jquerypack"></script><script src="/Widgets/Scripts/jquery.peex.js" type="text/javascript" class="jquerypeex" id="jquerypeex"></script><script src="/Widgets/Scripts/Plugins/Common.js" type="text/javascript" class="commonPlugin" id="commonPlugin"></script><link href="/_skins/School/Style/UxStyle.css" type="text/css" rel="stylesheet"></head>
<body style="background-color:rgb(255, 255, 255);background-position:center top;background-repeat:no-repeat; background-attachment:scroll;background-image:url('/_skins/School/images/all_bg.jpg');">
    <div id="pagecontainer" style="width:100%px;margin:0 auto">
        <we7design:we7zoneplaceholder id="bodyplaceholder" runat="server"><we7design:we7layout runat="server" id="we7layout_147900974269673">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147900974269673_cloumn1">
 <wew:TopInfo control="TopInfo" filename="/Widgets/WidgetCollection/MyParts/TopInfo/TopInfo.ascx" id="TopInfo_147900979962833" cssclass="TopInfo" runat="server"></wew:TopInfo></we7design:we7layoutcolumn>
</we7design:we7layout><we7design:we7layout runat="server" id="we7layout_147900974087379">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147900974087379_cloumn1" style="" cssclass="layout">
 <we7design:we7layout runat="server" id="we7layout_147900975389087">
 <we7design:we7layoutcolumn float="left" width="60" widthunit="%" runat="server" id="we7layout_147900975389087_cloumn1" style="" cssclass="">
 <wew:SiteHeader control="SiteHeader" filename="/Widgets/WidgetCollection/MyParts/SiteHeader/SiteHeader.ascx" id="SiteHeader_147900979755195" cssclass="SiteHeader" runat="server"></wew:SiteHeader></we7design:we7layoutcolumn>
  <we7design:we7layoutcolumn float="left" width="40" widthunit="%" runat="server" id="we7layout_147900975389087_cloumn2" style="" cssclass="">
 <wew:Search2015 control="Search2015" filename="/Widgets/WidgetCollection/MyParts/Search2015/Search2015.ascx" id="Search2015_147900982757096" cssclass="Search2015" runat="server"></wew:Search2015></we7design:we7layoutcolumn>
</we7design:we7layout></we7design:we7layoutcolumn>
</we7design:we7layout><we7design:we7layout runat="server" id="we7layout_147900973436179">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147900973436179_cloumn1">
 <wew:ChannelMenuV_DropDown id="ChannelMenuV_DropDown_147900982044162" tag="" cssclass="ChannelMenuV_DropDown" filename="/Widgets/WidgetCollection/MyParts/ChannelMenuV.DropDown/ChannelMenuV.DropDown.ascx" runat="server"></wew:ChannelMenuV_DropDown></we7design:we7layoutcolumn>
</we7design:we7layout><we7design:we7layout runat="server" id="we7layout_147900973641026">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147900973641026_cloumn1" style="" cssclass="layout_content">
 <we7design:we7layout runat="server" id="we7layout_147900977612634">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147900977612634_cloumn1">
 <wew:ChannelMenu_CurrentLocation control="ChannelMenu_CurrentLocation" filename="/Widgets/WidgetCollection/MyParts/ChannelMenu.CurrLocation/ChannelMenu.CurrentLocation.ascx" id="ChannelMenu_CurrentLocation_147900981757841" cssclass="ChannelMenu_CurrentLocation" runat="server"></wew:ChannelMenu_CurrentLocation></we7design:we7layoutcolumn>
</we7design:we7layout><we7design:we7layout runat="server" id="we7layout_147900978010761">
 <we7design:we7layoutcolumn float="left" width="250" widthunit="px" runat="server" id="we7layout_147900978010761_cloumn1" style="" cssclass="">
 <we7design:we7layout runat="server" id="we7layout_147900985840757">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147900985840757_cloumn1">
 <wew:Sidebar_ChannelNav id="Sidebar_ChannelNav_147900985045462" icon="/_skins/School/images/header_tit01.png" bordercolor="" margintop10="True" cssclass="Sidebar_ChannelNav" ownerid="" filename="/Widgets/WidgetCollection/MyParts/Sidebar.ChannelNav/Sidebar.ChannelNav.ascx" runat="server"></wew:Sidebar_ChannelNav><we7design:we7layout runat="server" id="we7layout_147900985671985">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147900985671985_cloumn1">
 <wew:articlelist_ymd id="ArticleList_YMD_147900986727166" icon="/_skins/School/images/header_tit02.png" marginleft10="False" margintop10="True" bordercolor="" isshow="False" tags="" pagesize="5" ownerid="{89fbf068-cdfe-446c-9d1f-cf8269315d9a}" cssclass="ArticleRight" includechildren="True" dateformat="MM/dd" titlelength="34" filename="/Widgets/WidgetCollection/MyParts/ArticleList.YMD/ArticleList.YMD.ascx" runat="server"></wew:articlelist_ymd><we7design:we7layout runat="server" id="we7layout_147900985461391">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147900985461391_cloumn1">
 <wew:articlelist_ymd id="ArticleList_YMD_147900986908852" icon="/_skins/School/images/header_tit02.png" marginleft10="False" margintop10="True" bordercolor="" isshow="False" tags="" pagesize="6" ownerid="{57626e8b-ab72-416c-a678-94e16e33bd68}" cssclass="ArticleRight" includechildren="True" dateformat="MM/dd" titlelength="34" filename="/Widgets/WidgetCollection/MyParts/ArticleList.YMD/ArticleList.YMD.ascx" runat="server"></wew:articlelist_ymd></we7design:we7layoutcolumn>
</we7design:we7layout></we7design:we7layoutcolumn>
</we7design:we7layout></we7design:we7layoutcolumn>
</we7design:we7layout></we7design:we7layoutcolumn>
 <we7design:we7layoutcolumn float="left" width="30" widthunit="px" runat="server" id="we7layout_147900978010761_cloumn2" style="" cssclass="">
 </we7design:we7layoutcolumn>
  <we7design:we7layoutcolumn float="left" width="780" widthunit="px" runat="server" id="we7layout_147900978010761_cloumn3" style="" cssclass="">
 <wew:PagedArticleList_FrameLoop id="PagedArticleList_FrameLoop_147901097378449" margintop10="False" includechildren="True" dateformat="yyyy-MM-dd" tags="" bordercolor="" icon="/_skins/School/images/header_tit05.png" isshow="False" titlelength="50" pager-requestpageindex="pi" pager-pagesize="20" cssclass="PagedArticleList_FrameLoop" pager-pagerdivclass="page_css page_line" ownerid="" pager-vmtemplatefilename="/Widgets/WidgetCollection/文章列表类/PagedArticleList.Default/vm/pager.vm" pager-pagerspanclass="pagecss" filename="/Widgets/WidgetCollection/MyParts/PagedArticleList.FrameLoop/PagedArticleList.FrameLoop.ascx" runat="server"></wew:PagedArticleList_FrameLoop></we7design:we7layoutcolumn>
</we7design:we7layout></we7design:we7layoutcolumn>
</we7design:we7layout><we7design:we7layout runat="server" id="we7layout_147900973925856">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147900973925856_cloumn1">
 <wew:Footer_Default control="Footer_Default" filename="/Widgets/WidgetCollection/MyParts/Footer.Default/Footer.Default.ascx" id="Footer_Default_147900981135211" cssclass="Footer_Default" runat="server"></wew:Footer_Default></we7design:we7layoutcolumn>
</we7design:we7layout></we7design:we7zoneplaceholder>
    </div>
</body>
</html>