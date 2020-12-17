package entities;

public class Group extends Base {
    private String name;

    public Group() { }

    public Group(long id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) { this.name = name; }

    @Override
    public String toString() {
        return "Group: " + name + ", (id: " + id + ").";
    }
}
