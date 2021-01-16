$(function() {
	var t = new Date();
	//获取天气
	function findWeather() {
		var cityUrl = 'http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=js';
		$.getScript(cityUrl, function(script, textStatus, jqXHR) {
			var citytq = remote_ip_info.city; // 获取城市\
			$.ajax({
				url: "http://open.weather.sina.com.cn/api/location/getIndexSuggestion/" + citytq + "",
				dataType: "jsonp",
				async: true,
				success: function(data){
					var ocity = data.result.data[0].loc_code;
					var url = "http://open.weather.sina.com.cn/api/web/getRealInfo/" + ocity + "";
					$.ajax({
						url: url,
						dataType: "jsonp",
						cache: true,
						success: function(data) {
							console.log(data);
							var odat = data.result.data;
							var icon = odat.weather_type;
							console.log(icon);
							var state_code = '';
							if('晴'.indexOf(icon) >= 0) {
								state_code = '1';
							} else if('多云大风阴雾霾'.indexOf(icon) >= 0) {
								state_code = '0';
							} else if('大雪暴风雪'.indexOf(icon) >= 0) {
								state_code = '2';
							} else if('大雨小雨暴雨雷阵雨'.indexOf(icon) >= 0) {
								state_code = '3';
							}
							if(t.getHours() > 6 && t.getHours() < 18) {
								function side() {
									$('.side_botlogo').attr('src', '/res/default/bg/image/nav_down_pic_' + state_code + '.png').css({'display':'block','opacity':'1'});
								}
								side();
							}else{
								$('.side_botlogo').attr('src', '/res/default/bg/image/nav_down_pic.png').css({'display':'block','opacity':'1'});

							}

						}
					});
				}
			});

		});
	}
	findWeather();

})