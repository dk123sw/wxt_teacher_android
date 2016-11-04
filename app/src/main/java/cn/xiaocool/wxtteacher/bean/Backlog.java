package cn.xiaocool.wxtteacher.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/19.
 */
public class Backlog implements Serializable {

    private String status;
    /**
     *   "status": "success",
     "data": [
     {
     "id": "120",
     "userid": "605",
     "title": "学校大待办事宜",
     "content": "学校大待办事宜",
     "type": "3",
     "create_time": "1463621492",
     "username": "潘宁",
     "avatar": "weixiaotong.png",
     "pic": [],
     "comment": [],
     "": [],
     "readcount": 1,
     "allreader": 9,
     "readtag": 1
     },
     */

    private List<BacklogData> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<BacklogData> getData() {
        return data;
    }

    public void setData(List<BacklogData> data) {
        this.data = data;
    }

    public static class BacklogData implements Serializable{

//
//        "id": "124",
//                "userid": "597",
//                "name": "李春波12312",
//                "photo": "weixiaotong.png",
//                "title": "撒娇的凯撒记得看",
//                "content": "请注意撒旦撒来自教育局的通知",
//                "status": "0",
//                "create_time": "1468489737",
//                "piclist": [
//        {
//            "id": "25",
//                "pictureurl": "uploads/microblog/notice5971611178.png"
//        },
//        {
//            "id": "26",
//                "pictureurl": "uploads/microblog/notice5971292884.png"
//        },

//        "iscando": "1",


        private String id;
        private String userid;
        private String title;
        private String content;
        private String type;
        private String create_time;
        private String username;
        private String avatar;
        private ArrayList<PicData> pic;
        private String iscando;
        private ArrayList<Reciverlist> reciverlist;

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

        public ArrayList<PicData> getPic() {
            return pic;
        }

        public void setPic(ArrayList<PicData> pic) {
            this.pic = pic;
        }



        public String getIscando() {
            return iscando;
        }

        public void setIscando(String iscando) {
            this.iscando = iscando;
        }

        public ArrayList<Reciverlist> getReciverlist() {
            return reciverlist;
        }

        public void setReciverlist(ArrayList<Reciverlist> reciverlist) {
            this.reciverlist = reciverlist;
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


        //                "reciverlist": [
//        {
//            "name": "潘宁",
//                "photo": "啥时候",
//                "feedback": "",
//                "finish": "0",
//                "read_time": null,
//                "create_time": "127"
//        }
//        ]
        public static class Reciverlist implements Serializable{
            private String id;
            private String name;
            private String feedback;
            private String finish;
            private String read_time;
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

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getFeedback() {
                return feedback;
            }

            public void setFeedback(String feedback) {
                this.feedback = feedback;
            }

            public String getFinish() {
                return finish;
            }

            public void setFinish(String finish) {
                this.finish = finish;
            }

            public String getRead_time() {
                return read_time;
            }

            public void setRead_time(String read_time) {
                this.read_time = read_time;
            }
        }
    }
}
