package domain;

import lombok.Data;

@Data
public class Result {
    public Result() {
    }

    public Result(Integer code) {
        this.code = code;
    }

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(Integer code, Object data) {
        this.code = code;
        this.data = data;
    }

    public Result(Integer code, Object data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    private Integer code;
    private Object data;
    private String msg;
}
