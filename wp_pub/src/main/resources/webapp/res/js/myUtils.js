/**
 * 
 */
//查找最近的节点，传递回调函数
function findClosestByCallback(obj,className,expression,func){
	var parent = $(obj).closest(className);
	$(parent).find(expression).each(function(){func(this,obj);});
}

//查找最近的节点
function findClosestByNode(obj,className,expression){
	var parent = $(obj).closest(className);
	return $(parent).find(expression);
}

//加载地址库选项
function obtainAreaOptions(id,level,obj){
	jQuery.getJSON("buyer/area_service.htm",{id:id,level:level},function(data,status){
		if(status=="success"){	
			var areas = eval( data.resultData ); 
			var value=$(obj).val();
			$(obj).empty();
			for(var i=0;i<areas.length;i++){
				if(areas[i].id==value){
					$(obj).append("<option value='"+areas[i].id+"' checked='checked' >"+areas[i].name+"</option>"); 
				}else{
					$(obj).append("<option value='"+areas[i].id+"'>"+areas[i].name+"</option>"); 
				}
				
			}
		}else{
			layer.alert("错误：操作异常  "); 
			}
		});
}

//初始化表单，主要针对rodio、checkbox和select
function initValue(){
	$("input").each(function(){
		var value = $(this).attr("initValue");
		if(!value){
			return;
		}
		if($(this).attr("type")=="checkbox"|| $(this).attr("type")=="radio"){
			if($(this).val()==value){
				$(this).attr("checked",true);
			}else{
				$(this).attr("checked",false);
			}
			return true; 
		}
	});
	$("select").each(function(){
		var value = $(this).attr("initValue");
		if(!value){
			return;
		}
		var option = $(this).find("option[value='"+value+"']");
		if(option.size() <= 0){
			$(this).find("option:selected").attr("selected",false);
			$(this).append("<option value='"+value+"' selected='selected'>"+value+"</option>");
		}else{
			option.attr("selected",true);
		}
	});
}




//根据json对象的值初始化form表单
function initFormValue(form,obj){
	if(obj==null||form==null){
		return;
	}
	jQuery.each(obj,function(name,value) {
		var node = $(form).find("input[name="+name+"]");
		if(node.size()>0){
			if($(node).attr("type")=="checkbox"|| $(node).attr("type")=="radio"){
				if($(node).val()==value){
					$(node).attr("checked",true);
				}else{
					$(node).attr("checked",false);
				}
				return true; 
			}
			$(node).val(value);
		}else{
			node = $(form).find("select[name="+name+"]");
			if(node.size() <= 0) return true; 
			var option = $(node).find("option[value='"+value+"']");
			if(option.size() <= 0){
				$(node).find("option:selected").attr("selected",false);
				var key = name+"_desc";
				node.append("<option value='"+value+"' selected='selected'>"+obj[key]+"</option>");
			}else{
				option.attr("selected",true);
			}
		}
	});
}

function SetCookie(name,value)//两个参数，一个是cookie的名子，一个是值   
{  
   var Days = 30; //此 cookie 将被保存 30 天   
   var exp = new Date(); //new Date("December 31, 9998");   
   exp.setTime(exp.getTime() + Days*24*60*60*1000);  
   document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();  
}  
function getCookie(name)//取cookies函数      
{  
   var arr = document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));  
   if(arr != null) return unescape(arr[2]); return null;  

}  
function delCookie(name)//删除cookie   
{  
   var exp = new Date();  
   exp.setTime(exp.getTime() - 1);  
   var cval=getCookie(name);  
   if(cval!=null) document.cookie= name + "="+cval+";expires="+exp.toGMTString();  
}  

