package rest;

public enum CodeMsg {
    //通用的错误码
    SUCCESS(0, "success"),
    SERVER_ERROR(500100, "服务端异常"),
    ACCESS_LIMIT_REACHED(500101, "访问太频繁"),
    //登录模块 5002XX
    USER_INFO_ERROR(500201, "手机号或密码错误"),
    USER_NOT_FOUND(500202, "用户不存在"),
    USER_NO_LOGIN(500203, "用户未登录"),
    //秒杀模块 5003XX
    SEC_KILL_OVER(500301, "秒杀商品已秒杀光"),
    SEC_KILL_REPEATED(500302, "已秒杀成功"),
    //库存模块 5004XX
    INVENTORY_NOT_ENOUGH(500401, "库存不够")
    ;

    private int code;
    private String msg;

    CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
