package cn.allen.medical.entry;

import java.io.Serializable;

public class User implements Serializable {
    private String userId;
    private String token;
    private boolean isDefaultPwd;
    private int invalidTime;
    private int expiresTime;
    private String merchantName;
    private String organizationName;
    private String loginAccount;
    private String userName;
    private String mobilePhone;
    private String telPhone;
    private String email;
    private String address;
    private String pictureUrl;

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

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getTelPhone() {
        return telPhone;
    }

    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", token='" + token + '\'' +
                ", isDefaultPwd=" + isDefaultPwd +
                ", invalidTime=" + invalidTime +
                ", expiresTime=" + expiresTime +
                ", merchantName='" + merchantName + '\'' +
                ", organizationName='" + organizationName + '\'' +
                ", loginAccount='" + loginAccount + '\'' +
                ", userName='" + userName + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", telPhone='" + telPhone + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", pictureUrl='" + pictureUrl + '\'' +
                '}';
    }
}
