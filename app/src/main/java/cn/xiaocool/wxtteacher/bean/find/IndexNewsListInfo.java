package cn.xiaocool.wxtteacher.bean.find;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by mac on 16/1/31.
 */
public class IndexNewsListInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String newsId;
    private String newsTitle;
    private String NewsAuthor;
    private String NewsTag;
    private ArrayList<NewsImgInfo> newsImg;
    private String NewsTime;
    private String NewsNum;
    private ArrayList<NewsMatterInfo> newsMatter;
    private String NewsRadio;
    private String NewsRadioUrl;
    private String NewsRadioPath;
    private String NewsPicState;
    private ArrayList<NewsPicMatterInfo> newsPicMatter;
    private String NewsProject;
    private String newsPicMatterName;
    private ArrayList<NewsSlideImgInfo> newsSlideImg;
    private String newsClass;
    private String newsSilde;
    private String newsMatterState;
    private String newsSort;
    private String newsState;
    private String newsWaste;
    private String newsCityId;
    private String newsBindAdmin;
    private String newsPush;
    private String newsClick;
    private String newsAudit;
    private String NewImgUrl;

    /**
     * 判断多图文里面图片路径返回的长度
     *
     * @return
     */
    public int getNewsImgInfoBoolean() {
        if (getNewsImg().size() == 1) {
            return 1;
        } else if (getNewsImg().size() == 2) {
            return 2;
        } else if (getNewsImg().size() == 3) {
            return 3;
        } else if (getNewsImg().size() > 3) {
            return getNewsImg().size();
        } else {
            return 0;
        }
    }

    public String getNewsSilde() {
        return newsSilde;
    }

    public void setNewsSilde(String newsSilde) {
        this.newsSilde = newsSilde;
    }

    public String getNewImgUrl(){
        return NewImgUrl;
    }
    public void setNewImgUrl(String newImgUrl){
        this.NewImgUrl = newImgUrl;
    }

    public String getNewsMatterState() {
        return newsMatterState;
    }

    public void setNewsMatterState(String newsMatterState) {
        this.newsMatterState = newsMatterState;
    }

    public String getNewsSort() {
        return newsSort;
    }

    public void setNewsSort(String newsSort) {
        this.newsSort = newsSort;
    }

    public String getNewsState() {
        return newsState;
    }

    public void setNewsState(String newsState) {
        this.newsState = newsState;
    }

    public String getNewsWaste() {
        return newsWaste;
    }

    public void setNewsWaste(String newsWaste) {
        this.newsWaste = newsWaste;
    }

    public String getNewsCityId() {
        return newsCityId;
    }

    public void setNewsCityId(String newsCityId) {
        this.newsCityId = newsCityId;
    }

    public String getNewsBindAdmin() {
        return newsBindAdmin;
    }

    public void setNewsBindAdmin(String newsBindAdmin) {
        this.newsBindAdmin = newsBindAdmin;
    }

    public String getNewsPush() {
        return newsPush;
    }

    public void setNewsPush(String newsPush) {
        this.newsPush = newsPush;
    }

    public String getNewsClick() {
        return newsClick;
    }

    public void setNewsClick(String newsClick) {
        this.newsClick = newsClick;
    }

    public String getNewsAudit() {
        return newsAudit;
    }

    public void setNewsAudit(String newsAudit) {
        this.newsAudit = newsAudit;
    }

    public String getNewsClass() {
        return newsClass;
    }

    public void setNewsClass(String newsClass) {
        this.newsClass = newsClass;
    }

    public ArrayList<NewsSlideImgInfo> getNewsSlideImg() {
        if (newsSlideImg == null && !newsSlideImg.equals("null")) {
            newsSlideImg = new ArrayList<NewsSlideImgInfo>();
        }
        return newsSlideImg;
    }

    public void setNewsSlideImg(ArrayList<NewsSlideImgInfo> newsSlideImg) {
        this.newsSlideImg = newsSlideImg;
    }

    public String getNewsPicMatterName() {
        return newsPicMatterName;
    }

    public void setNewsPicMatterName(String newsPicMatterName) {
        this.newsPicMatterName = newsPicMatterName;
    }

    public String getNewsTag() {
        return NewsTag;
    }

    public void setNewsTag(String newsTag) {
        NewsTag = newsTag;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsAuthor() {
        return NewsAuthor;
    }

    public void setNewsAuthor(String newsAuthor) {
        NewsAuthor = newsAuthor;
    }

    public ArrayList<NewsImgInfo> getNewsImg() {
        if (newsImg == null && !newsImg.equals("null")) {
            newsImg = new ArrayList<NewsImgInfo>();
        }
        return newsImg;
    }

    public void setNewsImg(ArrayList<NewsImgInfo> newsImg) {
        this.newsImg = newsImg;
    }

    public String getNewsTime() {
        return NewsTime;
    }

    public void setNewsTime(String newsTime) {
        NewsTime = newsTime;
    }

    public String getNewsNum() {
        return NewsNum;
    }

    public void setNewsNum(String newsNum) {
        NewsNum = newsNum;
    }

    public ArrayList<NewsMatterInfo> getNewsMatter() {
        if (newsMatter == null && !newsMatter.equals("null")) {
            newsMatter = new ArrayList<NewsMatterInfo>();
        }
        return newsMatter;
    }

    public void setNewsMatter(ArrayList<NewsMatterInfo> newsMatter) {
        this.newsMatter = newsMatter;
    }

    public String getNewsRadio() {
        return NewsRadio;
    }

    public void setNewsRadio(String newsRadio) {
        NewsRadio = newsRadio;
    }

    public String getNewsRadioUrl() {
        return NewsRadioUrl;
    }

    public void setNewsRadioUrl(String newsRadioUrl) {
        NewsRadioUrl = newsRadioUrl;
    }

    public String getNewsRadioPath() {
        return NewsRadioPath;
    }

    public void setNewsRadioPath(String newsRadioPath) {
        NewsRadioPath = newsRadioPath;
    }

    public String getNewsPicState() {
        return NewsPicState;
    }

    public void setNewsPicState(String newsPicState) {
        NewsPicState = newsPicState;
    }

    public ArrayList<NewsPicMatterInfo> getNewsPicMatter() {
        if (newsPicMatter == null && !newsPicMatter.equals("null")) {
            newsPicMatter = new ArrayList<NewsPicMatterInfo>();
        }
        return newsPicMatter;
    }

    public void setNewsPicMatter(ArrayList<NewsPicMatterInfo> newsPicMatter) {
        this.newsPicMatter = newsPicMatter;
    }

    public String getNewsProject() {
        return NewsProject;
    }

    public void setNewsProject(String newsProject) {
        NewsProject = newsProject;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    @Override
    public String toString() {
        return "IndexNewsListInfo [newsId=" + newsId + ", newsTitle=" + newsTitle + ", NewsAuthor=" + NewsAuthor + ", NewsTag=" + NewsTag + ", newsImg=" + newsImg + ", NewsTime=" + NewsTime
                + ", NewsNum=" + NewsNum + ", newsMatter=" + newsMatter + ", NewsRadio=" + NewsRadio + ", NewsRadioUrl=" + NewsRadioUrl + ", NewsRadioPath=" + NewsRadioPath + ", NewsPicState="
                + NewsPicState + ", newsPicMatter=" + newsPicMatter + ", NewsProject=" + NewsProject + ", newsPicMatterName=" + newsPicMatterName + ", newsSlideImg=" + newsSlideImg + ", newsClass="
                + newsClass + ", newsSilde=" + newsSilde + ", newsMatterState=" + newsMatterState + ", newsSort=" + newsSort + ", newsState=" + newsState + ", newsWaste=" + newsWaste
                + ", newsCityId=" + newsCityId + ", newsBindAdmin=" + newsBindAdmin + ", newsPush=" + newsPush + ", newsClick=" + newsClick + ", newsAudit=" + newsAudit + "]";
    }

}