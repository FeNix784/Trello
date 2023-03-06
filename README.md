# Трелло
Данный проект представляет собой программную реализацию системы, которая осуществялет гибкое управление проектами путем командного взаимодействия.

Основными гибкими методологиями принято считать Scrum и Kanban.Их целями принято считать получение готового качественного продукта вовремя.

Данный проект визуализирует процессы, чтобы они были у команды на виду. Для этого используют так называемую доску и прикрепленные к ней карточки или стикеры.

Доска является обязательным элементом гибких методологий. Каждый член команды имеет к ней доступ и видит, на каком этапе находится та или иная задача. Она содержит в себе разделы(колонки), в которых находятся карточки задач.
Карточка содержит в себе трактовку, описание, тип, исполнителей, комментарии задачи и движется по потоку перетекая в другие столбцы в зависимости от их состояния.

Главная цель проекта создать систему, которая позволит визуализировать процесс разработки, чтобы каждый участник проекта видел картину целиком и корректировал отдельные ее части, понимая, как его изменения затронут весь проект.

## Authors

- [Бусуек Евгений](https://github.com/EugeneBUSUEK)
- [Гришучков Данила](https://github.com/grishuchkov)
- [Зайцев Егор](https://github.com/Papugaicheg)
- [Назаров Никита](https://github.com/luvlaceeeee)
- [Ткачев Даниил](https://github.com/FeNix784)
# trello

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/trello-1.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.

## Related Guides

- Hibernate ORM ([guide](https://quarkus.io/guides/hibernate-orm)): Define your persistent model with Hibernate ORM and
  JPA
- RESTEasy Reactive ([guide](https://quarkus.io/guides/resteasy-reactive)): A JAX-RS implementation utilizing build time
  processing and Vert.x. This extension is not compatible with the quarkus-resteasy extension, or any of the extensions
  that depend on it.
- YAML Configuration ([guide](https://quarkus.io/guides/config#yaml)): Use YAML to configure your Quarkus application
- JDBC Driver - PostgreSQL ([guide](https://quarkus.io/guides/datasource)): Connect to the PostgreSQL database via JDBC

## Provided Code

### YAML Config

Configure your application with YAML

[Related guide section...](https://quarkus.io/guides/config-reference#configuration-examples)

The Quarkus application configuration is located in `src/main/resources/application.yml`.

### Hibernate ORM

Create your first JPA entity

[Related guide section...](https://quarkus.io/guides/hibernate-orm)

### RESTEasy Reactive

Easily start your Reactive RESTful Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)
