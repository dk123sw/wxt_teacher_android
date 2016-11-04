package cn.xiaocool.wxtteacher.bean.find;

import java.io.Serializable;

/**
 * Created by wzh on 2016/2/20.
 */
public class LevelOneCommentInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**返回一级评论ID*/
    private String newsWorkDiscussId;
    /**返回资讯用户ID*/
    private String newsBindMemberId;
    /**返回一级评论内容*/
    private String newsDiscussMatter;
    /**返回一级评论用户头像地址*/
    private String memberImg;
    /**返回一级评论用户姓名*/
    private String memberName;
    /**返回资讯ID*/
    private String newsId;

    public String getNewsWorkDiscussId() {
        return newsWorkDiscussId;
    }

    public void setNewsWorkDiscussId(String newsWorkDiscussId) {
        this.newsWorkDiscussId = newsWorkDiscussId;
    }

    public String getNewsBindMemberId() {
        return newsBindMemberId;
    }

    public void setNewsBindMemberId(String newsBindMemberId) {
        this.newsBindMemberId = newsBindMemberId;
    }

    public String getNewsDiscussMatter() {
        return newsDiscussMatter;
    }

    public void setNewsDiscussMatter(String newsDiscussMatter) {
        this.newsDiscussMatter = newsDiscussMatter;
    }

    public String getMemberImg() {
        return memberImg;
    }

    public void setMemberImg(String memberImg) {
        this.memberImg = memberImg;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    @Override
    public String toString() {
        return "LevelOneCommentInfo [newsWorkDiscussId=" + newsWorkDiscussId
                + ", newsBindMemberId=" + newsBindMemberId
                + ", newsDiscussMatter=" + newsDiscussMatter + ", memberImg="
                + memberImg + ", memberName=" + memberName + ", newsId="
                + newsId + "]";
    }

}

