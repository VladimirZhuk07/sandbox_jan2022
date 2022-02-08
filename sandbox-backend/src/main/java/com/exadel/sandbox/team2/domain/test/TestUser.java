package com.exadel.sandbox.team2.domain.test;

import com.exadel.sandbox.team2.domain.base.AuditableEntity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor


public class TestUser extends AuditableEntity {

    public static final TestUser testUser = getInstance();

    private static TestUser getUser;

    private String username;


    private TestUser(){
        username = "Ron";
    }

    public static TestUser getInstance(){
        if(getUser == null){
            getUser = new TestUser();
        }

        return getUser;
    }

}
