<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ Register Src="/Widgets/WidgetCollection/MyParts/TopInfo/TopInfo.ascx" TagName="TopInfo" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/SiteHeader/SiteHeader.ascx" TagName="SiteHeader" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/Search2015/Search2015.ascx" TagName="Search2015" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/ChannelMenuV.DropDown/ChannelMenuV.DropDown.ascx" TagName="ChannelMenuV_DropDown" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/ChannelMenu.CurrLocation/ChannelMenu.CurrentLocation.ascx" TagName="ChannelMenu_CurrentLocation" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/Sidebar.ChannelNav/Sidebar.ChannelNav.ascx" TagName="Sidebar_ChannelNav" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/商城下载类/Vote.Default/Vote.Default.ascx" TagName="Vote_Default" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/内容模型类/Feedback_FeedbackView/Feedback_FeedbackView.ascx" TagName="Feedback_FeedbackView" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/Footer.Default/Footer.Default.ascx" TagName="Footer_Default" TagPrefix="wew" %>

<html>
<head runat="server">
    <title></title>
    <link href="/Admin/VisualTemplate/Style/VisualDesign.LayoutsBasics.css" rel="stylesheet" type="text/css">
<link href="/Widgets/Themes/theme/Style.css" type="text/css" rel="stylesheet" class="themestyle" id="themestyle"><script src="/Widgets/Scripts/jquery.pack.js" type="text/javascript" class="jquerypack" id="jquerypack"></script><script src="/Widgets/Scripts/jquery.peex.js" type="text/javascript" class="jquerypeex" id="jquerypeex"></script><script src="/Widgets/Scripts/Plugins/Common.js" type="text/javascript" class="commonPlugin" id="commonPlugin"></script><link href="/_skins/School/Style/UxStyle.css" type="text/css" rel="stylesheet"></head>
<body style="background-color:#ffffff;background-position:center top;background-repeat:no-repeat; background-attachment:scroll;background-image:url('/_skins/School/images/all_bg.jpg');">
    <div id="pagecontainer" style="width:100%px;margin:0 auto">
        <we7design:we7zoneplaceholder id="bodyplaceholder" runat="server"><we7design:we7layout runat="server" id="we7layout_147888310696413">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147888310696413_cloumn1">
 <wew:TopInfo control="TopInfo" filename="/Widgets/WidgetCollection/MyParts/TopInfo/TopInfo.ascx" id="TopInfo_147888315071956" cssclass="TopInfo" runat="server"></wew:TopInfo></we7design:we7layoutcolumn>
</we7design:we7layout><we7design:we7layout runat="server" id="we7layout_147888310540792">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147888310540792_cloumn1" style="" cssclass="layout">
 <we7design:we7layout runat="server" id="we7layout_147888311411054">
 <we7design:we7layoutcolumn float="left" width="60" widthunit="%" runat="server" id="we7layout_147888311411054_cloumn1" style="" cssclass="">
 <wew:SiteHeader control="SiteHeader" filename="/Widgets/WidgetCollection/MyParts/SiteHeader/SiteHeader.ascx" id="SiteHeader_147888315577264" cssclass="SiteHeader" runat="server"></wew:SiteHeader></we7design:we7layoutcolumn>
  <we7design:we7layoutcolumn float="left" width="40" widthunit="%" runat="server" id="we7layout_147888311411054_cloumn2" style="" cssclass="">
 <wew:Search2015 control="Search2015" filename="/Widgets/WidgetCollection/MyParts/Search2015/Search2015.ascx" id="Search2015_147888315850681" cssclass="Search2015" runat="server"></wew:Search2015></we7design:we7layoutcolumn>
</we7design:we7layout></we7design:we7layoutcolumn>
</we7design:we7layout><we7design:we7layout runat="server" id="we7layout_147888310838995">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147888310838995_cloumn1">
 <wew:ChannelMenuV_DropDown control="ChannelMenuV_DropDown" filename="/Widgets/WidgetCollection/MyParts/ChannelMenuV.DropDown/ChannelMenuV.DropDown.ascx" id="ChannelMenuV_DropDown_147888316305575" cssclass="ChannelMenuV_DropDown" runat="server"></wew:ChannelMenuV_DropDown></we7design:we7layoutcolumn>
