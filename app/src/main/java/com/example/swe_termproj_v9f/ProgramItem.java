package com.example.swe_termproj_v9f;

public class ProgramItem {
    private String programName;
    private String programDescription;
    private String url;

    public ProgramItem(String name, String description, String url) {
        this.programName = name;
        this.programDescription = description;
        this.url = url;
    }

    public String getProgramName() {
        return programName;
    }

    public String getProgramDescription() {
        return programDescription;
    }

    public String getUrl() {
        return url;
    }
}
