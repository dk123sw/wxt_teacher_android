package cn.xiaocool.wxtteacher.net.request.constant;

/**
 * Created by wzh on 2016/2/27.
 */
public interface WebHome extends NetBaseConstant {
    /**
     * 获取通知公告栏
     */
    public static final String NET_GET_ANNOUNCEMENT = NET_API_HOST + "a=GetSeverStatus";

    /**
     * 获取用户绑定的当前孩子信息
     */
    String WEB_GET_BABY_INFO = NET_API_HOST +"a=GetUserRelation";
    /**
     * 获取到校时间体温，离校时间、体温
     */
    String WEB_GET_TIME_TEM = NET_API_HOST +"a=GetStudentLog";
    /**
     * 获取身高、体重数据
     */
    String WEB_GET_STATURE_WEIGHT = NET_API_HOST +"a=GetChange_sta_wei&stuid";
    /**
     * 获取服务状态
     */
    String ME_GET_SERVICE_STATUS = NET_API_HOST + "a=GetSeverStatus";

    /**
     * 写入体重数据
     */
    String SPACE_INPUT_WEIGHT = NET_API_HOST + "a=RecordWeight";
    /**
     * 写入身高数据
     */
    String SPACE_INPUT_HEIGHT = NET_API_HOST + "a=RecordHeight";
    /**
     * 消息界面收到的消息
     */
    String SPACE_RECEIVED_MESSAGE = NET_API_HOST + "a=ReceiveidMessage";
    /**
     * 消息界面收到的消息
     */
    String SPACE_RECEIVED_TCLEAVE = NET_API_HOST1 + "a=getleavelist";
    /**
     * 消息界面发送的消息
     */
    String SPACE_SEND_MESSAGE = NET_API_HOST + "a=SentMessage";

    /**
     * 获取食谱
     */
    String RECIPES = NET_API_HOST + "a=WeekRecipe";
    /**
     * 获取新食谱
     */
    String RECIPESNEW = NET_API_HOST2 + "a=getCookbookContent";
    /**
     * 获取通知公告条目
     */
    String ANNOUNCEMENT = NET_API_HOST2 + "a=getnoticelist";
    /**
     * 获取待办事项
     */
    String BACKLOG_SEND = NET_API_HOST2 + "a=GetMySendSchedulelist";
    /**
     * 获取待办事项
     */
    String BACKLOG_RECIVE = NET_API_HOST2 + "a=GetMyReciveSchedulelist";
    /**
     * 发送通知公告
     */
    String WRITE_ANNOUNCEMENT = NET_API_HOST2+"a=publishnotice";

    /**
     * 获取老师点评
     */
    String TEACHER_REVIEW = NET_API_HOST + "a=TeacherComment";

    /**
     * 获取班级课件
     */
    String CLASS_COURSEWARE = NET_API_HOST + "a=SchoolCourseware";

