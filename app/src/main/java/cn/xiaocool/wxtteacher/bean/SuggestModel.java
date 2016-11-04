package cn.xiaocool.wxtteacher.bean;

/**
 * Created by Administrator on 2016/10/27.
 */
public class SuggestModel {

    /**
     * id : 1
     * userid : 597
     * message : 留言
     * create_time : 0
     * feed_userid : 0
     * feed_back :
     * feed_time : 0
     */

    private String id;
    private String userid;
    private String message;
    private String create_time;
    private String feed_userid;
    private String feed_back;
    private String feed_time;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getFeed_userid() {
        return feed_userid;
    }

    public void setFeed_userid(String feed_userid) {
        this.feed_userid = feed_userid;
    }

    public String getFeed_back() {
        return feed_back;
    }

    public void setFeed_back(String feed_back) {
        this.feed_back = feed_back;
    }

    public String getFeed_time() {
        return feed_time;
    }

    public void setFeed_time(String feed_time) {
        this.feed_time = feed_time;
    }
}
