package tech.lancelot.vo;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @author lancelot
 */
@Data
@RequiredArgsConstructor
public class Result<T> {
    public static final Integer  SUCCESS = 0;
    public static final Integer  ILLEGAL_TOKEN = 50008;
    public static final Integer  OTHER_CLIENTS_LOGGED_IN = 50012;
    public static final Integer  TOKEN_EXPIRED = 50014;

    /** 错误码. */
    private Integer code;

    /** 提示信息. */
    private String msg;

    /** 具体的内容. */
    private T data;

    public static Result success(Object object) {
        Result result = new Result();
        result.setCode(SUCCESS);
        result.setMsg("ok");
        result.setData(object);
        return result;
    }

    public static Result success() {
        return success(null);
    }

    public static Result error(Integer code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}
