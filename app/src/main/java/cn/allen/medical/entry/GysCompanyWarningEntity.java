package cn.allen.medical.entry;

import java.io.Serializable;
import java.util.List;

public class GysCompanyWarningEntity implements Serializable {

    /**
     * id :
     * organizationName :
     * certList : [{"currentDate":"2020-01-03 14:44:22","certName":"销售代表授权书",
     * "expireDate":"2019-08-31 00:00:00"},{"currentDate":"2020-01-03 14:44:22",
     * "certName":"营业执照","expireDate":"2019-09-30 00:00:00"}]
     */

    private String id;
    private String organizationName;
    private List<CertListBean> certList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public List<CertListBean> getCertList() {
        return certList;
    }

    public void setCertList(List<CertListBean> certList) {
        this.certList = certList;
    }

    public static class CertListBean {
        /**
         * currentDate : 2020-01-03 14:44:22
         * certName : 销售代表授权书
         * expireDate : 2019-08-31 00:00:00
         */

        private String currentDate;
        private String certName;
        private String expireDate;

        public String getCurrentDate() {
            return currentDate;
        }

        public void setCurrentDate(String currentDate) {
            this.currentDate = currentDate;
        }

        public String getCertName() {
            return certName;
        }

        public void setCertName(String certName) {
            this.certName = certName;
        }

        public String getExpireDate() {
            return expireDate;
        }

        public void setExpireDate(String expireDate) {
            this.expireDate = expireDate;
        }
    }
}
