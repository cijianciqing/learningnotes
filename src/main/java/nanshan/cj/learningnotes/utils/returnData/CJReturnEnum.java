package nanshan.cj.learningnotes.utils.returnData;

public enum CJReturnEnum {
    //这里是可以自己定义的，方便与前端交互即可
    SUCCESS(1,"成功"),
    DATA_IS_NULL(0,"数据为空"),
    UNKNOWN_ERROR(-1,"未知错误"),
    USER_NOT_EXIST(101,"用户不存在"),
    USER_IS_EXISTS(102,"用户已存在");
  ;

    private Integer code;
    private String msg;

    CJReturnEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
