package cn.allen.medical.data;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class HttpCallBack<T> {

    protected Type genericityType;

    public HttpCallBack() {
        Type genericSuperclass = getClass().getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            this.genericityType = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
        } else {
            this.genericityType = Object.class;
        }
    }
    /***
     * 请求成功回调
     * @param respone
     */
    public abstract void onSuccess(T respone);

    public abstract void onFailed(MeRespone respone);

    public Type getGenericityType() {
        return genericityType;
    }
}
