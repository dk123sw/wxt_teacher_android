package cn.xiaocool.wxtteacher.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/7.
 */
public class Announcement implements Serializable {

    /**
     * status : success
     * data : [{"id":"116","userid":"605","title":"","content":"","photo":"newpic.jpg","type":"3","create_time":"1463390797","username":"潘宁","avatar":"weixiaotong.png","pic":"","comment":[{"userid":"a","avatar":"aaa","name":"aaaa","content":"aaa"}],"like":[{"userid":"a","avatar":"aaa","name":"aaa"}],"readcount":1,"allreader":9,"readtag":1},{"id":"115","userid":"605","title":"","content":"","photo":"avatar6051463390744792.jpg","type":"3","create_time":"1463390770","username":"潘宁","avatar":"weixiaotong.png","pic":"","comment":[],"like":[],"readcount":1,"allreader":9,"readtag":1},{"id":"114","userid":"605","title":"","content":"","photo":"newpic.jpg","type":"3","create_time":"1463387538","username":"潘宁","avatar":"weixiaotong.png","pic":"","comment":[],"like":[],"readcount":1,"allreader":9,"readtag":1},{"id":"113","userid":"605","title":"犯花痴","content":"才拒绝","photo":"avatar6051463387519827.jpg","type":"3","create_time":"1463387522","username":"潘宁","avatar":"weixiaotong.png","pic":"","comment":[],"like":[],"readcount":1,"allreader":9,"readtag":1},{"id":"112","userid":"605","title":"","content":"","photo":"newpic.jpg","type":"3","create_time":"1463369564","username":"潘宁","avatar":"weixiaotong.png","pic":"","comment":[],"like":[],"readcount":1,"allreader":9,"readtag":1},{"id":"111","userid":"605","title":"","content":"","photo":"avatar6051463369554160.jpg","type":"3","create_time":"1463369557","username":"潘宁","avatar":"weixiaotong.png","pic":"","comment":[],"like":[],"readcount":1,"allreader":9,"readtag":1},{"id":"110","userid":"605","title":"返回查查","content":"出差出差","photo":"","type":"3","create_time":"1463283559","username":"潘宁","avatar":"weixiaotong.png","pic":"","comment":[],"like":[],"readcount":1,"allreader":9,"readtag":1},{"id":"109","userid":"605","title":"促成","content":"粗吃醋","photo":"","type":"3","create_time":"1463283531","username":"潘宁","avatar":"weixiaotong.png","pic":"","comment":[],"like":[],"readcount":1,"allreader":9,"readtag":1},{"id":"108","userid":"605","title":"好吃减肥减肥","content":"电话放假吵架","photo":"","type":"3","create_time":"1463282784","username":"潘宁","avatar":"weixiaotong.png","pic":"","comment":[],"like":[],"readcount":1,"allreader":9,"readtag":1},{"id":"107","userid":"605","title":"犯花痴","content":"吃坚持坚持","photo":"newpic","type":"3","create_time":"1463282378","username":"潘宁","avatar":"weixiaotong.png","pic":"","comment":[],"like":[],"readcount":1,"allreader":9,"readtag":1},{"id":"106","userid":"605","title":"u付付费","content":"忽大忽小话费","photo":"newpic","type":"3","create_time":"1463282199","username":"潘宁","avatar":"weixiaotong.png","pic":"","comment":[],"like":[],"readcount":1,"allreader":9,"readtag":1},{"id":"105","userid":"605","title":"这次行了","content":"","photo":"avatar6051463218390867.jpg","type":"1","create_time":"1463218412","username":"潘宁","avatar":"weixiaotong.png","pic":"","comment":[],"like":[],"readcount":1,"allreader":9,"readtag":1},{"id":"104","userid":"605","title":"哈哈","content":"","photo":"avatar6051463217974984.jpg","type":"1","create_time":"1463217981","username":"潘宁","avatar":"weixiaotong.png","pic":"","comment":[],"like":[],"readcount":1,"allreader":9,"readtag":1},{"id":"103","userid":"605","title":"哈哈","content":"","photo":"avatar6051463217537308","type":"1","create_time":"1463217543","username":"潘宁","avatar":"weixiaotong.png","pic":"","comment":[],"like":[],"readcount":1,"allreader":9,"readtag":1},{"id":"102","userid":"605","title":"","content":"","photo":"/sdcard/myheader/avatar6051463215507899.jpg","type":"1","create_time":"1463215629","username":"潘宁","avatar":"weixiaotong.png","pic":"","comment":[],"like":[],"readcount":1,"allreader":9,"readtag":1},{"id":"101","userid":"605","title":"哈哈","content":"","photo":"/sdcard/myheader/avatar6051463215305765.jpg","type":"1","create_time":"1463215307","username":"潘宁","avatar":"weixiaotong.png","pic":"","comment":[],"like":[],"readcount":1,"allreader":9,"readtag":1},{"id":"100","userid":"605","title":"","content":"","photo":"/sdcard/myheader/avatar6051463215283158.jpg","type":"1","create_time":"1463215286","username":"潘宁","avatar":"weixiaotong.png","pic":"","comment":[],"like":[],"readcount":1,"allreader":9,"readtag":1},{"id":"99","userid":"605","title":"","content":"","photo":"/sdcard/myheader/avatar6051463206949544.jpg","type":"1","create_time":"1463206951","username":"潘宁","avatar":"weixiaotong.png","pic":"","comment":[],"like":[],"readcount":1,"allreader":9,"readtag":1},{"id":"98","userid":"605","title":"哈哈","content":"","photo":"/sdcard/myheader/avatar6051463206523987.jpg","type":"1","create_time":"1463206525","username":"潘宁","avatar":"weixiaotong.png","pic":"","comment":[],"like":[],"readcount":1,"allreader":9,"readtag":1},{"id":"97","userid":"605","title":"","content":"","photo":"/sdcard/myheader/avatar6051463206498824.jpg","type":"1","create_time":"1463206500","username":"潘宁","avatar":"weixiaotong.png","pic":"","comment":[],"like":[],"readcount":1,"allreader":9,"readtag":1},{"id":"96","userid":"605","title":"����","content":"","photo":"/sdcard/myheader/avatar6051463206113220.jpg","type":"1","create_time":"1463206114","username":"潘宁","avatar":"weixiaotong.png","pic":"","comment":[],"like":[],"readcount":1,"allreader":9,"readtag":1},{"id":"95","userid":"597","title":"","content":"","photo":"11.jpg","type":"1","create_time":"1463204435","username":"李春波","avatar":"weixiaotong.png","pic":"","comment":[],"like":[],"readcount":1,"allreader":9,"readtag":1},{"id":"94","userid":"597","title":"标题","content":"","photo":"11.jpg","type":"1","create_time":"1463204426","username":"李春波","avatar":"weixiaotong.png","pic":"","comment":[],"like":[],"readcount":1,"allreader":9,"readtag":1},{"id":"93","userid":"597","title":"标题","content":"内容","photo":"11.jpg","type":"1","create_time":"1463204413","username":"李春波","avatar":"weixiaotong.png","pic":"","comment":[],"like":[],"readcount":1,"allreader":9,"readtag":1},{"id":"92","userid":"597","title":"","content":"","photo":"11.jpg","type":"1","create_time":"1463195286","username":"李春波","avatar":"weixiaotong.png","pic":"","comment":[],"like":[],"readcount":1,"allreader":9,"readtag":1},{"id":"91","userid":"597","title":"标题","content":"内容","photo":"11.jpg","type":"1","create_time":"1463193816","username":"李春波","avatar":"weixiaotong.png","pic":"","comment":[],"like":[],"readcount":1,"allreader":9,"readtag":1},{"id":"90","userid":"605","title":"","content":"","photo":"","type":"1","create_time":"1463134948","username":"潘宁","avatar":"weixiaotong.png","pic":"","comment":[],"like":[],"readcount":1,"allreader":9,"readtag":1},{"id":"89","userid":"605","title":"","content":"","photo":"","type":"1","create_time":"1463134109","username":"潘宁","avatar":"weixiaotong.png","pic":"","comment":[],"like":[],"readcount":1,"allreader":9,"readtag":1},{"id":"88","userid":"605","title":"","content":"","photo":"","type":"1","create_time":"1463134098","username":"潘宁","avatar":"weixiaotong.png","pic":"","comment":[],"like":[],"readcount":1,"allreader":9,"readtag":1},{"id":"87","userid":"597","title":"标题","content":"内容","photo":"11.jpg","type":"1","create_time":"1463124030","username":"李春波","avatar":"weixiaotong.png","pic":"","comment":[],"like":[],"readcount":1,"allreader":9,"readtag":1},{"id":"86","userid":"597","title":"123132","content":"123123123","photo":"default.jpg","type":"1","create_time":"1462963212","username":"李春波","avatar":"weixiaotong.png","pic":[{"id":"45","pictureurl":"uploads/microblog/notice5971591130.png","create_time":"1462963212"}],"comment":[],"like":[],"readcount":1,"allreader":9,"readtag":1},{"id":"85","userid":"605","title":"天翻地覆","content":"苟富贵好纠结","photo":"","type":"1","create_time":"1462961896","username":"潘宁","avatar":"weixiaotong.png","pic":"","comment":[],"like":[],"readcount":1,"allreader":9,"readtag":1},{"id":"84","userid":"605","title":"夫妇","content":"好吃发几个","photo":"","type":"1","create_time":"1462950311","username":"潘宁","avatar":"weixiaotong.png","pic":"","comment":[],"like":[],"readcount":1,"allreader":9,"readtag":1},{"id":"83","userid":"605","title":"吃坚持坚持","content":"吃就吃认不出电话","photo":"","type":"1","create_time":"1462945752","username":"潘宁","avatar":"weixiaotong.png","pic":"","comment":[],"like":[],"readcount":1,"allreader":9,"readtag":1},{"id":"82","userid":"605","title":"废话风好大","content":"传承","photo":"","type":"1","create_time":"1462936046","username":"潘宁","avatar":"weixiaotong.png","pic":"","comment":[],"like":[],"readcount":1,"allreader":9,"readtag":1},{"id":"81","userid":"597","title":"标题","content":"内容","photo":"11.jpg","type":"1","create_time":"1462930788","username":"李春波","avatar":"weixiaotong.png","pic":"","comment":[],"like":[],"readcount":1,"allreader":9,"readtag":1},{"id":"80","userid":"597","title":"13123","content":"1123123123","photo":"default.jpg","type":"1","create_time":"1462879528","username":"李春波","avatar":"weixiaotong.png","pic":[{"id":"41","pictureurl":"uploads/microblog/notice5971521706.png","create_time":"1462879528"}],"comment":[],"like":[],"readcount":1,"allreader":9,"readtag":1},{"id":"79","userid":"597","title":"231231","content":"123123123","photo":"default.jpg","type":"1","create_time":"1462879486","username":"李春波","avatar":"weixiaotong.png","pic":[{"id":"39","pictureurl":"uploads/microblog/notice5971699199.png","create_time":"1462879486"},{"id":"40","pictureurl":"uploads/microblog/notice5971239919.png","create_time":"1462879486"}],"readcount":1,"allreader":9,"readtag":1}]
     */

