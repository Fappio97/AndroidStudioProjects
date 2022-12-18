package com.unito.prenotazioniandroid.tabelle;

import java.util.ArrayList;
import java.util.Objects;

public class FormSelection {
    ArrayList<String> checkedDays;
    ArrayList<Integer> checkedHours;
    Integer selectedProf;//TODO atomic integer
    String selectedCourse;

    public FormSelection(ArrayList<String> checkedDays, ArrayList<Integer> checkedHours, Integer selectedProf, String selectedCourse) {
        setCheckedDays(checkedDays);
        setCheckedHours(checkedHours);
        setSelectedProf(selectedProf);
        setSelectedCourse(selectedCourse);
    }

    public ArrayList<String> getCheckedDays() {
        return checkedDays;
    }

    public void setCheckedDays(ArrayList<String> checkedDays) {
        this.checkedDays = checkedDays;
    }

    public ArrayList<Integer> getCheckedHours() {
        return checkedHours;
    }

    public void setCheckedHours(ArrayList<Integer> checkedHours) {
        this.checkedHours = checkedHours;
    }

    public Integer getSelectedProf() {
        return selectedProf;
    }

    public void setSelectedProf(Integer selectedProf) {
        this.selectedProf = selectedProf;
    }

    public String getSelectedCourse() {
        return selectedCourse;
    }

    public void setSelectedCourse(String selectedCourse) {
        this.selectedCourse = selectedCourse;
    }

    public FormSelection() {
        this(new ArrayList<String>(), new ArrayList<Integer>(), null, null);
    }

    @Override
    public String toString() {
        return "FormSelection{" +
                "checkedDays=" + checkedDays +
                ", checkedHours=" + checkedHours +
                ", selectedProf=" + selectedProf +
                ", selectedCourse='" + selectedCourse + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FormSelection that = (FormSelection) o;
        return Objects.equals(checkedDays, that.checkedDays) &&
                Objects.equals(checkedHours, that.checkedHours) &&
                Objects.equals(selectedProf, that.selectedProf) &&
                Objects.equals(selectedCourse, that.selectedCourse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(checkedDays, checkedHours, selectedProf, selectedCourse);
    }
}