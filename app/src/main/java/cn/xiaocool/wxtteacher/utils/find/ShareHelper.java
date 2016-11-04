package cn.xiaocool.wxtteacher.utils.find;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.xiaocool.wxtteacher.bean.find.IndexNewsListInfo;
import cn.xiaocool.wxtteacher.bean.find.NewsImgInfo;
import cn.xiaocool.wxtteacher.dao.CommunalInterfaces;

/**
 * 【ShareActivity】解析缓存 数据的帮助类
 * @author Administrator
 *
 */
public class ShareHelper {
    private static ShareHelper jsonHelper;

    ShareHelper() {
    }

    public static ShareHelper getInstance() {
        if (jsonHelper == null) {
            jsonHelper = new ShareHelper();
        }
        return jsonHelper;
    }

    /**
     * 解析缓存中【相关新闻】的JSON
     * @param json
     */
    public ArrayList<IndexNewsListInfo> resolveJson(String json) {
        ArrayList<IndexNewsListInfo> list = new ArrayList<IndexNewsListInfo>();
        try {
            JSONObject obj=new JSONObject(json);
            String state = obj.getString("state");
            if (state.equals(CommunalInterfaces._STATE)) {
                list.clear();
                for (int i = 0; i < obj.length() - 1; i++) {
                    JSONObject object = obj.getJSONObject("" + i);
                    IndexNewsListInfo shareInfo = new IndexNewsListInfo();
                    shareInfo.setNewsAuthor(object.getString("NewsAuthor"));
                    shareInfo.setNewsBindAdmin(object
                            .getString("NewsBindAdmin"));
                    shareInfo.setNewsCityId(object.getString("NewsCityId"));
                    shareInfo.setNewsClass(object.getString("NewsClass"));
                    shareInfo.setNewsClick(object.getString("NewsClick"));
                    shareInfo.setNewsId(object.getString("NewsId"));
                    shareInfo.setNewsMatterState(object
                            .getString("NewsMatterState"));
                    shareInfo.setNewsNum(object.getString("NewsNum"));
                    shareInfo.setNewsPicState(object.getString("NewsPicState"));
                    shareInfo.setNewsProject(object.getString("NewsProject"));
                    shareInfo.setNewsPush(object.getString("NewsPush"));
                    shareInfo.setNewsRadio(object.getString("NewsRadio"));
                    shareInfo.setNewsRadioPath(object
                            .getString("NewsRadioPath"));
                    shareInfo.setNewsRadioUrl(object.getString("NewsRadioUrl"));
                    shareInfo.setNewsSilde(object.getString("NewsSilde"));
//					shareInfo.setNewsSlideImg(object.getString("NewsSlideImg"));
                    shareInfo.setNewsSort(object.getString("NewsSort"));
                    shareInfo.setNewsState(object.getString("NewsState"));
                    shareInfo.setNewsTag(object.getString("NewsTag"));
                    shareInfo.setNewsTime(object.getString("NewsTime"));
                    shareInfo.setNewsTitle(object.getString("NewsTitle"));
//					shareInfo.setNnewsWaste(object.getString("NewsWaste"));
                    String newsImg = object.getString("NewsImg");
                    ArrayList<NewsImgInfo> newsImgList = new ArrayList<NewsImgInfo>();
                    if (newsImg.equals("") || newsImg.equals("null")) {
                        NewsImgInfo newsImgInfo = new NewsImgInfo();
                        newsImgInfo.setPath("");
                        newsImgInfo.setContent("");
                        newsImgList.add(newsImgInfo);
                    } else {
                        JSONArray newsImgArray = new JSONArray(newsImg);
                        for (int j = 0; j < newsImgArray.length(); j++) {
                            JSONObject imgJson = newsImgArray.getJSONObject(j);
                            NewsImgInfo newsImgInfo = new NewsImgInfo();
                            newsImgInfo.setPath(imgJson.getString("path"));
                            newsImgInfo
                                    .setContent(imgJson.getString("content"));
                            newsImgList.add(newsImgInfo);
                        }
                    }
                    shareInfo.setNewsImg(newsImgList);
                    list.add(shareInfo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
