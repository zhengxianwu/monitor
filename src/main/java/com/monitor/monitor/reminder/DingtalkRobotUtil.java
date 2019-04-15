package com.monitor.monitor.reminder;


import java.io.IOException;
import java.util.Map;

import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;


/**
 * Created by KAI on 2017/11/15.
 * ectest@foxmail.com
 */
@Component
public class DingtalkRobotUtil {
	
	private  String token = "2dced5c5d02e3a8b2769e61236bba976ab3dce7eb6de7883b14c2617145f7006";
	
    private  String URL = String.format("https://oapi.dingtalk.com/robot/send?access_token=%s",token);
    
    @Autowired
    private MapFactory factory;
    
    /**
     * 发送文字消息
     *
     * @param message
     * @return
     */
    public  String messageText(String message, String[] at, boolean isAtAll) {

        Map<String, Object> map = factory.of(
                "msgtype", "text",
                "text", factory.of("content", message),
                "at", factory.of("atMobiles", at),
                "isAtAll", isAtAll
        );

        return post(map);
    }

    /**
     * 发送超链接消息
     *
     * @param text
     * @param title
     * @param picUrl
     * @param messageUrl
     * @return
     */
    public  String messageLink(String text, String title, String picUrl, String messageUrl, String[] at, boolean isAtAll) {

        Map<String, Object> map = factory.of(
                "msgtype", "link",
                "link", factory.of("text", text, "title", title, "picUrl", picUrl, "messageUrl", messageUrl),
                "at", factory.of("atMobiles", at),
                "isAtAll", isAtAll
        );

        return post(map);
    }

    /**
     * 发送 markdown 消息
     *
     * @param text
     * @param title
     * @return
     */
    public  String messageMarkdown(String text, String title, String[] at, boolean isAtAll) {

        Map<String, Object> map = factory.of(
                "msgtype", "markdown",
                "markdown", factory.of("text", text, "title", title),
                "at", factory.of("atMobiles", at),
                "isAtAll", isAtAll
        );

        return post(map);
    }


    private  String post(Map body) {

        String returnString = null;

        try {
            returnString = Request.Post(URL).connectTimeout(3000)
                    .bodyString(JSON.toJSONString(body), ContentType.APPLICATION_JSON).execute().returnContent().asString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnString;

    }
    
    
    
    
  

}
