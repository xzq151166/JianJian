package pojo;


public class ExcelVo implements Cloneable{
    private String code;
    private String store;
    private String amount;
    private String dataResource;
    private Integer rowNum;
    private Integer columnindex;
    private String isResolved;
    private String isWrited;

    private String credential;
    private String credentialDate;
    private String remark;

    private String summary;
    //长摘要
    private String bigSummary;
    //交易时间
    private String time;
    //对账时间

    private String checkTime;
    private String accountName;
    private String  account;
    private String  accountDate;
    private String bankType;

    public String getBigSummary() {
        return bigSummary;
    }

    public void setBigSummary(String bigSummary) {
        this.bigSummary = bigSummary;
    }

    public String getIsWrited() {
        return isWrited;
    }

    public void setIsWrited(String isWrited) {
        this.isWrited = isWrited;
    }

    public String getCredentialDate() {
        return credentialDate;
    }

    public void setCredentialDate(String credentialDate) {
        this.credentialDate = credentialDate;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccountDate() {
        return accountDate;
    }

    public void setAccountDate(String accountDate) {
        this.accountDate = accountDate;
    }

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public String getCredential() {
        return credential;
    }

    public void setCredential(String credential) {
        this.credential = credential;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getIsResolved() {
        return isResolved;
    }

    public void setIsResolved(String isResolved) {
        this.isResolved = isResolved;
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

    public String getDataResource() {
        return dataResource;
    }

    public void setDataResource(String dataResource) {
        this.dataResource = dataResource;
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

    private Integer getActulRowNum(){
        return  this.getRowNum()+1;
    }


    @Override
    public String toString() {
        //重写实体类的tostring方法
        String desc = "第("+this.getActulRowNum()+")行"+"[编码: "+this.getCode()+",门店: "+this.getStore()+",金额 :"
                +this.getAmount()+"]\r\n";
        return desc;
    }

    @Override
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }

}
