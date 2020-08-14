package com.springcloudalibaba.model;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * Created by sunjianfei on 2020/8/14.
 */
@Data
@Component
public class UrlBlocker {
    private int code;
    private String msg;

}
