package cn.allen.medical.entry;

import java.io.Serializable;

public class User implements Serializable {
    private String userId;
    private String token;
    private boolean isDefaultPwd;
    private int invalidTime;
    private int expiresTime;

    public User() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isDefaultPwd() {
        return isDefaultPwd;
    }

    public void setDefaultPwd(boolean defaultPwd) {
        isDefaultPwd = defaultPwd;
    }

    public int getInvalidTime() {
        return invalidTime;
    }

    public void setInvalidTime(int invalidTime) {
        this.invalidTime = invalidTime;
    }

    public int getExpiresTime() {
        return expiresTime;
    }

    public void setExpiresTime(int expiresTime) {
        this.expiresTime = expiresTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", token='" + token + '\'' +
                ", isDefaultPwd=" + isDefaultPwd +
                ", invalidTime=" + invalidTime +
                ", expiresTime=" + expiresTime +
                '}';
    }
}