    /**
     * 获取在线请假
     */
    String LEAVE_REASON = NET_API_HOST+"a=OnlineLeave";
    /**
     * 获取家长叮嘱
     */
    String PARENT_WARN = NET_API_HOST1+"a=getentrustlist";
    /**
     * 获取班级活动
     */
    String CLASS_EVENTS = NET_API_HOST1+"a=getactivitylist";
    /**
     * 获取育儿知识
     */
    String REARING_CHILD = NET_API_HOST+"a=ParentingKnowledge";
    /**
     * 获取开心学堂
     */
    String HAPPY_SCHOOL = NET_API_HOST+"a=HappySchool";
    /**
     * 发送家长叮嘱
     */
    String PARENTS_TOLD = NET_API_HOST+"a=PatriarchEnjoin";
    /**
     * 获取班级课表
     */
    String CLASS_SCHEDULE = NET_API_HOST2+"a=ClassSyllabus";
    /**
     * 获取宝宝老师
     */
    String BABY_TEACHER = NET_API_HOST+"a=OnlineLeaveTeacher";
    /**
     * 获取在线客服联系方式
     */
    String ONLINE_SERVICE = NET_API_HOST +"a=service";
    /*
  *获取通讯录
  **/
    String MESSAGEADDRESS = NET_API_HOST+"a=MessageAddress";
    String TEACHER_ADDRESS = NET_API_HOST+"a=MessageAddress";
    /**
     * 获取班级学生列表
     */
    String STUDENT_LIST = NET_API_HOST2+"a=getstudentlist";
    /**
     * 获取发送消息
     */
    String SENDTOTEACJER = NET_API_HOST+"a=SendMessage";
    /**
     * 获取教师评论
     */
    String ADDTEACHERCOMMENT = NET_API_HOST1+"a=AddTeacherComment";
    /*
    * 编辑老师资料
    */
    String SAVEINFO = NET_TEACHER_HOST+"a=saveinfo";
    /*
    *获取周计划
    **/
    String GETSCHOOLPLAN = NET_SCHOOL_HOST+"a=getschoolplan";
    /**
     * 获取班级作业
     */
    String HOMEWORK = NET_API_HOST1+"a=gethomeworklist";
    /**
     * 获取信息群发
     */
    String NEWSGROUPS = NET_API_HOST4+"a=user_send_message";
    /**
     * 获取园丁列表
     */
    String TEACHER = NET_API_HOST2+"a=getteacherinfo";
    /**
     * 获取待接确认列表
     */
    String COLLECTION = NET_API_HOST1 +"a=gettransportconfirmation";
    /**
     * 老师所在班级
     */
    String CLASSLIST = NET_API_HOST1+"a=getteacherclasslist";
    /**
     * 老师所在班级和学生
     */
    String CLASS_STULIST = NET_API_HOST1+"a=getteacherclasslistandstudentlist";
    /**
     * 发送作业
     */
    String SEND_HOMEWORK = NET_API_HOST1+"a=addhomework";

    /**
     * 发送通知公告
     */
    String SEND_ANNOUNCE = NET_API_HOST2+"a=publishnotice";
    /**
     * 发送群发消息
     */
    String SEND_NEWSGROUP = "http://wxt.xiaocool.net/index.php?g=apps&m=message&"+"a=send_message";
    /**
     * 发送动态
     */
    String SEND_TRENDS = NET_API_HOST+"a=WriteMicroblog";
    /**
     * 发送待办事项
     */
    String SEND_SCHEDULE = NET_API_HOST2+"a=addschedule";
    /**
     * 处理待办事项
     */
    String DO_SCHEDULE = NET_API_HOST2+"a=DoSchedul";
    /**
     * 发送报名
     */
    String SEND_APPLY = NET_API_HOST2+"a=enterschol";
    /**
     * 发布活动
     */
    String SEND_EVENT = NET_API_HOST1+"a=addactivity";
    /**
     * 发布待接
     */
    String SEND_CONFIM = NET_API_HOST1+"a=addtransport";
    /**
     * 发送家长叮嘱回复
     */
    String SEND_PARENT_REMARK = NET_API_HOST1+"a=feedbackentrust";
    /**
     * 发送评论
     */
    String SEND_COMMENT = NET_API_HOST2+"a=SetComment";
    /**
     * 获取班级考勤
     */
    String GET_CLASSATTEND = NET_API_HOST1+"a=GetStudentAttendanceList";
    /**
     * 获取用户考勤
     */
    String GET_ATTEND = NET_API_HOST+"a=GetAttendance";
    /**
     * 获取评论列表
     */
    String GET_COMMENTS = NET_API_HOST2+"a=GetCommentlist";
    /**
     * 获取点赞列表
     */
    String GET_LIKE = NET_API_HOST2+"a=GetLikelist";
    /**
     * 点赞
     */
    String NET_SET_PRAISE = NET_API_HOST2+"a=SetLike";
    /**
     * 取消赞
     */
    String NET_DEL_PRAISE = NET_API_HOST2+"a=ResetLike";
    /**
     * 取消赞
     */
    String NET_GETTEACHER_INFO = NET_API_HOST2+"a=";
}
