<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ Register Src="/Widgets/WidgetCollection/MyParts/TopInfo/TopInfo.ascx" TagName="TopInfo" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/ChannelMenuV.DropDown/ChannelMenuV.DropDown.ascx" TagName="ChannelMenuV_DropDown" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/Search2015/Search2015.ascx" TagName="Search2015" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/PicFocusSlide/PicFocusSlide.ascx" TagName="PicFocusSlide" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/ArticleTopic/ArticleTopic.ascx" TagName="ArticleTopic" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/ArticleList.YMD/ArticleList.YMD.ascx" TagName="ArticleList_YMD" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/PicGroupScrool/PicGroupScrool.ascx" TagName="PicGroupScrool" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/Footer.Default/Footer.Default.ascx" TagName="Footer_Default" TagPrefix="wew" %>

<html>
<head runat="server">
    <title></title>
    <link href="/Admin/VisualTemplate/Style/VisualDesign.LayoutsBasics.css" rel="stylesheet" type="text/css">
<link href="/Widgets/Themes/theme/Style.css" type="text/css" rel="stylesheet" class="themestyle" id="themestyle"><script src="/Widgets/Scripts/jquery.pack.js" type="text/javascript" class="jquerypack" id="jquerypack"></script><script src="/Widgets/Scripts/jquery.peex.js" type="text/javascript" class="jquerypeex" id="jquerypeex"></script><script src="/Widgets/Scripts/Plugins/Common.js" type="text/javascript" class="commonPlugin" id="commonPlugin"></script><link href="/_skins/School/Style/UxStyle.css" type="text/css" rel="stylesheet"></head>
<body style="background-color:#f5f0db;background-position:center top;background-repeat:no-repeat; background-attachment:scroll;background-image:url('/Admin/VisualTemplate/PageEditor.aspx?file=site.welcome.ascx&folder=School&virtualdata=virtualdata&state=design1');">
    <div id="pagecontainer" style="width:100%px;margin:0 auto">
        <we7design:we7zoneplaceholder id="bodyplaceholder" runat="server"><we7design:we7layout runat="server" id="we7layout_149586319916971">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_149586319916971_cloumn1">
 <wew:TopInfo id="TopInfo_149586338619114" cssclass="TopInfo" filename="/Widgets/WidgetCollection/MyParts/TopInfo/TopInfo.ascx" runat="server"></wew:TopInfo><we7design:we7layout runat="server" id="we7layout_149614046361189">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_149614046361189_cloumn1" style="overflow:hidden;min-height:400px" cssclass="">
 <wew:ChannelMenuV_DropDown control="ChannelMenuV_DropDown" filename="/Widgets/WidgetCollection/MyParts/ChannelMenuV.DropDown/ChannelMenuV.DropDown.ascx" id="ChannelMenuV_DropDown_149614072680161" cssclass="ChannelMenuV_DropDown" runat="server"></wew:ChannelMenuV_DropDown></we7design:we7layoutcolumn>
</we7design:we7layout><we7design:we7layout runat="server" id="we7layout_149587911391516">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_149587911391516_cloumn1">
 <wew:Search2015 id="Search2015_149587913149533" iconwap="" iconhomepage="" iconcollection="" iconrss="" cssclass="Search2015" searchpage="" iconlogin="" filename="/Widgets/WidgetCollection/MyParts/Search2015/Search2015.ascx" runat="server"></wew:Search2015></we7design:we7layoutcolumn>
