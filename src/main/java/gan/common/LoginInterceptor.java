package gan.common;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import gan.user.userSvc;


public class LoginInterceptor implements HandlerInterceptor {
    static final Logger LOGGER = LoggerFactory.getLogger(userSvc.class);

    /**
     * Controller 실행 요청전.
     * 일반 사용자의 로그인 체크. 
     */
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) {
        HttpSession session = req.getSession();
        
        /*session.setAttribute("userid", "admin");
        session.setAttribute("userrole", "A");        
        session.setAttribute("userno",   "1");        
        session.setAttribute("usernm", "관리자");
       */
        
        try {
            if (session == null || session.getAttribute("userno") == null) {
                res.sendRedirect("userLogin"); 
                return false;
            }
        } catch (IOException ex) {
            LOGGER.error("LoginInterceptor");
        }
        
        return true;
    }
    
    public void postHandle(HttpServletRequest req, HttpServletResponse res, Object handler, ModelAndView mv) {
    }
    
    public void afterCompletion(HttpServletRequest req, HttpServletResponse res, Object handler, Exception ex) {
    }

}
