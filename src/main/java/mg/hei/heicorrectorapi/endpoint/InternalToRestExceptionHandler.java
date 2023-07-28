package mg.hei.heicorrectorapi.endpoint;

import lombok.extern.slf4j.Slf4j;
import mg.hei.heicorrectorapi.model.exception.BadRequestException;
import mg.hei.heicorrectorapi.model.exception.NotFoundException;
import mg.hei.heicorrectorapi.rest.model.Exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
@Slf4j
public class InternalToRestExceptionHandler {
  @ExceptionHandler(value = {BadRequestException.class})
  ResponseEntity<Exception> handleBadRequest(
      BadRequestException e) {
    log.info("Bad request", e);
    return new ResponseEntity<>(toRest(e, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = {MissingServletRequestParameterException.class})
  ResponseEntity<Exception> handleBadRequest(
      MissingServletRequestParameterException e) {
    log.info("Missing parameter", e);
    return handleBadRequest(new BadRequestException(e.getMessage()));
  }

  @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
  ResponseEntity<Exception> handleConversionFailed(
      MethodArgumentTypeMismatchException e) {
    log.info("Conversion failed", e);
    String message = e.getCause().getCause().getMessage();
    return handleBadRequest(new BadRequestException(message));
  }

  @ExceptionHandler(value = {NotFoundException.class})
  ResponseEntity<Exception> handleNotFound(
      NotFoundException e) {
    log.info("Not found", e);
    return new ResponseEntity<>(toRest(e, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(value = {java.lang.Exception.class})
  ResponseEntity<Exception> handleDefault(java.lang.Exception e) {
    log.error("Internal error", e);
    return new ResponseEntity<>(
        toRest(e, HttpStatus.INTERNAL_SERVER_ERROR),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private Exception toRest(java.lang.Exception e, HttpStatus status) {
    return new Exception()
        .type(status.toString())
        .message(e.getMessage());
  }
}
