using System.Collections.Generic;
using System.Web.UI.WebControls;
using Thinkment.Data;
using We7.CMS.Common;
using We7.CMS.WebControls;
using We7.CMS.WebControls.Core;
using We7.Framework;

namespace We7.CMS.Web.Widgets
{
    /// <summary>
    /// 文章列表数据提供者
    /// </summary>
    [ControlGroupDescription(Label = "Mobile文章详情", Icon = "Mobile文章详情", Description = "Mobile文章详情", DefaultType = "ArticleView.Default")]
    [ControlDescription(Name = "Mobile文章详情", Desc = "Mobile文章详情")]
    public partial class ArticleView_Default : ThinkmentDataControl
    {
        /// <summary>
        /// 文章业务助手
        /// </summary>
        protected ArticleHelper ArticleHelper
        {
            get { return HelperFactory.GetHelper<ArticleHelper>(); }
        }

        /// <summary>
        /// 栏目业务助手
        /// </summary>
        protected ChannelHelper ChannelHelper
        {
            get { return HelperFactory.GetHelper<ChannelHelper>(); }
        }

        /// <summary>
        /// 当前文章
        /// </summary>
        private Article thisArticle;
        /// <summary>
        /// 相关文章
        /// </summary>
        private List<Article> relevantArticles;

        /// <summary>
        /// 上一篇
        /// </summary>
        private Article previousArticle;

        /// <summary>
        ///下一篇 
        /// </summary>
        private Article nextArticle;

        /// <summary>
        /// 当前文章的附件
        /// </summary>
        private List<Attachment> attachments = new List<Attachment>();

        /// <summary>
        /// 相关文章条数
        /// </summary>
        [Parameter(Title = "相关文章条数", Type = "Number", DefaultValue = "6")]
        public int PageSize = 6;

        /// <summary>
        /// 标题长度
        /// </summary>
        [Parameter(Title = "标题长度", Type = "Number", DefaultValue = "40")]
        public int TitleLength = 40;

        /// <summary>
        /// 日期格式
        /// </summary>
        [Parameter(Title = "日期格式", Type = "String", DefaultValue = "yyyy-MM-dd")]
        public string DateFormat = "yyyy-MM-dd";

        /// <summary>
        /// 
        /// </summary>
        [Parameter(Title = "自定义边框样式", Type = "ColorSelector", DefaultValue = "")]
        public string BorderColor;

        /// <summary>
        /// 
        /// </summary>
        [Parameter(Title = "Tags标签", Type = "Tags", DefaultValue = "")]
        public string Tags;

        /// <summary>
        /// 是否显示附件
        /// </summary>
        [Parameter(Title = "是否显示附件", Type = "Boolean", DefaultValue = "1")]
        public bool IsShowAtta;

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
        /// 自定义Css类名称
        /// </summary>
        [Parameter(Title = "自定义Css类名称", Type = "String", DefaultValue = "ArticleView_Default")]
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
        /// 文章ID
        /// </summary>
        public string ArticleID
        {
            get
            {
                return ArticleHelper.GetArticleIDFromURL();
            }
        }

        /// <summary>
        /// 获得当前栏目下的第一篇文章
        /// </summary>
        /// <returns></returns>
        protected Article GetThisArticle()
        {
            string id = ChannelHelper.GetChannelIDFromURL();
            Channel ch = ChannelHelper.GetChannel(id, null);

            Criteria c = new Criteria(CriteriaType.Equals, "ChannelFullUrl", ch.FullUrl);
            c.Add(CriteriaType.Equals, "State", 1);
            Order[] os = new Order[] { new Order("Updated", OrderMode.Desc) };
            List<Article> aList = Assistant.List<Article>(c, os, 0, 1, new string[]
                                                                           {
                                                                               "ID", "Title", "ChannelFullUrl","Author","Source",
                                                                               "Created","Clicks",
                                                                               "SN","Content","OwnerID","Updated","State", "Thumbnail"
                                                                           });
            if (aList != null && aList.Count > 0)
            {
                return aList[0];
            }
            else
            {
                return new Article();
            }
        }

