package com.tjsj.wp.mvc.controller.bn;



import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.CreateUploadVideoRequest;
import com.aliyuncs.vod.model.v20170321.CreateUploadVideoResponse;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse.PlayInfo;
import com.aliyuncs.vod.model.v20170321.RefreshUploadVideoRequest;
import com.aliyuncs.vod.model.v20170321.RefreshUploadVideoResponse;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.PagedList;
import com.tjsj.base.entity.PageParameter;
import com.tjsj.base.entity.ResultJson;
import com.tjsj.m_util.exception.ParameterException;
import com.tjsj.wp.mvc.controller.WpBaseController;
import com.tjsj.wp.orm.entity.BnVideoTbl;


/**
 * @author andrew-silence
 *
 */

@RestController
@RequestMapping("admin/aliVideo")
public class AliVideoServiceController extends WpBaseController{
	private static Logger logger = LoggerFactory.getLogger(AliVideoServiceController.class);

	private final static  String accessKeyId = "LTAI4FkyFtHwtkhkTYthMWCi";
	private final static  String  accessKeySecret = "IxaFlYkCga91MJR9SVlwdpJLy8U3WQ";
	
	@RequestMapping(value="/CreateUploadVideo")
	public void CreateUploadVideo( HttpServletRequest request,
							HttpServletResponse response,
							String title,
							String fileName) {
		ResultJson rj = new ResultJson();
		try {

			  DefaultAcsClient client = initVodClient(accessKeyId, accessKeySecret);
		        CreateUploadVideoResponse videoResponse = new CreateUploadVideoResponse();
		        try {
		        	 CreateUploadVideoRequest videoRequest = new CreateUploadVideoRequest();
		        	 videoRequest.setTitle(title);
		        	 videoRequest.setFileName(fileName);
		             JSONObject userData = new JSONObject();
		             JSONObject messageCallback = new JSONObject();
		             messageCallback.put("CallbackURL", "http://xxxxx");
		             messageCallback.put("CallbackType", "http");
		             userData.put("MessageCallback", messageCallback.toJSONString());
		             JSONObject extend = new JSONObject();
		             extend.put("MyId", "user-defined-id");
		             userData.put("Extend", extend.toJSONString());
		             videoRequest.setUserData(userData.toJSONString());
		        	
		             videoResponse = createUploadVideo(client,title,fileName);
		            System.out.print("VideoId = " + videoResponse.getVideoId() + "\n");
		            System.out.print("UploadAddress = " + videoResponse.getUploadAddress() + "\n");
		            System.out.print("UploadAuth = " + videoResponse.getUploadAuth() + "\n");
		        } catch (Exception e) {
		        	
		            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
		            
		        }
		        System.out.print("RequestId = " + videoResponse.getRequestId() + "\n");
			rj.setData(videoResponse);
			rj.setResult(true);
			rj.setResultMsg("操作成功");
			
		} catch (Exception e) {
			logger.error("exception throws:", e);
			rj.setResult(false);
			rj.setResultMsg(e.getMessage());
		}
		outJson(response, rj);
	}
	/**
	 * 刷新视频上传凭证
	 * @param client 发送请求客户端
	 * @return RefreshUploadVideoResponse 刷新视频上传凭证响应数据
	 * @throws Exception
	*/	
	@RequestMapping(value="/refreshUploadVideo")
	public void RefreshUploadVideo( HttpServletRequest request,	HttpServletResponse response, String videoId) {
		ResultJson rj = new ResultJson();
		try {
			 DefaultAcsClient client = initVodClient(accessKeyId, accessKeySecret);
		    RefreshUploadVideoResponse videorefreshresponse = new RefreshUploadVideoResponse();
        	 videorefreshresponse = refreshUploadVideo(client,videoId);
		     System.out.print("UploadAddress = " + videorefreshresponse.getUploadAddress() + "\n");
		     System.out.print("UploadAuth = " + videorefreshresponse.getUploadAuth() + "\n");
			rj.setData(videorefreshresponse);
			rj.setResult(true);
			rj.setResultMsg("操作成功");
		} catch (Exception e) {
			logger.error("exception throws:", e);
			rj.setResult(false);
			rj.setResultMsg(e.getMessage());
		}
		outJson(response, rj);
	}	
	
