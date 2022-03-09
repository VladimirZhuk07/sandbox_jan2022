package com.exadel.sandbox.team2.serivce.impl;

import com.exadel.sandbox.team2.dto.UsersPOJO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class ReportServiceImpl {
    @PersistenceContext
    private EntityManager entityManager;

    public List<UsersPOJO> getUsersWhoBooked() {
        Session session = entityManager.unwrap(Session.class);
        String hql = "SELECT u.firstName, u.lastName, b.user " +
                "FROM User as u left join Booking as b";
        List<?> list = session.createQuery(hql).list();
        List<UsersPOJO> users = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            UsersPOJO pojoes = new UsersPOJO();
            Object[] row = (Object[]) list.get(i);
            pojoes.setId((Long) row[0]);
            pojoes.setFirstName((String) row[1]);
            pojoes.setLastName((String) row[2]);
            pojoes.setWorkplaceID((Long) row[3]);
            users.add(pojoes);
        }
        session.close();
        return users;
    }
}
