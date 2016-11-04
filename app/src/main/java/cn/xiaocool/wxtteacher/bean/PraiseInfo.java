package cn.xiaocool.wxtteacher.bean;

import java.io.Serializable;

/**
 * Created by mac on 16/2/21.
 */
public class PraiseInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    private String MemberName;// 点赞姓名
    private String MemberId;// 点赞用户ID
    private String MemberImg;// 点赞头像

    public String getMemberName() {
        return MemberName;
    }

    public void setMemberName(String memberName) {
        MemberName = memberName;
    }

    public String getMemberId() {
        return MemberId;
    }

    public void setMemberId(String memberId) {
        MemberId = memberId;
    }

    public String getMemberImg() {
        return MemberImg;
    }

    public void setMemberImg(String memberImg) {
        MemberImg = memberImg;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    @Override
    public String toString() {
        return "WorkPraiseInfo [MemberName=" + MemberName + ", MemberId=" + MemberId + ", MemberImg=" + MemberImg + "]";
    }

}