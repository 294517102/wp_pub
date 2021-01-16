using System;
using System.Collections;
using System.Configuration;
using System.Data;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using We7.CMS.Common;
using System.Collections.Generic;
using We7.Framework;
using We7.CMS.WebControls;
using Thinkment.Data;
using We7.CMS.WebControls.Core;
using System.Text;

namespace We7.CMS.Web.Widgets.Pic
{
    /// <summary>
    /// 文章列表数据提供者
    /// </summary>
    [ControlGroupDescription(Label = "itc焦点图文", Icon = "itc焦点图文", Description = "itc焦点图文", DefaultType = "PicFocusSlide")]
    [ControlDescription(Name = "itc焦点图文", Desc = "itc焦点图文")]
    public partial class PicFocusSlide : ThinkmentDataControl
    {

        private List<Article> articles;
        private Channel channel;
        [Parameter(Title = "缩略图标签", Type = "KeyValueSelector", Data = "thumbnail", DefaultValue = "flash")]
        public string ThumbnailTag = "flash";

        [Parameter(Title = "查询标签", Type = "String", DefaultValue = "")]
        public string Tag;

        [Parameter(Title = "宽度", Type = "Number", DefaultValue = "450")]
        public int FrameWidth = 450;

        [Parameter(Title = "高度", Type = "Number", DefaultValue = "230")]
        public int FrameHeight = 230;

        [Parameter(Title = "栏目", Type = "Channel", Required = true)]
        public string OwnerID = String.Empty;

        [Parameter(Title = "控件每页记录", Type = "Number", DefaultValue = "4")]
        public int PageSize = 4;

        [Parameter(Title = "包含子栏目", Type = "Boolean", DefaultValue = "1")]
        public bool IncludeChildren;

        [Parameter(Title = "是否显示置顶", Type = "Boolean", DefaultValue = "0", Required = true)]
        public bool IsShow;
        ///<summary>
        /// 标题长度
        /// </summary>
        [Parameter(Title = "标题长度", Type = "Number", DefaultValue = "15")]
        public int TitleLength = 50;
        /// <summary>
        /// 自定义Css类名称
        /// </summary>
        [Parameter(Title = "自定义Css类名称", Type = "String", DefaultValue = "PicFocusSlide")]
        public string CssClass;

        /// <summary>
        /// 自定义的css样式
        /// </summary>
        protected virtual string Css
        {
            get
            {
                return CssClass;
            }
        }

        protected override void OnInitData()
        {

        }
        public string FileUrl
        {
            get { return AppRelativeTemplateSourceDirectory.Remove(0, 1).Replace("//", "/"); }
        }

        /// <summary>
        /// 文章列表
        /// </summary>
        protected List<Article> Articles
        {
            get
            {
                if (articles == null)
                {
                    Criteria c = new Criteria(CriteriaType.None);

                    c.Add(CriteriaType.Equals, "ModelName", "System.Article");

                    if (!String.IsNullOrEmpty(OwnerID))
                    {
                        if (IncludeChildren)
                        {
                            c.Add(CriteriaType.Like, "ChannelFullUrl", Channel.FullUrl + "%");
                        }
                        else
                        {
                            c.Add(CriteriaType.Equals, "OwnerID", OwnerID);
                        }
                    }
                    c.Add(CriteriaType.Equals, "State", 1);
                    c.Add(CriteriaType.Equals, "IsImage", 1);

                    if (!String.IsNullOrEmpty(Tag))
                    {
                        c.Add(CriteriaType.Like, "Tags", "%" + Tag + "%");
                    }

                    Order[] os = IsShow ? new Order[] { new Order("IsShow", OrderMode.Desc), new Order("Updated", OrderMode.Desc) } : new Order[] { new Order("Updated", OrderMode.Desc) };
                    articles = Assistant.List<Article>(c, os, 0, PageSize);

                }
                return articles;
            }
            set { articles = value; }
        }
        /// <summary>
        /// 当前栏目信息
        /// </summary>
        protected Channel Channel
        {
            get
            {
                if (channel == null)
                {
                    ChannelHelper helper = HelperFactory.GetHelper<ChannelHelper>();
                    channel = helper.GetChannel(OwnerID, null) ?? new Channel();
                }
                return channel;
            }
        }
    }
}
