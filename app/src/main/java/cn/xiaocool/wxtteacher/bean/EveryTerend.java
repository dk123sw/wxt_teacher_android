package cn.xiaocool.wxtteacher.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/31.
 */
public class EveryTerend implements Serializable {

    private String status;

    private List<EveryTerendData> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<EveryTerendData> getData() {
        return data;
    }

    public void setData(List<EveryTerendData> data) {
        this.data = data;
    }

    public static class EveryTerendData {
        private String id;
        private String userid;
        private String title;
        private String content;
        private String photo;
        private String create_time;
        private String username;
        private int readcount;
        private int allreader;
        private int readtag;
        private ArrayList<LikeBean> Praise;// 点赞人
        private ArrayList<Comments> comment;// 评论

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
            return "EveryTerendData{" +
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
    }

    @Override
    public String toString() {
        return "Homework{" +
                "status='" + status + '\'' +
                ", data=" + data +
                '}';
    }



}
