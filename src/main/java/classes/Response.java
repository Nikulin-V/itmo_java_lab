package classes;

import java.io.Serializable;

public class Response implements Serializable {
    private final int code;
    private Object data;

    public Response(int code) {
        this.code = code;
    }

    public Response(int code, Object data) {
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public Object getData() {
        return data;
    }

    public Response setData(String data) {
        this.data = data;
        return this;
    }

//    @Override
//    public String toString() {
//        return "Response{" +
//                "code=" + code +
//                ", data=" + data +
//                '}';
//    }
}
