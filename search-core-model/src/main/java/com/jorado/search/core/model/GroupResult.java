package com.jorado.search.core.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupResult<T> implements Serializable {

    private String name;
    private int matches;
    private int ngroups;
    private Map<String, List<T>> groups;

    public GroupResult() {
        groups = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMatches() {
        return matches;
    }

    public void setMatches(int matches) {
        this.matches = matches;
    }

    public int getNgroups() {
        return ngroups;
    }

    public void setNgroups(int ngroups) {
        this.ngroups = ngroups;
    }

    public Map<String, List<T>> getGroups() {
        return groups;
    }

    public void setGroups(Map<String, List<T>> groups) {
        this.groups = groups;
    }
}