</we7design:we7layout><we7design:we7layout runat="server" id="we7layout_147888310990241">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147888310990241_cloumn1" style="" cssclass="layout_content">
 <we7design:we7layout runat="server" id="we7layout_14788831754911">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_14788831754911_cloumn1">
 <wew:ChannelMenu_CurrentLocation control="ChannelMenu_CurrentLocation" filename="/Widgets/WidgetCollection/MyParts/ChannelMenu.CurrLocation/ChannelMenu.CurrentLocation.ascx" id="ChannelMenu_CurrentLocation_14788831902251" cssclass="ChannelMenu_CurrentLocation" runat="server"></wew:ChannelMenu_CurrentLocation></we7design:we7layoutcolumn>
</we7design:we7layout><we7design:we7layout runat="server" id="we7layout_147888317239026">
 <we7design:we7layoutcolumn float="left" width="250" widthunit="px" runat="server" id="we7layout_147888317239026_cloumn1" style="" cssclass="">
 <we7design:we7layout runat="server" id="we7layout_147895765672615">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147895765672615_cloumn1">
 <wew:sidebar_channelnav control="Sidebar_ChannelNav" filename="/Widgets/WidgetCollection/MyParts/Sidebar.ChannelNav/Sidebar.ChannelNav.ascx" id="Sidebar_ChannelNav_147895767667186" cssclass="Sidebar_ChannelNav" runat="server"></wew:sidebar_channelnav><we7design:we7layout runat="server" id="we7layout_14789576537278">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_14789576537278_cloumn1">
 <wew:vote_default id="Vote_Default_147895737293622" icon="/_skins/SchoolB/images/header_bg02.png" bordercolor="" cssclass="Vote_Default" voteid="{64732aab-099c-4b31-afc7-e18db8b2a05f}" filename="/Widgets/WidgetCollection/商城下载类/Vote.Default/Vote.Default.ascx" runat="server"></wew:vote_default></we7design:we7layoutcolumn>
</we7design:we7layout></we7design:we7layoutcolumn>
</we7design:we7layout></we7design:we7layoutcolumn>
 <we7design:we7layoutcolumn float="left" width="50" widthunit="px" runat="server" id="we7layout_147888317239026_cloumn2" style="" cssclass="">
 </we7design:we7layoutcolumn>
  <we7design:we7layoutcolumn float="left" width="760" widthunit="px" runat="server" id="we7layout_147888317239026_cloumn3" style="" cssclass="">
 <wew:Feedback_FeedbackView id="Feedback_FeedbackView_147888319677967" contentlength="1000" isshow="True" ownerid="{a1ddff60-6124-447e-8e30-22c033796c10}" titlelength="50" orderinfo="%7B%22TableName%22%3A%22AdviceInfo%22%2C%22TableDesc%22%3A%22%E5%8F%8D%E9%A6%88%E4%BF%A1%E6%81%AF%22%2C%22Orders%22%3A%5B%7B%22Mode%22%3A1%2C%22AliasName%22%3A%22%E5%88%9B%E5%BB%BA%E6%97%B6%E9%97%B4%22%2C%22Name%22%3A%22Created%22%2C%22Adorn%22%3A0%2C%22Start%22%3A0%2C%22Length%22%3A0%7D%5D%7D" criteriainfostr="%7B%22TableName%22%3A%22AdviceInfo%22%2C%22TableDesc%22%3A%22%E5%8F%8D%E9%A6%88%E4%BF%A1%E6%81%AF%22%2C%22CriteriaJsonObjects%22%3A%5B%5D%7D" cssclass="Feedback_FeedbackView" filename="/Widgets/WidgetCollection/内容模型类/Feedback_FeedbackView/Feedback_FeedbackView.ascx" runat="server"></wew:Feedback_FeedbackView><we7design:we7layout runat="server" id="we7layout_148281824728232">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_148281824728232_cloumn1">
 </we7design:we7layoutcolumn>
</we7design:we7layout></we7design:we7layoutcolumn>
</we7design:we7layout></we7design:we7layoutcolumn>
</we7design:we7layout><we7design:we7layout runat="server" id="we7layout_147888312479390">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147888312479390_cloumn1">
 <wew:Footer_Default control="Footer_Default" filename="/Widgets/WidgetCollection/MyParts/Footer.Default/Footer.Default.ascx" id="Footer_Default_147888316705346" cssclass="Footer_Default" runat="server"></wew:Footer_Default></we7design:we7layoutcolumn>
</we7design:we7layout></we7design:we7zoneplaceholder>
    </div>
</body>
</html>