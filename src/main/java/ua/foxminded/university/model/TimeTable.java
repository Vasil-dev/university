package ua.foxminded.university.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@ToString
@Entity
@Table(name = "timetable", schema = "cms")
public class TimeTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "start")
    @DateTimeFormat(pattern = "HH:ss")
    private String start;

    @Column(name = "duration")
    @DateTimeFormat(pattern = "HH:ss")
    private String duration;

    public TimeTable(int id, String start, String duration) {
        this.id = id;
        this.start = start;
        this.duration = duration;
    }

    public TimeTable() {
    }

}
