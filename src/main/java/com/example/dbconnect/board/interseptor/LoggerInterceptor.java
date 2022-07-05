package com.example.dbconnect.board.interseptor;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;

@Slf4j
public class LoggerInterceptor implements HandlerInterceptor {
    //컨트롤러 실행 전에
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.debug("==================== START ====================");
        log.debug(" Request URI \t: " + request.getRequestURI());
        return true;
    }
    //컨트롤러 실행 후에 결과를 뷰로 보내기 전에 수행
    @Override
    public void postHandle(HttpServletRequest request , HttpServletResponse response, Object handler , ModelAndView modelAndView) throws Exception{
        log.debug("==================== END ======================");

    }

}
