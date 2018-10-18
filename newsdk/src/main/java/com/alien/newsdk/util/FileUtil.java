package com.alien.newsdk.util;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Alien on 2017/11/29.
 */

public class FileUtil {

    public static boolean writeBitmapIntoFile(Bitmap bitmap, File file){
        if (file==null)
            return false;
        if (file.exists())
            file.delete();
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG,90,fos);
            fos.flush();
            fos.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

//    public static File writeResponseIntoFile(ResponseBody responseBody, File finalFile) {
//        if (responseBody == null)
//            return null;
//        try {
//            InputStream is = null;
//            OutputStream os = null;
//            try {
//                if (finalFile == null)
//                    return null;
//                else if (finalFile.exists())
//                    finalFile.delete();
//
//                byte[] fileReader = new byte[4096];
//                is = responseBody.byteStream();
//                os = new FileOutputStream(finalFile);
//
//
//                while (true) {
//                    int read = is.read(fileReader);
//                    if (read == -1) {
//                        break;
//                    }
//                    os.write(fileReader, 0, read);
//                }
//
//                os.flush();
//                return finalFile;
//            } catch (FileNotFoundException e) {
//                return null;
//            } finally {
//                if (is != null)
//                    is.close();
//                if (os != null)
//                    os.close();
//            }
//        } catch (IOException e) {
//            return null;
//        }
//    }


    public static void writeObjectIntoFile(Object object, File file) {
        if (file == null)
            return;
        if (file.exists())
            file.delete();

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static <T> T readObjectFromFile(File file) {
        T result = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            result = (T) ois.readObject();
            ois.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (fis != null)
                    fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
