<%@ Control Language="C#" AutoEventWireup="true" CodeFile="SliderBanner.cs" Inherits="We7.CMS.Web.Widgets.SliderBanner" %>
<script type="text/C#" runat="server">
    [ControlDescription(Desc = "SliderBanner", Author = "wonders")]
    string MetaData;
</script>
<div class="<%=Css %>" <%=SetBoxBorderColor() %>>
    <div class="banner_zz"></div>
    <div class="ui-slider" id="slider1">
        <ul class="ui-slider-content" style="width: 300%">
            <% foreach (Link link in Items)
               {
            %>
            <li><a href="<%=link.Url %>"><span style="background-image:url(<%=link.Thumbnail %>)"></span></a></li>
            <%} %>
        </ul>
    </div>

    <script class="demo-script">
    (function (){
        var slider = new fz.Scroll('.ui-slider', {
            role: 'slider',
            indicator: true,
            autoplay: true,
            interval: 3000
        });

        slider.on('beforeScrollStart', function(fromIndex, toIndex) {
            console.log(fromIndex,toIndex)
        });

        slider.on('scrollEnd', function(cruPage) {
            console.log(cruPage)
        });
    })();
    </script>

</div>