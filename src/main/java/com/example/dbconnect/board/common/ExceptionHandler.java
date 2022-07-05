package com.example.dbconnect.board.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Slf4j
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ModelAndView defaultExceptionHandler(HttpServletRequest request , Exception exception){
        ModelAndView mv = new ModelAndView("board/error/error_default");
        mv.addObject("exception", exception);

        log.error("exception",exception);

        return mv;
    }


}
