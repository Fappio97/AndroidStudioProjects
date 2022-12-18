package com.unito.prenotazioniandroid.tabelle;

import java.util.ArrayList;
import java.util.List;

public class ServerFormSelections {
    private String[] days;
    private ArrayList<Integer> hours;
    private List<Docente> professors;
    private List<Corso> courses;

    public ServerFormSelections() {
        setDays(null);
        setProfessors(null);
        setHours(null);
        setProfessors(null);
    }

    public String[] getDays() {
        return days;
    }

    public void setDays(String[] days) {
        this.days = days;
    }

    public ArrayList<Integer> getHours() {
        return hours;
    }

    public void setHours(ArrayList<Integer> hours) {
        this.hours = hours;
    }

    public List<Docente> getProfessors() {
        return professors;
    }

    public void setProfessors(List<Docente> professors) {
        this.professors = professors;
    }

    public List<Corso> getCourses() {
        return courses;
    }

    public void setCourses(List<Corso> courses) {
        this.courses = courses;
    }
}
