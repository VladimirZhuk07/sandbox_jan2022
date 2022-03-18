package com.exadel.sandbox.team2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MailDto {
    private String recipient;
    private String header;
    private String body;
    private String link;

    public void putTextAndLink(String botUsername){
        this.body = String.format(
                """
                        Hello!\s
                        This email was sent to verify your account
                        Please follow the link below to verify it:
                        https://t.me/%s?start=%s""",
                botUsername,
                this.link
        );
    }

    public void putTextForBooking(String country, String city, String officeName,
                                  String address, int floor){
        this.body = String.format(
                """
                        Workplace is successfully booked.
                        Country: %s
                        City: %s
                        Office: %s
                        Address: %s
                        Floor: %s
                        """, country, city, officeName, address, floor
        );
    }
}