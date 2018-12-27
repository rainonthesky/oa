package com.oa.sys.util;

import com.oa.sys.controller.AreaController;
import org.apache.log4j.Logger;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class QueueMessageListener implements MessageListener {
    private static Logger logger=Logger.getLogger(QueueMessageListener.class);
    @Override
    public void onMessage(Message message) {
        TextMessage textMessage=(TextMessage)message;
        try{
            System.out.println("QueueMessageListener监听到了文本消息：\t"
                    + textMessage.getText());
            //do something ...

        }catch (Exception e){
            logger.error("active监听出现错误",e);
        }
    }
}
