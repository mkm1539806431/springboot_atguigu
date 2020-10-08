package com.xjtu;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {
    Logger logger= LoggerFactory.getLogger(getClass());
    @Test
    void contextLoads() {
        logger.trace("trace");
        logger.debug("trace");
        logger.info("trace");
        logger.warn("trace");
        logger.error("trace");
    }

}
