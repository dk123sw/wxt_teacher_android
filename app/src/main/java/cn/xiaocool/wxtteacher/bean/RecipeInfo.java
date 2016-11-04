package cn.xiaocool.wxtteacher.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by THB on 2016/6/15.
 */
public class RecipeInfo implements Serializable {
    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public ArrayList<RecipeData> getData() {
        return data;
    }

    public void setData(ArrayList<RecipeData> data) {
        this.data = data;
    }

    private String week;
    private ArrayList<RecipeData> data = new ArrayList<>();
    public static class RecipeData implements Serializable{
        private String title;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        private String content;
        private String photo;
    }
}
