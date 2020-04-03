package cn.edu.nju.eczuul.response;

import lombok.Getter;
import lombok.Setter;
import rest.CodeMsg;

@Getter
@Setter
public class Response<T> {
    private int code;
    private String msg;
    private T data;

    public boolean isSuccess() { return this.code == CodeMsg.SUCCESS.getCode();}
    public static <T> Response<T> success(T data) { return new Response<>(CodeMsg.SUCCESS.getCode(), CodeMsg.SUCCESS.getMsg(),data); }
    public static <T> Response<T> error(CodeMsg codeMsg) {
        return new Response<>(codeMsg);
    }

    private Response(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private Response(CodeMsg codeMsg) {
        if (codeMsg!=null) {
            this.code = codeMsg.getCode();
            this.msg = codeMsg.getMsg();
            this.data = null;
        }
    }
}
