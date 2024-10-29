package com.SchoolApplication.UC.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Shift shift;
    @ElementCollection
// se utiliza para crear listas de  objetos integrables.Un objeto integrable son datos destinados a usarse únicamente en el objeto que lo contiene.//Esto se hace asi porque trabajamos con datos simples y Spring se encarga de crear esta entidad y configurar la relación uno a muchos automáticamente.
    @Column(name = "days")
    private List<String> days = new ArrayList<>();
    @ElementCollection
    // se utiliza para crear listas de  objetos integrables.Un objeto integrable son datos destinados a usarse únicamente en el objeto que lo contiene.//Esto se hace asi porque trabajamos con datos simples y Spring se encarga de crear esta entidad y configurar la relación uno a muchos automáticamente.
    @Column(name = "times")
    private List<String> times = new ArrayList<>();

    @OneToMany(mappedBy = "schedule", fetch = FetchType.EAGER)
    Set<CurseSchedule> curseSchedules = new HashSet<>();

    public Schedule(Shift shift, List<String> days, List<String> times) {
        this.shift = shift;
        this.days = days;
        this.times = times;
    }


    public Schedule() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

    public List<String> getDays() {
        return days;
    }

    public void setDays(List<String> days) {
        this.days = days;
    }

    public List<String> getTimes() {
        return times;
    }

    public void setTimes(List<String> times) {
        this.times = times;
    }

    public Set<CurseSchedule> getCurseSchedules() {
        return curseSchedules;
    }

    public void setCurseSchedules(Set<CurseSchedule> curseSchedules) {
        this.curseSchedules = curseSchedules;
    }

    public void addCurseSchedule(CurseSchedule curseSchedule){
        curseSchedule.setSchedule(this);
        curseSchedules.add(curseSchedule);
    }

    public List<Curse> getCurses(){
        return curseSchedules.stream().map(c -> c.getCurse()).collect( Collectors.toList());
    }

}
