package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.media.bean.User;
import nz.ac.otago.edmedia.spring.controller.BaseOperationController;
import nz.ac.otago.edmedia.spring.service.SearchCriteria;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * User search controller, returns matched username list in json.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 26/06/12
 *         Time: 10:13 AM
 */
public class UserSearchController extends BaseOperationController {

    @Override
    protected ModelAndView handle(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Object command,
                                  BindException errors) throws Exception {

        StringBuilder answer = new StringBuilder("[");
        String term = request.getParameter("term");
        if (StringUtils.isNotBlank(term)) {
            Set<User> users = new HashSet<User>();
            SearchCriteria c1 = new SearchCriteria.Builder()
                    .ilike("userName", term + "%")    // case insensitive
                    .orderBy("userName")
                    .build();
            @SuppressWarnings("unchecked")
            List<User> l1 = (List<User>) service.search(User.class, c1);
            users.addAll(l1);
            SearchCriteria c2 = new SearchCriteria.Builder()
                    .ilike("firstName", term + "%")   // case insensitive
                    .orderBy("userName")
                    .build();
            @SuppressWarnings("unchecked")
            List<User> l2 = (List<User>) service.search(User.class, c2);
            users.addAll(l2);
            SearchCriteria c3 = new SearchCriteria.Builder()
                    .ilike("lastName", term + "%")    // case insensitive
                    .orderBy("userName")
                    .build();
            @SuppressWarnings("unchecked")
            List<User> l3 = (List<User>) service.search(User.class, c3);
            users.addAll(l3);

            List<User> userList = new ArrayList<User>(users);
            for (int i = 0; i < userList.size(); i++) {
                User u = userList.get(i);
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
        }

        answer.append("]");
        response.setContentType("application/json");
        response.getWriter().println(answer.toString());
        response.getWriter().flush();
        return null;
    }

}
