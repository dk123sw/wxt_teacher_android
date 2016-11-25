package cn.xiaocool.wxtteacher.camera;

import android.os.Parcel;
import android.os.Parcelable;

import com.videogo.openapi.bean.EZCameraInfo;

import cn.xiaocool.wxtteacher.camera.lc_camera.ChannelInfo;


/**
 * 此实体类封装了一个萤石视频的EZCameraInfo和一个乐橙视频的channelInfo
 *
 *  萤石云数据结构为EzDeviceInfo包含多个EzCameraInfo
 *
 * */

public class MyCameraInfo implements Parcelable {
    public static final int EZ_CAMERA = 1;
    public static final int LC_CAMERA = 2;

    private int cameraType;
    private EZCameraInfo ezCameraInfo;
    private MyEzDeviceInfo myEzDeviceInfo;
    private ChannelInfo channelInfo;

    public EZCameraInfo getEzCameraInfo() {
        return ezCameraInfo;
    }

    public void setEzCameraInfo(EZCameraInfo ezCameraInfo) {
        this.ezCameraInfo = ezCameraInfo;
    }

    public int getCameraType() {
        return cameraType;
    }

    public void setCameraType(int cameraType) {
        this.cameraType = cameraType;
    }

    public MyEzDeviceInfo getMyEzDeviceInfo() {
        return myEzDeviceInfo;
    }

    public void setMyEzDeviceInfo(MyEzDeviceInfo myEzDeviceInfo) {
        this.myEzDeviceInfo = myEzDeviceInfo;
    }


    public ChannelInfo getChannelInfo() {
        return channelInfo;
    }

    public void setChannelInfo(ChannelInfo channelInfo) {
        this.channelInfo = channelInfo;
    }

    /**
     * 对应萤石云的EzDeviceInfo，但只是显示基本信息
     * 以后可删除
     */
    public static class MyEzDeviceInfo implements Parcelable {
        private boolean isSupportZoom;
        private boolean isSupportPTZ;
        private int status;//1 在线 2 不在线

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public boolean isSupportZoom() {
            return isSupportZoom;
        }

        public void setSupportZoom(boolean supportZoom) {
            isSupportZoom = supportZoom;
        }

        public boolean isSupportPTZ() {
            return isSupportPTZ;
        }

        public void setSupportPTZ(boolean supportPTZ) {
            isSupportPTZ = supportPTZ;
        }



        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeByte(this.isSupportZoom ? (byte) 1 : (byte) 0);
            dest.writeByte(this.isSupportPTZ ? (byte) 1 : (byte) 0);
            dest.writeInt(this.status);
        }

        public MyEzDeviceInfo() {
        }

        protected MyEzDeviceInfo(Parcel in) {
            this.isSupportZoom = in.readByte() != 0;
            this.isSupportPTZ = in.readByte() != 0;
            this.status = in.readInt();
        }

        public static final Creator<MyEzDeviceInfo> CREATOR = new Creator<MyEzDeviceInfo>() {
            @Override
            public MyEzDeviceInfo createFromParcel(Parcel source) {
                return new MyEzDeviceInfo(source);
            }

            @Override
            public MyEzDeviceInfo[] newArray(int size) {
                return new MyEzDeviceInfo[size];
            }
        };
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.cameraType);
        dest.writeParcelable(this.ezCameraInfo, flags);
        dest.writeParcelable(this.myEzDeviceInfo, flags);
    }

    public MyCameraInfo() {
    }

    protected MyCameraInfo(Parcel in) {
        this.cameraType = in.readInt();
        this.ezCameraInfo = in.readParcelable(EZCameraInfo.class.getClassLoader());
        this.myEzDeviceInfo = in.readParcelable(MyEzDeviceInfo.class.getClassLoader());
    }

    public static final Creator<MyCameraInfo> CREATOR = new Creator<MyCameraInfo>() {
        @Override
        public MyCameraInfo createFromParcel(Parcel source) {
            return new MyCameraInfo(source);
        }

        @Override
        public MyCameraInfo[] newArray(int size) {
            return new MyCameraInfo[size];
        }
    };
}