	@RequestMapping(value="/getPlayInfo")
	public void getPlayInfo( HttpServletRequest request,HttpServletResponse response, String videoId) {
		ResultJson rj = new ResultJson();
		try {
		    DefaultAcsClient client = initVodClient(accessKeyId, accessKeySecret);
		    GetPlayInfoResponse inforesponse = new GetPlayInfoResponse();
		    inforesponse = getPlayInfo(client,videoId);
		        List<GetPlayInfoResponse.PlayInfo> playInfoList = inforesponse.getPlayInfoList();
		        //播放地址
		        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
		            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
		        }
		        //Base信息
		        System.out.print("VideoBase.Title = " + inforesponse.getVideoBase().getTitle() + "\n");
		    rj.setData(inforesponse);
			rj.setResult(true);
			rj.setResultMsg("操作成功");
		} catch (Exception e) {
			logger.error("exception throws:", e);
			rj.setResult(false);
			rj.setResultMsg(e.getMessage());
		}
		outJson(response, rj);
	}	
	
	
	public  List<GetPlayInfoResponse.PlayInfo> getPlayInfo(String videoId) throws Exception{
			DefaultAcsClient client = initVodClient(accessKeyId, accessKeySecret);
		 	GetPlayInfoResponse inforesponse = getPlayInfo(client,videoId);
	       return inforesponse.getPlayInfoList();
	}

	public static RefreshUploadVideoResponse refreshUploadVideo(DefaultAcsClient client,String videoId) throws Exception {
	    RefreshUploadVideoRequest request = new RefreshUploadVideoRequest();
	    request.setVideoId(videoId);
	    return client.getAcsResponse(request);
	}
    /**
     * 获取视频上传地址和凭证
     * @param client 发送请求客户端
     * @return CreateUploadVideoResponse 获取视频上传地址和凭证响应数据
     * @throws Exception
    */
    public static CreateUploadVideoResponse createUploadVideo(DefaultAcsClient client,String title,String fileName) throws Exception {
        CreateUploadVideoRequest request = new CreateUploadVideoRequest();
        request.setTitle(title);
        request.setFileName(fileName);
        JSONObject userData = new JSONObject();
        JSONObject messageCallback = new JSONObject();
        messageCallback.put("CallbackURL", "http://xxxxx");
        messageCallback.put("CallbackType", "http");
        userData.put("MessageCallback", messageCallback.toJSONString());
        JSONObject extend = new JSONObject();
        extend.put("MyId", "user-defined-id");
        userData.put("Extend", extend.toJSONString());
        request.setUserData(userData.toJSONString());
        return client.getAcsResponse(request);
    }
    public static DefaultAcsClient initVodClient(String accessKeyId, String accessKeySecret) throws ClientException {
        String regionId = "cn-shanghai";  // 点播服务接入区域
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        return client;
    }
    /*获取播放地址函数*/
    public static GetPlayInfoResponse getPlayInfo(DefaultAcsClient client,String videoId) throws Exception {
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        request.setVideoId(videoId);
        return client.getAcsResponse(request);
    }

    /**
     * 删除视频
     * @param client 发送请求客户端
     * @return DeleteVideoResponse 删除视频响应数据
     * @throws Exception
    */
	public static DeleteVideoResponse deleteVideo(String videoList) throws Exception {
		DefaultAcsClient client = initVodClient(accessKeyId, accessKeySecret);
        DeleteVideoRequest request = new DeleteVideoRequest();
        //支持传入多个视频ID，多个用逗号分隔
        request.setVideoIds(videoList);
        return client.getAcsResponse(request);
    }	
	@RequestMapping("videolist")
	public ModelAndView videolist(HttpServletRequest request,HttpServletResponse response,Integer pageIndex,String url,PageParameter page,Integer type){
		if(StringUtils.isBlank(url)){
			url="content_manager/column/mediaFile_list";
		}
		ModelAndView mv = createBgView(url, request, response);
	try{
		ExpressionList<BnVideoTbl> exp=BnVideoTbl.find.where().eq("webSet", getWebSet(request)).eq("isDelete", -1);
		if(type!=null && type!=0){
			exp.eq("type", type);
		}
		PagedList<BnVideoTbl> comment = exp.orderBy("id desc").setFirstRow(page.getFirstRow()).setMaxRows(page.getMaxRows()).findPagedList();
		pagePrint(request, mv, comment);
	}catch(Exception e){
		e.printStackTrace();
	}
		return mv;
	}
	@RequestMapping(value="/save_video")
	public void save_video( HttpServletRequest request,	HttpServletResponse response, BnVideoTbl video) {
		ResultJson rj = new ResultJson();
		try {
			if(video==null) {
				throw new ParameterException("请上传视频");
			}
			if(video.getVideoId()==null) {
				throw new ParameterException("位置视频");
			}
			video.setWebSet(getWebSet(request));
		     List<PlayInfo> a=getPlayInfo(video.getVideoId());
		     String url=StringUtils.substringBefore(a.get(0).getPlayURL(),"?Expires=");
		     video.setPlayUrl(url);
		     video.save();
			rj.setResult(true);
			rj.setResultMsg("操作成功");
		} catch (Exception e) {
			logger.error("exception throws:", e);
			rj.setResult(false);
			rj.setResultMsg(e.getMessage());
		}
		outJson(response, rj);
	}
	@RequestMapping(value="/delete_video")
	public void delete_video( HttpServletRequest request,	HttpServletResponse response, Integer video_id) {
		ResultJson rj = new ResultJson();
		try {
			if(video_id==null) {
				throw new ParameterException("请选择视频");
			}
			BnVideoTbl video=BnVideoTbl.find.byId(video_id);
			deleteVideo(video.getVideoId());
			video.delete();
			rj.setResult(true);
			rj.setResultMsg("操作成功");
		} catch (Exception e) {
			logger.error("exception throws:", e);
			rj.setResult(false);
			rj.setResultMsg(e.getMessage());
		}
		outJson(response, rj);
	}	
    public static void main(String[] argv) {
	        CreateUploadVideoResponse videoResponse = new CreateUploadVideoResponse();
        try {
        	 DefaultAcsClient client = initVodClient(accessKeyId, accessKeySecret);
 		    GetPlayInfoResponse inforesponse = new GetPlayInfoResponse();
 		    inforesponse = getPlayInfo(client,"7ff4d56df5d244eeb0ae67919e4446e8");
 		        List<GetPlayInfoResponse.PlayInfo> playInfoList = inforesponse.getPlayInfoList();
 		        //播放地址
 		        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
 		            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
 		        }
 		        //Base信息
 		        System.out.print("VideoBase.Title = " + inforesponse.getVideoBase().getTitle() + "\n");	
 		       System.out.print(inforesponse.toString());
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
    }

}
