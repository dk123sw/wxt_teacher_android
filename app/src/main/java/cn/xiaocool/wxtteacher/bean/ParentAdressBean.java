package cn.xiaocool.wxtteacher.bean;

import java.io.Serializable;
import java.util.List;

import cn.xiaocool.wxtteacher.bean.bean.TreeNodeId;
import cn.xiaocool.wxtteacher.bean.bean.TreeNodeLabel;
import cn.xiaocool.wxtteacher.bean.bean.TreeNodePid;

/**
 * Created by Administrator on 2016/9/20.
 */
public class ParentAdressBean implements Serializable{

    /**
     * classid : 1
     * teacherid : 605
     * classname : 小一班
     * student_list : [{"id":"647","name":"随便1","photo":"weixiaotong.png","parent_list":[]},{"id":"646","name":"随变2","photo":"weixiaotong.png","parent_list":[]},{"id":"648","name":"随便3","photo":"weixiaotong.png","parent_list":[]},{"id":"643","name":"随便4","photo":"weixiaotong.png","parent_list":[]},{"id":"645","name":"随便6","photo":"weixiaotong.png","parent_list":[]},{"id":"644","name":"随便7","photo":"weixiaotong.png","parent_list":[]},{"id":"649","name":"随便8","photo":"weixiaotong.png","parent_list":[]},{"id":"657","name":"随便9","photo":"weixiaotong.png","parent_list":[]},{"id":"658","name":"10","photo":"weixiaotong.png","parent_list":[]},{"id":"665","name":"朱晟超","photo":"weixiaotong.png","parent_list":[{"id":"734","name":"吴纯纯","photo":"20160909171603735.png","phone":"18358334203","appellation":"母亲"},{"id":"735","name":"陈伟","photo":"20160909182734735.png","phone":"18267892065","appellation":"父亲"}]},{"id":"666","name":"秦安雅","photo":"newsgroup9321473428030668.jpg","parent_list":[{"id":"720","name":"彭叔叔","photo":"20160830175211666.png","phone":"13757706668","appellation":"叔叔"},{"id":"611","name":"彭水国","photo":"newsgroup4391471071538768.jpg","phone":"13611637163","appellation":"表舅公"},{"id":"686","name":"丁优雅","photo":"20160901153324686.png","phone":"13181514339","appellation":"母亲"}]},{"id":"667","name":"林程跃","photo":"weixiaotong.png","parent_list":[]},{"id":"668","name":"林欣辰","photo":"weixiaotong.png","parent_list":[]},{"id":"669","name":"曾熙","photo":"weixiaotong.png","parent_list":[]},{"id":"670","name":"黄显江","photo":"weixiaotong.png","parent_list":[]},{"id":"671","name":"薛直得","photo":"weixiaotong.png","parent_list":[]},{"id":"682","name":"蒋庆学生","photo":"20160817151532682.png","parent_list":[{"id":"717","name":"Defef","photo":"20160805155113681.png","phone":"122334445","appellation":"阿姨"},{"id":"718","name":"Secede","photo":"20160805171641681.png","phone":"1234567","appellation":"姥爷"}]},{"id":"661","name":"啊强小衰","photo":"20160825183402661.png","parent_list":[{"id":"681","name":"高德江","photo":"20160905181915681.png","phone":"18672910380","appellation":"父亲"},{"id":"790","name":"也成","photo":"20160903171319681.png","phone":"123454323","appellation":"叔叔"},{"id":"784","name":"芭芭","photo":"20160824170413681.png","phone":"130","appellation":"姥姥"}]}]
     */

    private String classid;
    private String teacherid;
    @TreeNodeLabel
    private String classname;
    @TreeNodeId
    private int nid;
    @TreeNodePid
    private int nparentId;
    /**
     * id : 647
     * name : 随便1
     * photo : weixiaotong.png
     * parent_list : []
     */

    private List<StudentListBean> student_list;

    public String getClassid() {
        return classid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }

    public String getTeacherid() {
        return teacherid;
    }

    public void setTeacherid(String teacherid) {
        this.teacherid = teacherid;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public List<StudentListBean> getStudent_list() {
        return student_list;
    }

    public void setStudent_list(List<StudentListBean> student_list) {
        this.student_list = student_list;
    }


    public int getNid() {
        return nid;
    }

    public void setNid(int nid) {
        this.nid = nid;
    }

    public int getNparentId() {
        return nparentId;
    }

    public void setNparentId(int nparentId) {
        this.nparentId = nparentId;
    }

    public  class StudentListBean implements Serializable{
        @TreeNodeId
        private String id;
        @TreeNodeLabel
        private String name;
        private String photo;
        private List<ParentListBean> parent_list;
        @TreeNodeId
        private int nid;
        @TreeNodePid
        private int nparentId;

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

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }


        public List<ParentListBean> getParent_list() {
            return parent_list;
        }

        public void setParent_list(List<ParentListBean> parent_list) {
            this.parent_list = parent_list;
        }


        public int getNid() {
            return nid;
        }

        public void setNid(int nid) {
            this.nid = nid;
        }

        public int getNparentId() {
            return nparentId;
        }

        public void setNparentId(int nparentId) {
            this.nparentId = nparentId;
        }
    }

    public class ParentListBean implements Serializable{

        /**
         * id : 681
         * name : 高德江
         * photo : 20160905181915681.png
         * phone : 18672910380
         * appellation : 父亲
         */
        @TreeNodeId
        private String id;
        @TreeNodeLabel
        private String name;
        private String photo;
        private String phone;
        private String appellation;
        @TreeNodeId
        private int nid;
        @TreeNodePid
        private int nparentId;

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

        public String getAppellation() {
            return appellation;
        }

        public void setAppellation(String appellation) {
            this.appellation = appellation;
        }


        public int getNid() {
            return nid;
        }

        public void setNid(int nid) {
            this.nid = nid;
        }

        public int getNparentId() {
            return nparentId;
        }

        public void setNparentId(int nparentId) {
            this.nparentId = nparentId;
        }
    }
}
