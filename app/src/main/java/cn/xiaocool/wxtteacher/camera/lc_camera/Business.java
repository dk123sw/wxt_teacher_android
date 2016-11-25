package cn.xiaocool.wxtteacher.camera.lc_camera;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.lechange.opensdk.api.LCOpenSDK_Api;
import com.lechange.opensdk.api.bean.BindDevice;
import com.lechange.opensdk.api.bean.BindDeviceInfo;
import com.lechange.opensdk.api.bean.CheckDeviceBindOrNot;
import com.lechange.opensdk.api.bean.ControlDeviceWifi;
import com.lechange.opensdk.api.bean.DeviceAlarmPlan;
import com.lechange.opensdk.api.bean.DeviceAlarmPlan.ResponseData.RulesElement;
import com.lechange.opensdk.api.bean.DeviceList;
import com.lechange.opensdk.api.bean.DeviceList.Response;
import com.lechange.opensdk.api.bean.DeviceList.ResponseData.DevicesElement;
import com.lechange.opensdk.api.bean.DeviceList.ResponseData.DevicesElement.ChannelsElement;
import com.lechange.opensdk.api.bean.DeviceOnline;
import com.lechange.opensdk.api.bean.ModifyDeviceAlarmPlan;
import com.lechange.opensdk.api.bean.ModifyDeviceAlarmStatus;
import com.lechange.opensdk.api.bean.QueryCloudRecordNum;
import com.lechange.opensdk.api.bean.QueryCloudRecords;
import com.lechange.opensdk.api.bean.QueryCloudRecords.ResponseData.RecordsElement;
import com.lechange.opensdk.api.bean.QueryLocalRecordNum;
import com.lechange.opensdk.api.bean.QueryLocalRecords;
import com.lechange.opensdk.api.bean.SetAllStorageStrategy;
import com.lechange.opensdk.api.bean.UnBindDevice;
import com.lechange.opensdk.api.bean.WifiAround;
import com.lechange.opensdk.api.client.BaseRequest;
import com.lechange.opensdk.api.client.BaseResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.xiaocool.wxtteacher.view.WxtApplication;


public class Business {

	private static class Instance {
		static Business instance = new Business();
	}

	public static Business getInstance() {
		return Instance.instance;
	}

	//hls,rtsp等相关底层库的错误码解析
	public static final int RESULT_SOURCE_OPENAPI = 99;           //在播放过程中rest请求回调类型
	public static class CloudStorageCode {
		public static final String HLS_DOWNLOAD_FAILD = "0"; // 下载失败
		public static final String HLS_DOWNLOAD_BEGIN = "1"; // 开始下载
		public static final String HLS_DOWNLOAD_END = "2"; // 下载结束
		public static final String HLS_SEEK_SUCCESS = "3"; // 定位成功
		public static final String HLS_SEEK_FAILD = "4"; // 定位失败
		public static final String HLS_ABORT_DONE = "5";
		public static final String HLS_RESUME_DONE = "6";
	}

	public static class PlayerResultCode {	
		public static final String STATE_PACKET_FRAME_ERROR = "0"; // 组帧失败
		public static final String STATE_RTSP_TEARDOWN_ERROR = "1"; // 内部要求关闭,如连接断开等
		public static final String STATE_RTSP_DESCRIBE_READY = "2"; // 会话已经收到Describe响应
		public static final String STATE_RTSP_AUTHORIZATION_FAIL = "3"; // RTSP鉴权失败
		public static final String STATE_RTSP_PLAY_READY = "4";  // 收到PLAY响应
		//public static final String STATE_RTSP_FILE_PLAY_OVER = "5"; // 录像文件回放正常结束
	}
	
	
	public final static String tag = "Business";
	// 码流超时时间
	public final static int DMS_TIMEOUT = 60 * 1000;

	private List<ChannelInfo> mChannelInfoList = new ArrayList<ChannelInfo>(); // 由于demo，不考虑多线程操作问题
	private List<RecordInfo> mRecordInfoList = new ArrayList<RecordInfo>(); // 由于demo，不考虑多线程操作问题

	private String mHost;
	private String mAppId;
	private String mAppSecret;
	//private boolean bInit = false;

