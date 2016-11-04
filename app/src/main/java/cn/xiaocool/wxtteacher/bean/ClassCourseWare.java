package cn.xiaocool.wxtteacher.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/8/15.
 */
public class ClassCourseWare implements Serializable {


    /**
     * id : 1
     * subject : 语文
     * courseware_info : [{"courseware_id":"11","schoolid":"1","classid":"1","user_id":"597","subjectid":"1","courseware_title":"","courseware_content":"课件测试","courseware_url":"","courseware_time":"1471243428","courseware_status":"0","teacher_name":"奥黛丽赫本","teacher_photo":"newsgroup4051469613043127.jpg","teacher_duty":"副校长","pic":[]},{"courseware_id":"14","schoolid":"1","classid":"1","user_id":"597","subjectid":"1","courseware_title":"","courseware_content":"班级课件","courseware_url":"","courseware_time":"1471245822","courseware_status":"0","teacher_name":"奥黛丽赫本","teacher_photo":"newsgroup4051469613043127.jpg","teacher_duty":"副校长","pic":[{"picture_url":""}]},{"courseware_id":"17","schoolid":"1","classid":"1","user_id":"597","subjectid":"1","courseware_title":"","courseware_content":"","courseware_url":"","courseware_time":"1471245924","courseware_status":"0","teacher_name":"奥黛丽赫本","teacher_photo":"newsgroup4051469613043127.jpg","teacher_duty":"副校长","pic":[{"picture_url":""}]},{"courseware_id":"20","schoolid":"1","classid":"1","user_id":"597","subjectid":"1","courseware_title":"","courseware_content":"","courseware_url":"","courseware_time":"1471246342","courseware_status":"0","teacher_name":"奥黛丽赫本","teacher_photo":"newsgroup4051469613043127.jpg","teacher_duty":"副校长","pic":[{"picture_url":""}]},{"courseware_id":"23","schoolid":"1","classid":"1","user_id":"597","subjectid":"1","courseware_title":"标题","courseware_content":"班级课件","courseware_url":"","courseware_time":"1471246378","courseware_status":"0","teacher_name":"奥黛丽赫本","teacher_photo":"newsgroup4051469613043127.jpg","teacher_duty":"副校长","pic":[{"picture_url":""}]}]
     */

    private String id;
    private String subject;
    /**
     * courseware_id : 11
     * schoolid : 1
     * classid : 1
     * user_id : 597
     * subjectid : 1
     * courseware_title :
     * courseware_content : 课件测试
     * courseware_url :
     * courseware_time : 1471243428
     * courseware_status : 0
     * teacher_name : 奥黛丽赫本
     * teacher_photo : newsgroup4051469613043127.jpg
     * teacher_duty : 副校长
     * pic : []
     */

    private List<CoursewareInfoBean> courseware_info;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<CoursewareInfoBean> getCourseware_info() {
        return courseware_info;
    }

    public void setCourseware_info(List<CoursewareInfoBean> courseware_info) {
        this.courseware_info = courseware_info;
    }

    public static class CoursewareInfoBean implements Serializable{


        /**
         * courseware_id : 20
         * schoolid : 1
         * classid : 1
         * user_id : 597
         * subjectid : 1
         * courseware_title :
         * courseware_content :
         * courseware_url :
         * courseware_time : 1471246342
         * courseware_status : 0
         * teacher_name : 奥黛丽赫本
         * teacher_photo : newsgroup4051469613043127.jpg
         * teacher_duty : 副校长
         * pic : [{"picture_url":""}]
         */

        private String courseware_id;
        private String schoolid;
        private String classid;
        private String user_id;
        private String subjectid;
        private String courseware_title;
        private String courseware_content;
        private String courseware_url;
        private String courseware_time;
        private String courseware_status;
        private String teacher_name;
        private String teacher_photo;
        private String teacher_duty;
        /**
         * picture_url :
         */

        private List<PicBean> pic;

        public String getCourseware_id() {
            return courseware_id;
        }

        public void setCourseware_id(String courseware_id) {
            this.courseware_id = courseware_id;
        }

        public String getSchoolid() {
            return schoolid;
        }

        public void setSchoolid(String schoolid) {
            this.schoolid = schoolid;
        }

        public String getClassid() {
            return classid;
        }

        public void setClassid(String classid) {
            this.classid = classid;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getSubjectid() {
            return subjectid;
        }

        public void setSubjectid(String subjectid) {
            this.subjectid = subjectid;
        }

        public String getCourseware_title() {
            return courseware_title;
        }

        public void setCourseware_title(String courseware_title) {
            this.courseware_title = courseware_title;
        }

        public String getCourseware_content() {
            return courseware_content;
        }

        public void setCourseware_content(String courseware_content) {
            this.courseware_content = courseware_content;
        }

        public String getCourseware_url() {
            return courseware_url;
        }

        public void setCourseware_url(String courseware_url) {
            this.courseware_url = courseware_url;
        }

        public String getCourseware_time() {
            return courseware_time;
        }

        public void setCourseware_time(String courseware_time) {
            this.courseware_time = courseware_time;
        }

        public String getCourseware_status() {
            return courseware_status;
        }

        public void setCourseware_status(String courseware_status) {
            this.courseware_status = courseware_status;
        }

        public String getTeacher_name() {
            return teacher_name;
        }

        public void setTeacher_name(String teacher_name) {
            this.teacher_name = teacher_name;
        }

        public String getTeacher_photo() {
            return teacher_photo;
        }

        public void setTeacher_photo(String teacher_photo) {
            this.teacher_photo = teacher_photo;
        }

        public String getTeacher_duty() {
            return teacher_duty;
        }

        public void setTeacher_duty(String teacher_duty) {
            this.teacher_duty = teacher_duty;
        }

        public List<PicBean> getPic() {
            return pic;
        }

        public void setPic(List<PicBean> pic) {
            this.pic = pic;
        }

        public static class PicBean implements Serializable{
            private String picture_url;

            public String getPicture_url() {
                return picture_url;
            }

            public void setPicture_url(String picture_url) {
                this.picture_url = picture_url;
            }
        }
    }
}
