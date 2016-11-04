package cn.xiaocool.wxtteacher.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

import cn.xiaocool.wxtteacher.R;

/**
 * Created by wzh on 2016/2/21.
 */
public class MyProgressDialog extends Dialog {
    private Context context;
    private static MyProgressDialog myProgressDialog;

    private MyProgressDialog(Context context) {
        super(context);
        this.context = context;
    }
    private MyProgressDialog(Context context, int theme) {
        super(context, theme);
        // TODO Auto-generated constructor stub
    }
    /**
     * 自定义方法，创建对话框
     * 方法名:createDialog
     * @param context
     * @return
     */
    public static MyProgressDialog createDialog(Context context ) {
        myProgressDialog = new MyProgressDialog(context, R.style.ProgressDialog);
//        myProgressDialog.setContentView(R.layout.item_for_dialog);
        myProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        return myProgressDialog;
    }
    /**
     * 获取焦点
     */
    public void onWindowFocusChanged(boolean hasFocus) {
        if (myProgressDialog==null) {
            return;
        }
        ImageView imageView = (ImageView) myProgressDialog.findViewById(R.id.dialogimage);
        AnimationDrawable animationDrawable =(AnimationDrawable) imageView.getBackground();
        animationDrawable.start();
    }
    /**
     * 提示内容
     * 方法名:setMessage
     * @param message
     * @return
     */
    public MyProgressDialog setMessage(String message) {
        TextView strMessage = (TextView) myProgressDialog.findViewById(R.id.dialogmessage);
        if (strMessage != null) {
            strMessage.setText(message);
        }
        return myProgressDialog;
    }
}