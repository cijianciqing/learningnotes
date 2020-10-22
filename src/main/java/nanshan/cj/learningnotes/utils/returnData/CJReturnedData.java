package nanshan.cj.learningnotes.utils.returnData;

import lombok.Data;

import java.io.Serializable;

@Data
public class CJReturnedData<T> implements Serializable {
    private  Integer code;
    private String msg;
    private T data;
}
