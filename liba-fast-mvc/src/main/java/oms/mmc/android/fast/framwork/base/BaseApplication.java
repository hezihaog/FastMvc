package oms.mmc.android.fast.framwork.base;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 应用程序基类
 */
@SuppressLint("Registered")
public class BaseApplication extends Application {
    private static Context _context;
    private static Resources _resource;

    public static BaseApplication context() {
        return (BaseApplication) _context;
    }

    public static Resources resources() {
        return _resource;
    }

    public static String string(int id) {
        return _resource.getString(id);
    }

    public static String string(int id, Object... args) {
        return _resource.getString(id, args);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        _context = getApplicationContext();
        _resource = _context.getResources();
    }

    public static Context getContext() {
        return _context;
    }

    public static Resources getResource() {
        return _resource;
    }

    /**
     * 保存对象
     */
    public synchronized boolean saveObject(Serializable ser, String file) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = openFileOutput(file, MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(ser);
            oos.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
            } catch (Exception e) {
            }
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception e) {
            }
        }
    }

    /**
     * 判断缓存是否存在
     */
    public boolean isExistDataCache(String cachefile) {
        boolean exist = false;
        File data = getFileStreamPath(cachefile);
        if (data.exists()) {
            exist = true;
        }
        return exist;
    }

    /**
     * 读取对象
     */
    public synchronized Serializable readObject(String file) {
        if (!isExistDataCache(file))
            return null;
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = openFileInput(file);
            ois = new ObjectInputStream(fis);
            return (Serializable) ois.readObject();
        } catch (FileNotFoundException e) {
        } catch (Exception e) {
            e.printStackTrace();
            // 反序列化失败 - 删除缓存文件
            if (e instanceof InvalidClassException) {
                File data = getFileStreamPath(file);
                boolean isOk = data.delete();
                return isOk == true ? null : null;
            }
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
            } catch (Exception e) {
            }
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (Exception e) {
            }
        }
        return null;
    }
}
