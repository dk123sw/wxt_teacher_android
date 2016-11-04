package cn.xiaocool.wxtteacher.utils;

/**
 * Created by wzh on 2016/2/20.
 */

import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

import cn.xiaocool.wxtteacher.bean.find.IndexNewsListInfo;
import cn.xiaocool.wxtteacher.bean.find.LevelOneCommentInfo;

/**
 *  * 集合转换String   String 转换集合工具类
 * 单例模式
 * Created by mac on 16/1/31.
 */
public class SaveSPUtils {
    private static SaveSPUtils saveSP;

    private SaveSPUtils() {

    }

    public static SaveSPUtils getInstance() {
        if (saveSP == null) {
            saveSP = new SaveSPUtils();
        }
        return saveSP;
    }

    /**
     * 将一个集合 数据转化成一个字符串
     *
     * @param indexNewsList
     * @return
     * @throws java.io.IOException
     */
    public static String SceneList2String(
            ArrayList<IndexNewsListInfo> indexNewsList) throws IOException {
        // 实例化一个ByteArrayOutputStream对象，用来装载压缩后的字节文件。
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        // 然后将得到的字符数据装载到ObjectOutputStream
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                byteArrayOutputStream);
        // writeObject 方法负责写入特定类的对象的状态，以便相应的 readObject 方法可以还原它
        objectOutputStream.writeObject(indexNewsList);
        // 最后，用Base64.encode将字节文件转换成Base64编码保存在String中
        String SceneListString = new String(Base64.encode(
                byteArrayOutputStream.toByteArray(), Base64.DEFAULT));
        // 关闭objectOutputStream
        objectOutputStream.close();
        return SceneListString;
    }

    /**
     * 将一个字符类型转换成集合
     *
     * @param SceneListString
     * @return
     * @throws java.io.StreamCorruptedException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @SuppressWarnings("unchecked")
    public static ArrayList<IndexNewsListInfo> String2SceneList(
            String SceneListString) throws StreamCorruptedException,
            IOException, ClassNotFoundException {
        byte[] mobileBytes = Base64.decode(SceneListString.getBytes(),
                Base64.DEFAULT);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                mobileBytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(
                byteArrayInputStream);
        ArrayList<IndexNewsListInfo> SceneList = (ArrayList<IndexNewsListInfo>) objectInputStream
                .readObject();
        objectInputStream.close();
        return SceneList;
    }

    /**
     * 相关评论的集合（转化成字符串 缓存前准备使用）
     *
     * @param levelOneList
     * @return
     * @throws IOException
     */
    public static String LevelOneCommenListString(
            ArrayList<LevelOneCommentInfo> levelOneList) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                byteArrayOutputStream);
        objectOutputStream.writeObject(levelOneList);
        String SceneListString = new String(Base64.encode(
                byteArrayOutputStream.toByteArray(), Base64.DEFAULT));
        objectOutputStream.close();
        return SceneListString;
    }

    /**
     * 相关评论（把一个字符串 转换成集合 读取缓存时候使用）
     * @param SceneListString
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @SuppressWarnings("unchecked")
    public static ArrayList<LevelOneCommentInfo> LevelOneCommenStringList(
            String SceneListString) throws IOException, ClassNotFoundException {
        byte[] mobileBytes = Base64.decode(SceneListString.getBytes(),
                Base64.DEFAULT);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                mobileBytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(
                byteArrayInputStream);
        ArrayList<LevelOneCommentInfo> SceneList = (ArrayList<LevelOneCommentInfo>) objectInputStream
                .readObject();
        objectInputStream.close();
        return SceneList;
    }

}
