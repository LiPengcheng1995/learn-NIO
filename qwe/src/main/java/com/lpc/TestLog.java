package com.lpc;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lipengcheng1995 Created date 2021-08-06 14:50
 **/
@Slf4j
public class TestLog {
    public static void main(String[] args) {
        int i=0;
        do{
            log.info("lipengcheng1995");
            i++;
        }while (i<200000);
    }
}
