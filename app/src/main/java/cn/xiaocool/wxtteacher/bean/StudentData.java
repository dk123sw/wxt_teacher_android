package cn.xiaocool.wxtteacher.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/5.
 */
public class StudentData implements Serializable{

    /**
     * id : 665
     * photo : weixiaotong.png
     * name : 朱晟超
     * arrive_count : 0
     */

    private String id;
    private String photo;
    private String name;
    private String arrive_count;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArrive_count() {
        return arrive_count;
    }

    public void setArrive_count(String arrive_count) {
        this.arrive_count = arrive_count;
    }
}
