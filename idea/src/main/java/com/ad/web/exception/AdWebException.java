package com.ad.web.exception;

import com.ad.web.common.result.ResultCodeEnum;
import lombok.Data;

@Data
public class AdWebException extends RuntimeException {
  private Integer code;
    public AdWebException(Integer code,String message) {
        super(message);
        this.code = code;
    }
    public AdWebException(ResultCodeEnum resultCodeEnum){
      super(resultCodeEnum.getMessage());
      this.code = resultCodeEnum.getCode();
    }
}
