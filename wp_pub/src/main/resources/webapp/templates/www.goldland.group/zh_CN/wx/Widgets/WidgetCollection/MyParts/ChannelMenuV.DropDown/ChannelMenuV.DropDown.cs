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
using We7.Framework;
using We7.CMS.Common;
using System.Collections.Generic;
using Thinkment.Data;
using We7.CMS.WebControls.Core;
using We7.CMS.WebControls;

namespace We7.CMS.Web.Widgets.Nav
{
    [ControlGroupDescription(Label = "Menu竖向下拉菜单", Icon = "Menu竖向下拉菜单", Description = "Menu竖向下拉菜单", DefaultType = "ChannelMenuV.DropDown")]
    [ControlDescription(Desc = "Menu竖向下拉菜单")]
    public partial class ChannelMenuV_DropDown : ThinkmentDataControl
    {
        private List<Channel> firstLevelChannels, secondLevelChannels, topChannels;
        private Channel currentChannel;

        private ChannelHelper ChannelHelper
        {
            get { return HelperFactory.GetHelper<ChannelHelper>(); }
        }

        protected Channel CurrentChannel
        {
            get
            {
                if (currentChannel == null)
                {
                    string cid = ChannelHelper.GetChannelIDFromURL();
                    currentChannel = ChannelHelper.GetChannel(cid, null) ?? new Channel();
                }
                return currentChannel;
            }
        }

        public List<Channel> FirstLevelChannels
        {
            get
            {
                if (firstLevelChannels == null)
                {
                    firstLevelChannels = ChannelHelper.GetChannels(We7Helper.EmptyGUID, true) ?? new List<Channel>();
                }
                return firstLevelChannels;
            }
        }

       

        [Parameter(Title = "标签", Type = "String", DefaultValue = "")]
        public string Tag;
        /// <summary>
        /// 自定义Css类名称
        /// </summary>
        [Parameter(Title = "自定义Css类名称", Type = "String", DefaultValue = "ChannelMenuV_DropDown")]
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
        /// <summary>
        /// 浮动两级菜单用
        /// </summary>
        public List<Channel> TopChannels
        {
            get
            {
                if (topChannels == null)
                {
                    List<Channel> list = ChannelHelper.GetChannels(We7Helper.EmptyGUID, true) ?? new List<Channel>();
                    foreach (Channel c in list)
                    {
                        if (!string.IsNullOrEmpty(Tag) && c.Tags.Contains(Tag))
                        {
                            if (topChannels == null)
                                topChannels = new List<Channel>();
                            topChannels.Add(c);
                        }
                        else
                        {
                            if (topChannels == null)
                                topChannels = new List<Channel>();
                            if(string.IsNullOrEmpty(Tag))
                                topChannels.Add(c);
                        }
                    }
                    for (int i = 0; i < topChannels.Count; i++)
                    {
                        topChannels[i].SubChannels = ChannelHelper.GetChannels(topChannels[i].ID) ?? new List<Channel>();
                        if (topChannels[i].SubChannels.Count > 0)
                            topChannels[i].HaveSon = true;
                    }
                }
                return topChannels;
            }
        }
    }
}
