package ua.foxminded.university.model;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @ToString
@Entity
@Table(name = "timetable", schema = "cms")
public class TimeTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "start")
    private String start;

    @Column(name = "duration")
    private String duration;

    public TimeTable(int id, String start, String duration) {
        this.id = id;
        this.start = start;
        this.duration = duration;
    }

    public TimeTable() {
    }

}
