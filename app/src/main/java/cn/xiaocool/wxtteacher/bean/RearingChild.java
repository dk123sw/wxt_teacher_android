package cn.xiaocool.wxtteacher.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/3/21.
 */
public class RearingChild implements Serializable{

    private String status;
    /**
     * happy_title : 育儿知识1
     * happy_content : 育儿知识
     * happy_pic : happy.jpg
     * releasename : 刘老师
     * happy_time : 1458166785
     */

    private List<RearingChildData> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<RearingChildData> getData() {
        return data;
    }

    public void setData(List<RearingChildData> data) {
        this.data = data;
    }

    public static class RearingChildData {
        private String happy_title;
        private String happy_content;
        private String happy_pic;
        private String releasename;
        private String happy_time;

        public String getHappy_title() {
            return happy_title;
        }

        public void setHappy_title(String happy_title) {
            this.happy_title = happy_title;
        }

        public String getHappy_content() {
            return happy_content;
        }

        public void setHappy_content(String happy_content) {
            this.happy_content = happy_content;
        }

        public String getHappy_pic() {
            return happy_pic;
        }

        public void setHappy_pic(String happy_pic) {
            this.happy_pic = happy_pic;
        }

        public String getReleasename() {
            return releasename;
        }

        public void setReleasename(String releasename) {
            this.releasename = releasename;
        }

        public String getHappy_time() {
            return happy_time;
        }

        public void setHappy_time(String happy_time) {
            this.happy_time = happy_time;
        }
    }
}
