<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ Register Src="/Widgets/WidgetCollection/MyParts/TopInfo/TopInfo.ascx" TagName="TopInfo" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/SiteHeader/SiteHeader.ascx" TagName="SiteHeader" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/Search2015/Search2015.ascx" TagName="Search2015" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/ChannelMenuV.DropDown/ChannelMenuV.DropDown.ascx" TagName="ChannelMenuV_DropDown" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/ChannelMenu.CurrLocation/ChannelMenu.CurrentLocation.ascx" TagName="ChannelMenu_CurrentLocation" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/PicNewsList/PicNewsList.ascx" TagName="PicNewsList" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/Sidebar.ChannelNav/Sidebar.ChannelNav.ascx" TagName="Sidebar_ChannelNav" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/ArticleList.YMD/ArticleList.YMD.ascx" TagName="ArticleList_YMD" TagPrefix="wew" %>



<html>
<head runat="server">
    <title></title>
    <link href="/Admin/VisualTemplate/Style/VisualDesign.LayoutsBasics.css" rel="stylesheet" type="text/css">
<link href="/Widgets/Themes/theme/Style.css" type="text/css" rel="stylesheet" class="themestyle" id="themestyle"><script src="/Widgets/Scripts/jquery.pack.js" type="text/javascript" class="jquerypack" id="jquerypack"></script><script src="/Widgets/Scripts/jquery.peex.js" type="text/javascript" class="jquerypeex" id="jquerypeex"></script><script src="/Widgets/Scripts/Plugins/Common.js" type="text/javascript" class="commonPlugin" id="commonPlugin"></script><link href="/_skins/SchoolA/Style/UxStyle.css" type="text/css" rel="stylesheet"><link href="/_skins/SchoolB/Style/UxStyle.css" type="text/css" rel="stylesheet"></head>
<body style="background-color:rgb(241, 251, 251);background-position:center top;background-repeat:no-repeat; background-attachment:scroll;background-image:url('/_skins/SchoolB/images/all_bg.jpg');">
    <div id="pagecontainer" style="width:100%px;margin:0 auto">
        <we7design:we7zoneplaceholder id="bodyplaceholder" runat="server"><we7design:we7layout runat="server" id="we7layout_147891699285717">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147891699285717_cloumn1">
 <wew:TopInfo control="TopInfo" filename="/Widgets/WidgetCollection/MyParts/TopInfo/TopInfo.ascx" id="TopInfo_147891710671865" cssclass="TopInfo" runat="server"></wew:TopInfo></we7design:we7layoutcolumn>
</we7design:we7layout><we7design:we7layout runat="server" id="we7layout_147891699582176">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147891699582176_cloumn1" style="" cssclass="layout">
 <we7design:we7layout runat="server" id="we7layout_147891699932740">
 <we7design:we7layoutcolumn float="left" width="60" widthunit="%" runat="server" id="we7layout_147891699932740_cloumn1" style="" cssclass="">
 <wew:SiteHeader id="SiteHeader_147891709246011" cssclass="SiteHeader" filename="/Widgets/WidgetCollection/MyParts/SiteHeader/SiteHeader.ascx" runat="server"></wew:SiteHeader></we7design:we7layoutcolumn>
  <we7design:we7layoutcolumn float="left" width="40" widthunit="%" runat="server" id="we7layout_147891699932740_cloumn2" style="" cssclass="">
 <wew:Search2015 control="Search2015" filename="/Widgets/WidgetCollection/MyParts/Search2015/Search2015.ascx" id="Search2015_147891709443475" cssclass="Search2015" runat="server"></wew:Search2015></we7design:we7layoutcolumn>
</we7design:we7layout></we7design:we7layoutcolumn>
</we7design:we7layout><we7design:we7layout runat="server" id="we7layout_147891700455440">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147891700455440_cloumn1">
 <wew:ChannelMenuV_DropDown control="ChannelMenuV_DropDown" filename="/Widgets/WidgetCollection/MyParts/ChannelMenuV.DropDown/ChannelMenuV.DropDown.ascx" id="ChannelMenuV_DropDown_147891710055024" cssclass="ChannelMenuV_DropDown" runat="server"></wew:ChannelMenuV_DropDown></we7design:we7layoutcolumn>
