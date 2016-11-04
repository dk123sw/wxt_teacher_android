package cn.xiaocool.wxtteacher.utils;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.io.File;

import cn.xiaocool.wxtteacher.head.picture.Util;

/**
 * Created by wzh on 2016/2/20.
 */
@SuppressLint("SdCardPath")
public class HeadPicture {
    private String filepath = "/sdcard/myheader";
    private String filepathimg = "";
    private String picname = "newpic";

    public void getHeadPicture(ImageView head) {
        filepathimg = filepath + "/" + picname + ".jpg";
        File f = new File(filepathimg);
        if (f.exists()) {
            Bitmap bm = BitmapFactory.decodeFile(filepathimg);
            head.setImageBitmap(Util.toRoundBitmap(bm));
        }
    }
}
