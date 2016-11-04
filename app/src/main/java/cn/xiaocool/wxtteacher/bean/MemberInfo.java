package cn.xiaocool.wxtteacher.bean;

import java.io.Serializable;

import cn.xiaocool.wxtteacher.dease.InviteMessage.InviteMesageStatus;


/**
 * Created by mac on 16/2/21.
 */
public class MemberInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String memberId;// 用户ID
    private String memberName;// 姓名
    private String memberContactName;//手机通讯录姓名
    private String memberCompany;// 公司
    private String memberPosition;// 职务
    private String memberImg;// 头像
    private String memberEase;// 环信ID
    private String memberGender;// 性别
    private String memberPhone;// 手机号
    private String memberCityId;
    private String memberCity;
    private String memberEmail;
    private String memberReason;
    private String applyId;
    private String applyState;
    private String isFriend;
    private String isKa;
    private Long memberTime;
    private InviteMesageStatus memberStatue;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberContactName() {
        return memberContactName;
    }

    public void setMemberContactName(String memberContactName) {
        this.memberContactName = memberContactName;
    }

    public String getMemberCompany() {
        return memberCompany;
    }

    public void setMemberCompany(String memberCompany) {
        this.memberCompany = memberCompany;
    }

    public String getMemberPosition() {
        return memberPosition;
    }

    public void setMemberPosition(String memberPosition) {
        this.memberPosition = memberPosition;
    }

    public String getMemberImg() {
        return memberImg;
    }

    public void setMemberImg(String memberImg) {
        this.memberImg = memberImg;
    }

    public String getMemberEase() {
        return memberEase;
    }

    public void setMemberEase(String memberEase) {
        this.memberEase = memberEase;
    }

    public String getMemberGender() {
        return memberGender;
    }

    public void setMemberGender(String memberGender) {
        this.memberGender = memberGender;
    }

    public String getMemberPhone() {
        return memberPhone;
    }

    public void setMemberPhone(String memberPhone) {
        this.memberPhone = memberPhone;
    }

    public String getMemberCityId() {
        return memberCityId;
    }

    public void setMemberCityId(String memberCityId) {
        this.memberCityId = memberCityId;
    }

    public String getMemberCity() {
        return memberCity;
    }

    public void setMemberCity(String memberCity) {
        this.memberCity = memberCity;
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public void setMemberEmail(String memberEmail) {
        this.memberEmail = memberEmail;
    }

    public String getMemberReason() {
        return memberReason;
    }

    public void setMemberReason(String memberReason) {
        this.memberReason = memberReason;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getApplyState() {
        return applyState;
    }

    public void setApplyState(String applyState) {
        this.applyState = applyState;
    }

    public String getIsFriend() {
        return isFriend;
    }

    public void setIsFriend(String isFriend) {
        this.isFriend = isFriend;
    }

    public String getIsKa() {
        return isKa;
    }

    public void setIsKa(String isKa) {
        this.isKa = isKa;
    }

    public Long getMemberTime() {
        return memberTime;
    }

    public void setMemberTime(Long memberTime) {
        this.memberTime = memberTime;
    }

    public InviteMesageStatus getMemberStatue() {
        return memberStatue;
    }

    public void setMemberStatue(InviteMesageStatus memberStatue) {
        this.memberStatue = memberStatue;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    @Override
    public String toString() {
        return "MemberInfo [memberId=" + memberId + ", memberName=" + memberName + ", memberContactName=" + memberContactName + ", memberCompany=" + memberCompany + ", memberPosition="
                + memberPosition + ", memberImg=" + memberImg + ", memberEase=" + memberEase + ", memberGender=" + memberGender + ", memberPhone=" + memberPhone + ", memberCityId=" + memberCityId
                + ", memberCity=" + memberCity + ", memberEmail=" + memberEmail + ", memberReason=" + memberReason + ", applyId=" + applyId + ", applyState=" + applyState + ", isFriend=" + isFriend
                + ", isKa=" + isKa + ", memberTime=" + memberTime + ", memberStatue=" + memberStatue + "]";
    }

}
