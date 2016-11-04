package cn.xiaocool.wxtteacher.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/8/10.
 */
public class ClassEventCount implements Serializable{

    /**
     * id : 1
     * classname : 小一班
     * class_count : 7
     * teacher_info : [{"id":"733","name":"赵晓霞","classid":"1","teacher_count":"0"},{"id":"732","name":"陈玲玲","classid":"1","teacher_count":"0"},{"id":"651","name":"唐海波","classid":"1","teacher_count":"2"},{"id":"605","name":"再见十八岁","classid":"1","teacher_count":"5"}]
     */

    private String id;
    private String classname;
    private String class_count;
    /**
     * id : 733
     * name : 赵晓霞
     * classid : 1
     * teacher_count : 0
     */

    private List<TeacherInfoBean> teacher_info;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getClass_count() {
        return class_count;
    }

    public void setClass_count(String class_count) {
        this.class_count = class_count;
    }

    public List<TeacherInfoBean> getTeacher_info() {
        return teacher_info;
    }

    public void setTeacher_info(List<TeacherInfoBean> teacher_info) {
        this.teacher_info = teacher_info;
    }

    public static class TeacherInfoBean implements Serializable{
        private String id;
        private String name;
        private String classid;
        private String teacher_count;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getClassid() {
            return classid;
        }

        public void setClassid(String classid) {
            this.classid = classid;
        }

        public String getTeacher_count() {
            return teacher_count;
        }

        public void setTeacher_count(String teacher_count) {
            this.teacher_count = teacher_count;
        }
    }
}
