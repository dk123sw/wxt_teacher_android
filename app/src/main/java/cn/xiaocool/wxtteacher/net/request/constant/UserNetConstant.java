package cn.xiaocool.wxtteacher.net.request.constant;

/**
 * Created by mac on 16/5/9.
 */
public interface UserNetConstant extends NetBaseConstant {
    /**
     *
     */
    public final static String NET_GET_MASTER_KAY = NET_BASE_HOST + "/Basic/SetMasterKey";
    /**
     * 用户注册 /User/UserSet/CostalRegister
     */
    public final static String NET_USER_REGISTER = NET_BASE_HOST + "/index.php?g=apps&m=index&a=AppRegister";
    /**
     * 用户登陆 /User/UserSet/CostalLogin
     */
    public final static String NET_USER_LOGIN = NET_BASE_HOST + "/index.php?g=apps&m=index&a=applogin";
    /**
     * 判断用户是否已注 /User/UserSet/IsRegPhone
     */
    public final static String NET_USER_IS_REG = NET_BASE_HOST + "/index.php?g=apps&m=index&a=isregphone";
    /**
     * 获取验证/Basic/GetSms
     */
    public final static String NET_USER_GET_VERIFICATION_CODE = NET_BASE_HOST + "/index.php?g=apps&m=index&a=SendMobileCode";
    /**
     * 获取用户信息 /User/UserSet/GetUserData
     */
    public final static String NET_USER_GET_DATA = NET_BASE_HOST + "/User/UserSet/GetUserData";
    /**
     * 修改用户个人资料接口[密码] /User/UserSet/UpdatePass
     */
    public final static String NET_USER_UPDATE_PASS = NET_BASE_HOST + "/index.php?g=apps&m=index&a=UpdatePass";
    /**
     * 修改用户个人资料接口[姓名] /User/UserSet/UpdateUserName
     */
    public final static String NET_USER_UPDATE_NAME = NET_BASE_HOST + "/User/UserSet/UpdateUserName";
    /**
     * 修改用户个人资料接口[职位] /User/UserSet/UpdateUserPostition
     */
    public final static String NET_USER_UPDATE_POSTION = NET_BASE_HOST + "/User/UserSet/UpdateUserPostition";
    /**
     * 修改用户个人资料接口[性别] /User/UserSet/UpdateUserSex
     */
    public final static String NET_USER_UPDATE_SEX = NET_BASE_HOST + "/User/UserSet/UpdateUserSex";
    /**
     * 修改用户个人资料接口[邮箱] /User/UserSet/UpdateUserEmail
     */
    public final static String NET_USER_UPDATE_EMAIL = NET_BASE_HOST + "/User/UserSet/UpdateUserEmail";
    /**
     * 修改用户个人资料接口[�?在城市] /User/UserSet/UpdateUserCityId
     */
    public final static String NET_USER_UPDATE_CITY = NET_BASE_HOST + "/User/UserSet/UpdateUserCityId";
    /**
     * 修改用户个人资料接口[公司名称] /User/UserSet/UpdateUserCompany
     */
    public final static String NET_USER_UPDATE_COMPANY = NET_BASE_HOST + "/User/UserSet/UpdateUserCompany";
    /**
     * 修改用户个人资料接口[注册手机号] /User/UserSet/UpdatePhone
     */
    public final static String NET_USER_UPDATE_PHONE = NET_BASE_HOST + "/User/UserSet/UpdatePhone";
    /**
     * 头像上传 /User/UserSet/UploadMemberImg
     */
    public final static String NET_USER_UPLOAD_HEAD_IMG = "http://wxt.xiaocool.net/index.php?g=apps&m=index&a=WriteMicroblog_upload";
    /**
     * 添加工作经验 /User/UserSet/AddUndergo
     */
    public final static String NET_USER_ADD_WORKING = NET_BASE_HOST + "/User/UserSet/AddUndergo";
    /**
     * 修改工作经验 /User/UserSet/UpdateUndergo
     */
    public final static String NET_USER_UPDATE_WORKING = NET_BASE_HOST + "/User/UserSet/UpdateUndergo";
    /**
     * 查询工作经验 /User/UserSet/GetUndergo
     */
    public final static String NET_USER_GET_WORKING = NET_BASE_HOST + "/User/UserSet/GetUndergo";
    /**
     * 附近的人 /User/UserSet/GetNearMember
     */
    public final static String NET_USER_GET_NEARMEMBER = NET_BASE_HOST + "/User/UserSet/GetNearMember";
    /**
     * 附近的人申请好友 /User/UserSet/ApplyFriends
     */
    public final static String NET_USER_APPLY_FRIENDS = NET_BASE_HOST + "/User/UserSet/ApplyFriends";
    /**
     * 附近的人申请好友 /User/UserSet/ApplyFriends
     */
    public final static String NET_USER_GET_WORKLIST_IMG = NET_BASE_HOST + "/User/UserSet/GetMemberWorkLast";
    /**
     * 提交意见反馈 /User/UsetSet/SubmitIdea
     */
    public final static String NET_USER_SUBMIT_IDEA = NET_BASE_HOST + "/User/UserSet/SubmitIdea";
    /**
     * 获取系统消息 /User/UserSet/SystemMessage
     */
    public final static String NET_USER_GET_SYSTEM_MESSAGE = NET_BASE_HOST + "/User/UserSet/SystemMessage";
    /**
     * 系统消息已读 /User/UserSet/ReadMessage
     */
    public final static String NET_USER_GET_SYSTEM_MESSAGE_READ = NET_BASE_HOST + "/User/UserSet/SystemMessage";
    /**
     * 系统消息未读消息数量 /User/UserSet/UnReadMessageNum
     */
    public final static String NET_USER_GET_SYSTEM_MESSAGE_UN_READ_NUM = NET_BASE_HOST + "/User/UserSet/UnReadMessageNum";
    /**
     *
     */
    public final static String NET_USER_GET_APPLY_FIREND = NET_BASE_HOST + "/User/UserSet/GetApplyFirend";
    /**
     * 删除申请好友记录 /User/UserSet/DelApplyFirend
     */
    public final static String NET_USER_DEL_APPLY_FIREND = NET_BASE_HOST + "/User/UserSet/DelApplyFirend";
    /**
     * 删除申请好友记录 /User/UserSet/GetAddress
     */
    public final static String NET_USER_GET_ADDRESS = NET_BASE_HOST + "/User/UserSet/GetAddress";
    /**
     * 获取安卓版本Basic/android_version
     */
    public final static String NET_USER_GET_ANDROID_VERSION = NET_BASE_HOST + "/Basic/android_version";
    /**
     * 获取安卓新安装包Basic/GetAndroid
     */
    public final static String NET_USER_GET_ANDROID = NET_BASE_HOST + "/Basic/GetAndroid";
    /**
     * 获取用户好友数量/User/UserSet/GetMemberFriendNum
     */
    public final static String NET_USER_GET_FERIEND_COUNT=NET_BASE_HOST + "/User/UserSet/GetMemberFriendNum";
    /**
     * 获取粉丝列表数量[大咖第一视角] /User/UserSet/GetFansNum
     */
    public final static String NET_USER_GET_FANS_COUNT=NET_BASE_HOST + "/User/UserSet/GetFansNum";
    /**
     * 获取全部已关注大咖列表数/User/UserSet/GetAllFoucsKaNum
     */
    public final static String NET_USER_GET_FOCUSKA_COUNT=NET_BASE_HOST + "/User/UserSet/GetAllFoucsKaNum";


}