$.fn.serializeObject = function()  
{ 
   var o = {};  
   var a = this.serializeArray();  
   $.each(a, function() {  
	   if(this.value == ""){
		   return true;
	   }
	   if(this.name.indexOf(".") > 0){
		   var strs= new Array(); 
		   strs=this.name.split(".");
		   var t={};
		   t[strs[1]]=this.value;
		   o[strs[0]] = t;  
	   }else{
       if (o[this.name]) {  
           if (!o[this.name].push) {  
               o[this.name] = [o[this.name]];  
           }  
           o[this.name].push(this.value || "");  
       } else {  
           o[this.name] = this.value || "";  
       } 
	   }
       
   });  
   return o;  
};

$.fn.serializeJSON = function() 
{
	return JSON.stringify(this.serializeObject());
}

$.postJSON = function(url, data, callback) {
    return jQuery.ajax({
		type: 'POST',
	    url:  url,
	    data: data ,
	    dataType: 'json',
	    success: callback
    	});
}


function callAjaxHTMLProcByUrl(action, urlparams, recallfun) {
	$.ajax({
		cache: false,
		type: "POST",
		url: action,
		data: urlparams,
		dataType: "html",
		async: true,
		success: function (data) {
			recallfun(data);
		},
		error: function (request) {
	 		layer.alert("提示：操作异常");  
		}
	});
}

function callAjaxHTMLProcByForm(form, recallfun) {
	$.ajax({
		cache: false,
		type: "POST",
		url: form.attr("action"),
		data: form.serializeObject(),
		dataType: "html",
		async: true,
		success: function (data) {
			recallfun(data);
		},
		error: function (request) {
			layer.alert("提示：操作异常");  
		}
	});
}

function callAjaxJsonProcByForm(form, recallfun) {
	$.ajax({
		cache: false,
		type: "POST",
		url: form.attr("action"),
		data: form.serializeObject(),
		dataType: "json",
		async: true,
		success: function (data) {
			recallfun(data);
		},
		error: function (request) {
			layer.alert("提示：操作异常");  
		}
	});
}
/**
 *判断str1字符串是否以str2为结束
 *@param str1 原字符串
 *@param str2 子串
 *@author pansen
 *@return 是-true,否-false
 */
function endWith(str1, str2){
	 if(str1 == null || str2 == null){
	  return false;
	 }
	 if(str1.length < str2.length){
	  return false;
	 }else if(str1 == str2){
	  return true;
	 }else if(str1.substring(str1.length - str2.length) == str2){
	  return true;
	 }
	 return false;
}
/**
 *判断str1字符串是否以str2为开头
 *@param str1 原字符串
 *@param str2 子串
 *@author pansen
 *@return 是-true,否-false
 */
function startWith(str1, str2){
	 if(str1 == null || str2 == null){
	  return false;
	 }
	 if(str1.length < str2.length){
	  return false;
	 }else if(str1 == str2){
	  return true;
	 }else if(str1.substr(0, str2.length) == str2){
	  return true;
	 }
	 return false;
}

//首位自动补齐0，pad(100, 4);  // 输出：0100  
pad = function(tbl) {  
	  return function(num, n) {  
	    return (0 >= (n = n-num.toString().length)) ? num : (tbl[n] || (tbl[n] = Array(n+1).join(0))) + num;  
	  }  
	}([]); 
//绑定单个选择事件	
jQuery.fn.grantRadioEvent = function(style){
		var obj = $(this);
		$(this).click(function(){
			$(obj).removeClass(style);
			$(this).addClass(style);
		});
	}
