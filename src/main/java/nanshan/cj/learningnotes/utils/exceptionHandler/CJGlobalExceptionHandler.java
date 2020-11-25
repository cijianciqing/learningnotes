package nanshan.cj.learningnotes.utils.exceptionHandler;

import cn.com.ns.cj.cjuniversalspringbootstarter.returnData.CJRDataUtil;
import cn.com.ns.cj.cjuniversalspringbootstarter.returnData.CJReturnedData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
/*暂时禁用此功能，便于开发调试*/
//@ControllerAdvice
public class CJGlobalExceptionHandler {
//
//    @Autowired
//    CJLogService cjLogService;


//    /*校验错误处理*/
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    @ResponseBody
//    public CJReturnedData exception(MethodArgumentNotValidException e) {
//        BindingResult bindingResult = e.getBindingResult();
//        List<ObjectError> allErrors = bindingResult.getAllErrors();
//
//        Map<String,String> cjValidateError = new HashMap<>();
////        List<ErrorMsg> errorMsgs = new ArrayList<>();
//
//       /* allErrors.forEach(objectError -> {
//            ErrorMsg errorMsg = new ErrorMsg();
//            FieldError fieldError = (FieldError)objectError;
//            errorMsg.setField(fieldError.getField());
//            errorMsg.setObjectName(fieldError.getObjectName());
//            errorMsg.setMessage(fieldError.getDefaultMessage());
//            errorMsgs.add(errorMsg);
//        });*/
//        allErrors.forEach(objectError->{
//            FieldError fieldError = (FieldError)objectError;
//            cjValidateError.put("cjErrorField",fieldError.getDefaultMessage());
//        });
//        CJReturnedData cjReturnedData = CJRDataUtil.Err(400, "后台校验错误");
//        cjReturnedData.setData(cjValidateError);
//        return cjReturnedData;
//    }


    /*校验错误处理*/
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String cjValidateException(MethodArgumentNotValidException e, HttpServletRequest request) {
        BindingResult bindingResult = e.getBindingResult();
        List<ObjectError> allErrors = bindingResult.getAllErrors();

        Map<String,String> cjValidateError = new HashMap<>();
//        List<ErrorMsg> errorMsgs = new ArrayList<>();

       /* allErrors.forEach(objectError -> {
            ErrorMsg errorMsg = new ErrorMsg();
            FieldError fieldError = (FieldError)objectError;
            errorMsg.setField(fieldError.getField());
            errorMsg.setObjectName(fieldError.getObjectName());
            errorMsg.setMessage(fieldError.getDefaultMessage());
            errorMsgs.add(errorMsg);
        });*/
        allErrors.forEach(objectError->{
            FieldError fieldError = (FieldError)objectError;
            cjValidateError.put("cjErrorField",fieldError.getDefaultMessage());
        });

        CJReturnedData cjReturnedData = CJRDataUtil.Err(400, "后台校验错误");
        cjReturnedData.setData(cjValidateError);

        Map<String, Object> map = new HashMap<>();
        map.put("cjMessage", cjReturnedData);
        request.setAttribute("cjExceptionHandlerMessage", map);
        return "forward:/error";
    }

    /*全局异常处理*/
    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, HttpServletRequest request) {

        Map<String, Object> map = new HashMap<>();
        //传入我们自己的错误状态码  4xx 5xx，否则就不会进入定制错误页面的解析流程
        /**
         * Integer statusCode = (Integer) request
         .getAttribute("javax.servlet.error.status_code");
         */
        log.info("this is  ns.cj.cjknowledge.universal.exceptionHandler.CJGlobalExceptionHandler:");
        //设置状态码
        request.setAttribute("javax.servlet.error.status_code", 500);
//        map.put("cjUrl", request.getRequestURL().toString());
        //获取exception的详细错误信息
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        e.printStackTrace(new PrintStream(baos));
        map.put("cjMessage", "这是全局自定义错误信息");

        request.setAttribute("cjExceptionHandlerMessage", map);
        //写入数据库
//        CJUserError cjUserError = new CJUserError();
//        cjUserError.setUsername("cjTemp");
//        cjUserError.setUrl(request.getRequestURL().toString());
//        cjUserError.setErrorMessage(cjException);
//        cjLogService.saveUserErrorLog(cjUserError);
        //获取请求的parameter
//        Map<String, String[]> parameterMap = request.getParameterMap();
//        for (Map.Entry<String,String[]> entry: parameterMap.entrySet()) {
//            System.out.print(entry.getKey()+"-->");
//            System.out.println(Arrays.toString(entry.getValue()));
//        }

        //转发到/error
        return "forward:/error";
    }


}
