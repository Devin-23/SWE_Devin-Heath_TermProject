package com.example.swe_termproj_v9f;

public class CourseItem {
    private String courseId;
    private String courseName;
    private int lectures;
    private int labs;
    private int credits;

    public CourseItem(String courseId, String courseName, int lectures, int labs, int credits) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.lectures = lectures;
        this.labs = labs;
        this.credits = credits;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getLectures() {
        return lectures;
    }

    public int getLabs() {
        return labs;
    }

    public int getCredits() {
        return credits;
    }
}
