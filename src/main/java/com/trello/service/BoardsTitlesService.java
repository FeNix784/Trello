package com.trello.service;

import io.quarkus.hibernate.orm.panache.common.ProjectedFieldName;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public record BoardsTitlesService(Long id, String title) {

    public BoardsTitlesService(@ProjectedFieldName("board.id") Long id, @ProjectedFieldName("board.title") String title) {
        this.id = id;
        this.title = title;
    }
}
