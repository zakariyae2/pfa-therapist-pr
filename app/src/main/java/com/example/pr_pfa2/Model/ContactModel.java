package com.example.pr_pfa2.Model;

import java.util.List;

public class ContactModel {

    /*
    private String chatID;
    private List<String> participantsIDs;


    public ContactModel() {
    }

    public ContactModel(String chatID, List<String> participantsIDs) {
        this.chatID = chatID;
        this.participantsIDs = participantsIDs;
    }

    public String getChatID() {
        return chatID;
    }

    public void setChatID(String chatID) {
        this.chatID = chatID;
    }

    public List<String> getParticipantsIDs() {
        return participantsIDs;
    }

    public void setParticipantsIDs(List<String> participantsIDs) {
        this.participantsIDs = participantsIDs;
    }

     */

    private String user1ID;
    private String User2ID;
    private String lastMessage;

    public ContactModel() {
    }

    public ContactModel(String user1ID, String user2ID, String lastMessage) {
        this.user1ID = user1ID;
        this.User2ID = user2ID;
        this.lastMessage = lastMessage;
    }

    public String getUser1ID() {
        return user1ID;
    }

    public void setUser1ID(String user1ID) {
        this.user1ID = user1ID;
    }

    public String getUser2ID() {
        return User2ID;
    }

    public void setUser2ID(String user2ID) {
        User2ID = user2ID;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}


