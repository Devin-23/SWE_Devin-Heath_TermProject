package com.example.swe_termproj_v9f;

public class TimetableEntry {
    private String className;
    private String classTime;
    private String classLocation;

    public TimetableEntry(String className, String classTime, String classLocation) {
        this.className = className;
        this.classTime = classTime;
        this.classLocation = classLocation;
    }

    public String getClassName() {
        return className;
    }

    public String getClassTime() {
        return classTime;
    }

    public String getClassLocation() {
        return classLocation;
    }
}
