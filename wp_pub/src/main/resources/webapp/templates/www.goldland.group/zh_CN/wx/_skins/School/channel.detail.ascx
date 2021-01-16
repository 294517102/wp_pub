<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ Register Src="/Widgets/WidgetCollection/MyParts/TopInfo/TopInfo.ascx" TagName="TopInfo" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/ChannelMenuV.DropDown/ChannelMenuV.DropDown.ascx" TagName="ChannelMenuV_DropDown" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/Search2015/Search2015.ascx" TagName="Search2015" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/ChannelMenu.CurrLocation/ChannelMenu.CurrentLocation.ascx" TagName="ChannelMenu_CurrentLocation" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/ArticleList.YMD/ArticleList.YMD.ascx" TagName="ArticleList_YMD" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/ArticleView.Default/ArticleView.Default.ascx" TagName="ArticleView_Default" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/Footer.Default/Footer.Default.ascx" TagName="Footer_Default" TagPrefix="wew" %>

<html>
<head runat="server">
    <title></title>
    <link href="/Admin/VisualTemplate/Style/VisualDesign.LayoutsBasics.css" rel="stylesheet" type="text/css">
<link href="/Widgets/Themes/theme/Style.css" type="text/css" rel="stylesheet" class="themestyle" id="themestyle"><script src="/Widgets/Scripts/jquery.pack.js" type="text/javascript" class="jquerypack" id="jquerypack"></script><script src="/Widgets/Scripts/jquery.peex.js" type="text/javascript" class="jquerypeex" id="jquerypeex"></script><script src="/Widgets/Scripts/Plugins/Common.js" type="text/javascript" class="commonPlugin" id="commonPlugin"></script><link href="/_skins/School/Style/UxStyle.css" type="text/css" rel="stylesheet"></head>
<body style="background-color:#f5f1da;background-position:center top;background-repeat:no-repeat; background-attachment:scroll;">
    <div id="pagecontainer" style="width:100%px;margin:0 auto">
        <we7design:we7zoneplaceholder id="bodyplaceholder" runat="server"><we7design:we7layout runat="server" id="we7layout_14781574794220">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_14781574794220_cloumn1">
 <wew:TopInfo control="TopInfo" filename="/Widgets/WidgetCollection/MyParts/TopInfo/TopInfo.ascx" id="TopInfo_14781574873398" cssclass="TopInfo" runat="server"></wew:TopInfo><we7design:we7layout runat="server" id="we7layout_149615438292670">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_149615438292670_cloumn1" style="min-height:400px" cssclass="">
 <wew:channelmenuv_dropdown control="ChannelMenuV_DropDown" filename="/Widgets/WidgetCollection/MyParts/ChannelMenuV.DropDown/ChannelMenuV.DropDown.ascx" id="ChannelMenuV_DropDown_149615453903390" cssclass="ChannelMenuV_DropDown" runat="server"></wew:channelmenuv_dropdown></we7design:we7layoutcolumn>
</we7design:we7layout></we7design:we7layoutcolumn>
</we7design:we7layout><we7design:we7layout runat="server" id="we7layout_147815747815591">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147815747815591_cloumn1">
 <wew:Search2015 control="Search2015" filename="/Widgets/WidgetCollection/MyParts/Search2015/Search2015.ascx" id="Search2015_149615452106343" cssclass="Search2015" runat="server"></wew:Search2015></we7design:we7layoutcolumn>
</we7design:we7layout><we7design:we7layout runat="server" id="we7layout_147815747672035">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147815747672035_cloumn1" style="" cssclass="layout_content">
 <we7design:we7layout runat="server" id="we7layout_147815769862112">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147815769862112_cloumn1">
 <wew:ChannelMenu_CurrentLocation control="ChannelMenu_CurrentLocation" filename="/Widgets/WidgetCollection/MyParts/ChannelMenu.CurrLocation/ChannelMenu.CurrentLocation.ascx" id="ChannelMenu_CurrentLocation_147815773232736" cssclass="ChannelMenu_CurrentLocation" runat="server"></wew:ChannelMenu_CurrentLocation></we7design:we7layoutcolumn>
</we7design:we7layout><we7design:we7layout runat="server" id="we7layout_14781577062401">
 <we7design:we7layoutcolumn float="left" width="250" widthunit="px" runat="server" id="we7layout_14781577062401_cloumn1" style="" cssclass="">
 <we7design:we7layout runat="server" id="we7layout_147822660875954">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147822660875954_cloumn1">
 <wew:ArticleList_YMD id="ArticleList_YMD_147822662347326" icon="/_skins/School/images/header_normal.jpg" marginleft10="False" margintop10="False" bordercolor="" isshow="False" tags="" pagesize="6" ownerid="{97a99800-a40d-4926-9329-3a1becfdeb04}" cssclass="ArticleRight" includechildren="True" dateformat="MM/dd" titlelength="34" filename="/Widgets/WidgetCollection/MyParts/ArticleList.YMD/ArticleList.YMD.ascx" runat="server"></wew:ArticleList_YMD><we7design:we7layout runat="server" id="we7layout_147822660697511">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147822660697511_cloumn1">
 <wew:ArticleList_YMD id="ArticleList_YMD_147822662762365" icon="/_skins/School/images/header_normal.jpg" marginleft10="False" margintop10="True" bordercolor="" isshow="False" tags="" pagesize="6" ownerid="{21878620-7c85-4da0-be2e-984276537bb2}" cssclass="ArticleRight" includechildren="True" dateformat="MM/dd" titlelength="34" filename="/Widgets/WidgetCollection/MyParts/ArticleList.YMD/ArticleList.YMD.ascx" runat="server"></wew:ArticleList_YMD></we7design:we7layoutcolumn>
</we7design:we7layout></we7design:we7layoutcolumn>
</we7design:we7layout></we7design:we7layoutcolumn>
 <we7design:we7layoutcolumn float="left" width="30" widthunit="px" runat="server" id="we7layout_14781577062401_cloumn2" style="" cssclass="">
 </we7design:we7layoutcolumn>
  <we7design:we7layoutcolumn float="left" width="780" widthunit="px" runat="server" id="we7layout_14781577062401_cloumn3" style="" cssclass="">
 <wew:ArticleView_Default id="ArticleView_Default_147815773941034" dateformat="yyyy/MM/dd" pagesize="6" titlelength="32" isshowatta="True" cssclass="ArticleView_Default" bordercolor="" tags="" filename="/Widgets/WidgetCollection/MyParts/ArticleView.Default/ArticleView.Default.ascx" runat="server"></wew:ArticleView_Default></we7design:we7layoutcolumn>
</we7design:we7layout></we7design:we7layoutcolumn>
</we7design:we7layout><we7design:we7layout runat="server" id="we7layout_147822658264635">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147822658264635_cloumn1">
 <wew:Footer_Default id="Footer_Default_147822658843280" cssclass="Footer_Default" filename="/Widgets/WidgetCollection/MyParts/Footer.Default/Footer.Default.ascx" runat="server"></wew:Footer_Default></we7design:we7layoutcolumn>
</we7design:we7layout></we7design:we7zoneplaceholder>
    </div>
</body>
</html>