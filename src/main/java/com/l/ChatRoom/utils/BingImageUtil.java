package com.l.ChatRoom.utils;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.stereotype.Component;


/**
 * Created by Chaofan at 2018-09-26 15:40
 * email:chaofan2685@qq.com
 **/
@Component
public class BingImageUtil {

    /**
     * 同步必应壁纸
     * @param index 起始点，0表示今天，1表示昨天，2前天，以此类推
     * @param sum 同步壁纸的数量，最多7张
     */
    public static String download(Integer index, Integer sum){
        Integer i = 0;
        String result = HttpUtil.get("https://www.bing.com/HPImageArchive.aspx?format=js&idx="+index+"&n="+sum);
        JSONObject jsonObject = JSONUtil.parseObj(result);
        JSONArray array = JSONUtil.parseArray(jsonObject.get("images"));
        String url=null;
        for (int j = 0; j < array.size(); j++) {
           url = "http://s.cn.bing.net"+(JSONUtil.parseObj(array.get(j)).get("url").toString());

        }
        return url;
    }

    public static void main(String[] args) {
        System.out.println(download(0,1));
    }

}
