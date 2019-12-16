package cn.allen.medical.data;

import java.io.Serializable;

public class MeRespone implements Serializable {
    private int code;
    private String message;
    private String data;
    private Object error;

    public MeRespone() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }

    public MeRespone(int code, String message, Object error) {
        this.code = code;
        this.message = message;
        this.error = error;
    }

    @Override
    public String toString() {
        return "MeRespone{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", error=" + error +
                '}';
    }
}
