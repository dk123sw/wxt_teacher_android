package cn.xiaocool.wxtteacher.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 潘 on 2016/4/4.
 */
public class Aaa implements Serializable{

    /**
     * status : success
     * data : [{"recipe_date":"1","recipe_pic":"yu.jpg","recipe_title":"鱼香肉丝","recipe_info":"鱼香肉丝是一道常见川菜。作为地道的成都人，大丸丸很爱鱼香一类的菜品。虽说是鱼香味，但并不以鱼烹调，而是以各种调味品烹调而成。"},{"recipe_date":"2","recipe_pic":"yu.jpg","recipe_title":"鱼香肉丝","recipe_info":"鱼香肉丝是一道常见川菜。作为地道的成都人，大丸丸很爱鱼香一类的菜品。虽说是鱼香味，但并不以鱼烹调，而是以各种调味品烹调而成。"},{"recipe_date":"2","recipe_pic":"qie.jpg","recipe_title":"风味茄子","recipe_info":"风味茄子的做法比较有特色一点，做好的茄子很软很香，调料放得均匀了味道很鲜美，是一道别具风味的菜肴。"},{"recipe_date":"3","recipe_pic":"yu.jpg","recipe_title":"鱼香肉丝","recipe_info":"鱼香肉丝是一道常见川菜。作为地道的成都人，大丸丸很爱鱼香一类的菜品。虽说是鱼香味，但并不以鱼烹调，而是以各种调味品烹调而成。"},{"recipe_date":"4","recipe_pic":"yu.jpg","recipe_title":"鱼香肉丝","recipe_info":"鱼香肉丝是一道常见川菜。作为地道的成都人，大丸丸很爱鱼香一类的菜品。虽说是鱼香味，但并不以鱼烹调，而是以各种调味品烹调而成。"},{"recipe_date":"5","recipe_pic":"yu.jpg","recipe_title":"鱼香肉丝","recipe_info":"鱼香肉丝是一道常见川菜。作为地道的成都人，大丸丸很爱鱼香一类的菜品。虽说是鱼香味，但并不以鱼烹调，而是以各种调味品烹调而成。"}]
     */

    private String status;
    /**
     * recipe_date : 1
     * recipe_pic : yu.jpg
     * recipe_title : 鱼香肉丝
     * recipe_info : 鱼香肉丝是一道常见川菜。作为地道的成都人，大丸丸很爱鱼香一类的菜品。虽说是鱼香味，但并不以鱼烹调，而是以各种调味品烹调而成。
     */

    private List<DataBean> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String recipe_date;
        private String recipe_title;
        private String recipe_info;

        public String getRecipe_date() {
            return recipe_date;
        }

        public void setRecipe_date(String recipe_date) {
            this.recipe_date = recipe_date;
        }

        public String getRecipe_title() {
            return recipe_title;
        }

        public void setRecipe_title(String recipe_title) {
            this.recipe_title = recipe_title;
        }

        public String getRecipe_info() {
            return recipe_info;
        }

        public void setRecipe_info(String recipe_info) {
            this.recipe_info = recipe_info;
        }
    }
}
