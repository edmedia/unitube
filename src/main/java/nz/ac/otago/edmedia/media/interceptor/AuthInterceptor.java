package nz.ac.otago.edmedia.media.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Authentication & Authorization Interceptor.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 17/09/2009
 *         Time: 12:25:59
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        return super.preHandle(request, response, handler);
    }
}
