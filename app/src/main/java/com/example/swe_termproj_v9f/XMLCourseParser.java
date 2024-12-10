package com.example.swe_termproj_v9f;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XMLCourseParser {

    public static List<CourseItem> parse(InputStream inputStream) throws Exception {
        List<CourseItem> courses = new ArrayList<>();
        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(inputStream, null);

        String courseId = null, courseName = null;

        int lectures = 0, labs = 0, credits = 0;

        int eventType = parser.getEventType();

        while (eventType != XmlPullParser.END_DOCUMENT) {
            String tagName = parser.getName();
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    if ("courseId".equals(tagName)) {
                        courseId = parser.nextText();
                    } else if ("courseName".equals(tagName)) {
                        courseName = parser.nextText();
                    } else if ("lectures".equals(tagName)) {
                        lectures = Integer.parseInt(parser.nextText());
                    } else if ("labs".equals(tagName)) {
                        labs = Integer.parseInt(parser.nextText());
                    } else if ("credits".equals(tagName)) {
                        credits = Integer.parseInt(parser.nextText());
                    }
                    break;

                case XmlPullParser.END_TAG:
                    if ("course".equals(tagName)) {
                        courses.add(new CourseItem(courseId, courseName, lectures, labs, credits));
                    }
                    break;
            }

            eventType = parser.next();

        }

        return courses;

    }
}
