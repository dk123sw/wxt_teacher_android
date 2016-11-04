package cn.xiaocool.wxtteacher.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/7/26.
 */
public class NoticeRecive implements Serializable{

    /**
     * status : success
     * data : [{"id":"2","noticeid":"166","receiverid":"597","receivertype":"0","create_time":"0","notice_info":[{"name":"We","photo":"weixiaotong.png","id":"166","userid":"597","title":"标题","type":"1","content":"内容","create_time":"1469500694"}],"receiv_list":[{"id":"2","noticeid":"166","receiverid":"597","receivertype":"0","create_time":"0"}],"pic":[{"photo":"11.jpg"}]}]
     */

    private String status;
    /**
     * id : 2
     * noticeid : 166
     * receiverid : 597
     * receivertype : 0
     * create_time : 0
     * notice_info : [{"name":"We","photo":"weixiaotong.png","id":"166","userid":"597","title":"标题","type":"1","content":"内容","create_time":"1469500694"}]
     * receiv_list : [{"id":"2","noticeid":"166","receiverid":"597","receivertype":"0","create_time":"0"}]
     * pic : [{"photo":"11.jpg"}]
     */

    private List<DataBean> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        private String id;
        private String noticeid;
        private String receiverid;
        private String receivertype;
        private String create_time;
        /**
         * name : We
         * photo : weixiaotong.png
         * id : 166
         * userid : 597
         * title : 标题
         * type : 1
         * content : 内容
         * create_time : 1469500694
         */

        private List<NoticeInfoBean> notice_info;
        /**
         * id : 2
         * noticeid : 166
         * receiverid : 597
         * receivertype : 0
         * create_time : 0
         */

        private List<ReceivListBean> receiv_list;
        /**
         * photo : 11.jpg
         */

        private List<PicBean> pic;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNoticeid() {
            return noticeid;
        }

        public void setNoticeid(String noticeid) {
            this.noticeid = noticeid;
        }

        public String getReceiverid() {
            return receiverid;
        }

        public void setReceiverid(String receiverid) {
            this.receiverid = receiverid;
        }

        public String getReceivertype() {
            return receivertype;
        }

        public void setReceivertype(String receivertype) {
            this.receivertype = receivertype;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public List<NoticeInfoBean> getNotice_info() {
            return notice_info;
        }

        public void setNotice_info(List<NoticeInfoBean> notice_info) {
            this.notice_info = notice_info;
        }

        public List<ReceivListBean> getReceiv_list() {
            return receiv_list;
        }

        public void setReceiv_list(List<ReceivListBean> receiv_list) {
            this.receiv_list = receiv_list;
        }

        public List<PicBean> getPic() {
            return pic;
        }

        public void setPic(List<PicBean> pic) {
            this.pic = pic;
        }

        public static class NoticeInfoBean implements Serializable{
            private String name;
            private String photo;
            private String id;
            private String userid;
            private String title;
            private String type;
            private String content;
            private String create_time;

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

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }
        }

        public static class ReceivListBean implements Serializable{
            private String id;
            private String noticeid;
            private String receiverid;
            private String receivertype;
            private String create_time;
            private String phone;
            private String photo;
            private String name;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getNoticeid() {
                return noticeid;
            }

            public void setNoticeid(String noticeid) {
                this.noticeid = noticeid;
            }

            public String getReceiverid() {
                return receiverid;
            }

            public void setReceiverid(String receiverid) {
                this.receiverid = receiverid;
            }

            public String getReceivertype() {
                return receivertype;
            }

            public void setReceivertype(String receivertype) {
                this.receivertype = receivertype;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
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
        }

        public static class PicBean implements Serializable{
            private String photo;

            public String getPhoto() {
                return photo;
            }

            public void setPhoto(String photo) {
                this.photo = photo;
            }
        }
    }
}
