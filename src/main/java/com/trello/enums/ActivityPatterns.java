package com.trello.enums;

public enum ActivityPatterns {

    ColumnAdding("%s добавил(а) эту карточку в список %s"),
    UserJoined("%s присоединился(-лась) к этой карточке");

    private final String pattern;

    ActivityPatterns(String pattern) {
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }
}
