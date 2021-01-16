<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ Register Src="/Widgets/WidgetCollection/MyParts/TopInfo/TopInfo.ascx" TagName="TopInfo" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/ChannelMenuV.DropDown/ChannelMenuV.DropDown.ascx" TagName="ChannelMenuV_DropDown" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/ChannelMenu.CurrLocation/ChannelMenu.CurrentLocation.ascx" TagName="ChannelMenu_CurrentLocation" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/ArticleList.YMD/ArticleList.YMD.ascx" TagName="ArticleList_YMD" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/PagedArticleList.Default/PagedArticleList.Default.ascx" TagName="PagedArticleList_Default" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/Footer.Default/Footer.Default.ascx" TagName="Footer_Default" TagPrefix="wew" %>

<html>
<head runat="server">
    <title></title>
    <link href="/Admin/VisualTemplate/Style/VisualDesign.LayoutsBasics.css" rel="stylesheet" type="text/css">
<link href="/Widgets/Themes/theme/Style.css" type="text/css" rel="stylesheet" class="themestyle" id="themestyle"><script src="/Widgets/Scripts/jquery.pack.js" type="text/javascript" class="jquerypack" id="jquerypack"></script><script src="/Widgets/Scripts/jquery.peex.js" type="text/javascript" class="jquerypeex" id="jquerypeex"></script><script src="/Widgets/Scripts/Plugins/Common.js" type="text/javascript" class="commonPlugin" id="commonPlugin"></script><link href="/_skins/School/Style/UxStyle.css" type="text/css" rel="stylesheet"></head>
<body style="background-color:#f5f1da;background-position:center top;background-repeat:no-repeat; background-attachment:scroll;">
    <div id="pagecontainer" style="width:100%px;margin:0 auto">
        <we7design:we7zoneplaceholder id="bodyplaceholder" runat="server"><we7design:we7layout runat="server" id="we7layout_147814426541884">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147814426541884_cloumn1">
 <wew:TopInfo control="TopInfo" filename="/Widgets/WidgetCollection/MyParts/TopInfo/TopInfo.ascx" id="TopInfo_147815829649467" cssclass="TopInfo" runat="server"></wew:TopInfo></we7design:we7layoutcolumn>
</we7design:we7layout><we7design:we7layout runat="server" id="we7layout_149615624552010">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_149615624552010_cloumn1" style="min-height:400px" cssclass="">
 <wew:channelmenuv_dropdown control="ChannelMenuV_DropDown" filename="/Widgets/WidgetCollection/MyParts/ChannelMenuV.DropDown/ChannelMenuV.DropDown.ascx" id="ChannelMenuV_DropDown_147814427067210" cssclass="ChannelMenuV_DropDown" runat="server"></wew:channelmenuv_dropdown></we7design:we7layoutcolumn>
</we7design:we7layout><we7design:we7layout runat="server" id="we7layout_147814426381590">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147814426381590_cloumn1">
 <we7design:we7layout runat="server" id="we7layout_147814488611465">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147814488611465_cloumn1" style="" cssclass="layout_content">
 <we7design:we7layout runat="server" id="we7layout_147814491829580">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147814491829580_cloumn1">
 <wew:ChannelMenu_CurrentLocation control="ChannelMenu_CurrentLocation" filename="/Widgets/WidgetCollection/MyParts/ChannelMenu.CurrLocation/ChannelMenu.CurrentLocation.ascx" id="ChannelMenu_CurrentLocation_147814495505786" cssclass="ChannelMenu_CurrentLocation" runat="server"></wew:ChannelMenu_CurrentLocation><we7design:we7layout runat="server" id="we7layout_147913355435384">
 <we7design:we7layoutcolumn float="left" width="250" widthunit="px" runat="server" id="we7layout_147913355435384_cloumn1" style="" cssclass="">
 <we7design:we7layout runat="server" id="we7layout_147895534870141">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147895534870141_cloumn1">
 <wew:ArticleList_YMD id="ArticleList_YMD_147895538174256" icon="/_skins/School/images/header_normal.jpg" marginleft10="False" margintop10="True" bordercolor="" isshow="False" tags="" pagesize="5" ownerid="{97a99800-a40d-4926-9329-3a1becfdeb04}" cssclass="ArticleRight" includechildren="True" dateformat="MM/dd" titlelength="34" filename="/Widgets/WidgetCollection/MyParts/ArticleList.YMD/ArticleList.YMD.ascx" runat="server"></wew:ArticleList_YMD></we7design:we7layoutcolumn>
</we7design:we7layout><we7design:we7layout runat="server" id="we7layout_147895534322233">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147895534322233_cloumn1">
 <wew:ArticleList_YMD id="ArticleList_YMD_14789553535172" icon="/_skins/School/images/header_normal.jpg" marginleft10="False" margintop10="True" bordercolor="" isshow="False" tags="" pagesize="5" ownerid="{21878620-7c85-4da0-be2e-984276537bb2}" cssclass="ArticleRight" includechildren="True" dateformat="MM/dd" titlelength="34" filename="/Widgets/WidgetCollection/MyParts/ArticleList.YMD/ArticleList.YMD.ascx" runat="server"></wew:ArticleList_YMD></we7design:we7layoutcolumn>
</we7design:we7layout></we7design:we7layoutcolumn>
 <we7design:we7layoutcolumn float="left" width="30" widthunit="px" runat="server" id="we7layout_147913355435384_cloumn2" style="" cssclass="">
 </we7design:we7layoutcolumn>
  <we7design:we7layoutcolumn float="left" width="780" widthunit="px" runat="server" id="we7layout_147913355435384_cloumn3" style="" cssclass="">
 <we7design:we7layout runat="server" id="we7layout_147913493714760">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147913493714760_cloumn1">
 <wew:PagedArticleList_Default id="PagedArticleList_Default_147814487908142" margintop10="True" includechildren="True" dateformat="yyyy-MM-dd" tags="" bordercolor="" icon="/_skins/School/images/header_normal.jpg" isshow="False" titlelength="70" pager-requestpageindex="pi" pager-pagesize="15" cssclass="PagedArticleList_Default" pager-pagerdivclass="page_css page_line" ownerid="" pager-vmtemplatefilename="/Widgets/WidgetCollection/文章列表类/PagedArticleList.Default/vm/pager.vm" pager-pagerspanclass="pagecss" filename="/Widgets/WidgetCollection/MyParts/PagedArticleList.Default/PagedArticleList.Default.ascx" runat="server"></wew:PagedArticleList_Default></we7design:we7layoutcolumn>
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