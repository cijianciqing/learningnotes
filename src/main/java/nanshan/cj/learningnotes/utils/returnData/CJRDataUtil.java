package nanshan.cj.learningnotes.utils.returnData;

public class CJRDataUtil {
    /*成功，且返回体有数据*/
    public static CJReturnedData success(Object object) {
        CJReturnedData r = new CJReturnedData();
        r.setCode(CJReturnEnum.SUCCESS.getCode());
        r.setMsg(CJReturnEnum.SUCCESS.getMsg());
        r.setData(object);
        return r;
    }
    //成功，但返回体没数据
    public static  CJReturnedData success(){
        return success(null);
    }
    //失败返回信息
    public static CJReturnedData Err(Integer code,String msg){
        CJReturnedData r = new CJReturnedData();
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }
}
