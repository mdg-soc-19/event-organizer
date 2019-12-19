package com.example.eventorganiser;

public class Event {
    private String name_of_grp;
    private String name_of_event;
    private String date_and_time;
    private String venue;
    private String specifications;
    private String prerequisite;

    public Event(String name_of_grp, String name_of_event, String date_and_time, String venue, String specifications, String prerequisite) {
        this.name_of_grp = name_of_grp;
        this.name_of_event = name_of_event;
        this.date_and_time = date_and_time;
        this.venue = venue;
        this.specifications = specifications;
        this.prerequisite = prerequisite;
    }

    public Event() {
    }

    public String getName_of_grp() {
        return name_of_grp;
    }

    public void setName_of_grp(String name_of_grp) {
        this.name_of_grp = name_of_grp;
    }

    public String getName_of_event() {
        return name_of_event;
    }

    public void setName_of_event(String name_of_event) {
        this.name_of_event = name_of_event;
    }

    public String getDate_and_time() {
        return date_and_time;
    }

    public void setDate_and_time(String date_and_time) {
        this.date_and_time = date_and_time;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public String getPrerequisite() {
        return prerequisite;
    }

    public void setPrerequisite(String prerequisite) {
        this.prerequisite = prerequisite;
    }
}
