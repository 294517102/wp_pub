<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ Register Src="/Widgets/WidgetCollection/MyParts/TopInfo/TopInfo.ascx" TagName="TopInfo" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/SiteHeader/SiteHeader.ascx" TagName="SiteHeader" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/Search2015/Search2015.ascx" TagName="Search2015" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/ChannelMenuV.DropDown/ChannelMenuV.DropDown.ascx" TagName="ChannelMenuV_DropDown" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/ChannelMenu.CurrLocation/ChannelMenu.CurrentLocation.ascx" TagName="ChannelMenu_CurrentLocation" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/Sidebar.ChannelNav/Sidebar.ChannelNav.ascx" TagName="Sidebar_ChannelNav" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/商城下载类/Vote.Default/Vote.Default.ascx" TagName="Vote_Default" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/内容模型类/Feedback_FeedbackEdit/Feedback_FeedbackEdit.ascx" TagName="Feedback_FeedbackEdit" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/内容模型类/Feedback_FeedbackList/Feedback_FeedbackList.ascx" TagName="Feedback_FeedbackList" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/内容模型类/Feedback_FeedbackQueryList/Feedback_FeedbackQueryList.ascx" TagName="Feedback_FeedbackQueryList" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/MyParts/Footer.Default/Footer.Default.ascx" TagName="Footer_Default" TagPrefix="wew" %>

<html>
<head runat="server">
    <title></title>
    <link href="/Admin/VisualTemplate/Style/VisualDesign.LayoutsBasics.css" rel="stylesheet" type="text/css">
<link href="/Widgets/Themes/theme/Style.css" type="text/css" rel="stylesheet" class="themestyle" id="themestyle"><script src="/Widgets/Scripts/jquery.pack.js" type="text/javascript" class="jquerypack" id="jquerypack"></script><script src="/Widgets/Scripts/jquery.peex.js" type="text/javascript" class="jquerypeex" id="jquerypeex"></script><script src="/Widgets/Scripts/Plugins/Common.js" type="text/javascript" class="commonPlugin" id="commonPlugin"></script><link href="/_skins/School/Style/UxStyle.css" type="text/css" rel="stylesheet"></head>
<body style="background-color:rgb(241, 251, 251);background-position:center top;background-repeat:no-repeat; background-attachment:scroll;background-image:url('/_skins/School/images/all_bg.jpg');">
    <div id="pagecontainer" style="width:100%px;margin:0 auto">
        <we7design:we7zoneplaceholder id="bodyplaceholder" runat="server"><we7design:we7layout runat="server" id="we7layout_147841452441581">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147841452441581_cloumn1">
 <wew:TopInfo control="TopInfo" filename="/Widgets/WidgetCollection/MyParts/TopInfo/TopInfo.ascx" id="TopInfo_147841453894714" cssclass="TopInfo" runat="server"></wew:TopInfo><we7design:we7layout runat="server" id="we7layout_14784146360922">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_14784146360922_cloumn1" style="" cssclass="layout">
 <we7design:we7layout runat="server" id="we7layout_14788750223181">
 <we7design:we7layoutcolumn float="left" width="60" widthunit="%" runat="server" id="we7layout_14788750223181_cloumn1" style="" cssclass="">
 <wew:SiteHeader id="SiteHeader_147887505175442" cssclass="SiteHeader" filename="/Widgets/WidgetCollection/MyParts/SiteHeader/SiteHeader.ascx" runat="server"></wew:SiteHeader></we7design:we7layoutcolumn>
  <we7design:we7layoutcolumn float="left" width="40" widthunit="%" runat="server" id="we7layout_14788750223181_cloumn2" style="" cssclass="">
 <wew:Search2015 control="Search2015" filename="/Widgets/WidgetCollection/MyParts/Search2015/Search2015.ascx" id="Search2015_14788750553745" cssclass="Search2015" runat="server"></wew:Search2015></we7design:we7layoutcolumn>
