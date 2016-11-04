package cn.xiaocool.wxtteacher.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/7/25.
 */
public class NewsGroupRecive {

    /**
     * status : success
     * data : [{"id":"23","message_id":"321","receiver_user_id":"605","receiver_user_name":"潘宁","message_type":"0","read_time":null,"send_message":{"id":"321","schoolid":"1","send_user_id":"597","send_user_name":"嘿嘿","message_content":"111","message_time":"1468575737"},"picture":[{"picture_url":"hahsid.png"}],"receiver":[{"message_id":"321","receiver_user_id":"605","receiver_user_name":"潘宁","read_time":null}]}]
     */

    private String status;
    /**
     * id : 23
     * message_id : 321
     * receiver_user_id : 605
     * receiver_user_name : 潘宁
     * message_type : 0
     * read_time : null
     * send_message : {"id":"321","schoolid":"1","send_user_id":"597","send_user_name":"嘿嘿","message_content":"111","message_time":"1468575737"}
     * picture : [{"picture_url":"hahsid.png"}]
     * receiver : [{"message_id":"321","receiver_user_id":"605","receiver_user_name":"潘宁","read_time":null}]
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

    public static class DataBean implements Serializable {
        private String id;
        private String message_id;
        private String receiver_user_id;
        private String receiver_user_name;
        private String message_type;
        private Object read_time;
        /**
         * id : 321
         * schoolid : 1
         * send_user_id : 597
         * send_user_name : 嘿嘿
         * message_content : 111
         * message_time : 1468575737
         */

        private SendMessageBean send_message;
        /**
         * picture_url : hahsid.png
         */

        private List<PictureBean> picture;
        /**
         * message_id : 321
         * receiver_user_id : 605
         * receiver_user_name : 潘宁
         * read_time : null
         */

        private List<ReceiverBean> receiver;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMessage_id() {
            return message_id;
        }

        public void setMessage_id(String message_id) {
            this.message_id = message_id;
        }

        public String getReceiver_user_id() {
            return receiver_user_id;
        }

        public void setReceiver_user_id(String receiver_user_id) {
            this.receiver_user_id = receiver_user_id;
        }

        public String getReceiver_user_name() {
            return receiver_user_name;
        }

        public void setReceiver_user_name(String receiver_user_name) {
            this.receiver_user_name = receiver_user_name;
        }

        public String getMessage_type() {
            return message_type;
        }

        public void setMessage_type(String message_type) {
            this.message_type = message_type;
        }

        public Object getRead_time() {
            return read_time;
        }

        public void setRead_time(Object read_time) {
            this.read_time = read_time;
        }

        public SendMessageBean getSend_message() {
            return send_message;
        }

        public void setSend_message(SendMessageBean send_message) {
            this.send_message = send_message;
        }

        public List<PictureBean> getPicture() {
            return picture;
        }

        public void setPicture(List<PictureBean> picture) {
            this.picture = picture;
        }

        public List<ReceiverBean> getReceiver() {
            return receiver;
        }

        public void setReceiver(List<ReceiverBean> receiver) {
            this.receiver = receiver;
        }

        public static class SendMessageBean implements Serializable{
            private String id;
            private String schoolid;
            private String send_user_id;
            private String send_user_name;
            private String message_content;
            private String message_time;
            private String photo;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getSchoolid() {
                return schoolid;
            }

            public void setSchoolid(String schoolid) {
                this.schoolid = schoolid;
            }

            public String getSend_user_id() {
                return send_user_id;
            }

            public void setSend_user_id(String send_user_id) {
                this.send_user_id = send_user_id;
            }

            public String getSend_user_name() {
                return send_user_name;
            }

            public void setSend_user_name(String send_user_name) {
                this.send_user_name = send_user_name;
            }

            public String getMessage_content() {
                return message_content;
            }

            public void setMessage_content(String message_content) {
                this.message_content = message_content;
            }

            public String getMessage_time() {
                return message_time;
            }

            public void setMessage_time(String message_time) {
                this.message_time = message_time;
            }

            public String getPhoto() {
                return photo;
            }

            public void setPhoto(String photo) {
                this.photo = photo;
            }
        }

        public static class PictureBean implements Serializable{
            private String picture_url;

            public String getPicture_url() {
                return picture_url;
            }

            public void setPicture_url(String picture_url) {
                this.picture_url = picture_url;
            }
        }

        public static class ReceiverBean implements Serializable{
            private String message_id;
            private String receiver_user_id;
            private String receiver_user_name;
            private Object read_time;
            private String phone;
            private String photo;

            public String getMessage_id() {
                return message_id;
            }

            public void setMessage_id(String message_id) {
                this.message_id = message_id;
            }

            public String getReceiver_user_id() {
                return receiver_user_id;
            }

            public void setReceiver_user_id(String receiver_user_id) {
                this.receiver_user_id = receiver_user_id;
            }

            public String getReceiver_user_name() {
                return receiver_user_name;
            }

            public void setReceiver_user_name(String receiver_user_name) {
                this.receiver_user_name = receiver_user_name;
            }

            public Object getRead_time() {
                return read_time;
            }

            public void setRead_time(Object read_time) {
                this.read_time = read_time;
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
        }
    }
}