</we7design:we7layout></we7design:we7layoutcolumn>
</we7design:we7layout><we7design:we7layout runat="server" id="we7layout_14781860555073">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_14781860555073_cloumn1" style="" cssclass="layout_content">
 <we7design:we7layout runat="server" id="we7layout_149588008234497">
 <we7design:we7layoutcolumn float="left" width="580" widthunit="px" runat="server" id="we7layout_149588008234497_cloumn1" style="" cssclass="">
 <wew:PicFocusSlide id="PicFocusSlide_149588640187441" pagesize="3" ownerid="" includechildren="True" titlelength="60" isshow="False" thumbnailtag="HomeScroll" cssclass="PicScrollAuto" tag="首页翻转图片" frameheight="230" framewidth="450" filename="/Widgets/WidgetCollection/MyParts/PicFocusSlide/PicFocusSlide.ascx" runat="server"></wew:PicFocusSlide></we7design:we7layoutcolumn>
 <we7design:we7layoutcolumn float="left" width="40" widthunit="px" runat="server" id="we7layout_149588008234497_cloumn2" style="" cssclass="">
 </we7design:we7layoutcolumn>
  <we7design:we7layoutcolumn float="left" width="400" widthunit="px" runat="server" id="we7layout_149588008234497_cloumn3" style="" cssclass="">
 <we7design:we7layout runat="server" id="we7layout_14958804738862">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_14958804738862_cloumn1">
 <wew:ArticleTopic id="ArticleTopic_149588046572768" icon="/_skins/School/images/header_tit01.png" marginleft10="False" margintop10="False" orderinfo="%7B%22TableName%22%3A%22Article%22%2C%22TableDesc%22%3A%22%E6%96%87%E7%AB%A0%22%2C%22Orders%22%3A%5B%7B%22Mode%22%3A1%2C%22AliasName%22%3A%22%E6%98%AF%E5%90%A6%E7%BD%AE%E9%A1%B6%22%2C%22Name%22%3A%22IsShow%22%2C%22Adorn%22%3A0%2C%22Start%22%3A0%2C%22Length%22%3A0%7D%2C%7B%22Mode%22%3A1%2C%22AliasName%22%3A%22%E6%9B%B4%E6%96%B0%E6%97%B6%E9%97%B4%22%2C%22Name%22%3A%22Updated%22%2C%22Adorn%22%3A0%2C%22Start%22%3A0%2C%22Length%22%3A0%7D%5D%7D" bordercolor="" tags="头条" includechildren="True" ownerid="{21878620-7c85-4da0-be2e-984276537bb2}" cssclass="ArticleTopic" criteriainfo="%7B%22TableName%22%3A%22Article%22%2C%22TableDesc%22%3A%22%E6%96%87%E7%AB%A0%22%2C%22CriteriaJsonObjects%22%3A%5B%5D%7D" dateformat="MM-dd" titlelength="50" pagesize="1" filename="/Widgets/WidgetCollection/MyParts/ArticleTopic/ArticleTopic.ascx" runat="server"></wew:ArticleTopic></we7design:we7layoutcolumn>
</we7design:we7layout><we7design:we7layout runat="server" id="we7layout_149588047079513">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_149588047079513_cloumn1">
 <wew:ArticleList_YMD id="ArticleList_YMD_149588050025636" ownerid="" pagesize="6" isshow="False" tags="" titlelength="48" dateformat="MM/dd" marginleft10="True" icon="" cssclass="NoHeaderList" bordercolor="" margintop10="False" includechildren="True" filename="/Widgets/WidgetCollection/MyParts/ArticleList.YMD/ArticleList.YMD.ascx" runat="server"></wew:ArticleList_YMD></we7design:we7layoutcolumn>
</we7design:we7layout></we7design:we7layoutcolumn>
</we7design:we7layout><we7design:we7layout runat="server" id="we7layout_149587579596313">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_149587579596313_cloumn1">
 </we7design:we7layoutcolumn>
</we7design:we7layout><we7design:we7layout runat="server" id="we7layout_14782478310698">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_14782478310698_cloumn1">
 <wew:PicGroupScrool id="PicGroupScrool_147883413401338" ownerid="{5a126aa3-1c77-484f-ae89-cd14c2ef5ffa}" isshow="False" thumbnailtag="Company" pagesize="10" titlelength="50" icon="/_skins/School/images/header_tit01.png" cssclass="GroupList" includechildren="True" bordercolor="" tags="" filename="/Widgets/WidgetCollection/MyParts/PicGroupScrool/PicGroupScrool.ascx" runat="server"></wew:PicGroupScrool></we7design:we7layoutcolumn>
</we7design:we7layout></we7design:we7layoutcolumn>
</we7design:we7layout><we7design:we7layout runat="server" id="we7layout_14781860467082">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_14781860467082_cloumn1">
 <wew:footer_default id="Footer_Default_147818605091988" cssclass="Footer_Default" filename="/Widgets/WidgetCollection/MyParts/Footer.Default/Footer.Default.ascx" runat="server"></wew:footer_default></we7design:we7layoutcolumn>
</we7design:we7layout></we7design:we7zoneplaceholder>
    </div>
</body>
</html>