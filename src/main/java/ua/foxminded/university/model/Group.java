package ua.foxminded.university.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Getter @Setter @ToString
@Entity
@Table(name = "groups", schema = "cms")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "groupName")
    private String name;

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy = "group")
    private List<Student> studentList;

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy = "group")
    private List<Lecture> lectures;


    public Group() {

    }
    public Group(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return id == group.id && Objects.equals(name, group.name) && Objects.equals(studentList, group.studentList) && Objects.equals(lectures, group.lectures);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, studentList, lectures);
    }
}
