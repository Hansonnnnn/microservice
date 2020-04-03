package cn.edu.nju.order.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import rest.CodeMsg;
import rest.RestResponse;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@ResponseBody
public class OrderExceptionHandler {
    @ExceptionHandler(value = {Exception.class})
    public RestResponse handleException(HttpServletRequest request, Exception e) {
        e.printStackTrace();
        return RestResponse.builder().status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .code(CodeMsg.SERVER_ERROR.getCode())
                .message(CodeMsg.SERVER_ERROR.getMsg())
                .developerMessage(e.getMessage())
                .build();
    }
}
