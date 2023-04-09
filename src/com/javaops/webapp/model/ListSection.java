package com.javaops.webapp.model;

import java.util.List;

public class ListSection extends AbstractSection {
    private List<String> strings;

    public ListSection(List<String> strings) {
        this.strings = strings;
    }
}