//从href中提取页面编号
function getPageIndex(obj){
	var href = $(obj).attr("href");
	var page = href.substring(href.indexOf("&pageIndex=")+"&pageIndex=".length);
	return page;
}
//checkbox同步选中
function synCheck(obj,synObj){
	if($(obj).is(':checked')){
		$(synObj).attr("checked",'true');
	}else{
		$(synObj).removeAttr("checked");
	}
}
function batchUpd(form, batchObjStr, objs) {
	var mulitId = "";
	objs.each(function() {
		if($(this).val() != "") {	  
			mulitId += "," + $(this).val();
		}
	});
	if (mulitId != "") {
		Dialog.confirm("请确认是否批量提交该数据？注意！提交完就无法恢复！", function() {
			form.find("#" + batchObjStr).eq(0).val(mulitId.substring(1));
			form.submit();
		});
	}
}
function IsPc() { 
	var sUserAgent= navigator.userAgent.toLowerCase(); 
	console.log("获取的头："+sUserAgent);
	console.log("获取的session终端："+'$!header_terminal');
	var bIsIpad= sUserAgent.match(/ipad/i) == "ipad"; 
	var bIsIphoneOs= sUserAgent.match(/iphone os/i) == "iphone os"; 
	var bIsMidp= sUserAgent.match(/midp/i) == "midp"; 
	var bIsUc7= sUserAgent.match(/rv:1.2.3.4/i) == "rv:1.2.3.4"; 
	var bIsUc= sUserAgent.match(/ucweb/i) == "ucweb"; 
	var bIsAndroid= sUserAgent.match(/android/i) == "android"; 
	var bIsCE= sUserAgent.match(/windows ce/i) == "windows ce"; 
	var bIsWM= sUserAgent.match(/windows mobile/i) == "windows mobile"; 
	if (bIsIphoneOs || bIsMidp || bIsUc7 || bIsUc || bIsAndroid || bIsCE || bIsWM) { 
		return false;
	} else { 
		return true;
	} 
}
//post的方式提交表单，非ajax
jQuery(function ($) {
    $.extend({
        form: function (url, data, method) {
            if (method == null) method = 'POST';
            if (data == null) data = {};
 
            var form = $('<form>').attr({
                method: method,
                action: url
            }).css({
                display: 'none'
            });
 
            var addData = function (name, data) {
                if ($.isArray(data)) {
                    for (var i = 0; i < data.length; i++) {
                        var value = data[i];
                        addData(name + '[]', value);
                    }
                } else if (typeof data === 'object') {
                    for (var key in data) {
                        if (data.hasOwnProperty(key)) {
                            addData(name + '[' + key + ']', data[key]);
                        }
                    }
                } else if (data != null) {
                    form.append($('<input>').attr({
                        type: 'hidden',
                        name: String(name),
                        value: String(data)
                    }));
                }
            };
 
            for (var key in data) {
                if (data.hasOwnProperty(key)) {
                    addData(key, data[key]);
                }
            }
 
            return form.appendTo('body');
        }
    });
});

//layui 提示校验异常信息  。 by gongdzh
function showVMsg(obj,msg){
	   $(obj).focus();
	   $(obj).addClass("layui-form-danger");
	   layer.msg(msg, {icon: 2,time: 2000}); 
	   $(obj).change(function(){
		  if($(this).val().length > 0 &&　$(this).hasClass("layui-form-danger")){
			   $(this).removeClass("layui-form-danger");
		   } 
	   });
}
//关闭 layer当前窗口
function closeWindow(){
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	parent.layer.close(index);
}
//时间格式化
Date.prototype.format = function(format) {
	
    var date = {
           "M+": this.getMonth() + 1<10?'0'+ this.getMonth():this.getMonth(),
           "d+": this.getDate()<10?'0'+this.getDate():this.getDate(),
           "h+": this.getHours()<10?'0'+this.getHours():this.getHours(),
           "m+": this.getMinutes()<10?'0'+this.getMinutes():this.getMinutes(),
           "s+": this.getSeconds()<10?'0'+this.getSeconds():this.getSeconds(),
           "q+": Math.floor((this.getMonth() + 3) / 3),
           "S+": this.getMilliseconds()
    };
    if (/(y+)/i.test(format)) {
           format = format.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length));
    }
    for (var k in date) {
           if (new RegExp("(" + k + ")").test(format)) {
                  format = format.replace(RegExp.$1, RegExp.$1.length == 1
                         ? date[k] : ("00" + date[k]).substr(("" + date[k]).length));
           }
    }
    return format;
}


