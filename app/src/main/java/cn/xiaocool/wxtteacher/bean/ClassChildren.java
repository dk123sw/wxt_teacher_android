package cn.xiaocool.wxtteacher.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/4/26.
 */
public class ClassChildren implements Serializable {

    /**
     * status : success
     * data : [{"id":"597","name":"李春波","phone":"18363866803"}]
     */

    private String status;
    /**
     * id : 597
     * name : 李春波
     * phone : 18363866803
     */

    private List<ClassChildrenData> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ClassChildrenData> getData() {
        return data;
    }

    public void setData(List<ClassChildrenData> data) {
        this.data = data;
    }

    public static class ClassChildrenData {
        private String id;
        private String name;
        private String phone;

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

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", phone='" + phone + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ClassChildren{" +
                "status='" + status + '\'' +
                ", data=" + data +
                '}';
    }
}
