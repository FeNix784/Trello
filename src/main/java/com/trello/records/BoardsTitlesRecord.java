package com.trello.records;

import io.quarkus.hibernate.orm.panache.common.ProjectedFieldName;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public record BoardsTitlesRecord(Long id, String title) {

    public BoardsTitlesRecord(@ProjectedFieldName("board.id") Long id, @ProjectedFieldName("board.title") String title) {
        this.id = id;
        this.title = title;
    }
}
