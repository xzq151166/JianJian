package pojo;

import java.util.List;

/**
 * 差异详情
 */
public class DifferDesc{
    private String desc;
    private List<ExcelVo> bankList;
    private List<ExcelVo> storeList;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<ExcelVo> getBankList() {
        return bankList;
    }

    public void setBankList(List<ExcelVo> bankList) {
        this.bankList = bankList;
    }

    public List<ExcelVo> getStoreList() {
        return storeList;
    }

    public void setStoreList(List<ExcelVo> storeList) {
        this.storeList = storeList;
    }
}
