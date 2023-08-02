package ua.foxminded.university.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter @Setter
@Entity
@Table(name = "lecture", schema = "cms")
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "lecture_name")
    private String lectureName;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, targetEntity = Group.class)
    @JoinColumn(name = "lecture_group_id")
    private Group group;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, targetEntity = Teacher.class)
    @JoinColumn(name = "lecture_teacher_id")
    private Teacher teacher;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, targetEntity = Student.class)
    @JoinColumn(name = "lecture_student_id")
    private Student student;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, targetEntity = TimeTable.class)
    @JoinColumn(name = "lecture_time_id")
    private TimeTable timeTable;

    public Lecture(int id, String lectureName) {
        this.id = id;
        this.lectureName = lectureName;
    }

    public Lecture() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lecture lecture = (Lecture) o;
        return id == lecture.id && Objects.equals(lectureName, lecture.lectureName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lectureName);
    }

    @Override
    public String toString() {
        return lectureName;
    }

}
