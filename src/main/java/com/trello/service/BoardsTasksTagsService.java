package com.trello.service;

import com.trello.entity.BoardEntity;
import com.trello.entity.BoardsTasksTagsEntity;
import com.trello.entity.TagEntity;
import com.trello.entity.TaskEntity;

import javax.ws.rs.core.Response;

public class BoardsTasksTagsService {

    public static Response addRecord(BoardEntity board, TaskEntity task, TagEntity tag) {
        BoardsTasksTagsEntity btt = BoardsTasksTagsEntity.find("board_id = ?1 and tag_id = ?2", board.id, tag.id).firstResult();
        if (btt == null) {
            BoardsTasksTagsEntity new_btt = new BoardsTasksTagsEntity(board, task, tag);
            BoardsTasksTagsEntity.persist(new_btt);
            if (new_btt.isPersistent()) return Response.ok(tag).build();
        } else if (btt.task == null) {
            btt.task = task;
            return Response.ok(tag).build();
        } else {
            BoardsTasksTagsEntity new_btt = new BoardsTasksTagsEntity(board, task, tag);
            BoardsTasksTagsEntity.persist(new_btt);
            if (new_btt.isPersistent()) return Response.ok(tag).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}
