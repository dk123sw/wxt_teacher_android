package cn.xiaocool.wxtteacher;

import android.content.Intent;

/**
 * Created by wzh on 2016/2/20.
 */
public interface ReceiverInterface {
    /**
     * @author zhuchongkun
     * @param actions
     * @exception Registratioin
     *                of radio
     *
     */
    void regiserRadio(String[] actions);

    /**
     * @author zhuchongkun
     * @exception Cancellatioin
     *                of radio
     */
    void destroyRadio();

    /**
     * @author zhuchongkun
     * @param intent
     * @exception To
     *                deal with radio
     */
    void dealWithRadio(Intent intent);

}