    private String status;
    /**
     * id : 116
     * userid : 605
     * title :
     * content :
     * photo : newpic.jpg
     * type : 3
     * create_time : 1463390797
     * username : 潘宁
     * avatar : weixiaotong.png
     * pic :
     * comment : [{"userid":"a","avatar":"aaa","name":"aaaa","content":"aaa"}]
     * like : [{"userid":"a","avatar":"aaa","name":"aaa"}]
     * readcount : 1
     * allreader : 9
     * readtag : 1
     */

    private List<AnnouncementData> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<AnnouncementData> getData() {
        return data;
    }

    public void setData(List<AnnouncementData> data) {
        this.data = data;
    }

    public static class AnnouncementData implements Serializable{
        private String id;
        private String userid;
        private String title;
        private String content;
        private String photo;
        private String type;
        private String create_time;
        private String username;
        private String avatar;
        private List<PicData> pic;
        private int readcount;
        private int allreader;
        private int readtag;
        /**
         * userid : a
         * avatar : aaa
         * name : aaaa
         * content : aaa
         */

        private ArrayList<Comments> comment;
        /**
         * userid : a
         * avatar : aaa
         * name : aaa
         */

        private List<LikeBean> like;

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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public List<PicData> getPic() {
            return pic;
        }

        public void setPic(List<PicData> pic) {
            this.pic = pic;
        }

        public int getReadcount() {
            return readcount;
        }

        public void setReadcount(int readcount) {
            this.readcount = readcount;
        }

        public int getAllreader() {
            return allreader;
        }

        public void setAllreader(int allreader) {
            this.allreader = allreader;
        }

        public int getReadtag() {
            return readtag;
        }

        public void setReadtag(int readtag) {
            this.readtag = readtag;
        }

        public ArrayList<Comments> getComment() {
            return comment;
        }

        public void setComment(ArrayList<Comments> comment) {
            this.comment = comment;
        }

        public List<LikeBean> getLike() {
            if (like == null || like.equals("null")) {
                like = new ArrayList<LikeBean>();
            }
            return like;
        }

        public void setLike(List<LikeBean> like) {
            this.like = like;
        }
        public static class PicData implements Serializable{
            private String id;
            private String pictureurl;
            private String create_time;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getPictureurl() {
                return pictureurl;
            }

            public void setPictureurl(String pictureurl) {
                this.pictureurl = pictureurl;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }
        }


    }
}
