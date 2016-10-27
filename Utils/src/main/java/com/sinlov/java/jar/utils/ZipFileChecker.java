package com.sinlov.java.jar.utils;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * <pre>
 *     sinlov
 *
 *     /\__/\
 *    /`    '\
 *  ≈≈≈ 0  0 ≈≈≈ Hello world!
 *    \  --  /
 *   /        \
 *  /          \
 * |            |
 *  \  ||  ||  /
 *   \_oo__oo_/≡≡≡≡≡≡≡≡o
 *
 * </pre>
 * Created by sinlov on 16/10/27.
 */
public class ZipFileChecker {

    public static List<String> getZipFileList(String zipPath) {
        byte b[] = new byte[1024];
        int length;
        ZipFile zipFile;
        List<String> fileList = null;
        try {
            zipFile = new ZipFile(new File(zipPath));
            Enumeration enumeration = zipFile.entries();
            ZipEntry zipEntry;
            fileList = new ArrayList<>();
            while (enumeration.hasMoreElements()) {
                zipEntry = (ZipEntry) enumeration.nextElement();
                zipEntry.getName();
                fileList.add(zipEntry.getName());
//                System.out.println("Path: " + zipEntry.getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileList;
    }

    public static boolean checkJarIsRepeat(String zipPath) {
        List<String> fullJarList = getZipFileList(zipPath);
        Set<String> shotList = new HashSet<>();
        shotList.addAll(fullJarList);
        System.out.println("full jar size: " + fullJarList.size());
        System.out.println("shot jar size: " + shotList.size());
//        for (String fJar:
//             fullJarList) {
//            System.out.println("Path: " + fullJarList);
//        }
        return shotList.size() < fullJarList.size();
    }
}
