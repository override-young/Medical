package cn.allen.medical.data;

import allen.frame.tools.Logger;
import allen.frame.tools.StringUtils;

public class HttpBody {
    public HttpBody() {
    }

    public String okHttpPost(Object[] arrays) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (arrays != null) {
            for (int i = 0; i < arrays.length; i++) {
                sb.append((i == 0 ? "\"" : ",\"") + arrays[i++] + "\":" + StringUtils.getObject(arrays[i]));
            }
        }
        sb.append("}");
        Logger.http("body",  "[" + sb.toString() + "]");
        return sb.toString();
    }
}
