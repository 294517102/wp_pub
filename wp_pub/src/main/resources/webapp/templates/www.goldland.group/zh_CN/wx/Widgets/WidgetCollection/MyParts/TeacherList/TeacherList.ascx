<%@ Control Language="C#" AutoEventWireup="true" Debug="true" CodeFile="TeacherList.cs"
    Inherits="We7.CMS.Web.Widgets.TeacherList" %>
<script type="text/C#" runat="server">
    [ControlDescription(Desc = "TeacherList教师信息列表", Author = "wonders")]
    string MetaData;
</script>
<div class="<%= CssClass %> <%=MarginCss %> ">
        <h3 <%=BackgroundIcon() %>>
            <span><a title="更多" target="_blank" href="<%=Channel.FullUrl %>">更多>></a></span><em><%=Channel.Name %><!-- <i> / <%=Channel.Description %></i> --></em>
        </h3>
        <table cellpadding="0" cellspacing="0" width="100%">
            <tr>
                <th>成员姓名</th>
                <th width="260">职称</th>
                <th width="150">个人情况</th>
            </tr>
            <%=SetBoxBorderColor() %>
            <% foreach (Article article in Articles)
            { %>
            <tr>
                <td><a target="_blank" href="<%=article.Url %>" title="<%=ToStr(article.Title) %>"><%=ToStr(article.Title,TitleLength) %></a></td>
                <td><%=article.SubTitle %></td>
                <td><a target="_blank" href="<%=article.Url %>">查看详情</a></td>
            </tr>
            <%} %>
        </table>
</div>