</we7design:we7layout><we7design:we7layout runat="server" id="we7layout_147891700209693">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147891700209693_cloumn1" style="" cssclass="layout_content">
 <we7design:we7layout runat="server" id="we7layout_147891821794563">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147891821794563_cloumn1">
 <wew:ChannelMenu_CurrentLocation control="ChannelMenu_CurrentLocation" filename="/Widgets/WidgetCollection/MyParts/ChannelMenu.CurrLocation/ChannelMenu.CurrentLocation.ascx" id="ChannelMenu_CurrentLocation_147891822491770" cssclass="ChannelMenu_CurrentLocation" runat="server"></wew:ChannelMenu_CurrentLocation></we7design:we7layoutcolumn>
</we7design:we7layout><we7design:we7layout runat="server" id="we7layout_147891702357461">
 <we7design:we7layoutcolumn float="left" width="780" widthunit="px" runat="server" id="we7layout_147891702357461_cloumn1" style="" cssclass="">
 <wew:PicNewsList id="PicNewsList_147891827027162" margintop10="False" thumbnailtag="PicArticle" dateformat="yyyy/MM/dd" includechildren="True" descriptionlength="200" icon="/_skins/SchoolB/images/fangkuai_biaoti_left.png" bordercolor="" tags="" isshow="False" pager-pagesize="10" pager-requestpageindex="pi" cssclass="PicNewsList" pager-vmtemplatefilename="/Widgets/WidgetCollection/文章列表类/PagedArticleList.Default/vm/pager.vm" pager-pagerdivclass="page_css page_line" ownerid="" titlelength="30" pager-pagerspanclass="pagecss" pagesize="10" filename="/Widgets/WidgetCollection/MyParts/PicNewsList/PicNewsList.ascx" runat="server"></wew:PicNewsList></we7design:we7layoutcolumn>
 <we7design:we7layoutcolumn float="left" width="30" widthunit="px" runat="server" id="we7layout_147891702357461_cloumn2" style="" cssclass="">
 </we7design:we7layoutcolumn>
  <we7design:we7layoutcolumn float="left" width="250" widthunit="px" runat="server" id="we7layout_147891702357461_cloumn3" style="" cssclass="">
 <we7design:we7layout runat="server" id="we7layout_14789549259677">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_14789549259677_cloumn1">
 <wew:Sidebar_ChannelNav control="Sidebar_ChannelNav" filename="/Widgets/WidgetCollection/MyParts/Sidebar.ChannelNav/Sidebar.ChannelNav.ascx" id="Sidebar_ChannelNav_147895494240880" cssclass="Sidebar_ChannelNav" runat="server"></wew:Sidebar_ChannelNav></we7design:we7layoutcolumn>
</we7design:we7layout><we7design:we7layout runat="server" id="we7layout_147891828259797">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147891828259797_cloumn1">
 <wew:ArticleList_YMD id="ArticleList_YMD_147892184998326" icon="/_skins/SchoolB/images/header_bg02.png" marginleft10="False" margintop10="True" bordercolor="" isshow="False" tags="" pagesize="5" ownerid="{89fbf068-cdfe-446c-9d1f-cf8269315d9a}" cssclass="ArticleRight" includechildren="True" dateformat="MM/dd" titlelength="34" filename="/Widgets/WidgetCollection/MyParts/ArticleList.YMD/ArticleList.YMD.ascx" runat="server"></wew:ArticleList_YMD></we7design:we7layoutcolumn>
</we7design:we7layout><we7design:we7layout runat="server" id="we7layout_147891828059613">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147891828059613_cloumn1">
 <wew:ArticleList_YMD id="ArticleList_YMD_147892187694516" icon="/_skins/SchoolB/images/header_bg02.png" marginleft10="False" margintop10="True" bordercolor="" isshow="False" tags="" pagesize="5" ownerid="{57626e8b-ab72-416c-a678-94e16e33bd68}" cssclass="ArticleRight" includechildren="True" dateformat="MM/dd" titlelength="34" filename="/Widgets/WidgetCollection/MyParts/ArticleList.YMD/ArticleList.YMD.ascx" runat="server"></wew:ArticleList_YMD></we7design:we7layoutcolumn>
</we7design:we7layout></we7design:we7layoutcolumn>
</we7design:we7layout></we7design:we7layoutcolumn>
</we7design:we7layout><we7design:we7layout runat="server" id="we7layout_147891702619111">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147891702619111_cloumn1">
 <span style='color:Red' title='文件“/Widgets/WidgetCollection/MyParts/Footer.Default/Footer.Default.ascx”不存在。'>此部件发生错误</span></we7design:we7layoutcolumn>
</we7design:we7layout></we7design:we7zoneplaceholder>
    </div>
</body>
</html>
