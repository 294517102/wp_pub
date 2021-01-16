<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ Register Src="/Widgets/WidgetCollection/MyParts/TopInfo/TopInfo.ascx" TagName="TopInfo" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/SiteHeader/SiteHeader.ascx" TagName="SiteHeader" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/Search2015/Search2015.ascx" TagName="Search2015" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/ChannelMenuV.DropDown/ChannelMenuV.DropDown.ascx" TagName="ChannelMenuV_DropDown" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/ChannelMenu.CurrLocation/ChannelMenu.CurrentLocation.ascx" TagName="ChannelMenu_CurrentLocation" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/ArticleView.Default/ArticleView.Default.ascx" TagName="ArticleView_Default" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/Footer.Default/Footer.Default.ascx" TagName="Footer_Default" TagPrefix="wew" %>

<html>
<head runat="server">
    <title></title>
    <link href="/Admin/VisualTemplate/Style/VisualDesign.LayoutsBasics.css" rel="stylesheet" type="text/css">
<link href="/Widgets/Themes/theme/Style.css" type="text/css" rel="stylesheet" class="themestyle" id="themestyle"><script src="/Widgets/Scripts/jquery.pack.js" type="text/javascript" class="jquerypack" id="jquerypack"></script><script src="/Widgets/Scripts/jquery.peex.js" type="text/javascript" class="jquerypeex" id="jquerypeex"></script><script src="/Widgets/Scripts/Plugins/Common.js" type="text/javascript" class="commonPlugin" id="commonPlugin"></script><link href="/_skins/School/Style/UxStyle.css" type="text/css" rel="stylesheet"></head>
<body style="background-color:rgb(255, 255, 255);background-position:center top;background-repeat:no-repeat; background-attachment:scroll;background-image:url('/_skins/School/images/all_bg.jpg');">
    <div id="pagecontainer" style="width:100%px;margin:0 auto">
        <we7design:we7zoneplaceholder id="bodyplaceholder" runat="server"><we7design:we7layout runat="server" id="we7layout_147816041643925">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147816041643925_cloumn1">
 <wew:TopInfo control="TopInfo" filename="/Widgets/WidgetCollection/MyParts/TopInfo/TopInfo.ascx" id="TopInfo_147816042290330" cssclass="TopInfo" runat="server"></wew:TopInfo><we7design:we7layout runat="server" id="we7layout_147900457101965">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147900457101965_cloumn1" style="" cssclass="layout">
 <we7design:we7layout runat="server" id="we7layout_147900457935458">
 <we7design:we7layoutcolumn float="left" width="60" widthunit="%" runat="server" id="we7layout_147900457935458_cloumn1" style="" cssclass="">
 <wew:SiteHeader control="SiteHeader" filename="/Widgets/WidgetCollection/MyParts/SiteHeader/SiteHeader.ascx" id="SiteHeader_147900459755371" cssclass="SiteHeader" runat="server"></wew:SiteHeader></we7design:we7layoutcolumn>
  <we7design:we7layoutcolumn float="left" width="40" widthunit="%" runat="server" id="we7layout_147900457935458_cloumn2" style="" cssclass="">
 <wew:Search2015 control="Search2015" filename="/Widgets/WidgetCollection/MyParts/Search2015/Search2015.ascx" id="Search2015_147900460665895" cssclass="Search2015" runat="server"></wew:Search2015></we7design:we7layoutcolumn>
</we7design:we7layout></we7design:we7layoutcolumn>
</we7design:we7layout></we7design:we7layoutcolumn>
</we7design:we7layout><we7design:we7layout runat="server" id="we7layout_147816041107310">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147816041107310_cloumn1">
 <wew:ChannelMenuV_DropDown control="ChannelMenuV_DropDown" filename="/Widgets/WidgetCollection/MyParts/ChannelMenuV.DropDown/ChannelMenuV.DropDown.ascx" id="ChannelMenuV_DropDown_147816042809177" cssclass="ChannelMenuV_DropDown" runat="server"></wew:ChannelMenuV_DropDown></we7design:we7layoutcolumn>
</we7design:we7layout><we7design:we7layout runat="server" id="we7layout_147816041393634">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147816041393634_cloumn1" style="" cssclass="layout_content">
 <we7design:we7layout runat="server" id="we7layout_147816045373397">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147816045373397_cloumn1">
 <wew:ChannelMenu_CurrentLocation control="ChannelMenu_CurrentLocation" filename="/Widgets/WidgetCollection/MyParts/ChannelMenu.CurrLocation/ChannelMenu.CurrentLocation.ascx" id="ChannelMenu_CurrentLocation_147816045621045" cssclass="ChannelMenu_CurrentLocation" runat="server"></wew:ChannelMenu_CurrentLocation></we7design:we7layoutcolumn>
</we7design:we7layout><we7design:we7layout runat="server" id="we7layout_14781604508700">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_14781604508700_cloumn1">
 <wew:ArticleView_Default id="ArticleView_Default_147816046139048" bordercolor="" tags="" isshowatta="True" dateformat="yyyy-MM-dd" cssclass="ArticleViewAll" pagesize="6" titlelength="40" filename="/Widgets/WidgetCollection/MyParts/ArticleView.Default/ArticleView.Default.ascx" runat="server"></wew:ArticleView_Default></we7design:we7layoutcolumn>
</we7design:we7layout></we7design:we7layoutcolumn>
</we7design:we7layout><we7design:we7layout runat="server" id="we7layout_147900455575494">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147900455575494_cloumn1">
 <wew:Footer_Default control="Footer_Default" filename="/Widgets/WidgetCollection/MyParts/Footer.Default/Footer.Default.ascx" id="Footer_Default_147900456278485" cssclass="Footer_Default" runat="server"></wew:Footer_Default></we7design:we7layoutcolumn>
</we7design:we7layout></we7design:we7zoneplaceholder>
    </div>
</body>
</html>