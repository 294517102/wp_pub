(function (doc, win) {
    var docEl = doc.documentElement,
        resizeEvt = 'orientationchange' in window ? 'orientationchange' : 'resize',
        recalc = function () {
            var clientWidth = docEl.clientWidth;
            if (!clientWidth) return;
            docEl.style.fontSize = 72 * (clientWidth / 1080) + 'px';
        };
    if (!doc.addEventListener) return;
    win.addEventListener(resizeEvt, recalc, false);
    doc.addEventListener('DOMContentLoaded', recalc, false);
})(document, window);


$(function () {
    /*侧边栏js*/
    var left = $('.left');
    var bg = $('.bgDiv');
    var leftNav = $('.leftNav');
    var main = $(".left-main");
    var headFoot = $(".head,.foot");
    showNav(left, leftNav, "left");
    function showNav(btn, navDiv, direction) {

        btn.on('click', function () {
            bg.css({
                display: "block",
                transition: "opacity .1s"
            });
            main.css({
                left:"10.4rem",
                transition: "left .3s"
            });
            headFoot.css({
                left: "10.4rem",
                transition: "left .3s"
            });
            if (direction == "left") {
                navDiv.css({
                    left: "0px",
                    transition: "left .3s"
                });
            }
        });
    }
    bg.on('click', function () {
        hideNav();
    });
    function hideNav() {
        leftNav.css({
            left: "-10.4rem",
            transition: "left .3s"
        });
        main.css({
            left:"0",
            transition: "left .3s"
        });
        headFoot.css({
            left: "0",
            transition: "left .3s"
        });
        bg.css({
            display: "none",
            transition: "display 1s"
        });

    }
    $(".bgDiv,.leftNav").height($(window).height());
    $(".foot li").on("click", function () {
        $(this).siblings().removeClass("active");
        $(this).addClass("active");
    });
});
