package pojo;

public class ServiceStatus {
    private Object data;
    private String message;
    private String status;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ServiceStatus(Object data, String message, String status){
        this.data = data;
        this.status = status;
        this.message = message;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
