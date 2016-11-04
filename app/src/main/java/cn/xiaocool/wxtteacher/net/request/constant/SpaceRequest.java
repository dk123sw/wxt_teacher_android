package cn.xiaocool.wxtteacher.net.request.constant;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import cn.xiaocool.wxtteacher.bean.UserInfo;
import cn.xiaocool.wxtteacher.dao.CommunalInterfaces;
import cn.xiaocool.wxtteacher.utils.LogUtils;


/**
 * Created by wzh on 2016/2/27.
 */
public class SpaceRequest {
    private Context mContext;
    private Handler handler;
    private UserInfo user;
    public static String id;
    private String etWeight;
    private String etHeight;

    public SpaceRequest(Context context, Handler handler) {
        super();
        this.mContext = context;
        this.handler = handler;
        user = new UserInfo();
        user.readData(mContext);
        id = user.getUserId();
    }


//        public SpaceRequest() {
//        super();
//        this.mContext = context;
//        user = new UserInfo();
//        user.readData(mContext);
//
//    }

    //获取当前绑定的宝宝信息
    public void BabyInfo() {
        id = user.getUserId();
        Log.e("id is1", id);
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                try {
                    String data = "&userid=" + id;
                    String result_data = NetUtil.getResponse(WebHome.WEB_GET_BABY_INFO, data);
                    JSONObject jsonObject = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.BABY_INFO;
                    msg.obj = jsonObject;
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //获取身高体重
    public void StatureWeight() {
        LogUtils.d("weixiaotong", "getCircleList");
        id = user.getUserId();
        Log.e("id is1", id);
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                try {
                    String data = "&stuid=" + id;
                    LogUtils.d("weixiaotong", data);
                    String result_data = NetUtil.getResponse(WebHome.WEB_GET_STATURE_WEIGHT, data);
                    LogUtils.e("announcement", result_data.toString());
                    JSONObject jsonObject = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.GET_STATURE_WEIGHT;
                    msg.obj = jsonObject;
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    //获取到校时间
    public void ArriveTimeTem() {

        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&stuid=" + id;
                String result_data = NetUtil.getResponse(WebHome.WEB_GET_TIME_TEM, data);
                LogUtils.e("announcement", result_data.toString());
                try {
                    JSONObject jsonObject = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.GET_TIME_TEM;
                    msg.obj = jsonObject;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

//    //写入数据库体重数据
//    public void InputWeight() {
////            id = user.getUserId();
//        Log.e("输入体重id", id);
//        etWeight = SpaceClickInputWeightActivity.input_text;
//        Log.e("进入", etWeight);
//        new Thread() {
//            Message msg = Message.obtain();
//
//            public void run() {
//                String data = "&stuid=" + id + "&weight=" + etWeight;
//                String result_data = NetUtil.getResponse(WebHome.SPACE_INPUT_WEIGHT, data);
//                try {
//                    JSONObject obj = new JSONObject(result_data);
//                    msg.what = CommunalInterfaces.INPUT_WEIGHT;
//                    msg.obj = obj;
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                } finally {
//                    handler.sendMessage(msg);
//                }
//
//            }
//        }.start();
//    }
//
//    //写入数据库身高数据
//    public void InputHeight() {
//        //            id = user.getUserId();
////            Log.e("输入体重id",id);
//        etHeight = SpaceClickInputHeightActivity.input_text;
//        Log.e("进入", etHeight);
//        new Thread() {
//            Message msg = Message.obtain();
//
//            public void run() {
//                String data = "&stuid=" + id + "&stature=" + etHeight;
//                String result_data = NetUtil.getResponse(WebHome.SPACE_INPUT_HEIGHT, data);
//                try {
//                    JSONObject obj = new JSONObject(result_data);
//                    msg.what = CommunalInterfaces.INPUT_HEIGHT;
//                    msg.obj = obj;
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                } finally {
//                    handler.sendMessage(msg);
//                }
//
//            }
//        }.start();
//    }


    //点开消息界面获取用户已经收到的消息
    public void ReceivedMessage() {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&userid=" + id;
                String result_data = NetUtil.getResponse(WebHome.SPACE_RECEIVED_MESSAGE, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.RECEIVED_MESSAGE;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }

            }
        }.start();
    }


    //点开消息界面获取用户已发送的消息
    public void sendMessage() {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&userid=" + id;
                String result_data = NetUtil.getResponse(WebHome.SPACE_SEND_MESSAGE, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.SEND_MESSAGE;
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
                String data = "&schoolid=1";
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

    //获取通知公告条目
    public void announcement() {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&schoolid=1";
                String result_data = NetUtil.getResponse(WebHome.ANNOUNCEMENT, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.ANNOUNCEMENT;
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

    //获取班级课件
    public void classCourseware() {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&schoolid=1&classid=1";
                String result_data = NetUtil.getResponse(WebHome.CLASS_COURSEWARE, data);
                Log.e( "run:classCourseware ",WebHome.CLASS_COURSEWARE+data );
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

    public void classEvents() {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&schoolid=1&classid=1";
                String result_data = NetUtil.getResponse(WebHome.CLASS_EVENTS, data);
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

    public void rearingChild() {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&schoolid=1";
                String result_data = NetUtil.getResponse(WebHome.REARING_CHILD, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.REARING_CHILD;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();

    }

    public void happySchool() {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&schoolid=1";
                String result_data = NetUtil.getResponse(WebHome.HAPPY_SCHOOL, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.HAPPY_SCHOOL;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    public void classSchedule() {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "schoolid=1&classid=1";
                String result_data = NetUtil.getResponse(WebHome.CLASS_SCHEDULE, data);
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

    public void inputPhoneNumAndName() {

    }

    public void babyTeacher() {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "schoolid=1&classid=1";
                String result_data = NetUtil.getResponse(WebHome.BABY_TEACHER, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.BABY_TEACHER;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    public void leaveReason(final String user_leave_reason) {
        new Thread() {
            Message msg = Message.obtain();
            public void run() {
                String data = "&userid=599&teacherid=1&content=" + user_leave_reason;
                String result_data = NetUtil.getResponse(WebHome.LEAVE_REASON, data);
                Log.i("Info_resultData",result_data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.LEAVE_REASON;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }
    public void parentWarn(final String user_parent_warn) {
        new Thread() {
            Message msg = Message.obtain();
            public void run() {
                String data = "&userid=599&teacherid=1&content=" + user_parent_warn;
                String result_data = NetUtil.getResponse(WebHome.PARENT_WARN, data);
                Log.i("Info_resultData",result_data);
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
    public void teacherAddress() {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&userid=597";
                String result_data = NetUtil.getResponse(WebHome.TEACHER_ADDRESS, data);
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
    public void  sendMessageToTeacher(final String sendMessage, final String teacherID) {
        new Thread() {
            Message msg = Message.obtain();
            public void run() {
                String data = "&sendid="+user.getUserId()+"&receiveid="+teacherID+"&content="+ sendMessage;
                LogUtils.i("Info_id_message",teacherID+sendMessage);
                String result_data = NetUtil.getResponse(WebHome.SENDTOTEACJER, data);
                Log.i("Info",result_data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.SENDTOTEACJER;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    public void newsGroupSend() {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "&userid=597&classid=1";
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


    public void writeAnnouncement(final String title, final String content) {
        new Thread() {
            Message msg = Message.obtain();

//            public void run() {
//                String data = "&userid=599&teacherid=1&content=" + user_leave_reason;
//                String result_data = NetUtil.getResponse(WebHome.LEAVE_REASON, data);
//                Log.i("Info_resultData", result_data);
//                try {
//                    JSONObject obj = new JSONObject(result_data);
//                    msg.what = CommunalInterfaces.LEAVE_REASON;
//                    msg.obj = obj;
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                } finally {
//                    handler.sendMessage(msg);
//                }
//            }

            @Override
            public void run() {
                String data = "&userid="+user.getUserId()+"&type=1&title="+title+"&content="+content+"&reciveid=12";
                String result_data = NetUtil.getResponse(WebHome.WRITE_ANNOUNCEMENT,data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    msg.what = CommunalInterfaces.WRITE_ANNOUNCEMENT;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }
}
