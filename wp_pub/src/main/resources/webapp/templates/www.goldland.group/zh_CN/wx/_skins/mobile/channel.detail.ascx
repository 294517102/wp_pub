<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ Register Src="/Widgets/WidgetCollection/Mobile/MbHeader/MbHeader.ascx" TagName="MbHeader" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/Mobile/ArticleView.Default/ArticleView.Default.ascx" TagName="ArticleView_Default" TagPrefix="wew" %>

<%@ Register Src="/Widgets/WidgetCollection/Mobile/Mobile_footer1/Mobile_footer.ascx" TagName="Mobile_footer" TagPrefix="wew" %>

<html>
<head runat="server">
    <meta charset="utf-8">
    <meta content="width=device-width, minimum-scale=1.0, maximum-scale=2.0" name="viewport">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <title></title>
    <!--标准mui.css-->
    <link rel="stylesheet" href="/_skins/mobile/css/mui.css">
    <link rel="stylesheet" href="/_skins/mobile/css/app.css">
    <script src="/_skins/mobile/js/mui.js"></script>

    <link href="/Admin/VisualTemplate/Style/VisualDesign.LayoutsBasics.css" rel="stylesheet" type="text/css">
<link href="/Widgets/Themes/theme/Style.css" type="text/css" rel="stylesheet" class="themestyle" id="themestyle"><script src="/Widgets/Scripts/jquery.pack.js" type="text/javascript" class="jquerypack" id="jquerypack"></script><script src="/Widgets/Scripts/jquery.peex.js" type="text/javascript" class="jquerypeex" id="jquerypeex"></script><script src="/Widgets/Scripts/Plugins/Common.js" type="text/javascript" class="commonPlugin" id="commonPlugin"></script><link href="/_skins/mobile/Style/UxStyle.css" type="text/css" rel="stylesheet"></head>
<body>
    <div id="pagecontainer">
        <we7design:we7zoneplaceholder id="bodyplaceholder" runat="server"><we7design:we7layout runat="server" id="we7layout_149077625502182">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_149077625502182_cloumn1" style="margin-bottom:50px" cssclass="">
 <wew:MbHeader control="MbHeader" filename="/Widgets/WidgetCollection/Mobile/MbHeader/MbHeader.ascx" id="MbHeader_149918378876719" cssclass="MbHeader" runat="server"></wew:MbHeader></we7design:we7layoutcolumn>
</we7design:we7layout><we7design:we7layout runat="server" id="we7layout_148246155495444">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_148246155495444_cloumn1">
 </we7design:we7layoutcolumn>
</we7design:we7layout><we7design:we7layout runat="server" id="we7layout_148246155084729">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_148246155084729_cloumn1" style="" cssclass="mobilebox">
 <wew:ArticleView_Default id="ArticleView_Default_148247441826942" dateformat="yyyy-MM-dd" pagesize="6" titlelength="40" isshowatta="True" cssclass="ArticleViewAll" bordercolor="" tags="" filename="/Widgets/WidgetCollection/Mobile/ArticleView.Default/ArticleView.Default.ascx" runat="server"></wew:ArticleView_Default></we7design:we7layoutcolumn>
</we7design:we7layout><we7design:we7layout runat="server" id="we7layout_149077615831275">
 <we7design:we7layoutcolumn float="none" widthunit="%" width="100" runat="server" id="we7layout_149077615831275_cloumn1">
 <wew:Mobile_footer control="Mobile_footer" filename="/Widgets/WidgetCollection/Mobile/Mobile_footer1/Mobile_footer.ascx" id="Mobile_footer_149077616937817" cssclass="Mobile_footer" runat="server"></wew:Mobile_footer></we7design:we7layoutcolumn>
</we7design:we7layout></we7design:we7zoneplaceholder>
    </div>
</body>
</html>