package io.github.ivanbabura.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "people")
public class Person implements Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name= "name")
    private String name;

    public Person() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + "'}";
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        try {
            return id.equals(((Person) obj).getId());
        } catch (NullPointerException e){
            return false;
        }
    }

    @Override
    public Person clone() {
        try {
            Person clone = (Person) super.clone();
            clone.setId(this.getId());
            clone.setName(this.getName());
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
