function ajaxPage(url,pageIndex,obj){
        	$.post("/query_like_doctor$ajax.htm?isMavin=2&&mode=ajax",{"pageIndex":pageIndex,"size":9},function(res){
        		var _data = JSON.parse(res);
                var ys_len = _data.resultList.length;
                    var str1 = "";var zhuan='';
                    if(_data.resultList.length > 0){
                        for(var i = 0; i< ys_len ; i++){
                            if(_data.resultList[i].position==null){
                                zhuan='暂无'
                            }else{
                                zhuan=_data.resultList[i].position.name;
                            }
                            // console.log(_data.resultList[i].level)
                            var level=_data.resultList[i].level
                            if(level==null){
                                level='暂无'
                            }
                            str1 += '<div class="zuozhen_jieshao">'+
        				    '<div class="zuozhen_jieshao_lf">'+
            					'<dl>'+
            					    '<a href="general_service_t3ncXUfMQv8itS1rnjr.htm?_url=doctor_detail&id='+_data.resultList[i].id+'">'+
            						'<dt><img src="'+_data.resultList[i].accessory.originalUrl+'" alt="" style="width:118px"></dt>'+
            						'<dd>'+
            							'<div class="xingming">'+
            								'<h5>'+ _data.resultList[i].name+'</h5>'+
            								'<span>'+zhuan+'</span>'+
            							'</div>'+
            							'<div class="keshi">科室：'+_data.resultList[i].column.title+'</div>'+
            							'<div class="jibie">'+level+'</div>'+
            							'<div class="shanchang">擅长:'+_data.resultList[i].speciality+'</div>'+
            						'</dd>'+
            						'</a>'+
            					'</dl>'+
            				'</div>'+
            				'<div class="zuozhen_jieshao_rg">'+
            					'<ul class="bgcolor">'+
            						'<li></li>'+
            						'<li>一</li>'+
            						'<li>二</li>'+
            						'<li>三</li>'+
            						'<li>四</li>'+
            						'<li>五</li>'+
            						'<li>六</li>'+
            						'<li>天</li>'+
            					'</ul>'+
            					'<ul class="shangwu'+i+'">'+
            					
            					'</ul>'+
            					'<ul class="xiawu'+i+'">'+'</ul>'+
            				'</div>'+
            			'</div>';
            		    $('.ysxx').html(str1);
            			
                    }
                        for(var j = 0 ; j<ys_len;j++){
                            var plen=_data.resultList[j].dutys.length;
            		    	var sli='<li>上午工作日</li>'
            		    	var xli='<li>下午工作日</li>'
            			    if(plen>1){
            			       for(var s=0;s<plen;s++){
                			    if(_data.resultList[j].dutys[s].typeAm!=null){
                			       if(_data.resultList[j].dutys[s].typeAm==1){
                			           sli += '<li>特</li>';
                			       }else if(_data.resultList[j].dutys[s].typeAm==2){
                			           sli += '<li>专</li>';
                			       }else if(_data.resultList[j].dutys[s].typeAm==3){
                			           sli += '<li>普</li>';
                			       }else if(_data.resultList[j].dutys[s].typeAm==4){
                			           sli += '<li>停</li>';
                			       }
                                			       
                			    }else{
                			       sli += '<li></li>';
                			    }
                			    $('.shangwu'+j).html(sli);
                			   } 
                			   for(var s=0;s<plen;s++){
                			    if(_data.resultList[j].dutys[s].typePm!=null){
                			       if(_data.resultList[j].dutys[s].typePm==1){
                			           xli += '<li>特</li>';
                			       }else if(_data.resultList[j].dutys[s].typePm==2){
                			           xli += '<li>专</li>';
                			       }else if(_data.resultList[j].dutys[s].typePm==3){
                			           xli += '<li>普</li>';
                			       }else if(_data.resultList[j].dutys[s].typePm==4){
                			           xli += '<li>停</li>';
                			       }
                                			       
                			    }else{
                			       xli += '<li></li>';
                			    }
                			    $('.shangwu'+j).html(sli);
                			    $('.xiawu'+j).html(xli);
                			   } 
            			    }else{
            			       $('.shangwu'+j).html('<li>上午工作日</li>');
            			       $('.xiawu'+j).html('<li>下午工作日</li>');
            			    }
            			    
            		    
                        }
                    console.log(_data.pageHtml);
                    $('.fenpage').html("哈哈"+_data.pageHtml+"===");
                    }else{
                        $('.fenpage').html('');
                         $('.ysxx').html("<div class='nodata'><img src='res/default/images/ys.png' alt='抱歉！没有筛选到相关医生' /><p>抱歉！没有筛选到相关医生</p></div>");
                    }
        	})
            
	    };