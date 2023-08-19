package com.dailycodebuffer.order.external.decoder;

import com.dailycodebuffer.order.exception.CustomException;
import com.dailycodebuffer.order.external.model.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

/**
 * Custom error decoder for feign client
 *
 * @author Glenn Cai
 * @version 1.0 18/08/2023
 */
@Log4j2
public class CustomErrorDecoder implements ErrorDecoder {
  @Override
  public Exception decode(String s, Response response) {
    ObjectMapper objectMapper = new ObjectMapper();
    log.info("::{}", response.request().url());
    log.info("::{}", response.request().headers());

    try {
      ErrorResponse errorResponse =
          objectMapper.readValue(response.body().asInputStream(), ErrorResponse.class);

      return new CustomException(
          errorResponse.getErrorCode(),
          errorResponse.getErrorMessage(),
          response.status());
    } catch (IOException e) {
      throw new CustomException("Internal Server Error", "INTERNAL_SERVER_ERROR", 500);
    }
  }
}
