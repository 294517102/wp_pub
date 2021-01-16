<%@ Control Language="C#" AutoEventWireup="true" CodeFile="Footer.Default.cs" Inherits="We7.CMS.Web.Widgets.Footer_Default" %>
<%@ Import Namespace="We7.Framework.Config" %>
<script type="text/C#" runat="server">
    [ControlDescription(Desc = "Footer底部版权区")]
    string MetaData;
</script>
<div class="<%=CssClass %>">
    <div class="footer_copyright">
        <div class="footer_logo"></div>
        <div class="footer_txt">
            <p><%=GeneralConfigs.GetConfig().Copyright%></p>
            <p><%=GeneralConfigs.GetConfig().Links%></p>
        </div>
        <div class="footer_jubao">
            <a href="http://report.12377.cn:13225/toreportinputNormal_anis.do" target="_blank"><img src="/_data/uploads/jubao.png" title="互联网有害信息举报专区" alt="互联网有害信息举报专区"></a>
        </div>
        <div class="footer_eweima">
            <p>扫描微信公众号</p>
        </div>
    </div>
    <div class="footer_bottom">备案号：<%=GeneralConfigs.GetConfig().IcpInfo%>　　</div>
</div>