	public final class HttpCode {
		public static final int SC_OK = 200;// OK
											// （API调用成功，但是具体返回结果，由content中的code和desc描述）
		public static final int Bad_Request = 400;// Bad Request （API格式错误，无返回内容）
		public static final int Unauthorized = 401;// Unauthorized
													// （用户名密码认证失败，无返回内容）
		public static final int Forbidden = 403;// Forbidden （认证成功但是无权限，无返回内容）
		public static final int Not_Found = 404;// Not Found （请求的URI错误，无返回内容）
		public static final int Precondition_Failed = 412;// Precondition Failed
															// （先决条件失败，无返回内容。通常是由于客户端所带的x-hs-date时间与服务器时间相差过大。）
		public static final int Internal_Server_Error = 500;// Internal Server
															// Error
															// （服务器内部错误，无返回内容）
		public static final int Service_Unavailable = 503;// Service Unavailable
															// （服务不可用，无返回内容。这种情况一般是因为接口调用超出频率限制。）
	}

	private String mToken = WxtApplication.LECHANGE_ACCESS_TOKEN; // userToken或accessToken

	public static class RetObject {
		public int mErrorCode = 0; // 错误码表示符 -1:返回体为null，0：成功，1：http错误， 2：业务错误
		public String mMsg;
		public Object resp;
	}

	/**
	 * 初始化client对象，配置client参数
	 * 
	 * @return
	 */
	public boolean init(String appid, String appsecret, String host) {
		//if (!bInit) {
			// 初始化客户端环境,接口已经去除，不需要调用！！！！！！！！！
			// LCOpenSDK_Api.initOpenApi();
			// 设置乐橙服务地址
			LCOpenSDK_Api.setHost(host);
			mAppId = appid;
			mAppSecret = appsecret;
			mHost = host;
			
			//bInit = true;
			Log.d(tag, "Init OK");
		//}
		return true;
	}

	/**
	 * 发送网络请求，并对请求结果的错误码进行处理
	 * 
	 * @param req
	 * @return
	 */
	private RetObject request(BaseRequest req) {
		return request(req, 5 * 1000);
	}

	/**
	 * 发送网络请求，并对请求结果的错误码进行处理
	 * 
	 * @param req
	 * @param timeOut
	 *            访问dms接口是，超时时间设置长一点
	 * @return
	 * @throws Exception
	 */
	private RetObject request(BaseRequest req, int timeOut) {
		// T t = LCOpenSDK_Api.request(req, timeOut);
		RetObject retObject = new RetObject();
		BaseResponse t = null;
		try {
			t = LCOpenSDK_Api.request(req, timeOut);
			Log.d(tag, req.getBody());

			if (t.getCode() == HttpCode.SC_OK) {
				// 请求成功，则看服务器处理错误
				if (!t.getApiRetCode().equals("0"))
					retObject.mErrorCode = 2; // 业务错误
				retObject.mMsg = "业务错误码 : " + t.getApiRetCode() + ", 错误消息 ："
						+ t.getApiRetMsg();
			} else {
				retObject.mErrorCode = 1; // http错误
				retObject.mMsg = "HTTP错误码 : " + t.getCode() + ", 错误消息 ："
						+ t.getDesc();
			}
			Log.d(tag, t.getBody());

			// Log.d(tag, retObject.mMsg);

		} catch (Exception e) {
			// if timeout,return;
			e.printStackTrace();
			retObject.mErrorCode = -1000;
			retObject.mMsg = "内部错误码 : -1000, 错误消息 ：" + e.getMessage();
			Log.d(tag, req.getBody() + retObject.mMsg);
		}
		retObject.resp = t;
		return retObject;
	}

	public String getHost() {
		return mHost;
	}

	public void setToken(String mToken) {
		this.mToken = mToken;
	}

	public String getToken() {
		return mToken;
	}



	/**
	 * 从检索出的设备列表中查找制定设备通道
	 *
	 * @param id
	 * @return
	 */
	public ChannelInfo getChannel(String id) {

		for (ChannelInfo chl : mChannelInfoList) {
			if (chl.getUuid().equals(id)) {
				return chl;
			}
		}
		return null;
	}

