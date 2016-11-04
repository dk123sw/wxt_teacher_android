package cn.xiaocool.wxtteacher.bean;

import java.io.Serializable;
import java.util.ArrayList;

import cn.xiaocool.wxtteacher.bean.find.DiscussInfo;
import cn.xiaocool.wxtteacher.bean.find.IndexNewsListInfo;


/**
 * Created by mac on 16/2/21.
 */
public class ClassCricleInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String state;
    private String Id;// 工作圈ID
    private String NewId;// 咨询ID
    private ArrayList<String> Imgs;// 工作圈图片路径 此为二位数组
    private IndexNewsListInfo News;// 咨询内容
    private String Matter;// 工作圈内容
    private String Radio;// 工作圈视频地址(暂留)
    private String Addtime;// 发表时间
    private String BindMemberId;// 用户ID
    private String MemberName;// 用户姓名
    private String MemberCompany;// 学校
    private String MemberPostition;// 班级
    private String MemberImg;// 用户头像
    private String MemberIsKa;//0不是，1是
    private ArrayList<TagInfo> Tag;
    private ArrayList<TagInfo> SkillTAg;
    private ArrayList<LikeBean> Praise;// 点赞人
    private String PraiseNum;// 点赞次数
    private ArrayList<DiscussInfo> Discuss;// 评论
    private String DiscussNum;// 评论数
    private ArrayList<Comments> comment;// 评论


    public ArrayList<Comments> getComment() {
        if (comment == null || comment.equals("null")) {
            comment = new ArrayList<Comments>();
        }
        return comment;
    }

    public void setComment(ArrayList<Comments> comment) {

        this.comment = comment;
    }
    public String getState() {
        if (state == null) {
            state = "-1";
        }
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getId() {
        return Id;
    }

    public void setId(String workId) {
        Id = workId;
    }

    public String getMatter() {
        return Matter;
    }

    public void setMatter(String workMatter) {
        Matter = workMatter;
    }

    public String getRadio() {
        return Radio;
    }

    public void setRadio(String workRadio) {
        Radio = workRadio;
    }

    public String getAddtime() {
        return Addtime;
    }

    public void setAddtime(String workAddtime) {
        Addtime = workAddtime;
    }

    public String getBindMemberId() {
        if(BindMemberId ==null){
            BindMemberId = "0";
        }
        return BindMemberId;
    }

    public void setBindMemberId(String workBindMemberId) {
        BindMemberId = workBindMemberId;
    }

    public String getMemberName() {
        if (MemberName==null&&MemberName==""){

            return "";
        }
        return MemberName;
    }

    public void setMemberName(String memberName) {

        MemberName = memberName;
    }

    public String getMemberCompany() {
        return MemberCompany;
    }

    public void setMemberCompany(String memberCompany) {
        MemberCompany = memberCompany;
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

    public String getMemberIsKa() {
        if (MemberIsKa==null) {
            MemberIsKa="0";
        }else if (MemberIsKa.equals("null")) {
            MemberIsKa="0";
        }
        return MemberIsKa;
    }

    public void setMemberIsKa(String memberIsKa) {
        MemberIsKa = memberIsKa;
    }

    public ArrayList<TagInfo> getTag() {
        return Tag;
    }

    public void setTag(ArrayList<TagInfo> tag) {
        Tag = tag;
    }

    public ArrayList<TagInfo> getSkillTAg() {
        return SkillTAg;
    }

    public void setSkillTAg(ArrayList<TagInfo> skillTAg) {
        SkillTAg = skillTAg;
    }

    public String getWorkPraiseNum() {
        if(PraiseNum ==null){
            PraiseNum = "0";
        }
        return PraiseNum;
    }

    public void setWorkPraiseNum(String workPraiseNum) {
        PraiseNum = workPraiseNum;
    }

    public String getWorkDiscussNum() {
        if (DiscussNum == null) {
            DiscussNum = "0";
        }
        return DiscussNum;
    }

    public void setWorkDiscussNum(String workDiscussNum) {
        DiscussNum = workDiscussNum;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public ArrayList<LikeBean> getWorkPraise() {
        if (Praise == null || Praise.equals("null")) {
            Praise = new ArrayList<LikeBean>();
        }
        return Praise;
    }

    public void setWorkPraise(ArrayList<LikeBean> workPraise) {
        Praise = workPraise;
    }

    public ArrayList<DiscussInfo> getWorkDiscuss() {
        if (Discuss == null || Discuss.equals("null")) {
            Discuss = new ArrayList<DiscussInfo>();
        }
        return Discuss;
    }

    public void setWorkDiscuss(ArrayList<DiscussInfo> workDiscuss) {
        Discuss = workDiscuss;
    }

    public String getNewId() {
        if (NewId == null) {
            NewId = "0";
        } else if (NewId.equals("")) {
            NewId = "0";
        } else if (NewId.equals("null")) {
            NewId = "0";
        }
        return NewId;
    }

    public void setNewId(String newId) {
        NewId = newId;
    }

    public ArrayList<String> getWorkImgs() {
        if (Imgs == null) {
            Imgs = new ArrayList<String>();
        }
        return Imgs;
    }

    public void setWorkImgs(ArrayList<String> workImgs) {
        Imgs = workImgs;
    }

    public IndexNewsListInfo getNews() {
        return News;
    }

    public void setNews(IndexNewsListInfo news) {
        News = news;
    }

    @Override
    public String toString() {
        return "WorkRingInfo [state=" + state + ", WorkId=" + Id + ", NewId=" + NewId + ", WorkImgs=" + Imgs + ", News=" + News + ", WorkMatter=" + Matter + ", WorkRadio=" + Radio
                + ", WorkAddtime=" + Addtime + ", WorkBindMemberId=" + BindMemberId + ", MemberName=" + MemberName + ", MemberCompany=" + MemberCompany + ", MemberPostition="
                + MemberPostition + ", MemberImg=" + MemberImg + ", Tag=" + Tag + ", SkillTAg=" + SkillTAg + ", WorkPraise=" + Praise + ", WorkPraiseNum=" + PraiseNum + ", WorkDiscuss="
                + Discuss + ", WorkDiscussNum=" + DiscussNum + "]";
    }

}
