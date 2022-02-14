package com.exadel.sandbox.team2.notification.gmail;

public class SendEmail {
    public static void main(String[] args) {
        SendEmailTLS sendEmailTLS = new SendEmailTLS();
        sendEmailTLS.setRecipientMail("divergenny@gmail.com");
        sendEmailTLS.start();
    }
}