	/**
	 * 获取用户下面所有设备通道列表
	 * 
	 * @param handler
	 */
	public void getChannelList(final Handler handler) {
		mChannelInfoList.clear(); //清数据
		TaskPoolHelper.addTask(new TaskPoolHelper.RunnableTask("real") {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				DeviceList req = new DeviceList();
				// req.data.filter = new ArrayList<String>(); //
				// 过滤条件，可指定获取特定设备序列号的设备信息，我们这获取用户所有设备，无过滤条件。
				req.data.token = mToken; // token
				req.data.queryRange = "1-10";
				DeviceList.Response resp = null;
				RetObject retObject = null;
				retObject = request(req);
				resp = (Response) retObject.resp;
				mChannelInfoList.clear(); //防止list崩溃
				if (resp != null && resp.data != null
						&& resp.data.devices != null)
					for (DevicesElement devElement : resp.data.devices) {
						List<ChannelInfo> list = devicesElementToResult(devElement);
						mChannelInfoList.addAll(list);
					}
				retObject.resp = mChannelInfoList;
				// 解析设备列表信息
				handler.obtainMessage(retObject.mErrorCode, retObject)
						.sendToTarget(); // 发送成功消息到界面
			}
		});
	}
	
	/**
	 * 获取用户设备信息
	 * 
	 * @param handler
	 */
	public void getDeviceInfo(final String chnlId, final Handler handler) {
		
		final ChannelInfo chl = getChannel(chnlId);
		if (chl == null) {
			return;
		}
		TaskPoolHelper.addTask(new TaskPoolHelper.RunnableTask("real") {
			
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				BindDeviceInfo req = new BindDeviceInfo();

				req.data.token = mToken; // token
				req.data.deviceId = chl.getDeviceCode();
				BindDeviceInfo.Response resp = null;
				
				RetObject retObject = null;
				retObject = request(req);
				resp = (BindDeviceInfo.Response) retObject.resp;
				
				// 网络请求失败
				if (retObject.mErrorCode != 0) {
					Log.d(tag, "getDeviceInfo faied " + retObject.mMsg);
					handler.obtainMessage(retObject.mErrorCode).sendToTarget();
				} else {
					if (resp.data == null || resp.data.channels == null) {
						Log.d(tag, "getDeviceInfo success data is null");
					}
					Bundle bundle = new Bundle();
					bundle.putInt("alarmStatus", resp.data.channels.get(0).alarmStatus);
					bundle.putInt("cloudStatus", resp.data.channels.get(0).csStatus);
					// todo:其他信息解析请见OpenAPI 平台sdk文档
					handler.obtainMessage(0, bundle).sendToTarget(); // 发送成功消息到界面
				}
			}
			
		});
	}

	/**
	 * 一个设备下面有多个通道，目前demo不需要太多设备信息，故没有解析设备信息，但设备信息解析可参照下面代码，或者参照OpenAPI sdk文档。
	 * 
	 * @param devElement
	 * @return
	 */
	private List<ChannelInfo> devicesElementToResult(DevicesElement devElement) {

		List<ChannelInfo> channelList = new ArrayList<ChannelInfo>();
		// 设备信息解析
		// DeviceInfo deviceInfo = new DeviceInfo();
		// deviceInfo.setSnCode(devElement.deviceId);
		// deviceInfo.setMode(devElement.deviceModel);
		// deviceInfo.setName(devElement.name);
		// deviceInfo.setCanUpgrade(devElement.canBeUpgrade);
		// deviceInfo.setAbility(StringToAbility(devElement.ability));
		// deviceInfo.setType(getDeviceType(devElement.baseline));
		// deviceInfo.setVersion(devElement.version);
		// deviceInfo.setShareFrom(devElement.beSharedFrom);
		// deviceInfo.setShareTo(devElement.beSharedTo);
		// deviceInfo.setLogo(devElement.logoUrl);
		// deviceInfo.setExtandAttributeValue(Share.ShareUserName.name(),
		// devElement.ownerUsername);
		// deviceInfo.setExtandAttributeValue(Share.ShareUserNcikName.name(),
		// devElement.ownerNickname);
		// if (devElement.online)
		// {
		// deviceInfo.setState(DeviceState.Online);
		// }
		// else
		// {
		// deviceInfo.setState(DeviceState.Offline);
		// }
		// devList.add(deviceInfo);

		Log.d(tag, "analyze device " + devElement.deviceId + " "
				+ devElement.name);

		// 解析通道列表
		if (devElement.channels != null) {
			for (ChannelsElement chnlElement : devElement.channels) {
				ChannelInfo chnlInfo = new ChannelInfo();
				chnlInfo.setDeviceCode(devElement.deviceId);
				chnlInfo.setDeviceModel(devElement.deviceModel);
				chnlInfo.setIndex(chnlElement.channelId);
				chnlInfo.setName(chnlElement.channelName);
				chnlInfo.setBackgroudImgURL(chnlElement.channelPicUrl);
				chnlInfo.setCloudMealStates(chnlElement.csStatus);
				chnlInfo.setAlarmStatus(chnlElement.alarmStatus);
				// // 是否为云台设备
				// if ((chnlElement.channelAbility != null
				// && chnlElement.channelAbility.contains("PTZ"))
				// || devElement.ability.contains("PTZ")) {
				// chnlInfo.setType(ChannelType.PtzCamera);
				// }
				// else {
				// chnlInfo.setType(ChannelType.Camera);
				// }
				// 是否支持加密
				if ((chnlElement.channelAbility != null && chnlElement.channelAbility
						.contains("HSEncrypt"))
						|| devElement.ability.contains("HSEncrypt")) {
					chnlInfo.setEncrypt(1);
				} else {
					chnlInfo.setEncrypt(0);
				}
				// 设置设备能力集
				chnlInfo.setAbility(StringToAbility(chnlElement.channelAbility)
						| StringToAbility(devElement.ability));

				// 设备与通道同时在线，通道才算在线
				if (chnlElement.channelOnline)
					switch (devElement.status) {
					case 0:
						chnlInfo.setState(ChannelInfo.ChannelState.Offline);
						break;
					case 1:
						chnlInfo.setState(ChannelInfo.ChannelState.Online);
						break;
					case 3:
						chnlInfo.setState(ChannelInfo.ChannelState.Upgrade);
						break;
					}
				channelList.add(chnlInfo);
			}
		}
		return channelList;
	}

	/**
	 * 设备能力集转换
	 * 
	 * @param strAbility
	 * @return
	 */
	public static int StringToAbility(String strAbility) {
		int ability = 0;
		if (strAbility == null) {
			return ability;
		}

		if (strAbility.contains("WLAN")) {
			ability |= ChannelInfo.Ability.WLAN;
		}
		if (strAbility.contains("AlarmPIR")) {
			ability |= ChannelInfo.Ability.AlarmPIR;
		}
		if (strAbility.contains("AudioTalk")) {
			ability |= ChannelInfo.Ability.AudioTalk;
		}
		if (strAbility.contains("VVP2P")) {
			ability |= ChannelInfo.Ability.VVP2P;
		}
		if (strAbility.contains("PTZ")) {
			ability |= ChannelInfo.Ability.PTZ;
		}
		if (strAbility.contains("HSEncrypt")) {
			ability |= ChannelInfo.Ability.HSEncrypt;
		}
		if (strAbility.contains("CloudStorage")) {
			ability |= ChannelInfo.Ability.CloudStorage;
		}
		return ability;
	}

	public void checkBindOrNot(final String deviceId, final Handler handler) {
		TaskPoolHelper.addTask(new TaskPoolHelper.RunnableTask("real") {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				CheckDeviceBindOrNot req = new CheckDeviceBindOrNot();
				req.data.token = mToken;
				req.data.deviceId = deviceId; // 设备id。
				// CheckDeviceBindOrNot.Response resp = null;
				RetObject retObject = null;
				retObject = request(req);

				// 将标示符和返回体发送给handle处理
				// retobject.resp = resp;
				handler.obtainMessage(retObject.mErrorCode, retObject)
						.sendToTarget();
			}
		});
	}

	/**
	 * 设备在线状态
	 * 
	 * @param deviceId
	 * @param handler
	 */
	public void checkOnline(final String deviceId, final Handler handler) {
		TaskPoolHelper.addTask(new TaskPoolHelper.RunnableTask("real") {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				DeviceOnline req = new DeviceOnline();
				req.data.token = mToken;
				req.data.deviceId = deviceId; // 设备id。
				RetObject retObject = null;
				retObject = request(req);

				// 将标示符和返回体发送给handle处理
				// retobject.resp = resp;
				handler.obtainMessage(retObject.mErrorCode, retObject)
						.sendToTarget();
			}
		});
	}

	/**
	 * 为账号绑定设备
	 * 
	 * @param deviceID
	 *            序列号
	 * @param handler
	 */
	public void bindDevice(final String deviceID, final Handler handler) {
		bindDevice(deviceID, "", handler);
	}

	public void bindDevice(final String deviceID, final String code,
						   final Handler handler) {

		TaskPoolHelper.addTask(new TaskPoolHelper.RunnableTask("real") {

			@Override
			public void run() {
				BindDevice req = new BindDevice();
				req.data.token = mToken;
				req.data.deviceId = deviceID;
				req.data.code = code;
				// BindDevice.Response resp = null;
				RetObject retObject = null;
				retObject = request(req, 5 * 1000);
				handler.obtainMessage(retObject.mErrorCode, retObject)
						.sendToTarget();
			}
		});
	}

	/**
	 * 为账号解绑设备
	 * 
	 *            是否删除云录像
	 * @param deviceID
	 *            序列号
	 * @param handler
	 */
	public void unBindDevice(final String deviceID, final Handler handler) {

		TaskPoolHelper.addTask(new TaskPoolHelper.RunnableTask("real") {
			@Override
			public void run() {
				UnBindDevice req = new UnBindDevice();
				req.data.token = mToken;
				req.data.deviceId = deviceID;
				// UnBindDevice.Response resp = null;
				RetObject retObject = null;
				retObject = request(req, 5 * 1000);
				handler.obtainMessage(retObject.mErrorCode, retObject)
						.sendToTarget();
			}
		});
	}

	// 网络周边
	public void getWifiStatus(final String ssid, final String deviceID,
							  final Handler handler) {
		TaskPoolHelper.addTask(new TaskPoolHelper.RunnableTask("real") {
			@Override
			public void run() {
				WifiAround req = new WifiAround();
				req.data.token = mToken;
				req.data.deviceId = deviceID;
				// WifiAround.Response resp = null;
				RetObject retObject = null;
				retObject = request(req, 45 * 1000);
				System.out.println("SSID:" + ssid);

				// 解析数据
				if (retObject.mErrorCode == 0) {
					// get device wifilist
					for (WifiAround.ResponseData.WLanElement element : ((WifiAround.Response) retObject.resp).data.wLan) {
						if (element.ssid.equals(ssid)) {
							System.out.println("obtainMessage:" + element.ssid);
							retObject.resp = element.bssid;
						}
					}
				}
				handler.obtainMessage(retObject.mErrorCode, retObject)
						.sendToTarget(); // 发送消息到界面
			}
		});
	}

	// 连接网络
	public void connectWifi(final String BSSID, final String ssid,
							final String password, final String deviceID, final Handler handler) {

		TaskPoolHelper.addTask(new TaskPoolHelper.RunnableTask("real") {

			@Override
			public void run() {
				ControlDeviceWifi req = new ControlDeviceWifi();
				req.data.password = password;
				req.data.linkEnable = true;
				req.data.ssid = ssid;
				req.data.deviceId = deviceID;
				req.data.bssid = BSSID;
				// ControlDeviceWifi.Response resp = null;
				System.out.println(ssid + "--" + password + "--" + deviceID);
				RetObject retObject = null;
				retObject = request(req, 45 * 1000); // dms timeout
				handler.obtainMessage(retObject.mErrorCode, retObject)
						.sendToTarget(); // 发送消息到界面
			}
		});
	}

	public void setAlarmPlanConfig(final String deviceID,
								   final String optional, final List<RulesElement> rules,
								   final Handler handler) {

		TaskPoolHelper.addTask(new TaskPoolHelper.RunnableTask("real") {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				ModifyDeviceAlarmPlan req = new ModifyDeviceAlarmPlan();
				req.data.deviceId = deviceID;
				req.data.channelId = optional;

				// System.out.println("channels.get(0).rules.size:"+channels.get(0).rules.size());
				for (RulesElement ele : rules) {
					System.out.println(ele.beginTime);
					System.out.println(ele.endTime);
					System.out.println(ele.period);
				}
				RetObject retObject = null;
				retObject = request(req);
				handler.obtainMessage(retObject.mErrorCode, retObject)
						.sendToTarget(); // 发送成功消息到界面
			}
		});

	}

	/**
	 * 查询报警计划配置
	 * 
	 * @param deviceID
	 *            设备id
	 * @param optional
	 *            通道id
	 * @param handler
	 *            回调
	 */
	public void getAlarmPlanConfig(final String deviceID,
								   final String optional, final Handler handler) {

		TaskPoolHelper.addTask(new TaskPoolHelper.RunnableTask("real") {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				DeviceAlarmPlan req = new DeviceAlarmPlan();
				req.data.deviceId = deviceID; // 过滤条件，可指定获取特定设备序列号的设备信息。
				req.data.channelId = optional; // 通道id
				DeviceAlarmPlan.Response resp = null;
				RetObject retObject = null;
				retObject = request(req);

				// 网络请求失败
				if (resp == null || resp.data == null) {
					// throw new
					// BusinessException(BusinessErrorCode.BEC_COMMON_NULL_POINT);
				}

				resp = (DeviceAlarmPlan.Response) retObject.resp;
				// 如果设备在线
				if (resp.data != null) {
					retObject.resp = resp.data.rules;
				}
				handler.obtainMessage(retObject.mErrorCode, retObject)
						.sendToTarget(); // 发送消息到界面
			}
		});

	}


	/**
	 * 查询通道下的录像总数
	 *
	 */
	public void queryRecordNum(String chnlId, final String startTime,
							   final String endTime, final Handler handler) {
		final ChannelInfo chl = getChannel(chnlId);
		if (chl == null) {
			return;
		}
		TaskPoolHelper.addTask(new TaskPoolHelper.RunnableTask("real") {
			@Override
			public void run() {
				QueryLocalRecordNum req = new QueryLocalRecordNum();
				req.data.token = mToken;
				req.data.beginTime = startTime;
				// req.data.type = "All"; 默认为all
				req.data.channelId = String.valueOf(chl.getIndex());
				req.data.endTime = endTime;
				req.data.deviceId = chl.getDeviceCode();
				Log.d(tag, "strStartTime:" + startTime + "strEndTime:" + endTime);

				RetObject retObject = null;
				retObject = request(req, DMS_TIMEOUT);
				QueryLocalRecordNum.Response resp = (QueryLocalRecordNum.Response) retObject.resp;

				// 网络请求失败
				if (retObject.mErrorCode != 0) {
					Log.d(tag, "QueryLocalRecordNum faied " + retObject.mMsg);
					handler.obtainMessage(retObject.mErrorCode).sendToTarget();
				} else {
					if (resp.data == null) {
						Log.d(tag, "QueryLocalRecordNum success data is null");
					}
					// todo:其他信息解析请见OpenAPI 平台sdk文档
					handler.obtainMessage(0, resp.data.recordNum, 
							resp.data.recordNum > 10 ? resp.data.recordNum - 9 : 1).sendToTarget(); // 发送成功消息到界面
				}
			}
		});
	}

	/**
	 * 查询通道下的录像列表
	 * 
	 * @param chnlId
	 * @param startTime
	 * @param endTime
	 * @param startIndex
	 * @param endIndex
	 * @param handler
	 */
	public void queryRecordList(String chnlId, final String startTime,
								final String endTime, int startIndex, int endIndex,
								final Handler handler) {
		mRecordInfoList.clear(); //清数据
		
		final ChannelInfo chl = getChannel(chnlId);
		if (chl == null) {
			return;
		}

		final String strNneed = String.valueOf(startIndex) + "-"
				+ String.valueOf(endIndex); // 需要录像的条数，从那条记录到那一条，demo只读取前面5条，不查询完所有录像

		TaskPoolHelper.addTask(new TaskPoolHelper.RunnableTask("real") {

			@Override
			public void run() {

				QueryLocalRecords req = new QueryLocalRecords();
				req.data.token = mToken;
				req.data.beginTime = startTime;
				// req.data.type = "All"; 默认为all
				req.data.channelId = String.valueOf(chl.getIndex());
				req.data.queryRange = strNneed;
				req.data.endTime = endTime;
				req.data.deviceId = chl.getDeviceCode();
				Log.d(tag, "strStartTime:" + startTime + "strEndTime:" + endTime);

				RetObject retObject = null;
				retObject = request(req);
				QueryLocalRecords.Response resp = (QueryLocalRecords.Response) retObject.resp;
				
				// 网络请求失败
				if (retObject.mErrorCode != 0) {
					Log.d(tag, "QueryRecordList faied " + retObject.mMsg);
					handler.obtainMessage(retObject.mErrorCode).sendToTarget();
				} else {
					if (resp.data == null || resp.data.records == null) {
						Log.d(tag, "queryRecordList success data is null");
					}

					for (QueryLocalRecords.ResponseData.RecordsElement element : resp.data.records) {
						RecordInfo info = new RecordInfo();
						info.setChnlUuid(chl.getUuid());
						long dateStart = TimeHelper
								.getTimeStamp(element.beginTime);
						info.setStartTime(dateStart);
						long dateEnd = TimeHelper.getTimeStamp(element.endTime);
						info.setEndTime(dateEnd);
						info.setRecordPath(element.recordId);
						
						mRecordInfoList.add(info);
					}
					Collections.reverse(mRecordInfoList);// 反序 临时使用下
					// todo:其他信息解析请见OpenAPI 平台sdk文档
					handler.obtainMessage(0, mRecordInfoList).sendToTarget(); // 发送成功消息到界面
				}
			}
		});
	}

	/**
	 * 查询通道下的云录像总数
	 */
	public void queryCloudRecordNum(String chnlId, final String startTime,
									final String endTime, final Handler handler) {

		final ChannelInfo chl = getChannel(chnlId);
		if (chl == null) {
			return;
		}
		//
		TaskPoolHelper.addTask(new TaskPoolHelper.RunnableTask("real") {

			@Override
			public void run() {
				QueryCloudRecordNum req = new QueryCloudRecordNum();
				req.data.token = mToken;
				req.data.beginTime = startTime;
				req.data.endTime = endTime;
				req.data.channelId = String.valueOf(chl.getIndex());
				req.data.deviceId = chl.getDeviceCode();
				Log.d(tag, "startTime:" + req.data.beginTime + "endTime:"
						+ req.data.endTime + "channelId:" + req.data.channelId
						+ "deviceId:" + chl.getDeviceCode());

				RetObject retObject = null;
				retObject = request(req); // 码流请求超时时间长一些
				QueryCloudRecordNum.Response resp = (QueryCloudRecordNum.Response) retObject.resp;
				// 网络请求失败
				if (retObject.mErrorCode != 0) {
					Log.d(tag, "QueryCloudRecordNum faied " + retObject.mMsg);
					handler.obtainMessage(retObject.mErrorCode).sendToTarget();
				} else {
					if (resp.data == null) {
						Log.d(tag, "QueryCloudRecordNum success data is null");
					}
					// todo:其他信息解析请见OpenAPI 平台sdk文档
					handler.obtainMessage(0, resp.data.recordNum, 
							resp.data.recordNum > 10 ? resp.data.recordNum - 9 : 1).sendToTarget(); // 发送成功消息到界面
				}
			}
		});
	}

	/**
	 * 查询通道下的云录像列表
	 * 
	 * @param chnlId
	 * @param startTime
	 * @param endTime
	 * @param startIndex
	 * @param endIndex
	 * @param handler
	 */
	public void queryCloudRecordList(String chnlId, final String startTime,
									 final String endTime, int startIndex, int endIndex,
									 final Handler handler) {
		mRecordInfoList.clear();   //清数据
		final ChannelInfo chl = getChannel(chnlId);
		if (chl == null) {
			return;
		}

		final String strNneed = String.valueOf(startIndex) + "-"
				+ String.valueOf(endIndex); // 需要录像的条数，从那条记录到那一条，demo只读取前面10条，不查询完所有录像
		//
		TaskPoolHelper.addTask(new TaskPoolHelper.RunnableTask("real") {

			@Override
			public void run() {
				QueryCloudRecords req = new QueryCloudRecords();
				req.data.token = mToken;
				req.data.beginTime = startTime;
				req.data.endTime = endTime;
				req.data.channelId = String.valueOf(chl.getIndex());
				req.data.queryRange = strNneed;
				req.data.deviceId = chl.getDeviceCode();
				Log.d(tag, "startTime:" + req.data.beginTime + "endTime:"
						+ req.data.endTime + "channelId:" + req.data.channelId
						+ "deviceId:" + chl.getDeviceCode() + "strNneed:"
						+ strNneed);

				RetObject retObject = null;
				retObject = request(req); // 码流请求超时时间长一些
				QueryCloudRecords.Response resp = (QueryCloudRecords.Response) retObject.resp;
				// 网络请求失败
				if (retObject.mErrorCode != 0) {
					Log.d(tag, "queryCloudRecordList faied " + retObject.mMsg);
					handler.obtainMessage(retObject.mErrorCode).sendToTarget();
				} else {
					if (resp.data == null || resp.data.records == null) {
						Log.d(tag, "queryRecordList success data is null");
					}

					for (RecordsElement element : resp.data.records) {
						RecordInfo info = new RecordInfo();
						info.setChnlUuid(chl.getUuid());

						String dateStart = element.beginTime;
						// dateStart *= 1000;
						info.setStartTime(TimeHelper.getTimeStamp(dateStart));
						String dateEnd = element.endTime;
						// dateEnd *= 1000;
						info.setEndTime(TimeHelper.getTimeStamp(dateEnd));
						info.setRecordID(element.recordId);
						info.setBackgroudImgUrl(element.thumbUrl);
						info.setDeviceId(element.deviceId);
						info.setType(RecordInfo.RecordType.PublicCloud);
						info.setFileLength(Long.parseLong(element.size));
						mRecordInfoList.add(info);
					}
					Collections.reverse(mRecordInfoList);// 反序 临时使用下
					// todo:其他信息解析请见OpenAPI 平台sdk文档
					handler.obtainMessage(0, mRecordInfoList).sendToTarget(); // 发送成功消息到界面
				}
			}
		});
	}
	
	/**
	 * 获取设备动检计划
	 * @param chnlId
	 * @param handler
	 */
	public void getAlarmStatus(final String chnlId, final Handler handler) {

		final ChannelInfo chl = getChannel(chnlId);
		if (chl == null) {
			return;
		}
		TaskPoolHelper.addTask(new TaskPoolHelper.RunnableTask("real") {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				DeviceAlarmPlan req = new DeviceAlarmPlan();
				req.data.token = mToken;
				req.data.deviceId = chl.getDeviceCode();
				req.data.channelId = String.valueOf(chl.getIndex());

				DeviceAlarmPlan.Response resp = null;
				RetObject retObject = null;
				retObject = request(req);
				resp = (DeviceAlarmPlan.Response)retObject.resp;

				if(retObject.mErrorCode !=0){
					handler.obtainMessage(retObject.mErrorCode).sendToTarget();
				}else{
					int arg1 = resp.data.rules.get(0).enable == true ? 1:0;
					handler.obtainMessage(0,arg1,0).sendToTarget();
					
				}
	

			}
		});
	}

	/**
	 * 改变设备的动检开关
	 * 
	 * @param enable
	 */
	public void modifyAlarmStatus(final boolean enable, final String chnlId,
			final Handler handler) {
		final ChannelInfo chl = getChannel(chnlId);
		if (chl == null) {
			return;
		}
		TaskPoolHelper.addTask(new TaskPoolHelper.RunnableTask("real") {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				ModifyDeviceAlarmStatus req = new ModifyDeviceAlarmStatus();
				req.data.token=mToken;
				req.data.deviceId = chl.getDeviceCode();
				req.data.channelId = String.valueOf(chl.getIndex());
				req.data.enable = enable;

				ModifyDeviceAlarmStatus.Response resp = null;
				RetObject retObject = null;
				retObject = request(req);
				

				// 网络请求失败
				handler.obtainMessage(retObject.mErrorCode).sendToTarget();

				
			}
		});
	}
	
	public void setStorageStartegy(final String enable, final String chnlId,
								   final Handler handler) {
		final ChannelInfo chl = getChannel(chnlId);
		if (chl == null) {
			return;
		}
		TaskPoolHelper.addTask(new TaskPoolHelper.RunnableTask("real") {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				SetAllStorageStrategy req = new SetAllStorageStrategy();
				req.data.token=mToken;
				req.data.deviceId = chl.getDeviceCode();
				req.data.channelId = String.valueOf(chl.getIndex());
				req.data.status = enable;

				SetAllStorageStrategy.Response resp = null;
				RetObject retObject = null;
				retObject = request(req);
				

				// 网络请求失败
				handler.obtainMessage(retObject.mErrorCode).sendToTarget();

				
			}
		});
	}




	/**
	 * 检索已查询出来的录像对象
	 * 
	 * @param id
	 * @return
	 */
	public RecordInfo getRecord(String id) {
		// TODO Auto-generated method stub
		for (RecordInfo red : mRecordInfoList) {
			if (red.getId().equals(id)) {
				return red;
			}
		}
		return null;
	}
}
