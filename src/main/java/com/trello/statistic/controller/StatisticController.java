package com.trello.statistic.controller;


import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.inject.Inject;
import javax.persistence.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.*;
import java.util.*;


@Path("/stats")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StatisticController {

    @Inject
    SessionFactory sessionFactory;



    @GET
    public Response getStats(@QueryParam("taskId") Long taskId, @QueryParam("userId") Long userId,
                             @QueryParam("boardId") Long boardId, @QueryParam("columnId") Long columnId,
                             @QueryParam("startTime") Long startTime, @QueryParam("endTime") Long endTime) {
        Map<String,Object> statsU = new HashMap<>();
        Map<String,Object> statsT = new HashMap<>();
        Map<String,Object> statsC = new HashMap<>();
        Map<String,Object> statsB = new HashMap<>();


        Session session = sessionFactory.openSession();
        Query query = session.createQuery("select avg(e.duration) from StatisticEntity e join e.task.makers a where e.board.id = ?1 and a.id = ?2 and e.duration<>0");
        query.setParameter(1,boardId);
        query.setParameter(2,userId);
        Double avg = (Double) query.getSingleResult();

        query = session.createQuery("select max(e.duration) from StatisticEntity e join e.task.makers a where e.board.id = ?1 and a.id = ?2");
        query.setParameter(1,boardId);
        query.setParameter(2,userId);
        Long max = (Long) query.getSingleResult();

        query = session.createQuery("select min(e.duration) from StatisticEntity e join e.task.makers a where e.board.id = ?1 and a.id = ?2 and e.duration<>0");
        query.setParameter(1,boardId);
        query.setParameter(2,userId);
        Long min = (Long) query.getSingleResult();


        query = session.createQuery("select count(e.duration) from StatisticEntity e join e.task.makers a where e.board.id = ?1 and a.id = ?2 and e.duration<>0");
        query.setParameter(1,boardId);
        query.setParameter(2,userId);
        Long count= (Long) query.getSingleResult();



        statsU.put("AVG",avg);
        statsU.put("MAX",max);
        statsU.put("MIN",min);
        statsU.put("COUNT",count);

        query = session.createQuery("select avg(e.duration) from StatisticEntity e where e.task.id = ?1 and e.duration<>0");
        query.setParameter(1,taskId);
        avg = (Double) query.getSingleResult();

        query = session.createQuery("select max(e.duration) from StatisticEntity e where e.task.id = ?1");
        query.setParameter(1,taskId);
        max = (Long) query.getSingleResult();

        query = session.createQuery("select min(e.duration) from StatisticEntity e where e.task.id = ?1 and e.duration<>0");
        query.setParameter(1,taskId);
        min = (Long) query.getSingleResult();


        query = session.createQuery("select count(e.duration) from StatisticEntity e where e.task.id = ?1 and e.duration<>0");
        query.setParameter(1,taskId);
        count = (Long) query.getSingleResult();

        query = session.createQuery("select sum(e.duration) from StatisticEntity e where e.task.id = ?1 and e.duration<>0");
        query.setParameter(1,taskId);
        Long sum = (Long) query.getSingleResult();

        query = session.createQuery("select e.column.id ,e.duration from StatisticEntity e where e.task.id = ?1 order by e.date");
        query.setParameter(1,taskId);
        List<List<Long>> rout = (List<List<Long>>) query.getResultList();



        statsT.put("AVG",avg);
        statsT.put("MAX",max);
        statsT.put("MIN",min);
        statsT.put("COUNT",count);
        statsT.put("SUM",sum);
        statsT.put("ROUT", rout);



        query = session.createQuery("select avg(e.duration) from StatisticEntity e where e.column.id = ?1 and e.duration<>0");
        query.setParameter(1,columnId);
        avg = (Double) query.getSingleResult();

        query = session.createQuery("select max(e.duration) from StatisticEntity e where e.column.id = ?1");
        query.setParameter(1,columnId);
        max = (Long) query.getSingleResult();

        query = session.createQuery("select min(e.duration) from StatisticEntity e where e.column.id = ?1 and e.duration<>0");
        query.setParameter(1,columnId);
        min = (Long) query.getSingleResult();


        query = session.createQuery("select count(e.duration) from StatisticEntity e where e.column.id = ?1 and e.duration<>0");
        query.setParameter(1,columnId);
        count = (Long) query.getSingleResult();

        statsC.put("AVG",avg);
        statsC.put("MAX",max);
        statsC.put("MIN",min);
        statsC.put("COUNT",count);



        query = session.createQuery("select avg(e.duration) from StatisticEntity e where e.board.id = ?1 and e.duration<>0");
        query.setParameter(1,boardId);
        avg = (Double) query.getSingleResult();

        query = session.createQuery("select max(e.duration) from StatisticEntity e where e.board.id = ?1");
        query.setParameter(1,boardId);
        max = (Long) query.getSingleResult();

        query = session.createQuery("select min(e.duration) from StatisticEntity e where e.board.id = ?1 and e.duration<>0");
        query.setParameter(1,boardId);
        min = (Long) query.getSingleResult();


        query = session.createQuery("select count(distinct e) from StatisticEntity e where e.board.id = ?1");
        query.setParameter(1,boardId);
        count = (Long) query.getSingleResult();
//select a.id , count(distinct e.task.id) from StatisticEntity e join e.task.makers a where e.board.id = 3 group by a.id, e.task.id





        query = session.createQuery("select a.id , (select count(distinct e1.task.id) from StatisticEntity e1 join e1.task.makers a1 where e1.board.id = ?1 and a1.id = a.id group by a.id) from StatisticEntity e join e.task.makers a where e.board.id = ?1 group by a.id");
        query.setParameter(1,boardId);
        List<List<Long>> userTasks= (List<List<Long>>) query.getResultList();

        query = session.createQuery("select a, count(e) from StatisticEntity e join e.task.makers a where e.board.id = ?1 and e.column.position = (select max(e1.column.position)from StatisticEntity e1 where e1.board.id = ?1) group by a");
        query.setParameter(1,boardId);
        List<List<Long>> userDoneTasks= (List<List<Long>>) query.getResultList();

        query = session.createQuery("select count(e) from StatisticEntity e where e.board.id = ?1 and e.column.position = (select max(e1.column.position)from StatisticEntity e1 where e1.board.id = ?1) and e.date between ?2 and ?3");
        query.setParameter(1,boardId);

        LocalDate date = LocalDate.now();
        query.setParameter(2,date.atStartOfDay().toLocalDate().toEpochSecond(LocalTime.MIN, ZoneOffset.MIN));
        query.setParameter(3,date.atStartOfDay().plusDays(1).toLocalDate().toEpochSecond(LocalTime.MIN, ZoneOffset.MIN));

        List<List<Long>> todayTasks= (List<List<Long>>) query.getResultList();

        query = session.createQuery("select count(e) from StatisticEntity e where e.board.id = ?1 and e.column.position = (select max(e1.column.position)from StatisticEntity e1 where e1.board.id = ?1)");
        query.setParameter(1,boardId);
        List<List<Long>> doneTasks= (List<List<Long>>) query.getResultList();


        query = session.createQuery("select count(e) from StatisticEntity e where e.board.id = ?1 and e.column.position = (select max(e1.column.position)from StatisticEntity e1 where e1.board.id = ?1) and e.date between ?2 and ?3");
        query.setParameter(1,boardId);
        query.setParameter(2, startTime);
        query.setParameter(3, endTime);
        List<List<Long>> doneTasksInTimeInterval= (List<List<Long>>) query.getResultList();

        statsB.put("AVG",avg);
        statsB.put("MAX",max);
        statsB.put("MIN",min);
        statsB.put("COUNT",count);

        statsB.put("USER__TASKS",userTasks);
        statsB.put("USER__DONE_TASKS",userDoneTasks);

        statsB.put("DONE__TASKS",doneTasks);
        statsB.put("TASKS__TODAY",todayTasks);
        statsB.put("TIME_INTEVAL__DONE",doneTasksInTimeInterval);



        Map<String,Map<String, Object>> stats = new HashMap<>();
        stats.put("User",statsU);
        stats.put("Board",statsB);
        stats.put("Column",statsC);
        stats.put("Task",statsT);
        return Response.ok(stats).build();
    }

}
