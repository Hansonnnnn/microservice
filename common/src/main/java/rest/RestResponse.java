package rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * Rest API response
 * Class structure design refers to https://zhaox.github.io/design/2015/10/23/rest-api-error-response-design
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestResponse<T> {
    //status Status的内容与HTTP状态码内容相同，这个字段的存在，使得错误信息自包含，客户端只需要解析HTTP响应的body部分，就可以获取所有跟这次出错相关的信息。
    private int status;
    //code 自定义错误码。自定义错误码的长度和个数都可以自己定义，这样就突破了HTTP状态码的个数限制。例子中的错误码是40483，其中404代表了请求的资源不存在，而83则制定了这次出错，具体是哪一种资源不存在
    private int code;
    //message 用户可理解的错误信息，应当根据用户的locale信息返回对应语言的版本。这个错误信息意在返回给使用客户端的用户阅读，不应该包含任何技术信息。有了这个字段，客户端的开发者在出错时，能够展示恰当的信息给最终用户
    private String message;
    //developerMessage 该出错的详细技术信息，提供给客户端的开发者阅读。可以包含Exception的信息、StackTrace，或者其它有用的技术信息
    private String developerMessage;
    //moreInfo 给出一个URL，客户端开发站访问这个URL可以看到更详细的关于该种出错信息的描述。在该URL展示的网页中，可以包含该出错信息的定义，产生原因，解决办法等等
    private String moreInfo;
    //requestId 请求ID，服务为每一个请求唯一生成一个请求ID，当客户端开发者无法自助解决问题时，可以联络服务开发者，同时提供该请求ID。一个好的服务，服务开发者应当可以根据此ID，定位到该次请求的所有相关log，进而定位问题，解决问题。
//    private String requestId;
    private T data;

    public boolean isSuccess() {return this.code == CodeMsg.SUCCESS.getCode();}
    public static <T> RestResponse<T> success(T data) {return new RestResponse<>(data);}
    public static <T> RestResponse<T> error(HttpStatus status, CodeMsg codeMsg) {
        return new RestResponse<>(status, codeMsg);
    }

    private RestResponse(T data){
        this.status = HttpStatus.OK.value();
        this.code = CodeMsg.SUCCESS.getCode();
        this.message = CodeMsg.SUCCESS.getMsg();
        this.data = data;
    }

    private RestResponse(HttpStatus status, CodeMsg codeMsg) {
        this.status = status.value();
        this.code = codeMsg.getCode();
        this.message = codeMsg.getMsg();
    }
}
