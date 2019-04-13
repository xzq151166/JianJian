package enumPoJo;

public enum StatusMessage {

    失败("1","不好意思，程序执行失败了."),
    成功("2","执行成功，yeah.");

    private String code;//状态值
    private String message;//状态值

    StatusMessage(String code, String message){
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }


}
