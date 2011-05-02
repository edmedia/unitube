package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.listener.BaseServletListener;
import nz.ac.otago.edmedia.media.bean.User;
import nz.ac.otago.edmedia.spring.controller.BaseOperationController;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Monitor current login users.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 13/03/2008
 *         Time: 14:02:29
 */
public class MonitorController extends BaseOperationController {

    @SuppressWarnings("unchecked")
    protected ModelAndView handle(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Object command,
                                  BindException errors) throws Exception {

        Map model = errors.getModel();

        ServletContext ctx = getServletContext();
        long accessNum = (Long) ctx.getAttribute(BaseServletListener.CURRENT_ACCESS_USER_NUM_KEY);
        Map map = (Map) ctx.getAttribute(BaseServletListener.LOGIN_USER_MAP_KEY);
        Collection users = null;
        if (map != null)
            users = map.values();
        List list = service.list(getCommandClass());
        List notLoginUsers = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            User user = (User) list.get(i);
            if (users != null) {
                for (Iterator it = users.iterator(); it.hasNext();) {
                    User loginUser = (User) it.next();
                    if (!loginUser.getId().equals(user.getId())) {
                        notLoginUsers.add(user);
                        break;
                    }
                }
            } else {
                notLoginUsers.add(user);
            }
        }

        model.put("accessNum", accessNum);
        model.put("loginUsers", users);
        model.put("notLoginUsers", notLoginUsers);

        return getModelAndView(model, request);
    }
}
