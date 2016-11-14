package cn.xiaocool.wxtteacher.bean;

/**
 * Created by Administrator on 2016/10/27.
 */
public class CommunicateModel {

    /**
     * id : 13
     * send_uid : 597
     * receive_uid : 605
     * content : heloo111
     * status : 1
     * create_time : 1477466570
     * send_face : newsgroup9281472003298107.jpg
     * send_nickname : Gfh
     * receive_face : newsgroup6171473498324434.jpg
     * receive_nickname : 再见十八岁
     */

    private String id;
    private String send_uid;
    private String receive_uid;
    private String content;
    private String status;
    private String create_time;
    private String send_face;
    private String send_nickname;
    private String receive_face;
    private String receive_nickname;
    private String send_type;
    private String receive_type;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSend_uid() {
        return send_uid;
    }

    public void setSend_uid(String send_uid) {
        this.send_uid = send_uid;
    }

    public String getReceive_uid() {
        return receive_uid;
    }

    public void setReceive_uid(String receive_uid) {
        this.receive_uid = receive_uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getSend_face() {
        return send_face;
    }

    public void setSend_face(String send_face) {
        this.send_face = send_face;
    }

    public String getSend_nickname() {
        return send_nickname;
    }

    public void setSend_nickname(String send_nickname) {
        this.send_nickname = send_nickname;
    }

    public String getReceive_face() {
        return receive_face;
    }

    public void setReceive_face(String receive_face) {
        this.receive_face = receive_face;
    }

    public String getReceive_nickname() {
        return receive_nickname;
    }

    public void setReceive_nickname(String receive_nickname) {
        this.receive_nickname = receive_nickname;
    }

    public String getSend_type() {
        return send_type;
    }

    public void setSend_type(String send_type) {
        this.send_type = send_type;
    }

    public String getReceive_type() {
        return receive_type;
    }

    public void setReceive_type(String receive_type) {
        this.receive_type = receive_type;
    }
}
