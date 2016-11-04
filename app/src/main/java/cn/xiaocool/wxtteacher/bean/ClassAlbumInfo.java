package cn.xiaocool.wxtteacher.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/5/13.
 */
public class ClassAlbumInfo implements Serializable{

    private ArrayList<String> contents_imgs;//相册图片地址
    private String contents_strings;//发布的内容
    private UserInfo release_user;//发布人
    private String release_time;//发布时间
    private int num_like;//点赞数
    private int num_content;//评论数


    public ArrayList<String> getContents_imgs() {
        return contents_imgs;
    }

    public void setContents_imgs(ArrayList<String> contents_imgs) {
        this.contents_imgs = contents_imgs;
    }

    public String getContents_strings() {
        return contents_strings;
    }

    public void setContents_strings(String contents_strings) {
        this.contents_strings = contents_strings;
    }

    public UserInfo getRelease_user() {
        return release_user;
    }

    public void setRelease_user(UserInfo release_user) {
        this.release_user = release_user;
    }

    public String getRelease_time() {
        return release_time;
    }

    public void setRelease_time(String release_time) {
        this.release_time = release_time;
    }

    public int getNum_like() {
        return num_like;
    }

    public void setNum_like(int num_like) {
        this.num_like = num_like;
    }

    public int getNum_content() {
        return num_content;
    }

    public void setNum_content(int num_content) {
        this.num_content = num_content;
    }
}


