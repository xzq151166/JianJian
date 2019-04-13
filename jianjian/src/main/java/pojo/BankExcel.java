package pojo;

public class BankExcel {
    private String code;
    private String store;
    private String amount;
    private String dataResource;
    private Integer rowNum;
    private Integer columnindex;
    private String isResolved;
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDataResource() {
        return dataResource;
    }

    public void setDataResource(String dataResource) {
        this.dataResource = dataResource;
    }

    public Integer getRowNum() {
        return rowNum;
    }

    public void setRowNum(Integer rowNum) {
        this.rowNum = rowNum;
    }

    public Integer getColumnindex() {
        return columnindex;
    }

    public void setColumnindex(Integer columnindex) {
        this.columnindex = columnindex;
    }

    public String getIsResolved() {
        return isResolved;
    }

    public void setIsResolved(String isResolved) {
        this.isResolved = isResolved;
    }
}
