package com.example.swe_termproj_v9f;

public class ButtonItem {
    private String buttonName;
    private Class<?> destinationActivity;

    public ButtonItem(String buttonName, Class<?> destinationActivity) {
        this.buttonName = buttonName;
        this.destinationActivity = destinationActivity;
    }

    public String getButtonName() {
        return buttonName;
    }

    public Class<?> getDestinationActivity() {
        return destinationActivity;
    }
}

//was attempting to use the same item for both Buttons and Programs but kept breaking for some reason so ahd to change things

/*
* public class ButtonItem {
    private String name;
    private String description;

    public ButtonItem(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}

* */
