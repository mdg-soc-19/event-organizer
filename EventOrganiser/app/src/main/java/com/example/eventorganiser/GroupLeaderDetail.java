package com.example.eventorganiser;

public class GroupLeaderDetail {
    private String username;
    private String groupName;
    private String emailId;

    public GroupLeaderDetail(String username, String groupName, String emailId) {
        this.username = username;
        this.groupName = groupName;
        this.emailId = emailId;
    }

    public GroupLeaderDetail() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
}
