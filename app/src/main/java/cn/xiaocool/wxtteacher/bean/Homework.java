package cn.xiaocool.wxtteacher.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/9.
 */
public class Homework implements Serializable {

    /**
     * status : success
     * data : [{"id":"16","userid":"597","title":"AsfasdfasdfA","content":"Asdfasfdasdfasdfa","photo":"5971813.png,5971639.png,5971913.png","create_time":"1462021714","username":"潘宁","readcount":1,"allreader":5,"readtag":1},{"id":"15","userid":"597","title":"32414","content":"12341241234","photo":"","create_time":"1461988751","username":"潘宁","readcount":1,"allreader":5,"readtag":1},{"id":"14","userid":"597","title":"1341212","content":"112341234124","photo":"","create_time":"1461988743","username":"潘宁","readcount":1,"allreader":5,"readtag":1},{"id":"13","userid":"597","title":"233214124","content":"134124312341324","photo":"5971773.png","create_time":"1461985845","username":"潘宁","readcount":1,"allreader":5,"readtag":1},{"id":"12","userid":"597","title":"周四作业","content":"作业内容，快来看","photo":"11.jpg","create_time":"1461917809","username":"潘宁","readcount":1,"allreader":5,"readtag":1},{"id":"11","userid":"597","title":"周四作业","content":"作业内容，快来看","photo":"11.jpg","create_time":"1461917799","username":"潘宁","readcount":1,"allreader":5,"readtag":1},{"id":"10","userid":"597","title":"周四作业","content":"作业内容，快来看","photo":"11.jpg","create_time":"0","username":"潘宁","readcount":1,"allreader":5,"readtag":1},{"id":"9","userid":"597","title":"","content":"作业内容，快来看","photo":"11.jpg","create_time":"0","username":"潘宁","readcount":1,"allreader":5,"readtag":1},{"id":"8","userid":"597","title":"","content":"作业内容，快来看","photo":"11.jpg","create_time":"0","username":"潘宁","readcount":1,"allreader":5,"readtag":1}]
     */

    private String status;
    /**
     * id : 16
     * userid : 597
     * title : AsfasdfasdfA
     * content : Asdfasfdasdfasdfa
     * photo : 5971813.png,5971639.png,5971913.png
     * create_time : 1462021714
     * username : 潘宁
     * readcount : 1
     * allreader : 5
     * readtag : 1
     */

    private List<HomeworkData> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<HomeworkData> getData() {
        return data;
    }

    public void setData(List<HomeworkData> data) {
        this.data = data;
    }

    public static class HomeworkData implements Serializable{
        private String id;
        private String userid;
        private String title;
        private String content;
        private String photo;
        private String create_time;
        private String username;
        private String subject;
        private int readcount;
        private int allreader;
        private int readtag;
        private ArrayList<LikeBean> Praise;// 点赞人
        private ArrayList<Comments> comment;// 评论
        private ArrayList<String> pics;

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
            if (comment == null || comment.equals("null")) {
                comment = new ArrayList<Comments>();
            }
            return comment;
        }

        public void setComment(ArrayList<Comments> comment) {

            this.comment = comment;
        }



        @Override
        public String toString() {
            return "HomeworkData{" +
                    "id='" + id + '\'' +
                    ", userid='" + userid + '\'' +
                    ", title='" + title + '\'' +
                    ", content='" + content + '\'' +
                    ", photo='" + photo + '\'' +
                    ", create_time='" + create_time + '\'' +
                    ", username='" + username + '\'' +
                    ", readcount=" + readcount +
                    ", allreader=" + allreader +
                    ", readtag=" + readtag +
                    '}';
        }

        public ArrayList<LikeBean> getWorkPraise() {
            if (Praise == null || Praise.equals("null")) {
                Praise = new ArrayList<LikeBean>();
            }
            return Praise;
        }

        public void setWorkPraise(ArrayList<LikeBean> workPraise) {
            Praise = workPraise;
        }

        public ArrayList<String> getPics() {
            if (pics == null || pics.equals("null")) {
                pics = new ArrayList<String>();
            }
            return pics;
        }

        public void setPics(ArrayList<String> pics) {
            this.pics = pics;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }
    }

    @Override
    public String toString() {
        return "Homework{" +
                "status='" + status + '\'' +
                ", data=" + data +
                '}';
    }


}