</we7design:we7layout></we7design:we7layoutcolumn>
</we7design:we7layout></we7design:we7layoutcolumn>
</we7design:we7layout><we7design:we7layout runat="server" id="we7layout_147840144290838">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147840144290838_cloumn1">
 <wew:ChannelMenuV_DropDown control="ChannelMenuV_DropDown" filename="/Widgets/WidgetCollection/MyParts/ChannelMenuV.DropDown/ChannelMenuV.DropDown.ascx" id="ChannelMenuV_DropDown_147841457767914" cssclass="ChannelMenuV_DropDown" runat="server"></wew:ChannelMenuV_DropDown></we7design:we7layoutcolumn>
</we7design:we7layout><we7design:we7layout runat="server" id="we7layout_147840155967047">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147840155967047_cloumn1" style="" cssclass="layout_content">
 <we7design:we7layout runat="server" id="we7layout_147887510487131">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147887510487131_cloumn1">
 <wew:ChannelMenu_CurrentLocation control="ChannelMenu_CurrentLocation" filename="/Widgets/WidgetCollection/MyParts/ChannelMenu.CurrLocation/ChannelMenu.CurrentLocation.ascx" id="ChannelMenu_CurrentLocation_147887511806155" cssclass="ChannelMenu_CurrentLocation" runat="server"></wew:ChannelMenu_CurrentLocation></we7design:we7layoutcolumn>
</we7design:we7layout><we7design:we7layout runat="server" id="we7layout_147887510222567">
 <we7design:we7layoutcolumn float="left" width="250" widthunit="px" runat="server" id="we7layout_147887510222567_cloumn1" style="" cssclass="">
 <we7design:we7layout runat="server" id="we7layout_147895566376948">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147895566376948_cloumn1">
 <wew:sidebar_channelnav control="Sidebar_ChannelNav" filename="/Widgets/WidgetCollection/MyParts/Sidebar.ChannelNav/Sidebar.ChannelNav.ascx" id="Sidebar_ChannelNav_147895567223338" cssclass="Sidebar_ChannelNav" runat="server"></wew:sidebar_channelnav><we7design:we7layout runat="server" id="we7layout_147895566160232">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147895566160232_cloumn1">
 <wew:Vote_Default id="Vote_Default_147896021859737" icon="/_skins/School/images/header_tit02.png" bordercolor="" cssclass="Vote_Default" voteid="{64732aab-099c-4b31-afc7-e18db8b2a05f}" filename="/Widgets/WidgetCollection/商城下载类/Vote.Default/Vote.Default.ascx" runat="server"></wew:Vote_Default><we7design:we7layout runat="server" id="we7layout_147895565995532">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147895565995532_cloumn1">
 </we7design:we7layoutcolumn>
</we7design:we7layout></we7design:we7layoutcolumn>
</we7design:we7layout></we7design:we7layoutcolumn>
</we7design:we7layout></we7design:we7layoutcolumn>
 <we7design:we7layoutcolumn float="left" width="50" widthunit="px" runat="server" id="we7layout_147887510222567_cloumn2" style="" cssclass="">
 </we7design:we7layoutcolumn>
  <we7design:we7layoutcolumn float="left" width="760" widthunit="px" runat="server" id="we7layout_147887510222567_cloumn3" style="" cssclass="">
 <we7design:we7layout runat="server" id="we7layout_147887517194055">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147887517194055_cloumn1">
 <wew:feedback_feedbackedit id="Feedback_FeedbackEdit_14788752705805" isshowsn="False" successprompt="你的问题已经提交成功，请留意查看回复！" titlerepeatprompt="已有该问题！" isvalidatetitle="False" redirecturl="/jxgt/zxfw/" ischoicetype="True" encryption="True" accepttype="{934762e8-9428-43dd-8bd8-cecc68ec8d7d}" audittype="{934762e8-9428-43dd-8bd8-cecc68ec8d7d}" filename="/Widgets/WidgetCollection/内容模型类/Feedback_FeedbackEdit/Feedback_FeedbackEdit.ascx" cssclass="Feedback_FeedbackEdit" runat="server"></wew:feedback_feedbackedit><we7design:we7layout runat="server" id="we7layout_14788751702725">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_14788751702725_cloumn1" style="margin-top:50px" cssclass="">
 <wew:feedback_feedbacklist id="Feedback_FeedbackList_147895593149296" titlelength="50" pager-vmtemplatefilename="/Widgets/WidgetCollection/文章列表类/PagedArticleList.Default/vm/pager.vm" pager-pagerspanclass="pagecss" ownerid="{a1ddff60-6124-447e-8e30-22c033796c10}" isshow="False" contentlength="80" pager-pagerdivclass="page_css page_line" cssclass="Feedback_FeedbackList" criteriainfostr="%7B%22TableName%22%3A%22AdviceInfo%22%2C%22TableDesc%22%3A%22%E5%8F%8D%E9%A6%88%E4%BF%A1%E6%81%AF%22%2C%22CriteriaJsonObjects%22%3A%5B%5D%7D" orderinfo="%7B%22TableName%22%3A%22AdviceInfo%22%2C%22TableDesc%22%3A%22%E5%8F%8D%E9%A6%88%E4%BF%A1%E6%81%AF%22%2C%22Orders%22%3A%5B%7B%22Mode%22%3A1%2C%22AliasName%22%3A%22%E5%88%9B%E5%BB%BA%E6%97%B6%E9%97%B4%22%2C%22Name%22%3A%22Created%22%2C%22Adorn%22%3A0%2C%22Start%22%3A0%2C%22Length%22%3A0%7D%5D%7D" pager-requestpageindex="pi" pager-pagesize="10" advicetype="{934762e8-9428-43dd-8bd8-cecc68ec8d7d}" filename="/Widgets/WidgetCollection/内容模型类/Feedback_FeedbackList/Feedback_FeedbackList.ascx" runat="server"></wew:feedback_feedbacklist></we7design:we7layoutcolumn>
