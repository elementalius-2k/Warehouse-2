package servlets;

import entities.Group;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.DBLoader;
import services.GroupService;

import java.io.IOException;
import java.util.List;

public class GroupsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DBLoader.load();
        req.setAttribute("all", getAllGroups());
        req.getRequestDispatcher("views/groups.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("add") != null) {
            if (addNewGroup(req.getParameter("addName")))
                req.setAttribute("added",true);
            else
                req.setAttribute("added",false);
        }
        if (req.getParameter("find") != null) {
            req.setAttribute("foundGroups", findGroup(req.getParameter("findName")));
        }
        if (req.getParameter("delete") != null && req.getParameter("groupID") != null) {
            if (deleteGroup(Long.parseLong(req.getParameter("groupID"))))
                req.setAttribute("deleted",true);
            else
                req.setAttribute("deleted",false);
        }
        if (req.getParameter("update") != null && req.getParameter("groupID") != null) {
            if(updateGroup(Long.parseLong(req.getParameter("groupID")), req.getParameter("newName")))
                req.setAttribute("updated",true);
            else
                req.setAttribute("updated",false);
        }

        doGet(req, resp);
    }

    private boolean addNewGroup(String name) {
        long id = GroupService.getInstance().insert(new Group(0, name));
        return id != 0;
    }

    private List<Group> findGroup(String name) {
        return GroupService.getInstance().getByName(name);
    }

    private List<Group> getAllGroups() {
        return GroupService.getInstance().getAll();
    }

    private boolean deleteGroup(long id) {
        GroupService service = GroupService.getInstance();
        if (service.numberOfProductsInGroup(id) == 0) {
            service.delete(id);
            return true;
        }
        return false;
    }

    private boolean updateGroup(long id, String name) {
        GroupService service = GroupService.getInstance();
        Group group = service.getByID(id);
        if (!name.equals("")) {
            group.setName(name);
            service.update(group);
            return true;
        }
        return false;
    }
}
