package cn.xiaocool.wxtteacher.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/3/24.
 */
public class ClassSchedule implements Serializable{

    /**
     * status : success
     * data : [{"syllabus_date":"1","syllabus_name":"语文","syllabus_no":"1"},{"syllabus_date":"1","syllabus_name":"美术","syllabus_no":"2"},{"syllabus_date":"1","syllabus_name":"英语","syllabus_no":"3"},{"syllabus_date":"1","syllabus_name":"语文","syllabus_no":"5"},{"syllabus_date":"1","syllabus_name":"绘画","syllabus_no":"8"},{"syllabus_date":"2","syllabus_name":"语文","syllabus_no":"1"},{"syllabus_date":"3","syllabus_name":"绘画","syllabus_no":"1"},{"syllabus_date":"3","syllabus_name":"数学","syllabus_no":"2"},{"syllabus_date":"4","syllabus_name":"体育","syllabus_no":"1"},{"syllabus_date":"4","syllabus_name":"数学","syllabus_no":"2"},{"syllabus_date":"4","syllabus_name":"语文","syllabus_no":"2"},{"syllabus_date":"5","syllabus_name":"音乐","syllabus_no":"1"}]
     */

    private String status;
    /**
     * syllabus_date : 1
     * syllabus_name : 语文
     * syllabus_no : 1
     */

    private List<ClassScheduleData> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ClassScheduleData> getData() {
        return data;
    }

    public void setData(List<ClassScheduleData> data) {
        this.data = data;
    }

    public static class ClassScheduleData {
        private String syllabus_date;
        private String syllabus_name;
        private String syllabus_no;

        public String getSyllabus_date() {
            return syllabus_date;
        }

        public void setSyllabus_date(String syllabus_date) {
            this.syllabus_date = syllabus_date;
        }

        public String getSyllabus_name() {
            return syllabus_name;
        }

        public void setSyllabus_name(String syllabus_name) {
            this.syllabus_name = syllabus_name;
        }

        public String getSyllabus_no() {
            return syllabus_no;
        }

        public void setSyllabus_no(String syllabus_no) {
            this.syllabus_no = syllabus_no;
        }
    }
}
