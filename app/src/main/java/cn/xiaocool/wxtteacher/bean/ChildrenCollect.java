package cn.xiaocool.wxtteacher.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/5/9.
 */
public class ChildrenCollect implements Serializable {

    /**
     * status : success
     * data : [{"id":"1","teacherid":"609","userid":"28","photo":"","delivery_time":"1457596464","delivery_status":"1","parentid":"122","parenttime":"1462415788","teachername":"老王","teacheravatar":"weixiaotong.png","teacherphone":"18865511108","studentname":"","studentavatar":"default.png","studentphone":""},{"id":"2","teacherid":"609","userid":"28","photo":"","delivery_time":"1457596466","delivery_status":"1","parentid":"0","parenttime":"0","teachername":"老王","teacheravatar":"weixiaotong.png","teacherphone":"18865511108","studentname":"","studentavatar":"default.png","studentphone":""},{"id":"3","teacherid":"609","userid":"597","photo":"122.jpg","delivery_time":"1462785198","delivery_status":"0","parentid":"28","parenttime":"0","teachername":"老王","teacheravatar":"weixiaotong.png","teacherphone":"18865511108","studentname":"","studentavatar":"default.png","studentphone":""},{"id":"4","teacherid":"609","userid":"597","photo":"122.jpg","delivery_time":"1462785384","delivery_status":"0","parentid":"28","parenttime":"0","teachername":"老王","teacheravatar":"weixiaotong.png","teacherphone":"18865511108","studentname":"","studentavatar":"default.png","studentphone":""}]
     */

    private String status;
    /**
     * id : 1
     * teacherid : 609
     * userid : 28
     * photo :
     * delivery_time : 1457596464
     * delivery_status : 1
     * parentid : 122
     * parenttime : 1462415788
     * teachername : 老王
     * teacheravatar : weixiaotong.png
     * teacherphone : 18865511108
     * studentname :
     * studentavatar : default.png
     * studentphone :
     */

    private List<ChildrenCollectData> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ChildrenCollectData> getData() {
        return data;
    }

    public void setData(List<ChildrenCollectData> data) {
        this.data = data;
    }

    public static class ChildrenCollectData {


    /**    "id": "843",
                "teacherid": "605",
                "studentid": "682",
                "photo": "122.jpg",
                "content": "",
                "delivery_time": "1469066197",
                "delivery_status": "1",
                "parentid": "681",
                "parenttime": "1469066622",
                "classname": null,
                "teachername": "潘宁",
                "teacheravatar": "啥时候",
                "teacherphone": "15589521956",
                "parentname": "蒋庆超",
                "parentavatar": "weixiaotong.png",
                "parentphone": "18672910380",
                "studentname": "蒋庆学生",
                "studentavatar": "weixiaotong.png",
                "studentphone": ""
     */
        private String id;
        private String teacherid;
        private String classname;
        private String userid;
        private String content;
        private String photo;
        private String delivery_time;
        private String delivery_status;
        private String parentid;
        private String parenttime;
        private String parentname;
        private String parentphone;
        private String parentavatar;
        private String teachername;
        private String teacheravatar;
        private String teacherphone;
        private String studentname;
        private String studentavatar;
        private String studentphone;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTeacherid() {
            return teacherid;
        }

        public void setTeacherid(String teacherid) {
            this.teacherid = teacherid;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getDelivery_time() {
            return delivery_time;
        }

        public void setDelivery_time(String delivery_time) {
            this.delivery_time = delivery_time;
        }

        public String getDelivery_status() {
            return delivery_status;
        }

        public void setDelivery_status(String delivery_status) {
            this.delivery_status = delivery_status;
        }

        public String getParentid() {
            return parentid;
        }

        public void setParentid(String parentid) {
            this.parentid = parentid;
        }

        public String getParenttime() {
            return parenttime;
        }

        public void setParenttime(String parenttime) {
            this.parenttime = parenttime;
        }

        public String getTeachername() {
            return teachername;
        }

        public void setTeachername(String teachername) {
            this.teachername = teachername;
        }

        public String getTeacheravatar() {
            return teacheravatar;
        }

        public void setTeacheravatar(String teacheravatar) {
            this.teacheravatar = teacheravatar;
        }

        public String getTeacherphone() {
            return teacherphone;
        }

        public void setTeacherphone(String teacherphone) {
            this.teacherphone = teacherphone;
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

        public String getStudentphone() {
            return studentphone;
        }

        public void setStudentphone(String studentphone) {
            this.studentphone = studentphone;
        }

        @Override
        public String toString() {
            return "ChildrenCollectData{" +
                    "id='" + id + '\'' +
                    ", teacherid='" + teacherid + '\'' +
                    ", userid='" + userid + '\'' +
                    ", photo='" + photo + '\'' +
                    ", delivery_time='" + delivery_time + '\'' +
                    ", delivery_status='" + delivery_status + '\'' +
                    ", parentid='" + parentid + '\'' +
                    ", parenttime='" + parenttime + '\'' +
                    ", teachername='" + teachername + '\'' +
                    ", teacheravatar='" + teacheravatar + '\'' +
                    ", teacherphone='" + teacherphone + '\'' +
                    ", studentname='" + studentname + '\'' +
                    ", studentavatar='" + studentavatar + '\'' +
                    ", studentphone='" + studentphone + '\'' +
                    '}';
        }

        public String getParentname() {
            return parentname;
        }

        public void setParentname(String parentname) {
            this.parentname = parentname;
        }

        public String getParentphone() {
            return parentphone;
        }

        public void setParentphone(String parentphone) {
            this.parentphone = parentphone;
        }

        public String getParentavatar() {
            return parentavatar;
        }

        public void setParentavatar(String parentavatar) {
            this.parentavatar = parentavatar;
        }

        public String getClassname() {
            return classname;
        }

        public void setClassname(String classname) {
            this.classname = classname;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    @Override
    public String toString() {
        return "ChildrenCollect{" +
                "status='" + status + '\'' +
                ", data=" + data +
                '}';
    }
}
