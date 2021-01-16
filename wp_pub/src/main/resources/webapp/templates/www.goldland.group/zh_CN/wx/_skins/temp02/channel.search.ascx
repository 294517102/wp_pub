<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ Register Src="/Widgets/WidgetCollection/MyParts/TopInfo/TopInfo.ascx" TagName="TopInfo" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/SiteHeader/SiteHeader.ascx" TagName="SiteHeader" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/ChannelMenuV.DropDown/ChannelMenuV.DropDown.ascx" TagName="ChannelMenuV_DropDown" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/ChannelMenu.CurrLocation/ChannelMenu.CurrentLocation.ascx" TagName="ChannelMenu_CurrentLocation" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/搜索类/Search.ResultUI/PagedArticleList.DefaultSearch.ascx" TagName="PagedArticleList_DefaultSearch" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/Footer.Default/Footer.Default.ascx" TagName="Footer_Default" TagPrefix="wew" %>

<html>
<head runat="server">
    <title></title>
    <link href="/Admin/VisualTemplate/Style/VisualDesign.LayoutsBasics.css" rel="stylesheet" type="text/css">
<link href="/Widgets/Themes/theme/Style.css" type="text/css" rel="stylesheet" class="themestyle" id="themestyle"><script src="/Widgets/Scripts/jquery.pack.js" type="text/javascript" class="jquerypack" id="jquerypack"></script><script src="/Widgets/Scripts/jquery.peex.js" type="text/javascript" class="jquerypeex" id="jquerypeex"></script><script src="/Widgets/Scripts/Plugins/Common.js" type="text/javascript" class="commonPlugin" id="commonPlugin"></script><link href="/_skins/temp02/Style/UxStyle.css" type="text/css" rel="stylesheet"></head>
<body style="background-color:#f7f1da;background-position:center top;background-repeat:no-repeat; background-attachment:scroll;">
    <div id="pagecontainer" style="width:100%px;margin:0 auto">
        <we7design:we7zoneplaceholder id="bodyplaceholder" runat="server"><we7design:we7layout runat="server" id="we7layout_14781581251043">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_14781581251043_cloumn1">
 <wew:TopInfo control="TopInfo" filename="/Widgets/WidgetCollection/MyParts/TopInfo/TopInfo.ascx" id="TopInfo_147815813263644" cssclass="TopInfo" runat="server"></wew:TopInfo></we7design:we7layoutcolumn>
</we7design:we7layout><we7design:we7layout runat="server" id="we7layout_150470422865770">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_150470422865770_cloumn1" style="" cssclass="topbg">
 <we7design:we7layout runat="server" id="we7layout_147886742570947">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147886742570947_cloumn1" style="" cssclass="layout">
 <we7design:we7layout runat="server" id="we7layout_150470151174877">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_150470151174877_cloumn1">
 <wew:siteheader control="SiteHeader" filename="/Widgets/WidgetCollection/MyParts/SiteHeader/SiteHeader.ascx" id="SiteHeader_14788674500895" cssclass="SiteHeader" runat="server"></wew:siteheader></we7design:we7layoutcolumn>
</we7design:we7layout></we7design:we7layoutcolumn>
</we7design:we7layout></we7design:we7layoutcolumn>
</we7design:we7layout><we7design:we7layout runat="server" id="we7layout_147815811952869">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147815811952869_cloumn1">
 <wew:ChannelMenuV_DropDown control="ChannelMenuV_DropDown" filename="/Widgets/WidgetCollection/MyParts/ChannelMenuV.DropDown/ChannelMenuV.DropDown.ascx" id="ChannelMenuV_DropDown_147815813803065" cssclass="ChannelMenuV_DropDown" runat="server"></wew:ChannelMenuV_DropDown></we7design:we7layoutcolumn>
</we7design:we7layout><we7design:we7layout runat="server" id="we7layout_147815812113035">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147815812113035_cloumn1" style="" cssclass="layout_content">
 <we7design:we7layout runat="server" id="we7layout_147815817639541">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147815817639541_cloumn1">
 <wew:ChannelMenu_CurrentLocation control="ChannelMenu_CurrentLocation" filename="/Widgets/WidgetCollection/MyParts/ChannelMenu.CurrLocation/ChannelMenu.CurrentLocation.ascx" id="ChannelMenu_CurrentLocation_147886749755096" cssclass="ChannelMenu_CurrentLocation" runat="server"></wew:ChannelMenu_CurrentLocation></we7design:we7layoutcolumn>
</we7design:we7layout><we7design:we7layout runat="server" id="we7layout_14781581712274">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_14781581712274_cloumn1">
 <wew:PagedArticleList_DefaultSearch id="PagedArticleList_DefaultSearch_150470192637455" includechildren="True" dateformat="yyyy-MM-dd" titlelength="80" margintop10="True" bordercolor="" icon="/_skins/temp02/images/header_tit02.png" thumbnailtag="HomeScroll" pager-pagesize="20" pager-requestpageindex="pi" cssclass="PagedArticleList_DefaultSearch" pager-pagerdivclass="page_css page_line" ownerid="" pager-vmtemplatefilename="/Widgets/WidgetCollection/文章列表类/PagedArticleList.Default/vm/pager.vm" pager-pagerspanclass="pagecss" filename="/Widgets/WidgetCollection/搜索类/Search.ResultUI/PagedArticleList.DefaultSearch.ascx" runat="server"></wew:PagedArticleList_DefaultSearch></we7design:we7layoutcolumn>
</we7design:we7layout></we7design:we7layoutcolumn>
</we7design:we7layout><we7design:we7layout runat="server" id="we7layout_147886804156163">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147886804156163_cloumn1">
 <wew:Footer_Default control="Footer_Default" filename="/Widgets/WidgetCollection/MyParts/Footer.Default/Footer.Default.ascx" id="Footer_Default_147886805156911" cssclass="Footer_Default" runat="server"></wew:Footer_Default></we7design:we7layoutcolumn>
</we7design:we7layout></we7design:we7zoneplaceholder>
    </div>
</body>
</html>