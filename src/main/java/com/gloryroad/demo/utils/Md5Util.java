package com.gloryroad.demo.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {
    private static final Logger LOG = LoggerFactory.getLogger(Md5Util.class);

    public Md5Util() {
    }

    public static String getMd5(String src) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(src.getBytes());
            return bytesToString(md.digest());
        } catch (NoSuchAlgorithmException var3) {
            return null;
        }
    }

    public static String getMd5(File file) {
        FileInputStream fis = null;
        String res = null;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            fis = new FileInputStream(file);
            byte[] buffer = new byte[8192];
            boolean var5 = true;

            int length;
            while((length = fis.read(buffer)) != -1) {
                md.update(buffer, 0, length);
            }

            res = bytesToString(md.digest());
        } catch (Exception var14) {
            LOG.error("getMd5 error", var14);
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException var13) {
            }

        }

        return res;
    }

    private static String bytesToString(byte[] data) {
        char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        char[] temp = new char[data.length * 2];

        for(int i = 0; i < data.length; ++i) {
            byte b = data[i];
            temp[i * 2] = hexDigits[b >>> 4 & 15];
            temp[i * 2 + 1] = hexDigits[b & 15];
        }

        return new String(temp);
    }
}
