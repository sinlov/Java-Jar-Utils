package com.sinlov.java.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

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
public class CmdUtils {

    public static String join(String cmd[]) {
        StringBuilder sb = new StringBuilder();
        for (String s : cmd)
            sb.append(s + " ");
        return sb.toString();
    }

    private static void runCmdLine(String[] cmdLine, final String cmdName) throws IOException, InterruptedException {
        System.out.println("Running " + cmdName + "\nCommand line: " + join(cmdLine));
        Process proc = Runtime.getRuntime().exec(cmdLine);
        proc.waitFor();
        InputStream err = proc.getErrorStream();
        InputStream in = proc.getInputStream();
        while (err.available() > 0)
            System.out.print((char) err.read());
        while (in.available() > 0)
            System.out.print((char) in.read());
    }

    public static void rmFile(String file) throws IOException, InterruptedException {
        String cmdLine[] = {"rm", file};
        runCmdLine(cmdLine, "rm file");
    }

    public static void rmFolder(String rmFolder) throws IOException, InterruptedException {
        if (!rmFolder.endsWith(File.separator)) {
            rmFolder = rmFolder + File.separator;
        }
        String cmdLine[] = {"rm", "-rf", rmFolder};
        runCmdLine(cmdLine, "rm folder");
    }
}
