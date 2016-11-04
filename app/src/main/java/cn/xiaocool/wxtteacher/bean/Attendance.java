package cn.xiaocool.wxtteacher.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/1.
 */
public class Attendance implements Serializable {

    /**
     * id : 1
     * userid : 597
     * name : 奥黛丽赫本
     * photo : newsgroup4051469613043127.jpg
     * schoolid : 1
     * arrivetime : 1467603740
     * leavetime : 1467606000
     * arrivepicture :
     * leavepicture : 0
     * arrivevideo :
     * leavevideo :
     * create_time : 1468980121
     * type : 1
     */

    private String id;
    private String userid;
    private String name;
    private String photo;
    private String schoolid;
    private String arrivetime;
    private String leavetime;
    private String arrivepicture;
    private String leavepicture;
    private String arrivevideo;
    private String leavevideo;
    private String create_time;
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getSchoolid() {
        return schoolid;
    }

    public void setSchoolid(String schoolid) {
        this.schoolid = schoolid;
    }

    public String getArrivetime() {
        if (arrivetime==null){
            return "0";
        }
        return arrivetime;
    }

    public void setArrivetime(String arrivetime) {
        this.arrivetime = arrivetime;
    }

    public String getLeavetime() {
        if (leavetime==null){
            return "0";
        }
        return leavetime;
    }

    public void setLeavetime(String leavetime) {
        this.leavetime = leavetime;
    }

    public String getArrivepicture() {
        return arrivepicture;
    }

    public void setArrivepicture(String arrivepicture) {
        this.arrivepicture = arrivepicture;
    }

    public String getLeavepicture() {
        return leavepicture;
    }

    public void setLeavepicture(String leavepicture) {
        this.leavepicture = leavepicture;
    }

    public String getArrivevideo() {
        return arrivevideo;
    }

    public void setArrivevideo(String arrivevideo) {
        this.arrivevideo = arrivevideo;
    }

    public String getLeavevideo() {
        return leavevideo;
    }

    public void setLeavevideo(String leavevideo) {
        this.leavevideo = leavevideo;
    }

    public String getCreate_time() {
        if (create_time==null){
            return "0";
        }
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
