package entities;

import java.util.Objects;

public class Base {
    protected long id;

    public Base() { }

    public Base(long id) {
        this.id = id;
    }

    public long getId(){
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Base base = (Base) obj;
        return id == base.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