        /// <summary>
        /// 当前文章
        /// </summary>
        protected Article ThisArticle
        {
            get
            {
                if (thisArticle == null)
                {
                    if (!We7Helper.IsEmptyID(ArticleID))
                    {
                        Criteria c = new Criteria(CriteriaType.Equals, "ID", ArticleID);
                        c.Add(CriteriaType.Equals, "State", 1);
                        Order[] os = new Order[] { new Order("Updated", OrderMode.Desc) };
                        List<Article> aList = Assistant.List<Article>(c, os, 0, 1, new string[]
                                                                                       {
                                                                                           "ID", "Title", "SubTitle","Author","Source",
                                                                                           "ChannelFullUrl",
                                                                                           "Created","Clicks",
                                                                                           "SN","Content","OwnerID","Updated","State", "Thumbnail"
                                                                                       });
                        if (aList != null && aList.Count > 0)
                        {
                            thisArticle = aList[0];
                        }
                    }
                }
                return thisArticle ?? new Article(); ;
            }
            set
            {
                thisArticle = value;
            }
        }

        /// <summary>
        /// 上一篇
        /// </summary>
        protected Article PreviousArticle
        {
            get
            {
                if (previousArticle == null)
                {
                    Criteria c = new Criteria(CriteriaType.None);
                    c.Add(CriteriaType.Equals, "OwnerID", ThisArticle.OwnerID);
                    c.Add(CriteriaType.MoreThan, "Updated", ThisArticle.Updated);
                    c.Add(CriteriaType.Equals, "State", 1);
                    Order[] os = new Order[] { new Order("Updated", OrderMode.Asc) };
                    List<Article> aList = Assistant.List<Article>(c, os, 0, 1, new string[]
                                                                                   {
                                                                                       "ID", "Title", "ChannelFullUrl",
                                                                                       "Created",
                                                                                       "SN","OwnerID","Updated","State"
                                                                                   });
                    if (aList != null && aList.Count > 0)
                    {
                        previousArticle = aList[0];
                    }
                }
                return previousArticle;
            }
        }

        /// <summary>
        /// 下一篇
        /// </summary>
        protected Article NextArticle
        {
            get
            {
                if (nextArticle == null)
                {
                    Criteria c = new Criteria(CriteriaType.None);
                    c.Add(CriteriaType.Equals, "OwnerID", ThisArticle.OwnerID);
                    c.Add(CriteriaType.LessThan, "Updated", ThisArticle.Updated);
                    c.Add(CriteriaType.Equals, "State", 1);
                    Order[] os = new Order[] { new Order("Updated", OrderMode.Desc) };
                    List<Article> aList = Assistant.List<Article>(c, os, 0, 1, new string[]
                                                                                   {
                                                                                       "ID", "Title", "ChannelFullUrl",
                                                                                       "Created",
                                                                                       "SN","OwnerID","Updated","State"
                                                                                   });
                    if (aList != null && aList.Count > 0)
                    {
                        nextArticle = aList[0];
                    }
                }
                return nextArticle;
            }
        }

        /// <summary>
        /// 相关文章
        /// </summary>
        protected List<Article> RelevantArticles
        {
            get
            {
                if (relevantArticles == null)
                {
                    if (!We7Helper.IsEmptyID(ArticleID))
                    {
                        Criteria c = new Criteria(CriteriaType.None);
                        c.Add(CriteriaType.Equals, "OwnerID", ThisArticle.OwnerID);
                        c.Add(CriteriaType.Equals, "State", 1);
                        Order[] os = new Order[] { new Order("Updated", OrderMode.Desc) };
                        List<Article> aList = Assistant.List<Article>(c, os, 0, PageSize, new string[]
                                                                                              {
                                                                                                  "ID", "Title",
                                                                                                  "ChannelFullUrl",
                                                                                                  "Created",
                                                                                                  "SN","OwnerID","Updated","State"
                                                                                              });
                        if (aList != null && aList.Count > 0)
                        {
                            relevantArticles = aList;
                        }
                    }
                }
                return relevantArticles;
            }
        }


        /// <summary>
        /// 例子数据
        /// </summary>
        /// <returns></returns>
        private Article GetExampleData()
        {
            Article temp = new Article();
            temp.ID = We7Helper.CreateNewID();
            temp.Title = "语文教材修订凸显文化自信";
            temp.SubTitle = "测试新闻详细副标题";
            temp.Content = "据语文出版社近日透露，新修订的中小学语文教材进行了大幅调整，40%原有课文被替换。新版教材大幅增加反映中华优秀传统文化的课文比重，相关课文最高占到全部课文的40%，并且增加了古诗文教学内容。语文教材修订，迅速成为广受热议的话题。";
            return temp;
        }

        protected override void OnDesigning()
        {
            ThisArticle = GetExampleData();
        }

        /// <summary>
        /// 获取附件列表
        /// </summary>
        protected List<Attachment> Attachments
        {
            get
            {
                return HelperFactory.GetHelper<AttachmentHelper>().GetAttachments(ThisArticle.ID);
            }
        }
    }
}