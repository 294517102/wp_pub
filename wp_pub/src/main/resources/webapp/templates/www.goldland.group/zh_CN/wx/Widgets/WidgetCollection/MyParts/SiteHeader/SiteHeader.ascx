<%@ Control Language="C#" AutoEventWireup="true" Inherits="We7.CMS.WebControls.BaseControl" %> 
 <script type="text/C#" runat="server"> 
   [ControlDescription(Desc = "LOGO标识",Author="wonders")] 
 [ControlGroupDescription(Label = "LOGO标识", Icon = "LOGO标识", Description = "LOGO标识", DefaultType = "SiteHeader")] 
 string MetaData; 
 </script> 
 <!--htmlWidgetStart--> 
 
<div class="<%= CssClass %>" >
    <div class="logo"></div>
</div>
     
 <!--HtmlWidgetEnd-->