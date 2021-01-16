

function ins(){
    var hei = window.innerHeight;
    var Div = document.getElementsByClassName("main")[0];
    if(hei>600){
        Div.style.height = hei+"px";
    }
}
smallPlaneMoveTimer = setInterval(ins,200);