package services;

import dao.DAO;
import dao.GroupDAO;
import entities.Group;

import java.util.List;

public class GroupService implements DAO<Group> {
    private static final GroupService instance = new GroupService();

    private final GroupDAO dao;

    public static GroupService getInstance() {
        return instance;
    }

    private GroupService() {
        dao = new GroupDAO();
    }

    public void createTable() {
        dao.createTable();
    }

    public long insert(Group group) {
        return dao.insert(group);
    }

    public void delete(long id) {
        dao.delete(id);
    }

    public void update(Group group) {
        dao.update(group);
    }

    public Group getByID(long id) {
        return dao.getByID(id);
    }

    public List<Group> getAll() {
        return dao.getAll();
    }

    public List<Group> getByName(String name) {
        return dao.getByName(name);
    }

    public int numberOfProductsInGroup(long id) {
        return dao.numberOfProductsInGroup(id);
    }
}
