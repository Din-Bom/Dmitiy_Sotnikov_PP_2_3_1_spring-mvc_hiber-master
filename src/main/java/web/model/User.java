package web.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private final String name;

    public User(String name) {
        this.name = name;
    }

    protected User() {
        this.name = null;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
