package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.media.bean.User;
import nz.ac.otago.edmedia.spring.controller.BaseOperationController;
import nz.ac.otago.edmedia.spring.service.SearchCriteria;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * UserName search controller, returns matched username list in json.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: Mar 29, 2011
 *         Time: 11:35:51 AM
 */
public class UserNameSearchController extends BaseOperationController {

    @Override
    protected ModelAndView handle(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Object command,
                                  BindException errors) throws Exception {

        String userName = request.getParameter("term");
        SearchCriteria criteria = new SearchCriteria.Builder()
                .like("userName", userName + "%")
                .orderBy("userName")
                .build();
        @SuppressWarnings("unchecked")
        List<User> list = (List<User>) service.search(User.class, criteria);
        StringBuilder answer = new StringBuilder("[");
        for (int i = 0; i < list.size(); i++) {
            User u = list.get(i);
            if (i != 0)
                answer.append(",");
            answer.append("\"");
            answer.append(u.getUserName());
            answer.append(" (");
            answer.append(u.getFirstName());
            answer.append(" ");
            answer.append(u.getLastName());
            answer.append(")");
            answer.append("\"");
        }
        answer.append("]");
        response.setContentType("application/json");
        response.getWriter().println(answer.toString());
        response.getWriter().flush();
        return null;
    }

}

