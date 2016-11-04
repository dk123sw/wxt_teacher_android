package cn.xiaocool.wxtteacher.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/6/17.
 */
public class TeacherInfo implements Serializable {

//    "id": "16",
//            "schoolid": "1",
//            "post_title": "舞蹈班",
//            "post_excerpt": "校园招聘   薛凌燕的摘要",
//            "post_keywords": "宝宝秀场   薛凌燕",
//            "post_date": "2000-01-01 00:00:00",
//            "smeta": "{\"thumb\":\"57633c0a1f947.jpg\"}",
//            "thumb": "/data/upload/57633c0a1f947.jpg"
    private String id;
    private String schoolid;
    private String post_title;
    private String post_excerpt;
    private String post_keywords;
    private String post_date;
    private String thumb;
    private String object_id;
    private String term_id;
    private String term_name;
    private String post_like;
    private String post_hits;
    private String recommended;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSchoolid() {
        return schoolid;
    }

    public void setSchoolid(String schoolid) {
        this.schoolid = schoolid;
    }

    public String getPost_title() {
        return post_title;
    }

    public void setPost_title(String post_title) {
        this.post_title = post_title;
    }

    public String getPost_keywords() {
        return post_keywords;
    }

    public void setPost_keywords(String post_keywords) {
        this.post_keywords = post_keywords;
    }

    public String getPost_date() {
        return post_date;
    }

    public void setPost_date(String post_date) {
        this.post_date = post_date;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getPost_excerpt() {
        return post_excerpt;
    }

    public void setPost_excerpt(String post_excerpt) {
        this.post_excerpt = post_excerpt;
    }

    public String getObject_id() {
        return object_id;
    }

    public void setObject_id(String object_id) {
        this.object_id = object_id;
    }

    public String getTerm_id() {
        return term_id;
    }

    public void setTerm_id(String term_id) {
        this.term_id = term_id;
    }

    public String getTerm_name() {
        return term_name;
    }

    public void setTerm_name(String term_name) {
        this.term_name = term_name;
    }

    public String getPost_like() {
        return post_like;
    }

    public void setPost_like(String post_like) {
        this.post_like = post_like;
    }

    public String getPost_hits() {
        return post_hits;
    }

    public void setPost_hits(String post_hits) {
        this.post_hits = post_hits;
    }

    public String getRecommended() {
        return recommended;
    }

    public void setRecommended(String recommended) {
        this.recommended = recommended;
    }
}
