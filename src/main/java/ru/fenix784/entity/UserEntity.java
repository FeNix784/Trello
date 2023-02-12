package ru.fenix784.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import ru.fenix784.entity.BoardEntity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class UserEntity extends PanacheEntity {

    public String name;

    @OneToMany
    public List<BoardEntity> boards;
}
