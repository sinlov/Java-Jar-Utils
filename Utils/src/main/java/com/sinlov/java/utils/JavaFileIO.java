package com.sinlov.java.utils;

import java.io.File;

/**
 * Java file IO tools
 * Created by "sinlov" on 2015/11/28.
 */
public class JavaFileIO {

    /**
     * default safe delete inner file max size is 8MB
     */
    public static final long DELETE_INNER_FILE_MAX_SIZE = 8 * 1024 * 1024;

    public static String TAG = JavaFileIO.class.getName();

    private static final String INPUT_PATH_ERROR = "Your input path error ";

    /**
     * 验证字符串是否为正确路径名正则表达式
     * <p>通过 path.matches(matches) 方法的返回值判断是否正确
     * <p>path 为路径字符串
     */
    public static String matches = "[A-Za-z]:\\\\[^:?\"><*]*";

    private static File file = null;

    /**
     * create or check folder
     * <p>自动添加文件分隔符，创建目录或者判断path是否为路径，如果路径格式不对返回异常{@link #INPUT_PATH_ERROR}
     * <p>如果创建失败，则返回null
     *
     * @param path Path
     * @return String
     */
    public static String createOrCheckFolder(String path) {
        String temp = null;
        checkInputPath(path);
        if (!path.endsWith(File.separator)) {
            temp = path + File.separator;
        }
        File dirFile = new File(path);
        if (dirFile.exists()) {
            return temp;
        } else {
            return dirFile.mkdirs() ? temp : null;
        }
    }

    /**
     * 删除单个文件
     *
     * @param path Path
     * @return boolean true success or false failure
     */
    public static boolean deleteFile(String path) {
        checkInputPath(path);
        file = new File(path);
        return file.isFile() && file.exists() && file.delete();
    }

    /**
     * 自动判断是否为文件路径，并根据路径删除指定的目录或文件，无论存在与否
     *
     * @param path full Path
     * @return boolean true is or false not
     */
    public static boolean deleteFolder(String path) {
        checkInputPath(path);
        file = new File(path);
        if (!file.exists()) {
            return false;
        } else {
            if (file.isFile()) {
                return deleteFile(path);
            } else {
                return deleteDirectory(path);
            }
        }
    }

    /**
     * 删除目录下的文件
     * <br>如果path不以文件分隔符结尾，自动添加文件分隔符
     * <p>如果path对应的文件不存在，或者不是一个目录，则退出
     *
     * @param path full path
     * @return boolean 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectoryInnerFiles(String path) {
        checkInputPath(path);
        if (!path.endsWith(File.separator)) {
            path = path + File.separator;
        }
        File dirFile = new File(path);
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        try {
            File[] files = dirFile.listFiles();
            if (null != files) {
                for (File file : files) {
                    if (file.isFile()) {
                        if (deleteFile(file.getAbsolutePath())) break;
                    } else {
                        if (deleteDirectory(file.getAbsolutePath())) break;
                    }
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 删除目录下的文件 , 此方法一般用于缓存文件夹，且缓存文件夹不得大于缓冲内存 8MB 的大小
     * <br>如果path不以文件分隔符结尾，自动添加文件分隔符
     * <p>如果path对应的文件不存在，或者不是一个目录，则退出
     * <br><p>删除文件夹内的文件，最大文件夹大小为 8MB {@link #DELETE_INNER_FILE_MAX_SIZE}， 如果超出将抛出内存溢出异常。
     *
     * @param path full path
     * @return boolean 目录删除成功返回true，否则返回false
     */
    public static boolean safeDeleteDirectoryInner(String path) {
        checkInputPath(path);
        if (!path.endsWith(File.separator)) {
            path = path + File.separator;
        }
        File dirFile = new File(path);
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        if (dirFile.length() > DELETE_INNER_FILE_MAX_SIZE) {
            new OutOfMemoryError().printStackTrace();
        }
        File[] files = dirFile.listFiles();
        if (null != files) {
            for (File file : files) {
                if (file.isFile()) {
                    if (deleteFile(file.getAbsolutePath())) break;
                } else {
                    if (deleteDirectory(file.getAbsolutePath())) break;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    /**
     * 删除目录（文件夹）以及目录下的文件 ,如果sPath不以文件分隔符结尾，自动添加文件分隔符
     * <p>如果dir对应的文件不存在，或者不是一个目录，则退出
     *
     * @param path full path
     * @return boolean 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String path) {
        checkInputPath(path);
        File dirFile = new File(path);
        return deleteDirectoryInnerFiles(path) && dirFile.delete();
    }

    private static void checkInputPath(String path) {
        if (path.matches(matches)) {
            new Throwable(INPUT_PATH_ERROR).printStackTrace();
        }
    }


    private JavaFileIO() {
    }
}
