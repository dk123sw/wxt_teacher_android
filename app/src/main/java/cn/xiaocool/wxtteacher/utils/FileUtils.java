package cn.xiaocool.wxtteacher.utils;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {

	public static String SDPATH = Environment.getExternalStorageDirectory() + "/formats/";
	
	public static void saveBitmap(Bitmap bm, String picName) {
		LogUtils.e("", "����ͼƬ");
		try {
			if (!isFileExist("")) {
				File tempf = createSDDir("");
			}
			File f = new File(SDPATH, picName + ".jpg");
			if (f.exists()) {
				f.delete();
			}
			FileOutputStream out = null;
			try {
				f.createNewFile();
				out = new FileOutputStream(f);
			} catch (Exception e) {
				e.printStackTrace();
			}
			bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
			try {
				out.flush();
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			LogUtils.e("", "�Ѿ�����");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static File createSDDir(String dirName) throws IOException {
		File dir = new File(SDPATH + dirName);
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

			System.out.println("createSDDir:" + dir.getAbsolutePath());
			System.out.println("createSDDir:" + dir.mkdir());
		}
		return dir;
	}

	public static boolean isFileExist(String fileName) {
		File file = new File(SDPATH + fileName);
		file.isFile();
		return file.exists();
	}

	public static void delFile(String fileName) {
		File file = new File(SDPATH + fileName);
		if (file.isFile()) {
			file.delete();
		}
		file.exists();
	}

	public static void deleteDir() {
		File dir = new File(SDPATH);
		if (dir == null || !dir.exists() || !dir.isDirectory())
			return;

		for (File file : dir.listFiles()) {
			if (file.isFile())
				file.delete(); // ɾ�������ļ�
			else if (file.isDirectory())
				deleteDir(); // �ݹ�ķ�ʽɾ���ļ���
		}
		dir.delete();// ɾ��Ŀ¼����
	}

	public static boolean fileIsExists(String path) {
		try {
			File f = new File(path);
			if (!f.exists()) {
				return false;
			}
		} catch (Exception e) {

			return false;
		}
		return true;
	}

}