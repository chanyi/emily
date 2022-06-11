package com.emily.emilyservice.log.jul;

import org.junit.Test;

import java.io.IOException;
import java.util.logging.*;

public class JULUtil {

    @Test
    public void test() throws IOException {
        Logger logger = Logger.getLogger("com.emily.emilyservice.log.jul.JULUtil");

        //关闭默认handler
        logger.setUseParentHandlers(false);
        ConsoleHandler handler = new ConsoleHandler();
        //输出到日志文件中
//        FileHandler fileHandler= new FileHandler("D:\\test\\test.log");
        SimpleFormatter simpleFormatter = new SimpleFormatter();
        handler.setFormatter(simpleFormatter);
        logger.addHandler(handler);
        handler.setLevel(Level.ALL);
        logger.setLevel(Level.ALL);
        logger.info("ss");
        logger.log(Level.INFO,"sss");
        logger.log(Level.FINE,"fine sss");

    }
}
