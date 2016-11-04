package cn.xiaocool.wxtteacher.bean.find;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by mac on 16/2/21.
 */
public class DiscussInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    private String WorkDiscussId;// 评论ID
    private String WorkBindMemberId;// 评论的用户ID
    private String WorkBindUnMember;// 是否为二次评论 0=>否 1=>是
    private String WorkDiscussMatter;// 评论内容
    private String WorkDiscussPraise;// 点赞次数
    private String WorkDiscussTime;// 评论时间
    private String MemberName;// 用户姓名
    private String ToMemberName;//被评论的姓名
    private String MemberPostition;// 公司职位
    private String MemberImg;// 用户头像
    private ArrayList<DiscussInfo> SonDiscuss;

    public String getWorkDiscussId() {
        return WorkDiscussId;
    }

    public void setWorkDiscussId(String workDiscussId) {
        WorkDiscussId = workDiscussId;
    }

    public String getWorkBindMemberId() {
        return WorkBindMemberId;
    }

    public void setWorkBindMemberId(String workBindMemberId) {
        WorkBindMemberId = workBindMemberId;
    }

    public String getWorkBindUnMember() {
        return WorkBindUnMember;
    }

    public void setWorkBindUnMember(String workBindUnMember) {
        WorkBindUnMember = workBindUnMember;
    }

    public String getWorkDiscussMatter() {
        return WorkDiscussMatter;
    }

    public void setWorkDiscussMatter(String workDiscussMatter) {
        WorkDiscussMatter = workDiscussMatter;
    }

    public String getWorkDiscussPraise() {
        return WorkDiscussPraise;
    }

    public void setWorkDiscussPraise(String workDiscussPraise) {
        WorkDiscussPraise = workDiscussPraise;
    }

    public String getWorkDiscussTime() {
        return WorkDiscussTime;
    }

    public void setWorkDiscussTime(String workDiscussTime) {
        WorkDiscussTime = workDiscussTime;
    }

    public String getMemberName() {
        return MemberName;
    }

    public void setMemberName(String memberName) {
        MemberName = memberName;
    }

    public String getToMemberName() {
        return ToMemberName;
    }

    public void setToMemberName(String toMemberName) {
        ToMemberName = toMemberName;
    }

    public String getMemberPostition() {
        return MemberPostition;
    }

    public void setMemberPostition(String memberPostition) {
        MemberPostition = memberPostition;
    }

    public String getMemberImg() {
        return MemberImg;
    }

    public void setMemberImg(String memberImg) {
        MemberImg = memberImg;
    }

    public ArrayList<DiscussInfo> getSonDiscuss() {
        if (SonDiscuss == null || SonDiscuss.equals("null")) {
            SonDiscuss = new ArrayList<DiscussInfo>();
        }
        return SonDiscuss;
    }

    public void setSonDiscuss(ArrayList<DiscussInfo> sonDiscuss) {
        SonDiscuss = sonDiscuss;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    @Override
    public String toString() {
        return "WorkDiscussInfo [WorkDiscussId=" + WorkDiscussId + ", WorkBindMemberId=" + WorkBindMemberId + ", WorkBindUnMember=" + WorkBindUnMember + ", WorkDiscussMatter=" + WorkDiscussMatter
                + ", WorkDiscussPraise=" + WorkDiscussPraise + ", WorkDiscussTime=" + WorkDiscussTime + ", MemberName=" + MemberName + ", ToMemberName=" + ToMemberName + ", MemberPostition="
                + MemberPostition + ", MemberImg=" + MemberImg + ", SonDiscuss=" + SonDiscuss + "]";
    }

}
