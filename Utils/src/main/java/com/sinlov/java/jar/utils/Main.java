package com.sinlov.java.jar.utils;

import com.sinlov.java.utils.CmdUtils;
import com.sinlov.java.utils.ZipUtils;

import java.io.File;
import java.io.IOException;

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
 * Created by sinlov on 16/10/26.
 */
public class Main {

    public static String LOCAL_HOST = System.getProperty("user.home");
    public static String LOCAL_HOST_BASE = LOCAL_HOST + "/.sinlov-java-jar-utils";
    public static String LOCAL_HOST_BASE_CATCH = LOCAL_HOST_BASE + "/catch/";
    public static boolean isSaveCatch = true;
    private static final String HELP_INFO = "You muse use like\n" +
            "\tjava -jar Utils-release-0.0.1-1.jar inJarPath outJarPath\n" +
            "and also check jar file is Repeat\n" +
            "\tTemp file will save in 'user.home/.sinlov-java-jar-utils'\n" +
            "Thank for use and more info at https://github.com/sinlov/Java-Jar-Utils";

    public static void main(String[] args) {
        if (args.length == 1 && args[0].equals("-h")) {
            System.out.println(HELP_INFO);
            System.exit(0);
        }
        if (args.length != 2) {
            System.out.println("need in check jar path, output jar path, more info see -h");
            System.exit(1);
        }
        try {
            String inputJar = args[0];
            String outputJar = args[1];
            if (ZipFileChecker.checkJarIsRepeat(inputJar)) {
                System.out.println("You jar " + inputJar + " has repeat file");
            }
            clearCatch();
            ZipUtils.unZip(inputJar, LOCAL_HOST_BASE_CATCH);
            File outJar = new File(outputJar);
            if (outJar.exists()) {
                CmdUtils.rmFile(outputJar);
            }
            ZipUtils.zip(LOCAL_HOST_BASE_CATCH, outputJar);
            if (!isSaveCatch) {
                CmdUtils.rmFolder(LOCAL_HOST_BASE);
            }
            System.out.println("finish");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.printf("same error happens is output");
            System.exit(1);
        }
    }

    private static void clearCatch() throws IOException, InterruptedException {
        File temp = new File(LOCAL_HOST_BASE_CATCH);
        if (temp.exists()) {
            CmdUtils.rmFolder(LOCAL_HOST_BASE_CATCH);
        }
    }
}
