package cn.xiaocool.wxtteacher.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/11.
 */
public class ClassList implements Serializable {

    /**
     * status : success
     * data : [{"classid":"2","classname":"一年级二班"},{"classid":"1","classname":"一年级一班"},{"classid":"1","classname":"一年级一班"}]
     */

    private String status;
    /**
     * classid : 2
     * classname : 一年级二班
     */

    private List<ClassListData> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ClassListData> getData() {
        return data;
    }

    public void setData(List<ClassListData> data) {
        this.data = data;
    }

    public static class ClassListData {
        private String classid;
        private String classname;
        private String type;
        private ArrayList<ClassStudentData> studentlist;

        public String getClassid() {
            return classid;
        }

        public void setClassid(String classid) {
            this.classid = classid;
        }

        public String getClassname() {
            return classname;
        }

        public void setClassname(String classname) {
            this.classname = classname;
        }

        public ArrayList<cn.xiaocool.wxtteacher.bean.ClassList.ClassStudentData> getStudentlist() {
            if (studentlist==null){
                studentlist = new ArrayList<>();
            }
            return studentlist;
        }

        public void setStudentlist(ArrayList<cn.xiaocool.wxtteacher.bean.ClassList.ClassStudentData> studentlist) {
            this.studentlist = studentlist;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public static class ClassStudentData implements Serializable{

        /*   "studentid": "661",
                   "comment_time": "1468651540",
                   "comment_status": "1",
                   "learn": "1",
                   "work": "1",
                   "sing": "1",
                   "labour": "1",
                   "strain": "1",
                   "comment_content": "哈哈哈"*/
        private String id;
        private String phone;
        private String name;
        private String sex;
        private String photo;
        private ArrayList<TeacherComment> teacherComments;
        private String type;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public ArrayList<TeacherComment> getTeacherComments() {
            if (teacherComments==null){
                teacherComments = new ArrayList<>();
            }
            return teacherComments;
        }

        public void setTeacherComments(ArrayList<TeacherComment> teacherComments) {
            this.teacherComments = teacherComments;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }


        public static class TeacherComment implements Serializable{

            /*   "studentid": "661",
                       "comment_time": "1468651540",
                       "comment_status": "1",
                       "learn": "1",
                       "work": "1",
                       "sing": "1",
                       "labour": "1",
                       "strain": "1",
                       "comment_content": "哈哈哈"*/
            private String id;
            private String phone;
            private String name;
            private String sex;
            private String photo;

            private String comment_time;
            private String comment_status;
            private String learn;
            private String work;
            private String sing;
            private String labour;
            private String strain;
            private String comment_content;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getPhoto() {
                return photo;
            }

            public void setPhoto(String photo) {
                this.photo = photo;
            }

            public String getComment_time() {
                return comment_time;
            }

            public void setComment_time(String comment_time) {
                this.comment_time = comment_time;
            }

            public String getComment_status() {
                return comment_status;
            }

            public void setComment_status(String comment_status) {
                this.comment_status = comment_status;
            }

            public String getLearn() {
                return learn;
            }

            public void setLearn(String learn) {
                this.learn = learn;
            }

            public String getWork() {
                return work;
            }

            public void setWork(String work) {
                this.work = work;
            }

            public String getSing() {
                return sing;
            }

            public void setSing(String sing) {
                this.sing = sing;
            }

            public String getLabour() {
                return labour;
            }

            public void setLabour(String labour) {
                this.labour = labour;
            }

            public String getStrain() {
                return strain;
            }

            public void setStrain(String strain) {
                this.strain = strain;
            }

            public String getComment_content() {
                return comment_content;
            }

            public void setComment_content(String comment_content) {
                this.comment_content = comment_content;
            }
        }
    }


}
