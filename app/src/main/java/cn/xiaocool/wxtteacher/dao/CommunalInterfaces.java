package cn.xiaocool.wxtteacher.dao;

/**
 * Created by mac on 16/1/31.
 */

public interface CommunalInterfaces {
    int STATE = 1;
    String _STATE = "success";
    int SEND_APPLY = 0x11;
    int CITY = 0x12;
    int MEETSPECIALIIST_STATE = 0x13;
    int SERVICE_LIST = 0x14;
    int GETSERVICEUSERDATA = 0x16;
    int SOUSUO_LIST = 0x17;
    int ADDMAKESERVICE = 0x18;
    String FIRSTKEY = "6be10ac3ed9ea86c";
    int GETLIST = 0x21;
    int ITEM_LAYOUT_TYPE_COUNT = 2;
    int TYPE_ONE = 0;
    int TYPE_TWO = 1;
    int GETUSERDATA = 0x22;
    int USERCITY = 0x23;
    int SPICALISTCITY = 0x24;
    int AUDITYESSERVICE = 0x25;
    int AUDITNOSERVICE = 0x26;
    int UPDATE_BANKCARD = 0x27;
    int ADDREFERRAL = 0x28;
    int DELMEMBERCARD = 0x29;
    int GETMEMBERREFERRAL = 0x30;
    int GETSPECIALISTIMG = 0x31;
    int GETHTTPHEADIMG = 0x32;
    int GETSERVICECITY = 0x33;
    int GETMEMBERWITHLIST = 0x34;

    /* 资讯-专栏——begin */
    int GETNEWLIST = 0xa;
    int MEMBERCOLUMN = 0xb;
    int INFORMATIONLIST = 0xc;
    int NEWSMETTER = 0xd;
    int NEWSRELATED = 0xe;
    int INDEXNEWSLIST =0x39;
    int INDEXSLIDENEWSLIST = 0x35;
    int DISCUSS = 0x36;
    int DISCUSSLIST = 0x37;
    int USERDATE = 0x38;
    int INDEXPUSHNEWSLIST = 0xf;
    int GETPUSHNEWSLIST=16;
    int NETGETSEARCH = 0x40;
    int GETCOLUMN = 0x41;
    int COLUMNLIKE = 0x42;
    int NEWCOLLECT = 0x43;
    int WORKDISCUSS = 0x45;
	/* ——end—— */

//校网
    //获取通知公告
    int GETANNOUNCEMENT = 0x44;
    int ADDWORKRING = 0x46;
    int GETPROJECTLIST = 0x47;
    int PROJECTCLASS = 0x48;
//我的界面
    //获取服务状态
    int GETSERVICESTATUS = 0x49;
    int GETINFORMATIONUPWARD = 0x50;
    int HIM_GETNEWCOLLECT=0x51;
// 宝宝空间
    //获取身高体重
    int GET_STATURE_WEIGHT=0x52;
    //获取当前孩子信息
    int GET_TIME_TEM=0x53;
    //写入体重数据
    int INPUT_WEIGHT=0x54;
    //写入身高数据
    int INPUT_HEIGHT=0x55;
    //获取收到的消息
    int RECEIVED_MESSAGE=0x56;
    //获取发送的消息
    int SEND_MESSAGE=0x57;
    //获取食谱
    int RECIPES = 0x57;
    //获取通知公告
    int ANNOUNCEMENT = 0x58;
    //获取待办事项
    int BACKLOG = 0x60;
    //获取老师点评
    int TEACHER_REVIEW  = 0x58;
    //获取班级课件
    int  CLASS_COURSEWARE= 0x59;
    //获取班级活动
    int CLASS_EVENTS = 0x70;
    //获取育儿知识
    int REARING_CHILD = 0x71;
    //获取开心学堂
    int HAPPY_SCHOOL = 0x72;
    //获取班级课程
    int CLASS_SCHEDULE = 0x73;
    //获取宝宝资料
    int BABY_INFO = 0x74;
    //获取宝宝老师
    int BABY_TEACHER = 0x75;
    //获取在线请假
    int LEAVE_REASON = 0x76;
    //在线客服
    int ONLINE_SERVICE = 0x77;
    //家长叮嘱
    int PARENT_WARN = 0x78;
    //通讯录
    int MESSAGEADDRESS=0x79;
    //发消息
    int SENDTOTEACJER = 0x80;
    //周计划
    int GETSCHOOLPLAN = 0x81;
    int ADDTEACHERCOMMENT = 0x82;
    //消息列表
    int GETNEWSLIST = 0x83;
    int SAVEINFO = 0x84;

    //班级学生列表
    int STUDENT_LIST = 0x90;
    //发送通知公告
    int WRITE_ANNOUNCEMENT = 0x91;
    //获取班级作业
    int HOMEWORK = 0x92;
    //获取待接去人列表
    int COLLECTION = 0x93;

    int CLASSLIST = 0x94;
    //发作业
    int SEND_HOMEWORK = 0x95;
    //发送家长叮嘱回复
    int SEND_PARENT_REMARK = 0x96;
    //获取点赞列表
    int GET_LIKE = 0x97;

    //获取评论列表
    int GET_COMMENTS = 0x98;

    //获取教师风采
    int TEACHERINFO = 0x99;
    //获取食谱
    int RECIPESNEW = 0x100;
    //获取园丁列表
    int TEACHER = 0x101;
    //获取班级和学生
    int CLASS_STU = 0x102;
    //信息群发
    int NEWSGROUP = 0x109;

    public static final String APP_ID="wx9aa88ce0b796d68f";
    /**
     * 6种状态 0=>待审核 1=>未评价 2=>约见成功 3=>审核失败 4=>通过审核 5=>待付款
     */
    String MAKESTATE_CHECKPENDING = "0";
    String MAKESTATE_UNVALUED = "1";
    String MAKESTATE_SUCCESS = "2";
    String MAKESTATE_FAILUREAUDIT = "3";
    String MAKESTATE_STATUSCHECK = "4";
    String MAKESTATE_OBLIGATION = "5";

}
