<%@ Control Language="C#" AutoEventWireup="true" Inherits="We7.CMS.WebControls.BaseControl" %> 
 <script type="text/C#" runat="server"> 
   [ControlDescription(Desc = "图标组导航",Author="system")] 
 [ControlGroupDescription(Label = "图标组导航", Icon = "图标组导航", Description = "图标组导航", DefaultType = "IconNavGroup")] 
 string MetaData; 
 </script>

<style>
	.flex-container {
		display: -webkit-box;
		display: -webkit-flex;
		display: flex;
		-webkit-flex-flow: row wrap;
		justify-content: space-between;
		text-align: center;
	}
	.mui-content-padded {
		padding: 10px;
	}
	.mui-content-padded a {
		margin: 3px;
		width: 50px;
		height: 50px;
		display: inline-block;
		text-align: center;/*
		background-color: #fff;
		border: 1px solid #ddd;*/
		border-radius: 25px;
		background-clip: padding-box;
		margin-bottom: 30px;
	}
	.mui-content-padded a .mui-icon {
		margin-top: 12px;
	}
	.mui-content-padded img { padding-top:8px;}
	.mui-content-padded span { width:50px; margin-top:13px; font-size:12px; display: block; line-height: 14px; }
</style>

 <!--htmlWidgetStart--> 
			<div class="mui-content-padded">
				<div class="flex-container">
					<a style="background:#00a0de;" href="/about/gsgk/1.aspx"><img src="/_skins/mobile/images/icon01.png" /><span>公司概况</span></a>
					<a style="background:#6885e4;" href="/about/qywh/43.aspx"><img src="/_skins/mobile/images/icon02.png" /><span>企业文化</span></a>
					<a style="background:#994ee1;" href="/news/jtxw/"><img src="/_skins/mobile/images/icon03.png" /><span>集团要闻</span></a>
					<a style="background:#61dc04;" href="/news/ggtz/"><img src="/_skins/mobile/images/icon04.png" /><span>公告通知</span></a>
					<a style="background:#00d361;" href="/news/mtbd/"><img src="/_skins/mobile/images/icon05.png" /><span>行业要闻</span></a>
					<a style="background:#fab600;" href="/dynamic/"><img src="/_skins/mobile/images/icon06.png" /><span>业务动态</span></a>
					<a style="background:#ff7e35;" href="/notice/"><img src="/_skins/mobile/images/icon07.png" /><span>招标公告</span></a>
					<a style="background:#ff1b5b;" href="/laws/"><img src="/_skins/mobile/images/icon08.png" /><span>政策法规</span></a>
					<a style="background:#00a0de;" href="/dqgz/"><img src="/_skins/mobile/images/icon09.png" /><span>党群工作</span></a>
					<a style="background:#6885e4;" href="/contact/2.aspx"><img src="/_skins/mobile/images/icon10.png" /><span>联系我们</span></a>
				</div>
			</div>
 <!--HtmlWidgetEnd-->