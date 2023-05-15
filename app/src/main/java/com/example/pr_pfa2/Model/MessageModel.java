package com.example.pr_pfa2.Model;

import com.google.firebase.Timestamp;

import java.util.Date;

public class MessageModel {


        //private String messageId;
        private String senderId;
        private String targetId;
        private String messageText;
        Timestamp timestamp;




        public MessageModel() {
            // Required empty constructor for Firestore
        }

        public MessageModel(String senderId, String targetId, String messageText, Timestamp timestamp) {
            //this.messageId = messageId;
            this.senderId = senderId;
            this.targetId = targetId;
            this.messageText = messageText;
            this.timestamp = timestamp;
        }

    /*public String getMessageId() {
            return messageId;
        }

     */
    /*
        public void setMessageId(String messageId) {
            this.messageId = messageId;
        }

     */

        public String getSenderId() {
            return senderId;
        }

        public void setSenderId(String senderId) {
            this.senderId = senderId;
        }

        public String getMessageText() {
            return messageText;
        }

        public void setMessageText(String messageText) {
            this.messageText = messageText;
        }

        public Timestamp getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Timestamp timestamp) {
            this.timestamp = timestamp;
        }

        public String getTargetId() {
        return targetId;
        }

        public void setTargetId(String targetId) {
        this.targetId = targetId;
        }


}
