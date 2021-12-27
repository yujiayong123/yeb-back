package com.xxx.server.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * Description: 消息
 * <p>
 * User: 于家勇
 * Date: 2021-12-10
 * version: 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ChatMsg {
    private String from;
    private String to;
    private String content;
    private LocalDateTime date;
    private String formNickName;
}
