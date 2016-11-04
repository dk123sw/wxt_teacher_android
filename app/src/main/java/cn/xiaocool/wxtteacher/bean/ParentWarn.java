package cn.xiaocool.wxtteacher.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/7.
 */
public class ParentWarn implements Serializable{

    /**
     * status : success
     * data : [{"id":"17","userid":"12","studentid":"22","teacherid":"597","content":"孩子有点感冒，让中午吃药","create_time":"1462330167","username":"潘宁","studentname":"潘亮","teachername":"李春波"},{"id":"16","userid":"12","studentid":"22","teacherid":"597","content":"孩子有点感冒，让中午吃药","create_time":"1461999741","username":"潘宁","studentname":"潘亮","teachername":"李春波"},{"id":"15","userid":"12","studentid":"22","teacherid":"597","content":"孩子有点感冒，让中午吃药","create_time":"1461999740","username":"潘宁","studentname":"潘亮","teachername":"李春波"},{"id":"14","userid":"12","studentid":"22","teacherid":"597","content":"孩子有点感冒，让中午吃药","create_time":"1461999739","username":"潘宁","studentname":"潘亮","teachername":"李春波"},{"id":"13","userid":"12","studentid":"22","teacherid":"597","content":"孩子有点感冒，让中午吃药","create_time":"1461999651","username":"潘宁","studentname":"潘亮","teachername":"李春波"},{"id":"12","userid":"12","studentid":"22","teacherid":"597","content":"孩子有点感冒，让中午吃药","create_time":"1461918991","username":"潘宁","studentname":"潘亮","teachername":"李春波"},{"id":"11","userid":"12","studentid":"22","teacherid":"597","content":"孩子有点感冒，让中午吃药","create_time":"1461916835","username":"潘宁","studentname":"潘亮","teachername":"李春波"}]
     */

    private String status;
    /**
     * id : 17
     * userid : 12
     * studentid : 22
     * teacherid : 597
     * content : 孩子有点感冒，让中午吃药
     * create_time : 1462330167
     * username : 潘宁
     * studentname : 潘亮
     * teachername : 李春波
     */

    private List<ParentWarnData> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ParentWarnData> getData() {
        return data;
    }

    public void setData(List<ParentWarnData> data) {
        this.data = data;
    }

    public static class ParentWarnData {
        private String id;
        private String userid;
        private String title;
        private String content;
        private String photo;
        private String create_time;
        private String username;
        private String studentname;
        private String studentavatar;
        private int readcount;
        private int allreader;
        private int readtag;
        private ArrayList<LikeBean> Praise;// 点赞人
        private ArrayList<Comments> comment;// 评论
        private ArrayList<String> pic;//图片数组




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

        public ArrayList<String> getPic() {
            if (pic == null || pic.equals("null")) {
                pic = new ArrayList<String>();
            }
            return pic;
        }

        public void setPic(ArrayList<String> pic) {
            this.pic = pic;
        }

        public String getStudentname() {
            return studentname;
        }

        public void setStudentname(String studentname) {
            this.studentname = studentname;
        }

        public String getStudentavatar() {
            return studentavatar;
        }

        public void setStudentavatar(String studentavatar) {
            this.studentavatar = studentavatar;
        }

        public static class LikeBean {
            private String userid;
            private String avatar;
            private String name;


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


        }



        @Override
        public String toString() {
            return "ParentWarnData{" +
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
        return "ParentWarn{" +
                "status='" + status + '\'' +
                ", data=" + data +
                '}';
    }
}
