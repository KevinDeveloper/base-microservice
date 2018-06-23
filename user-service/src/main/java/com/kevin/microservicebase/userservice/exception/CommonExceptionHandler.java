package com.kevin.microservicebase.userservice.exception;


import com.kevin.microservicebase.commonsupport.dto.RespDTO;
import com.kevin.microservicebase.commonsupport.exception.CommonException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @ClassName: CommonExceptionHandler
 * @Description:全局异常处理
 * @Author: Kevin
 * @Date: 2018/6/15 09:10
 */
@ControllerAdvice
@ResponseBody
public class CommonExceptionHandler {

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<RespDTO> handleException(Exception e) {
        RespDTO resp = new RespDTO();
        CommonException taiChiException = (CommonException) e;
        resp.code = taiChiException.getCode();
        resp.error = e.getMessage();

        return new ResponseEntity(resp, HttpStatus.OK);
    }

}
