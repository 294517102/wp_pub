<%@ Control Language="C#" AutoEventWireup="true" Inherits="We7.CMS.WebControls.BaseControl" %> 
 <script type="text/C#" runat="server"> 
   [ControlDescription(Desc = "Mb头部固定导航",Author="system")] 
 [ControlGroupDescription(Label = "Mb头部固定导航", Icon = "Mb头部固定导航", Description = "Mb头部固定导航", DefaultType = "MbHeader")] 
 string MetaData; 
 </script> 
 <!--htmlWidgetStart--> 
<style>		
	.mui-plus .plus{display: inline;}
	.plus{display: none;}
	#topPopover {position: fixed;top:20px;right: 6px;}
	#topPopover .mui-popover-arrow {left: auto;right: 6px;}
</style>

		<header class="mui-bar mui-bar-nav" style="background: url(/_skins/mobile/Images/mobile_bg.jpg)  no-repeat center center; ">
			<a class="mui-pull-left" style="width:60%; padding-top:2px;"><img src="/_skins/mobile/Images/mobile_logo.png" alt="" /></a>
			<a id="menu" class="mui-action-menu mui-icon mui-icon-bars mui-pull-right" href="#topPopover" style="color:#000; "></a>
		</header>

		<!-- 弹出菜单	 -->
		<div id="topPopover" class="mui-popover">
			<div class="mui-popover-arrow"></div>
			<div class="mui-scroll-wrapper">
				<div class="mui-scroll">
					<ul class="mui-table-view">
						<li class="mui-table-view-cell"><a href="/">微官网首页</a>
						</li>
						<li class="mui-table-view-cell"><a href="/about/gsgk/1.aspx">公司概况</a>
						</li>
						<li class="mui-table-view-cell"><a href="/about/zzjg/3.aspx">组织机构</a>
						</li>
						<li class="mui-table-view-cell"><a href="/about/qywh/43.aspx">企业文化</a>
						</li>
						<li class="mui-table-view-cell"><a href="/news/jtxw/">集团新闻</a>
						</li>
						<li class="mui-table-view-cell"><a href="/news/ggtz/">公告通知</a>
						</li>
						<li class="mui-table-view-cell"><a href="/news/zgsdt/">子公司动态</a>
						</li>
						<li class="mui-table-view-cell"><a href="/notice/">招标公告</a>
						</li>
						<li class="mui-table-view-cell"><a href="/laws/">政策法规</a>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<script>
			mui.init();
			
			mui.plusReady(function () {
			});
			
			mui('.mui-scroll-wrapper').scroll();
			mui('body').on('shown', '.mui-popover', function(e) {
				//console.log('shown', e.detail.id);//detail为当前popover元素
			});
			mui('body').on('hidden', '.mui-popover', function(e) {
				//console.log('hidden', e.detail.id);//detail为当前popover元素
			});
		</script>
		<!-- 弹出菜单	 -->

 <!--HtmlWidgetEnd-->