</we7design:we7layout></we7design:we7layoutcolumn>
</we7design:we7layout><we7design:we7layout runat="server" id="we7layout_147888176027878">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147888176027878_cloumn1" style="margin-top:50px" cssclass="">
 <wew:feedback_feedbackquerylist id="Feedback_FeedbackQueryList_147888176666776" pager-vmtemplatefilename="/Widgets/WidgetCollection/文章列表类/PagedArticleList.Default/vm/pager.vm" pager-pagerspanclass="pagecss" pager-pagerdivclass="page_css page_line" titlelength="50" ownerid="{a1ddff60-6124-447e-8e30-22c033796c10}" isshow="True" contentlength="80" cssclass="Feedback_FeedbackQueryList" criteriainfostr="%7B%22TableName%22%3A%22AdviceInfo%22%2C%22TableDesc%22%3A%22%E5%8F%8D%E9%A6%88%E4%BF%A1%E6%81%AF%22%2C%22CriteriaJsonObjects%22%3A%5B%5D%7D" orderinfo="%7B%22TableName%22%3A%22AdviceInfo%22%2C%22TableDesc%22%3A%22%E5%8F%8D%E9%A6%88%E4%BF%A1%E6%81%AF%22%2C%22Orders%22%3A%5B%7B%22Mode%22%3A1%2C%22AliasName%22%3A%22%E5%88%9B%E5%BB%BA%E6%97%B6%E9%97%B4%22%2C%22Name%22%3A%22Created%22%2C%22Adorn%22%3A0%2C%22Start%22%3A0%2C%22Length%22%3A0%7D%5D%7D" securityquery="False" pager-requestpageindex="pi" pager-pagesize="20" advicetype="{934762e8-9428-43dd-8bd8-cecc68ec8d7d}" filename="/Widgets/WidgetCollection/内容模型类/Feedback_FeedbackQueryList/Feedback_FeedbackQueryList.ascx" runat="server"></wew:feedback_feedbackquerylist></we7design:we7layoutcolumn>
</we7design:we7layout></we7design:we7layoutcolumn>
</we7design:we7layout></we7design:we7layoutcolumn>
</we7design:we7layout><we7design:we7layout runat="server" id="we7layout_147841456252715">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_147841456252715_cloumn1">
 <wew:Footer_Default control="Footer_Default" filename="/Widgets/WidgetCollection/MyParts/Footer.Default/Footer.Default.ascx" id="Footer_Default_147841459049040" cssclass="Footer_Default" runat="server"></wew:Footer_Default></we7design:we7layoutcolumn>
</we7design:we7layout></we7design:we7zoneplaceholder>
    </div>
</body>
</html>