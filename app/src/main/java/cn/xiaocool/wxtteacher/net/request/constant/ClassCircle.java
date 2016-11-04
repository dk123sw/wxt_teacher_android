package cn.xiaocool.wxtteacher.net.request.constant;

/**
 * Created by mac on 16/2/18.
 */
public interface ClassCircle extends NetBaseConstant {
    /**
     * 获取首页幻灯片[分类]列表 /News/GetIndexSlideNewsList
     */
    public static final String NET_GET_INDEXSLIDENEWSLIST = NET_API_HOST + "a=Viwepager";
    /**
     * 获取动态圈
     */
    public static final String NET_GET_CIRCLELIST = NET_API_HOST + "a=GetMicroblog";
    /**
     * 点赞
     */
    public static final String NET_SET_PRAISE = NET_API_HOST2 + "a=SetLike";
    /**
     * 取消赞
     */
    public static final String NET_DEL_PRAISE = NET_API_HOST2 + "a=ResetLike";
}

