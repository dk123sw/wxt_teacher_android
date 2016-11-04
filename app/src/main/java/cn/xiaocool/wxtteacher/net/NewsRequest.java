package cn.xiaocool.wxtteacher.net;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.xiaocool.wxtteacher.bean.UserInfo;
import cn.xiaocool.wxtteacher.dao.CommunalInterfaces;
import cn.xiaocool.wxtteacher.net.request.constant.NetUtil;
import cn.xiaocool.wxtteacher.net.request.constant.WebHome;
import cn.xiaocool.wxtteacher.utils.LogUtils;
import cn.xiaocool.wxtteacher.utils.NetBaseUtils;
import cn.xiaocool.wxtteacher.utils.TimeToolUtils;

/**
 * Created by 潘 on 2016/3/31.
 */
public class NewsRequest {
    private Context mContext;
    private Handler handler;
    private UserInfo user;

    public NewsRequest(Context context, Handler handler) {
        super();
        this.mContext = context;
        this.handler = handler;
        user = new UserInfo();
        user.readData(mContext);
    }


    public void send_chat(final List<NameValuePair> params,final int KEY){
        new Thread() {
            Message msg = Message.obtain();

            public void run() {

                String result_data = NetBaseUtils.getResponseForPost("http://wxt.xiaocool.net/index.php?g=apps&m=message&a=SendChatData", params, mContext);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = KEY;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();

    }
    //最新消息
    public void send_suggest(final String name, final int key) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&userid=" + user.getUserId()+"&message="+name;
                String result_data = NetUtil.getResponse("http://wxt.xiaocool.net/index.php?g=apps&m=index&a=LeaveMessage", data);
                LogUtils.e("getleavelist", WebHome.SPACE_RECEIVED_TCLEAVE + data);
                try {
                    JSONObject jsonObject = new JSONObject(result_data);
                    msg.what = key;
                    msg.obj = jsonObject;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }
    //发送老师点评
    public void send_teacherRemark(final String studentId,final String content, final String learn,final String work, final String sing,final String labour, final String strain,final int key) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&teacherid="+user.getUserId()
                        +"&studentid="+studentId+"&content="+content+"&learn="+learn+"&work="+
                        work+"&sing="+sing+"&labour="+labour+"&strain="+strain;
                String result_data = NetUtil.getResponse("http://wxt.xiaocool.net/index.php?g=apps&m=teacher&a=AddTeacherComment", data);
                LogUtils.e("send_replayleave", "http://wxt.xiaocool.net/index.php?g=apps&m=teacher&a=replyleave" + data);
                try {
                    JSONObject jsonObject = new JSONObject(result_data);
                    msg.what = key;
                    msg.obj = jsonObject;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //最新消息
    public void send_replayleave(final String leaveid,final String status, final String feedback,final int key) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&leaveid=" + leaveid+"&teacherid="+user.getUserId()+"&feedback="+feedback+"&status="+status;
                String result_data = NetUtil.getResponse("http://wxt.xiaocool.net/index.php?g=apps&m=teacher&a=replyleave", data);
                LogUtils.e("send_replayleave", "http://wxt.xiaocool.net/index.php?g=apps&m=teacher&a=replyleave" + data);
                try {
                    JSONObject jsonObject = new JSONObject(result_data);
                    msg.what = key;
                    msg.obj = jsonObject;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }


    //最新消息
    public void getUrl(final String name, final int key) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&name=" + name;
                String result_data = NetUtil.getResponse("http://wxt.xiaocool.net/index.php?g=apps&m=index&a=FairylandUrl", data);
                LogUtils.e("getleavelist", WebHome.SPACE_RECEIVED_TCLEAVE + data);
                try {
                    JSONObject jsonObject = new JSONObject(result_data);
                    msg.what = key;
                    msg.obj = jsonObject;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //最新消息
    public void getleavelist() {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&teacherid=" + user.getUserId();
                Log.e("syso id", user.getUserId());
                String result_data = NetUtil.getResponse(WebHome.SPACE_RECEIVED_TCLEAVE, data);
                LogUtils.e("getleavelist", WebHome.SPACE_RECEIVED_TCLEAVE + data);
                try {
                    JSONObject jsonObject = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.RECEIVED_MESSAGE;
                    msg.obj = jsonObject;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }


    //最新消息
    public void HotNews() {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&userid=" + user.getUserId();
                Log.e("syso id", user.getUserId());
                String result_data = NetUtil.getResponse(WebHome.SPACE_RECEIVED_MESSAGE, data);
                LogUtils.e("announcement", result_data.toString());
                try {
                    JSONObject jsonObject = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.RECEIVED_MESSAGE;
                    msg.obj = jsonObject;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //获取通知公告条目
    public void announcement() {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&userid=" + user.getUserId() + "&classid=" + user.getClassId() + "&schoolid=" + user.getSchoolId();
                String result_data = NetUtil.getResponse(WebHome.ANNOUNCEMENT, data);
                Log.e("announcement", WebHome.ANNOUNCEMENT + data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.ANNOUNCEMENT;
                    msg.obj = obj;
                    LogUtils.e("getggeeannouncement", "=========");
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //通讯录
    public void address() {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "userid=" + user.getUserId();
                String result_data = NetUtil.getResponse(WebHome.MESSAGEADDRESS, data);
                LogUtils.e("announce", result_data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.MESSAGEADDRESS;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //获取宝宝课成
    public void classSchedule(final String classid) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&schoolid=" + user.getSchoolId() + "&classid=" + classid;
                String result_data = NetUtil.getResponse(WebHome.CLASS_SCHEDULE, data);
                Log.e("result_data", WebHome.CLASS_SCHEDULE + data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.CLASS_SCHEDULE;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    /**
     * 班级活动的获取
     */
    public void classEvents() {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&userid=" + user.getUserId() + "&classid=" + user.getClassId();
                String result_data = NetUtil.getResponse(WebHome.CLASS_EVENTS, data);
                Log.e("classEvents", WebHome.CLASS_EVENTS + data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.CLASS_EVENTS;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }

            }
        }.start();

    }

    //获取食谱    传入schoolid
    public void recipes() {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&schoolid=" + user.getSchoolId();
                String result_data = NetUtil.getResponse(WebHome.RECIPES, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.RECIPES;
                    msg.obj = obj;
                    Log.e("successful enter", "it");
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //获取食谱(新)    传入schoolid begindate enddate
    public void getRecipes(final String begindate, final String enddate) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&schoolid=" + user.getSchoolId() + "&begindate=" + begindate + "&enddate=" + enddate;
                String result_data = NetUtil.getResponse(WebHome.RECIPESNEW, data);
                Log.e("getRecipes", WebHome.RECIPESNEW + data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.RECIPESNEW;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //获得班级课件
    public void classCourseware() {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&schoolid=" + user.getSchoolId() + "&classid=" + user.getClassId();
                String result_data = NetUtil.getResponse(WebHome.CLASS_COURSEWARE, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.CLASS_COURSEWARE;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //获取老师点评
    public void teacherReview() {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&stuid=" + "599";
                String result_data = NetUtil.getResponse(WebHome.TEACHER_REVIEW, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    LogUtils.e("11", result_data);
                    msg.what = CommunalInterfaces.TEACHER_REVIEW;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //获取周计划
    public void weekendplan() {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&schoolid=" + user.getSchoolId() + "&classid=1" + user.getClassId();
                String result_data = NetUtil.getResponse(WebHome.GETSCHOOLPLAN, data);
                LogUtils.e("weekendplan", WebHome.GETSCHOOLPLAN + data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    LogUtils.e("11", result_data);
                    msg.what = CommunalInterfaces.GETSCHOOLPLAN;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    public void send_weekendplan(final String url, final String data, final int KEY) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {


//                String data = "&userid=" + user.getUserId() + "&scheduleid=" + scheduleid + "&feedback=" + feedback + "&reciveid=" + reciveid + "&finish=" + finish;
                String result_data = NetUtil.getResponse(url, data);
                Log.e("result_data", result_data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = KEY;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //    //获取宝宝好友
//    public void getfriendlist() {
//        new Thread() {
//            Message msg = Message.obtain();
//
//            public void run() {
//                String data = "&studentid＝597";
//                String result_data = NetUtil.getResponse(WebHome.GETFRIENDLIST, data);
//                try {
//                    JSONObject obj = new JSONObject(result_data);
//                    LogUtils.e("11", result_data);
//                    msg.what = CommunalInterfaces.GETFRIENDLIST;
//                    msg.obj = obj;
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                } finally {
//                    handler.sendMessage(msg);
//                }
//            }
//        }.start();
//    }
    public void addressParent() {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&userid=" + user.getUserId();
                Log.i("InfoUserId", user.getUserId());
                String result_data = NetUtil.getResponse("http://wxt.xiaocool.net/index.php?g=apps&m=index&a=ParentContacts", data);
                LogUtils.e("addressParent", "http://wxt.xiaocool.net/index.php?g=apps&m=index&a=ParentContacts" + data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.MESSAGEADDRESS;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //添加教师评论
    public void AddTeacherComment(final String teacherid, final String studentid, final String content) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&teacherid=" + teacherid + "&studentid=" + studentid + "&content=" + content;
                String result_data = NetUtil.getResponse(WebHome.ADDTEACHERCOMMENT, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    LogUtils.e("11", result_data);
                    msg.what = CommunalInterfaces.ADDTEACHERCOMMENT;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    /**
     * 修改用户的头像
     *
     * @param img
     */
    public void updateUserImg(final String img) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                //添加参数用来组成URL地址
                //调用接口地址
                //获取返回
                String data = "&teachername=赵丽&sex=0&picurl=" + img;
                String result_data = NetUtil.getResponse(WebHome.SAVEINFO, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    LogUtils.e("11", result_data);
                    msg.what = CommunalInterfaces.SAVEINFO;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }

            }

            ;
        }.start();
    }

    public void parentWarn() {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&teacherid=" + user.getUserId();
                Log.d("userid", "id=" + user.getUserId());
                String result_data = NetUtil.getResponse(WebHome.PARENT_WARN, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.PARENT_WARN;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    /**
     * 获取家庭作业列表
     */
    public void homework() {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&userid=" + user.getUserId() + "&classid=" + user.getClassId();
                String result_data = NetUtil.getResponse(WebHome.HOMEWORK, data);
                LogUtils.e("NewsRequest-homework", WebHome.HOMEWORK + data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.HOMEWORK;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    /**
     * 获取信息群发
     */
    public void getNewsGoupInfos() {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&send_user_id=" + user.getUserId();
                String result_data = NetUtil.getResponse(WebHome.NEWSGROUPS, data);
                Log.e("NetUtil", WebHome.NEWSGROUPS + data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.NEWSGROUP;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }


    /**
     * 获取园丁列表
     */
    public void getTeacher() {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&schoolid=" + user.getSchoolId();
                String result_data = NetUtil.getResponse(WebHome.TEACHER, data);
                LogUtils.e("NewsRequest-teacher", WebHome.TEACHER + data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.TEACHER;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    public void collection() {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&teacherid=" + user.getUserId();
                String result_data = NetUtil.getResponse(WebHome.COLLECTION, data);
                Log.e("collection", WebHome.COLLECTION + data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.COLLECTION;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    public void myclass() {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&teacherid=" + user.getUserId();
                String result_data = NetUtil.getResponse(WebHome.CLASSLIST, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.CLASSLIST;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //班级和学生列表
    public void myclass_stu() {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&teacherid=" + user.getUserId();
                String result_data = NetUtil.getResponse(WebHome.CLASS_STULIST, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.CLASS_STU;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    public void send_annocunce(final String title, final String content, final String reciveid, final String picname, final String genre,final int KEY) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&userid=" + user.getUserId() + "&title=" + title + "&content=" + content + "&receiverid=" + reciveid + "&photo=" + picname + "&genre=" + genre;
                String result_data = NetUtil.getResponse(WebHome.SEND_ANNOUNCE, data);
                Log.e("send_annocunce", WebHome.SEND_ANNOUNCE + data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = KEY;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }


    public void send_homework(final String title, final String content, final String reciveid, final String picname, final String subject, final int KEY) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&teacherid=" + user.getUserId() + "&title=" + title + "&content=" + content + "&receiverid=" + reciveid + "&picture_url=" + picname + "&subject=" + subject;
                String result_data = NetUtil.getResponse(WebHome.SEND_HOMEWORK, data);
                LogUtils.e("send_homework",WebHome.SEND_HOMEWORK+data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = KEY;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    /**
     * 接口名称发布班级课件
     * 接口地址：a=AddSchoolCourseware
     * 入参：schoolid,classid,user_id,subjectid,courseware_title,courseware_content
     * 出参：无
     * Demo:http://wxt.xiaocool.net/index.php?g=apps&m=index&a=
     * AddSchoolCourseware&schoolid=1&classid=1,2,3&user_id=597&subjectid=1&courseware_title=标题&courseware_content=班级课件
     *
     * @param title
     * @param content
     * @param picname
     * @param subject
     * @param KEY
     */
    public void send_course(final String classid,final String title, final String content, final String picname, final String subject, final int KEY) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&schoolid=" + user.getSchoolId() +"&classid="+classid+"&user_id="+user.getUserId() +
                        "&courseware_title=" + title + "&courseware_content=" + content  + "&picture_url=" + picname + "&subjectid=" + subject;
                String result_data = NetUtil.getResponse("http://wxt.xiaocool.net/index.php?g=apps&m=index&a=AddSchoolCourseware", data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = KEY;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    /**
     * 发送信息群发
     * 接口名称用户发送消息(post传输) <>
     * 接口地址：a=send_message <>
     * 入参：send_user_id,schoolid,send_user_name,message_content,receiver_user_id,picture_url <>
     * 出参：无 <>
     * Demo:http://wxt.xiaocool.net/index.php?g=apps&m=message&a=send_message&send_user_id=600&schoolid=1&send_user_name=呵呵
     * &message_content=222&receiver_user_id=597,600&picture_url=ajhsdiaho.png<>
     */
    public void send_newsgroup(final String message_content, final String receiver_user_id, final String picture_url, final String genre,final int KEY) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {

                String data = "";
                if (picture_url.equals("null")) {
                    data = "&send_user_id=" + user.getUserId() + "&schoolid=" + user.getSchoolId() + "&send_user_name=" + user.getUserName()
                            + "&message_content=" + message_content + "&receiver_user_id=" + receiver_user_id + "&genre=" + genre;
                } else {
                    data = "&send_user_id=" + user.getUserId() + "&schoolid=" + user.getSchoolId() + "&send_user_name=" + user.getUserName()
                            + "&message_content=" + message_content + "&receiver_user_id=" + receiver_user_id + "&picture_url=" + picture_url + "&genre=" + genre;
                }

                String result_data = NetUtil.getResponse(WebHome.SEND_NEWSGROUP, data);
                Log.e("send_newsgroup", WebHome.SEND_NEWSGROUP + data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = KEY;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    public void send_trend(final String content, final String picname, final String type, final int KEY) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("userid", user.getUserId()));
                params.add(new BasicNameValuePair("schoolid", user.getSchoolId()));
                params.add(new BasicNameValuePair("type", type));
                params.add(new BasicNameValuePair("content", content));
                params.add(new BasicNameValuePair("classid", user.getClassId()));
                if (picname != null) {
                    if (!picname.equals("null")) {
                        params.add(new BasicNameValuePair("picurl", picname));
                    }

                }
                String result_data = NetBaseUtils.getResponseForPost(WebHome.SEND_TRENDS, params, mContext);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = KEY;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    /**
     * 接口名称发布班级活动
     * 接口地址：m=teacher&a=addactivity
     * 入参：teacherid,title,content,begintime,endtime(时间戳),starttime,finishtime,contactman,contactphone,isapply,receiverid,picture_url
     * 出参：activityid
     * Demo:http://wxt.xiaocool.net/index.php?g=apps&m=teacher&a=addactivity&teacherid=597&title=周四活动&content=活动内容，快来看
     * &contactman=李林&contactphone=123123123&begintime=1461917799&endtime=1461917809&starttime=1461917799&finishtime=1461917809
     * &isapply=1&receiverid=600&picture_url=1.png,2.png
     */
    public void send_event(final String reciveid, final String title, final String content, final String picname, final String begintime, final String endtime, final String starttime, final String finishtime, final String contactman, final String contactphone, final String isApply, final int KEY) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&teacherid=" + user.getUserId() + "&title=" + title + "&content=" + content + "&picture_url=" + picname + "&begintime=" + begintime + "" +
                        "&endtime=" + endtime + "&contactman=" + contactman + "&contactphone=" + contactphone + "&starttime=" + starttime + "&finishtime=" + finishtime + "&isapply="
                        + isApply + "&receiverid=" + reciveid;
                String result_data = NetUtil.getResponse(WebHome.SEND_EVENT, data);
                Log.e("send_event", WebHome.SEND_EVENT + data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = KEY;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    /**
     * 接口名称添加待办事宜
     * 接口地址：a=addschedule
     * 入参(post)：userid,schoolid,title,content,reciveid,photolist
     * 出参：无
     * Demo:http://wxt.xiaocool.net/index.php?g=apps&m=school&a=addschedule&userid=597&schoolid=1&title=尽快提交本周计划
     * &content=请注意查收来自教育局的通知&reciveid=605&photolist＝1.jpg,2.jpg
     */
    public void send_schedule(final String title, final String content, final String reciveid, final String picname, final int KEY) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
//                List<NameValuePair> params = new ArrayList<NameValuePair>();
//                params.add(new BasicNameValuePair("userid", user.getUserId()));
//                params.add(new BasicNameValuePair("schoolid", user.getSchoolId()));
//                params.add(new BasicNameValuePair("title", title));
//                params.add(new BasicNameValuePair("content", content));
//                params.add(new BasicNameValuePair("reciveid", reciveid));
//                if (picname != null) {
//                    params.add(new BasicNameValuePair("picurl", picname));
//                }
//                String result_data = NetBaseUtils.getResponseForPost(WebHome.SEND_SCHEDULE, params, mContext);

                String data = "&userid=" + user.getUserId() + "&schoolid=" + user.getSchoolId() + "&title=" + title + "&content=" + content + "&reciveid=" + reciveid + "&photolist=" + picname;
                String result_data = NetUtil.getResponse(WebHome.SEND_SCHEDULE, data);
                Log.e("result_data", WebHome.SEND_SCHEDULE + data);
                Log.e("result_data", result_data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = KEY;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }


    /**
     * 接口名称处理待办事宜
     * 接口地址：a=DoSchedul
     * 入参(post)：userid,scheduleid,feedback,reciveid,finish
     * 出参：无
     * Demo:http://wxt.xiaocool.net/index.php?g=apps&m=school&a=DoSchedul&userid=597
     * &scheduleid=121&feedback=尽快提交本周计划&reciveid=605&finish=1
     */

    public void do_schedule(final String lastid,final String scheduleid, final String feedback, final String reciveid, final String finish, final int KEY) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {


                String data = "&userid=" + user.getUserId() + "&scheduleid=" + scheduleid + "&feedback=" + feedback + "&reciveid=" + reciveid + "&finish=" + finish + "&id=" + lastid;
                String result_data = NetUtil.getResponse(WebHome.DO_SCHEDULE, data);
                Log.e("result_data", WebHome.DO_SCHEDULE + data);
                Log.e("result_data", result_data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = KEY;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    /**
     * 发送待办事项
     */
    public void send_confim(final String studentId, final String content, final String picname, final int KEY) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data;
                if (picname.equals("null")){
                    data = "&teacherid=" + user.getUserId() + "&studentid=" + studentId + "&content=" + content;
                }else {
                    data = "&teacherid=" + user.getUserId() + "&studentid=" + studentId + "&photo=" + picname
                            + "&content=" + content;
                }
                String result_data = NetUtil.getResponse(WebHome.SEND_CONFIM, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = KEY;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    /**
     * 接口名称教师获取一段时间内我的班级内学生的考勤记录
     * 接口地址：m=teacher&a=GetStudentAttendanceList
     * 入参：userid,begintime,endtime,type(1=签到，2签退)
     * 出参：lists
     * Demo:http://wxt.xiaocool.net/index.php?g=apps&m=teacher&a=GetStudentAttendanceList
     * &teacherid=597&begintime=1453334170&endtime=1453334170
     */
    public void getStudentAttendance(final String type, final int KEY) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&teacherid=605" + "&begintime=" + TimeToolUtils.getTodayTime(0, 0, 0) + "&endtime=" + TimeToolUtils.getNowTime() + "&type=" + type;

                String result_data = NetUtil.getResponse(WebHome.GET_CLASSATTEND, data);

                Log.e("getStudentAttendance", WebHome.GET_CLASSATTEND + data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = KEY;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }


    /**
     * http://wxt.xiaocool.net/index.php?g=apps&m=teacher&a=GetTeacherAttendanceList&userid=597&begintime=1468980121&endtime=1468980121
     */
    public void getTeacherAttendance(final String begintime, final String endtime, final String userid, final int KEY) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&userid=" + userid + "&begintime=" + begintime + "&endtime=" + endtime;

                String result_data = NetUtil.getResponse("http://wxt.xiaocool.net/index.php?g=apps&m=teacher&a=GetTeacherAttendanceList", data);

                Log.e("getTeacherAttendance", "http://wxt.xiaocool.net/index.php?g=apps&m=teacher&a=GetTeacherAttendanceList" + data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = KEY;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    public void choosecollect(final String classid) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&userid=" + user.getUserId() + "&classid=" + classid;
                String result_data = NetUtil.getResponse(WebHome.STUDENT_LIST, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.STUDENT_LIST;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    public void send_remark(final String id, final String content, final String type) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&userid=" + user.getUserId() + "&id=" + id + "&content=" + content + "&type=" + type;
                Log.d("final String id", data);
                String result_data = NetUtil.getResponse(WebHome.SEND_COMMENT, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.SEND_PARENT_REMARK;
                    msg.obj = obj;
                    Log.d("是否成功++", obj.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }


        }.start();
    }

    /**
     * 获取评论列表
     * 接口地址：m=school&a=GetCommentlist 入参：userid,refid,type  出参：list
     */

    public void get_comments(final String id, final String refid, final String type) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&userid=" + id + "&refid=" + refid + "&type=" + type;
                String result_data = NetUtil.getResponse(WebHome.GET_COMMENTS, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.GET_COMMENTS;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }


        }.start();
    }

    /**
     * 获取点赞列表
     * <p/>
     * 获取点赞列表
     * 接口地址：m=school&a=GetLikelist
     * 入参：userid,refid,type
     * 出参：list
     */

    public void get_like(final String id, final String refid, final String type) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&userid=" + id + "&refid=" + refid + "&type=" + type;
                String result_data = NetUtil.getResponse(WebHome.GET_LIKE, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.GET_LIKE;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }


        }.start();
    }

    /**
     * 作业点赞
     */
    public void Praise(final String workBindId, final int workPraiseKey, final String type) {
        LogUtils.d("weixiaotong", "getCircleList");
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                try {
                    String data = "&userid=" + user.getUserId() + "&id=" + workBindId + "&type=" + type;//!!!!!!!!!!!!!!!!!!!!!!!!!参数舛错了!
                    LogUtils.d("weixiaotong", data);
                    String result_data = NetUtil.getResponse(WebHome.NET_SET_PRAISE, data);
                    LogUtils.e("getIndexSlideNewsList", result_data.toString());
                    JSONObject jsonObject = new JSONObject(result_data);
                    msg.what = workPraiseKey;
                    msg.obj = jsonObject;
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    /**
     * 作业取消点赞
     */
    public void DelPraise(final String id, final int KEY, final String type) {
        LogUtils.d("weixiaotong", "getCircleList");
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                try {
                    String data = "userid=" + user.getUserId() + "&id=" + id + "&type=" + type;
                    LogUtils.d("weixiaotong", data);
                    String result_data = NetUtil.getResponse(WebHome.NET_DEL_PRAISE, data);

                    LogUtils.e("getIndexSlideNewsList", result_data.toString());
                    JSONObject jsonObject = new JSONObject(result_data);
                    msg.what = KEY;
                    msg.obj = jsonObject;
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }

            ;
        }.start();
    }

    /**
     * 获取已发送的待办事项
     */
    public void backlogSend() {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&userid=" + user.getUserId() + "&schoolid=" + user.getClassId();
                String result_data = NetUtil.getResponse(WebHome.BACKLOG_SEND, data);
                Log.e("backlogSend", WebHome.BACKLOG_SEND + data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.BACKLOG;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    /**
     * 获取接收的待办事项
     */
    public void backlogRecive() {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&userid=" + user.getUserId() + "&schoolid=" + user.getClassId();
                String result_data = NetUtil.getResponse(WebHome.BACKLOG_RECIVE, data);
                Log.e("backlogRecive",WebHome.BACKLOG_RECIVE+data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.BACKLOG;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    /**
     * 获取教师风采..................部分h5上一级页面
     */
    public void getTeacherInfo(final String key) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&schoolid=" + user.getSchoolId();
                String result_data = NetUtil.getResponse(WebHome.NET_GETTEACHER_INFO + key, data);
                Log.e("getTeacherInfo", WebHome.NET_GETTEACHER_INFO + key + data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.TEACHERINFO;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    /**
     * 获取系统通知
     */
    public void getArticleInfo() {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&term_id=3";
                String result_data = NetUtil.getResponse("http://wxt.xiaocool.net/index.php?g=apps&m=index&a=getSystemMessages", data);
                Log.e("getArticleInfo", "http://wxt.xiaocool.net/index.php?g=apps&m=index&a=getSystemMessages" + data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.TEACHERINFO;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    /**
     * 获取校网通知公告、新闻动态、育儿知识list
     */
    public void getSchoolListInfo(final String key, final int what) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&schoolid=" + user.getSchoolId();
                String result_data = NetUtil.getResponse(WebHome.NET_GETTEACHER_INFO + key, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = what;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    /**
     * 发送入学报名
     */
//    schoolid,username,sex(0,1),birthday(1999-05-21),address,classname(与账号所在班级无关，用户手动填写),
//    phone,qq,weixin(微信名称),education(学历),school(毕业学校),remark,photo(先调用上传图片接口，此处填写文件名)
    public void send_apply(final String username, final String sex, final String birthday, final String address,
                           final String classname, final String phone, final String qq, final String weixin, final String education,
                           final String school, final String remark, final String photo) {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("schoolid", user.getSchoolId()));
                params.add(new BasicNameValuePair("username", username));
                params.add(new BasicNameValuePair("sex", sex));
                params.add(new BasicNameValuePair("birthday", birthday));
                params.add(new BasicNameValuePair("address", address));
                params.add(new BasicNameValuePair("classname", classname));
                params.add(new BasicNameValuePair("phone", phone));
                params.add(new BasicNameValuePair("qq", qq));
                params.add(new BasicNameValuePair("weixin", weixin));
                params.add(new BasicNameValuePair("education", education));
                params.add(new BasicNameValuePair("school", school));
                params.add(new BasicNameValuePair("remark", remark));

                if (photo != null) {
                    params.add(new BasicNameValuePair("photo", photo));
                }
//                String data = "&schoolid="+user.getSchoolId()+"&username="+username+"&sex="+sex+"&birthday="+birthday+"&address="+address+
//                        "&classname"+classname+"&phone="+phone+ "&qq="+qq+"&weixin="+weixin+"&education="+education+"&school="+school+"&remark="+remark+
//                "&photo="+photo;
//                String result_data = NetUtil.getResponse(WebHome.SEND_APPLY, data);
//                Log.e("sdsd","jingbujing");
                String result_data = NetBaseUtils.getResponseForPost(WebHome.SEND_APPLY, params, mContext);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.SEND_APPLY;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }
}
