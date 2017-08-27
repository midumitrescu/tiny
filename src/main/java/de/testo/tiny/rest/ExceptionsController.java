package de.testo.tiny.rest;

import de.testo.tiny.model.Problem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionsController {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Problem handleValidationError(final MethodArgumentNotValidException exception) {

        BindingResult bindingResult = exception.getBindingResult();
        return Problem.builder()
                .statusCode(HttpStatus.BAD_REQUEST)
                .detail(bindingResult.getFieldError().getDefaultMessage())
                .build();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Problem handleInvalidBodyError(final HttpMessageNotReadableException exception) {

        return Problem.builder()
                .statusCode(HttpStatus.BAD_REQUEST)
                .detail(exception.getMessage().split(":")[0])
                .build();
    }

}
