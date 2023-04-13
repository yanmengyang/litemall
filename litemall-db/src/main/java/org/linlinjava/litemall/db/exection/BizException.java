package org.linlinjava.litemall.db.exection;

/**
 * @Auther: honglei
 * @Date: 2021/4/17 16:44
 * @Description:
 */
public class BizException extends Exception {
    public int getCode() {
        return code;
    }

    private  int code;
    public BizException(String message) {
        super(message);
    }
    public BizException(int code,String message) {
        super(message);
        this.code=code;
    }
}
