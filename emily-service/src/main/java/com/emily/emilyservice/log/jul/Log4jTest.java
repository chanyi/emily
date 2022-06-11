package com.emily.emilyservice.log.jul;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.io.IOException;

public class Log4jTest {

    @Test
    public void test() throws IOException {
        Logger logger =
                LogManager.getLogger(Log4jTest.class);
        logger.info("ss");
    }
}
