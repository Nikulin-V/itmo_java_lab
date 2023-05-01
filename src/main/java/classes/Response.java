package classes;

import java.io.Serializable;

public class Response implements Serializable {
    private final int code;
    private String data;

    private static Response INSTANCE;

    public static Response getEmptyResponce(){
        if (INSTANCE==null){
            INSTANCE = new Response(1)
                    .setData("responce is empty");
        }
        return INSTANCE;
    }

    public Response(int responseCode) {
        code = responseCode;
    }
    public String getData() {
        return data;
    }

    public Response setData(String data) {
        this.data = data;
        return this;
    }
}
