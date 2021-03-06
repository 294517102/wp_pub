var weather = null;
			$(function() {
				var t = new Date();
				//星期数字转换
				function chinanum(num) {
					var china = new Array('天', '一', '二', '三', '四', '五', '六');
					var arr = new Array();
					var english = num.split("")
					for(var i = 0; i < english.length; i++) {
						arr[i] = china[english[i]];
					}
					return arr.join("")
				}
				//获取时间
				function timer() {
					var d = new Date();
					time = '日期：';
					week = '';
					time += d.getFullYear() + '年'; //获取当前年份 
					time += d.getMonth() + 1 + '月'; //获取当前月份（0——11） 
					time += d.getDate() + '日&nbsp 星期';
					time += chinanum(d.getDay() + '');
					return time;
				}

				function current() {
					var d = new Date();
					var hour = d.getHours();
					var time = '';
					var m = '';
					if(hour >= 12) {
						hour = hour - 12;
						time = '时间：PM&nbsp' + hour;
					} else {
						time = '时间：AM&nbsp' + hour;
					}
					if(d.getMinutes() < 10) {
						m = '0' + d.getMinutes();
					} else {
						m = d.getMinutes();
					}
					time += ':' + m;
					return time;
				}
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
										console.log(data.result.data);
										var odat = data.result.data;
										var wd = odat.temperature;
										var icon = odat.weather_type;
										var src = '';
										var color = ['#CDE3E1', '#B7C9DD', '#FFECCE', '#BFD4D9'];
										$('#w_wrap .size').html(wd + "°");
										$('#w_wrap .state').html(icon);
										$('#w_wrap .city').html(citytq);
										if('晴'.indexOf(icon) >= 0) {
											src = 'res/default/bg/image/icon2.png';
											weather = "晴";
										} else if('多云大风阴雾霾'.indexOf(icon) >= 0) {
											src = 'res/default/bg/image/icon1.png';
											weather = "阴";
										} else if('大雪暴风雪'.indexOf(icon) >= 0) {
											src = 'res/default/bg/image/icon0.png';
											weather = "雪";
										} else if('大雨小雨暴雨雷阵雨'.indexOf(icon) >= 0) {
											src = 'res/default/bg/image/icon3.png';
											weather = "雨";
										}
										$('#w_wrap .icon').attr('src', src);
										if(t.getHours() > 6 && t.getHours() < 18) {
											$('.back_img').attr({
												"src": "res/default/bg/image/inner_bg" + src.slice(-5, -4) + ".png"
											});
											$('.body').css({
												"background": "url(res/default/bg/image/bg" + src.slice(-5, -4) + ".png) no-repeat fixed",
												'background-size': 'cover'
											});
											$('.color').css('background-color', color[src.slice(-5, -4)]);
										} else {
											$('.back_img').attr({
												"src": "res/default/bg/image/inner_bg_n_" + src.slice(-5, -4) + ".png"
											});
											weather = "夜";
										}
									}
								});
							}
						});

					});
				}
				findWeather();
				//写入
				$("#nowdate").html(timer);
				$("#nowTime").html(current);
				setInterval(function() {
					$("#nowTime").html(current);
				}, 30000);

			})
