﻿<%@ Control Language="C#" AutoEventWireup="true" CodeFile="Search2015.cs"
 Inherits="We7.CMS.Web.Widgets.Search2015" %>
<script type="text/C#" runat="server">
    [ControlDescription("Search搜索框")]
    string MetaData;
    /// <summary>
    /// 搜索地址
    /// </summary>
    [Parameter(Title = "搜索结果地址", Type = "String")]
    public string SearchPage = "/Search.aspx";
</script>
<div class="<%=Css %>">
    <div class="searchbox">
<!--头部-->
    <div class="full_model_search">
        <input value="请输入要搜索的关键词" class="model_search" onblur="if(this.value==''){this.value=this.defaultValue;}" onfocus="if(this.value==this.defaultValue){this.value='';}" value="输入关键字" /><a href="#" title="搜索" class="model_search_click"></a>
    </div>
<!--头部-->
        </div>
<script type="text/javascript">
    $(function () {
        //$('a[class="fav"]').addFavorite();
        //$('a[class="home"]').setHomePage();

        $('#change_search_model').click(function () {
            if ($('.model_list').css('display') != 'block') {
                $('.model_list').slideDown(500);
                try { clearTimeout(timer); } catch (e) { }
            } else {
                $('.model_list').mouseleave();
            };
        })

        $('.model_list').mouseleave(function () {
            timer = setTimeout(function () {
                $('.model_list').slideUp(100);
            }, 500);
        })

        $('.model_list a').click(function () {
            $('#change_search_model').text($(this).text())
            $('.model_list').slideUp(100);
            try { clearTimeout(timer); } catch (e) { }
        })

        $('.model_search_click').click(function () {
            var model = checkSearchModel();
            doModelSearch(model[0], model[1]);
        })

        $('#model_adv_search').click(function () {
            var model = checkSearchModel();
            doModelSearch(model[0], 2);
        })

        var checkSearchModel = function () {
            var model = $('#change_search_model').text();
            switch (model) {
                case '文章':
                    return [1, 1];
                case '图片':
                    return [2, 1];
                case '软件':
                    return [3, 1];
                default:
                    return [0, 2];
            }
        }

        var doModelSearch = function (modelId, searchType) {
            var searchValue = escape($('.model_search').val());

            if (searchType != 2 && (searchValue == null || searchValue == '')) {
                alert('请输入要搜索的关键词');
                $('.model_search').focus();
                return;
            }
            if (searchType == 2 && modelId == 0) {
                location.href = 'Search.aspx?keywords=' + searchValue;
                return;
            }
            switch (modelId) {
                case 1:
                    location.href = '<%=SearchPage %>?searchtype=1&modelId=' + modelId + '&nodeId=1&fieldOption=title&keywords=' + searchValue;
                    break;
                case 2:
                    location.href = '<%=SearchPage %>?searchType=1&modelId=' + modelId + '&nodeId=2&fieldOption=title&keywords=' + searchValue;
                    break;
                default:
                    location.href = '<%=SearchPage %>?searchtype=1&modelId=' + modelId + '&nodeId=3&fieldOption=title&keywords=' + searchValue;
                    break;
            }
        }
    });
</script>
</div>