package com.cxp.springquartz.job;

import lombok.Data;

/**
 * @author : cheng
 * @date : 2020-03-11 18:54
 */
@Data
public class ResponseDto {

    private int code;
    private String message;
    private Object data;

    public static ResponseDto success(Object data){
        ResponseDto dto = new ResponseDto();
        dto.setCode(200);
        dto.setData(data);
        return dto;
    }

    public static ResponseDto fail(int code,String message){
        ResponseDto dto = new ResponseDto();
        dto.setCode(code);
        dto.setMessage(message);
        return dto;
    }

    public static ResponseDto error(int code,String message){
        ResponseDto dto = new ResponseDto();
        dto.setCode(code);
        dto.setMessage(message);
        return dto;
    }
}
