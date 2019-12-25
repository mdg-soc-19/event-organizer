package com.example.eventorganiser;

public class Event {
    private String name_of_grp;
    private String name_of_event;
    private String date;
    private String venue;
    private String specifications;
    private String prerequisite;
    private String time;

    public Event(String name_of_grp, String name_of_event, String date_and_time, String venue, String specifications, String prerequisite,String time) {
        this.name_of_grp = name_of_grp;
        this.name_of_event = name_of_event;
        this.date = date_and_time;
        this.venue = venue;
        this.specifications = specifications;
        this.prerequisite = prerequisite;
        this.time = time;
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

    public String getDate() { return  date; }

    public void setDate(String date) {
        this.date = "On " +date;
    }

    public String getTime(){
        return time;
    }

    public void setTime(String time){
        this.time = "At "+time;
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
        this.specifications = "Specifications : "+specifications;
    }

    public String getPrerequisite() {
        return prerequisite;
    }

    public void setPrerequisite(String prerequisite) {
        this.prerequisite = "Prerequisites : "+prerequisite;
    }
}
