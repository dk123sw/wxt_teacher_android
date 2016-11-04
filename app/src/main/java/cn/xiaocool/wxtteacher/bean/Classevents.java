package cn.xiaocool.wxtteacher.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/20.
 */
public class Classevents implements Serializable{

    /**
     * status : success
     * data : [{"activity_title":"清明节扫墓春游","activity_content":"烈士陵园，纪念祖国先烈清明节扫墓春游","releasename":"刘老师","activity_pic":"chunyou.jpg","activity_time":"1458196885"},{"activity_title":"海边游泳","activity_content":"海边游泳","releasename":"老王","activity_pic":"youyong.jpg","activity_time":"1458132885"}]
     */

    private String status;
    /**
     * activity_title : 清明节扫墓春游
     * activity_content : 烈士陵园，纪念祖国先烈清明节扫墓春游
     * releasename : 刘老师
     * activity_pic : chunyou.jpg
     * activity_time : 1458196885
     */

    private List<ClassEventData> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ClassEventData> getData() {
        return data;
    }

    public void setData(List<ClassEventData> data) {
        this.data = data;
    }

    public static class ClassEventData implements Serializable {
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
        private ArrayList<String> pics;
        private ArrayList<ReciverlistInfo> reciverlist;
        private ArrayList<IsApplyList> isApplyLists;

        private String contactman;
        private String contactphone;

        //活动时间
        private String begintime;
        private String endtime;

        //报名时间
        private String starttime;
        private String finishtime;


        private String teachername;
        private String teacheravtar;

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
            return pics;
        }

        public void setPics(ArrayList<String> pics) {
            this.pics = pics;
        }

        public ArrayList<ReciverlistInfo> getReciverlist() {
            return reciverlist;
        }

        public void setReciverlist(ArrayList<ReciverlistInfo> reciverlist) {
            this.reciverlist = reciverlist;
        }

        public ArrayList<IsApplyList> getIsApplyLists() {
            return isApplyLists;
        }

        public void setIsApplyLists(ArrayList<IsApplyList> isApplyLists) {
            this.isApplyLists = isApplyLists;
        }

        public String getContactman() {
            return contactman;
        }

        public void setContactman(String contactman) {
            this.contactman = contactman;
        }

        public String getContactphone() {
            return contactphone;
        }

        public void setContactphone(String contactphone) {
            this.contactphone = contactphone;
        }

        public String getBegintime() {
            return begintime;
        }

        public void setBegintime(String begintime) {
            this.begintime = begintime;
        }

        public String getEndtime() {
            return endtime;
        }

        public void setEndtime(String endtime) {
            this.endtime = endtime;
        }

        public String getStarttime() {
            return starttime;
        }

        public void setStarttime(String starttime) {
            this.starttime = starttime;
        }

        public String getFinishtime() {
            return finishtime;
        }

        public void setFinishtime(String finishtime) {
            this.finishtime = finishtime;
        }

        public String getTeachername() {
            return teachername;
        }

        public void setTeachername(String teachername) {
            this.teachername = teachername;
        }

        public String getTeacheravtar() {
            return teacheravtar;
        }

        public void setTeacheravtar(String teacheravtar) {
            this.teacheravtar = teacheravtar;
        }

        public static class ReciverlistInfo implements Serializable{


            private String id;
            private String activity_id;
            private String receiverid;
            private String read_time;
            private String name;
            private String photo;
            private String phone;


            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getActivity_id() {
                return activity_id;
            }

            public void setActivity_id(String activity_id) {
                this.activity_id = activity_id;
            }

            public String getReceiverid() {
                return receiverid;
            }

            public void setReceiverid(String receiverid) {
                this.receiverid = receiverid;
            }

            public String getRead_time() {
                return read_time;
            }

            public void setRead_time(String read_time) {
                this.read_time = read_time;
            }

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

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }
        }

        public static class IsApplyList implements Serializable {
            /**
             * userid : 661
             * avatar : weixiaotong.png
             * name : 何北
             * applyid : 2
             * fathername :
             * contactphone :
             * age : 0
             * sex : 0
             * create_time : 1469196877
             */

            private String userid;
            private String avatar;
            private String name;
            private String applyid;
            private String fathername;
            private String contactphone;
            private String age;
            private String sex;
            private String create_time;

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

            public String getApplyid() {
                return applyid;
            }

            public void setApplyid(String applyid) {
                this.applyid = applyid;
            }

            public String getFathername() {
                return fathername;
            }

            public void setFathername(String fathername) {
                this.fathername = fathername;
            }

            public String getContactphone() {
                return contactphone;
            }

            public void setContactphone(String contactphone) {
                this.contactphone = contactphone;
            }

            public String getAge() {
                return age;
            }

            public void setAge(String age) {
                this.age = age;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

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
