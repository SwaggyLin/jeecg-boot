package org.jeecg.modules.tbk.util;

import cn.hutool.http.HttpUtil;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;

/**
 * @Description
 * @Author linminfeng
 * @Date 2019-12-27 10:15
 */
public class ShortUrlUtil {
    public static final String SHORTEN_API="http://sina-t.cn/api?link=";
    public static final String SOURCE="3271760578";
    public static String getShortenUrl(String longUrl){
        String shortUrl=HttpUtil.get(SHORTEN_API+"&url_long="+longUrl);
        System.out.println(shortUrl);
        return  shortUrl;
    }
    public static void main(String path[]) throws Exception {
        URL u = new URL("http://sina-t.cn/APIKEY/tcn_url?url=http%3A%2F%2Fwww.baidu.com");
        InputStream in = u.openStream();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            byte buf[] = new byte[1024];
            int read = 0;
            while ((read = in .read(buf)) > 0) {
                out.write(buf, 0, read);
            }
        } finally {
            if ( in != null) {
                in .close();
            }
        }
        byte b[] = out.toByteArray();
        System.out.println(new String(b, "utf-8"));
    }
}
