using System;
using System.Collections.Generic;
using System.Web.UI.WebControls;
using Thinkment.Data;
using We7.CMS.Common;
using We7.CMS.WebControls;
using We7.CMS.WebControls.Core;
using We7.Framework;

namespace We7.CMS.Web.Widgets
{
    [ControlGroupDescription(Label = "Menu侧栏二级导航", Icon = "Menu侧栏二级导航", Description = "Menu侧栏二级导航", DefaultType = "Sidebar.ChannelNav")]
    [ControlDescription(Desc = "Menu侧栏二级导航")]
    public partial class Sidebar_ChannelNav : ThinkmentDataControl
    {
        private Channel channel;
        private List<Channel> listChildren ;
        private Channel currentChannel;
        /// <summary>
        /// 栏目ID
        /// </summary>
        [Parameter(Title = "栏目", Type = "Channel", Required = true)]
        public string OwnerID = String.Empty;

        /// <summary>
        /// 上边距10像素
        /// </summary>
        [Parameter(Title = "上边距10像素", Type = "Boolean", DefaultValue = "1")]
        public bool MarginTop10;

        /// <summary>
        /// 自定义Css类名称
        /// </summary>
        [Parameter(Title = "自定义Css类名称", Type = "String", DefaultValue = "Sidebar_ChannelNav")]
        public string CssClass;

        [Parameter(Title = "自定义图标样式", Type = "CustomImage", DefaultValue = "")]
        public string Icon;

        /// <summary>
        /// 自定义图标
        /// </summary>
        protected virtual string CustomIcon
        {
            get
            {
                return Icon;
            }
        }
        /// <summary>
        /// 
        /// </summary>
        [Parameter(Title = "自定义边框样式", Type = "ColorSelector", DefaultValue = "")]
        public string BorderColor;

        protected virtual string BoxBorderColor
        {
            get
            {
                return BorderColor;
            }
        }

        protected string SetBoxBorderColor()
        {
            if (!string.IsNullOrEmpty(BoxBorderColor))
            {
                return string.Format("style=\"border-color:{0};\"", BoxBorderColor);
            }
            return string.Empty;
        }
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
        /// 当前栏目信息
        /// </summary>
        protected Channel Channel
        {
            get
            {
                if (channel == null)
                {
                    ChannelHelper helper = HelperFactory.Instance.GetHelper<ChannelHelper>();
                    if (string.IsNullOrEmpty(OwnerID))
                    {
                        OwnerID = helper.GetChannelIDFromURL();
                    }
                    channel = helper.GetChannel(OwnerID, null);
                    if (GetChildren(channel.ID).Count > 0)
                        listChildren = GetChildren(channel.ID);
                    else
                    {
                        if (channel.ParentID != We7Helper.EmptyGUID)
                        {
                            listChildren = GetChildren(channel.ParentID);
                            channel = helper.GetChannel(channel.ParentID, new string[]
                                                                              {
                                                                                  "ID", "Title", "ChannelFullUrl",
                                                                                  "Created",
                                                                                  "SN"
                                                                              });
                        }
                    }

                    return channel;
                }
                //return channel;
                return new Channel();
            }
        }
        private ChannelHelper ChannelHelper
        {
            get { return HelperFactory.GetHelper<ChannelHelper>(); }
        }
        /// <summary>
        /// 当前 栏目
        /// </summary>
        protected Channel CurrentChannel
        {
            get
            {
                if (currentChannel == null)
                {
                    string cid = ChannelHelper.GetChannelIDFromURL();
                    currentChannel = ChannelHelper.GetChannel(cid, new string[]
                                                                       {
                                                                           "ID", "Title", "ChannelFullUrl",
                                                                           "Created",
                                                                           "SN"
                                                                       }) ?? new Channel();

                }
                return currentChannel;
            }
        }

        /// <summary>
        /// 子栏目
        /// </summary>
        protected List<Channel> ChannelChildren
        {
            get
            {
                if (Channel != null)
                    return listChildren;
                return null;
            }
        }

        private List<Channel> GetChildren(string ID)
        {
            Criteria c = new Criteria(CriteriaType.Equals, "ParentID", ID);
            c.Add(CriteriaType.Equals, "State", 1);
            //c.Add(CriteriaType.NotEquals, "ID", Channel.ID);
            return Assistant.List<Channel>(c, new Order[] { new Order("Index") });
        }
        protected string BackgroundIcon()
        {
            if (!string.IsNullOrEmpty(CustomIcon))
            {
                return string.Format("style=\"background:url({0}) no-repeat;\"", CustomIcon);
            }
            return string.Empty;
        }




    }
}