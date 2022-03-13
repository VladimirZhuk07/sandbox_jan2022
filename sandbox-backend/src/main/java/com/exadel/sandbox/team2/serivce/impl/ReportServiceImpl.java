package com.exadel.sandbox.team2.serivce.impl;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Component
@AllArgsConstructor
@NoArgsConstructor
public class ReportServiceImpl {
    @PersistenceContext
    private EntityManager entityManager;

    //TODO Убрать класс если всё получится
   /* public List<UsersPOJO> getUsersWhoBooked() {
        Session session = entityManager.unwrap(Session.class);
        String hql = "SELECT u.id, u.firstName, u.lastName, b.startDate, b.endDate, b.workplace.id" +
                " FROM User u left join Booking as b ON u.id = b.user.id";
        List<?> list = session.createQuery(hql).list();
        List<UsersPOJO> users = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            UsersPOJO pojoes = new UsersPOJO();
            Object[] row = (Object[]) list.get(i);
            pojoes.setId((Long) row[0]);
            pojoes.setFirstName((String) row[1]);
            pojoes.setLastName((String) row[2]);
          *//*  pojoes.setTimeStart((LocalDate) row[3]);
            pojoes.setTimeEnd((LocalDate) row[4]);
            pojoes.setWorkplaceID((String) row[5]);*//*
            users.add(pojoes);
        }
        session.close();
        return users;
    }*/
}
