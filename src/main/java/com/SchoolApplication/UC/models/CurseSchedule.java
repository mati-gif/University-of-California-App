package com.SchoolApplication.UC.models;

import jakarta.persistence.*;

@Entity
public class CurseSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dayOfWeek; // Renombrado en el modelo
    private String time;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "curse_id")//Esta seria la clave foranea de la tabla curse
    private Curse curse;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;//Esta seria la clave foranea de la tabla schedule


    public CurseSchedule(String dayOfWeek, String time) {
        this.dayOfWeek = dayOfWeek;
        this.time = time;
    }

    public CurseSchedule() {
    }


    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Curse getCurse() {
        return curse;
    }

    public void setCurse(Curse curse) {
        this.curse = curse;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
}
