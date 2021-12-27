package com.xxx.server.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 公共返回对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespBean {

    private int code;
    private String msg;
    private Object object;

    /**
     * 成功返回结果
     * @param msg
     * @return
     */
    public static RespBean success(String msg) {
        return new RespBean(200,msg,null);
    }

    /**
     * 成功返回结果
     * @param msg
     * @return
     */
    public static RespBean success(String msg,Object o) {
        return new RespBean(200,msg,o);
    }

    /**
     * 失败返回结果
     * @param msg
     * @return
     */
    public static RespBean error(String msg) {
        return new RespBean(500,msg,null);
    }

    /**
     * 失败返回结果
     * @param msg
     * @return
     */
    public static RespBean error(String msg,Object o) {
        return new RespBean(500,msg,o);
    }
}
