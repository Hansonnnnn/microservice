package cn.edu.nju.eczuul.response;

public enum CodeMsg {
    //通用的错误码
    SUCCESS(0, "success"),
    //登录模块 5002XX
    USER_INFO_ERROR(500201, "手机号或密码错误");
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
