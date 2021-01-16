function layer_dialog(result_msg,icon,type,url){
	layer.msg(result_msg, {
		icon: icon,
		skin: 'layui-layer-lan',
		time: 3000, 
		area: ['200px', '80px']
}, function(){
	if(type=="1"){
		window.location.href=url;
	}else if(type=="2"){
		//	刷新
		window.location.reload();
	}else if(type=="3"){
		top.Dialog.close();
	}else if(type=="4"){
		window.location.reload();
		
	}else{
		//不执行任何操作
	}
});  	
}

//dialog
function layer_diag(title,url){
	layer.open({
		  type: 2,
		  title: title,
		  shadeClose: true,
		  shade: 0.3,
		  area: ['420px', '95%'],
		  content:url,
		}); 
}




