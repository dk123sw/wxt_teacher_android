package cn.xiaocool.wxtteacher.bean;

/**
 * Created by Administrator on 2016/8/3.
 */
public class ClassAttendance {


    /**
     * userid : 647
     * name :
     * phone :
     * photo :
     * studentid :
     * arrivetime :
     * leavetime :
     * type :
     * arrivepicture :
     * leavepicture :
     * arrivevideo :
     * leavevideo :
     * sign_date :
     * create_time :
     * status : 0
     */

    private String userid;
    private String name;
    private String phone;
    private String photo;
    private String studentid;
    private String arrivetime;
    private String leavetime;
    private String type;
    private String arrivepicture;
    private String leavepicture;
    private String arrivevideo;
    private String leavevideo;
    private String sign_date;
    private String create_time;
    private String status;


    private String checkedType;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getStudentid() {
        return studentid;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }

    public String getArrivetime() {
        return arrivetime;
    }

    public void setArrivetime(String arrivetime) {
        this.arrivetime = arrivetime;
    }

    public String getLeavetime() {
        return leavetime;
    }

    public void setLeavetime(String leavetime) {
        this.leavetime = leavetime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getArrivepicture() {
        return arrivepicture;
    }

    public void setArrivepicture(String arrivepicture) {
        this.arrivepicture = arrivepicture;
    }

    public String getLeavepicture() {
        return leavepicture;
    }

    public void setLeavepicture(String leavepicture) {
        this.leavepicture = leavepicture;
    }

    public String getArrivevideo() {
        return arrivevideo;
    }

    public void setArrivevideo(String arrivevideo) {
        this.arrivevideo = arrivevideo;
    }

    public String getLeavevideo() {
        return leavevideo;
    }

    public void setLeavevideo(String leavevideo) {
        this.leavevideo = leavevideo;
    }

    public String getSign_date() {
        return sign_date;
    }

    public void setSign_date(String sign_date) {
        this.sign_date = sign_date;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCheckedType() {
        if (checkedType!=null){
            return checkedType;
        }

        if (status.equals("1")){
            return "3";
        }
        if (sign_date==null||sign_date.equals("0")||sign_date.equals("")){
            return "1";
        }

        return "0";
    }

    public String getCheckedTypeByGo(){

        if (checkedType!=null){
            return checkedType;
        }
        if (status.equals("1")){
            return "3";
        }
        if (leavetime==null||leavetime.equals("")||leavetime.equals("0")){
            return "1";
        }


        return "0";
    }

    public void setCheckedType(String checkedType) {
        this.checkedType = checkedType;
    }
}
