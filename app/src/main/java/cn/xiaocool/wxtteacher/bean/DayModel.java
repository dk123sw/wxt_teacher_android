package cn.xiaocool.wxtteacher.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/8.
 */
public class DayModel implements Serializable {

    private String date;
    private String type;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
