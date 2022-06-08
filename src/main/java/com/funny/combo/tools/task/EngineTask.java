package com.funny.combo.tools.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Date 2022/6/28 23:10
 * @Created by fangli
 */
@Slf4j
@Component
public class EngineTask {

    @Scheduled(cron = "2 0/3 9-22 * * ?")
    public void loadRule() {
        System.out.println("EngineTask run");
    }


}
