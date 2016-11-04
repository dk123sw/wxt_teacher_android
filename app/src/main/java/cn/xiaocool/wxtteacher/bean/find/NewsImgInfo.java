package cn.xiaocool.wxtteacher.bean.find;

import java.io.Serializable;

/**
 * Created by wzh on 2016/2/20.
 */
public class NewsImgInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String path;
    private String content;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    @Override
    public String toString() {
        return "NewsImgInfo [path=" + path + ", content=" + content + "]";
    }


}

