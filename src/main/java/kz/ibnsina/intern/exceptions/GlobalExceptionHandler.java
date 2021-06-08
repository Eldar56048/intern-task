package kz.ibnsina.intern.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());


    @ExceptionHandler(ResourceNotFoundException.class)
    public ModelAndView handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest request) {
        LOGGER.error(exception.getMessage());
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(false), exception.getCode());
        ModelAndView mv = new ModelAndView();
        mv.addObject("error",errorDetails);
        mv.setStatus(HttpStatus.NOT_FOUND);
        mv.addObject("status", HttpStatus.NOT_FOUND);
        mv.setViewName("error");
        return mv;
    }

    @ExceptionHandler(ConflictException.class)
    public ModelAndView handleConflictException(ConflictException exception, WebRequest request) {
        LOGGER.error(exception.getMessage());
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(false), exception.getCode());
        ModelAndView mv = new ModelAndView();
        mv.addObject("error",errorDetails);
        mv.setStatus(HttpStatus.CONFLICT);
        mv.addObject("status", HttpStatus.CONFLICT);
        mv.setViewName("error");
        return mv;
    }

    @ExceptionHandler(NoAccessException.class)
    public ModelAndView handleNoAccessException(NoAccessException exception, WebRequest request) {
        LOGGER.error(exception.getMessage());
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(false), exception.getCode());
        ModelAndView mv = new ModelAndView();
        mv.addObject("error",errorDetails);
        mv.setStatus(HttpStatus.METHOD_NOT_ALLOWED);
        mv.addObject("status", HttpStatus.METHOD_NOT_ALLOWED);
        mv.setViewName("error");
        return mv;
    }

}
