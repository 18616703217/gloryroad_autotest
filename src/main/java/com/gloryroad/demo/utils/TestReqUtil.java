package com.gloryroad.demo.utils;

import com.google.common.collect.Maps;
import net.sourceforge.pinyin4j.PinyinHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestReqUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestReqUtil.class);
    private static final long PARTNER_RANDOM_MIN = 946656000000L;

    public TestReqUtil() {
    }

    public static long getProxyLastHeartBeat() {
        return System.currentTimeMillis();
    }

    public static String listToJson(List<String> srcList) {
        StringBuffer result = new StringBuffer();
        result.append("[");
        Iterator var2 = srcList.iterator();

        while(var2.hasNext()) {
            String item = (String)var2.next();
            result.append(item);
            result.append(",");
        }

        if (result.length() > 1) {
            result.delete(result.length() - 1, result.length());
        }

        result.append("]");
        return result.toString();
    }

    public static String getRandom() {
        String secretStr = String.valueOf(Math.round(Math.random() * (double)(System.currentTimeMillis() - 946656000000L) + 9.46656E11D));
        int random = (int)(Math.random() * 900.0D) + 100;
        StringBuffer sb = new StringBuffer();
        sb.append(secretStr);
        sb.append(random);
        if (sb.length() < 16) {
            for(int i = 0; i < 16 - sb.length(); ++i) {
                sb.append(0);
            }
        }

        return sb.toString();
    }

    public static String formatDate(long lDate) {
        Date date = new Date();
        date.setTime(lDate);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = format.format(date);
        return dateStr;
    }

    public static boolean isContainsChinese(String str) {
        String regEx = "[一-龥]";
        Pattern pat = Pattern.compile(regEx);
        Matcher matcher = pat.matcher(str);
        boolean flg = false;
        if (matcher.find()) {
            flg = true;
        }

        return flg;
    }

    public static Map<String, Object> genFuzzyQuery(String key, String keyWord) {
        Map<String, Object> fuzzyQuery = Maps.newHashMap();
        fuzzyQuery.put("$regex", String.format(".*%s.*", keyWord));
        fuzzyQuery.put("$options", "i");
        Map<String, Object> query = Maps.newHashMap();
        query.put(key, fuzzyQuery);
        return query;
    }

    public static boolean checkParamNull(String param) {
        return param == null || param.length() == 0;
    }

    public static String chineseParamEncode(String param) throws UnsupportedEncodingException {
        if (param != null) {
            param = new String(param.getBytes("ISO8859-1"), "UTF-8");
        }

        return param;
    }

    public static void replaceNullOfMapValue(Map<String, String> aMap) {
        Iterator var1 = aMap.entrySet().iterator();

        while(var1.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry)var1.next();
            if (entry.getValue() == null) {
                aMap.put(entry.getKey(), "");
            }
        }

    }


    public static String getPinyinOfHanzi(String hanzi) {
        if (hanzi != null && hanzi.length() != 0) {
            char[] ch = hanzi.toCharArray();
            StringBuffer s = new StringBuffer();
            char[] var3 = ch;
            int var4 = ch.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                char c = var3[var5];
                String[] temp = PinyinHelper.toHanyuPinyinStringArray(c);
                if (temp == null) {
                    s.append(Character.toLowerCase(c));
                } else {
                    String pinyin = temp[0];
                    s.append(pinyin.substring(0, pinyin.length() - 1));
                }
            }

            return s.toString();
        } else {
            return "";
        }
    }

    public static String formatFileVersion(int versionId) {
        int v1 = versionId / 1000000;
        int v2 = versionId % 1000000 / 1000;
        int v3 = versionId % 1000;
        return String.format("%d.%d.%d", v1, v2, v3);
    }

    public static String getRemoteIp(HttpServletRequest request) {
        return request.getHeader("x-forwarded-for") == null ? request.getRemoteAddr() : request.getHeader("x-forwarded-for");
    }


    public static boolean isZero(Integer value) {
        return value == null || value == 0;
    }

    public static void deleteDir(File dir) {
        if (dir.exists()) {
            File[] var1 = dir.listFiles();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                File fi = var1[var3];
                if (fi.isFile()) {
                    fi.delete();
                } else if (fi.isDirectory()) {
                    deleteDir(fi);
                }
            }

            dir.delete();
        }

    }
}
