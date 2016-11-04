package cn.xiaocool.wxtteacher.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/31.
 */
public class LikeBean implements Serializable{

    private String userid;
    private String avatar;
    private String name;
    private String time;
    private String phone;


    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
