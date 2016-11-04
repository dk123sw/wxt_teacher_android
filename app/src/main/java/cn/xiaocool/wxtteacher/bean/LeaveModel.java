package cn.xiaocool.wxtteacher.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/15.
 */
public class LeaveModel implements Serializable {
//
//    "id": "66",
//            "studentid": "599",
//            "parentid": "597",
//            "teacherid": "599",
//            "create_time": "0",
//            "begintime": "1468339200",
//            "endtime": "1468425600",
//            "reason": "",
//            "status": "1",
//            "feedback": "",
//            "deal_time": "0",
//            "classname": null,
//            "teachername": "",
//            "teacheravatar": "default.png",
//            "teacherphone": "",
//            "parentname": "I'm so excited",
//            "parentavatar": "weixiaotong.png",
//            "parentphone": "18363866803",
//            "studentname": "",
//            "studentavatar": "default.png"


    private String id;
    private String studentid;
    private String parentid;
    private String teacherid;
    private String create_time;
    private String begintime;
    private String endtime;
    private String reason;
    private String status;
    private String feedback;
    private String deal_time;
    private String classname;
    private String teachername;
    private String teacheravatar;
    private String teacherphone;
    private String parentname;
    private String parentavatar;
    private String parentphone;
    private String studentname;
    private String studentavatar;
    private ArrayList<String> pics;


    public String getStudentid() {
        return studentid;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getTeacherid() {
        return teacherid;
    }

    public void setTeacherid(String teacherid) {
        this.teacherid = teacherid;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getBegintime() {
        return begintime;
    }

    public void setBegintime(String begintime) {
        this.begintime = begintime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getDeal_time() {
        return deal_time;
    }

    public void setDeal_time(String deal_time) {
        this.deal_time = deal_time;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getTeachername() {
        return teachername;
    }

    public void setTeachername(String teachername) {
        this.teachername = teachername;
    }

    public String getTeacheravatar() {
        return teacheravatar;
    }

    public void setTeacheravatar(String teacheravatar) {
        this.teacheravatar = teacheravatar;
    }

    public String getTeacherphone() {
        return teacherphone;
    }

    public void setTeacherphone(String teacherphone) {
        this.teacherphone = teacherphone;
    }

    public String getParentname() {
        return parentname;
    }

    public void setParentname(String parentname) {
        this.parentname = parentname;
    }

    public String getParentavatar() {
        return parentavatar;
    }

    public void setParentavatar(String parentavatar) {
        this.parentavatar = parentavatar;
    }

    public String getParentphone() {
        return parentphone;
    }

    public void setParentphone(String parentphone) {
        this.parentphone = parentphone;
    }

    public String getStudentname() {
        return studentname;
    }

    public void setStudentname(String studentname) {
        this.studentname = studentname;
    }

    public String getStudentavatar() {
        return studentavatar;
    }

    public void setStudentavatar(String studentavatar) {
        this.studentavatar = studentavatar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<String> getPics() {
        return pics;
    }

    public void setPics(ArrayList<String> pics) {
        this.pics = pics;
    }
}
