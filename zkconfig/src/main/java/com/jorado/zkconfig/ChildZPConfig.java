package com.jorado.zkconfig;

import java.util.ArrayList;
import java.util.List;


public abstract class ChildZPConfig<T> extends ZPConfig {

    @Override
    public void adjust() {
        List<String> children = ConfigFactory.getWatcher().getChildren(path, false);
        if (children.size() <= 0) {
            return;
        }
        List<T> childrenNodes = new ArrayList<>();
        for (String child : children) {
            childrenNodes.add(parse(child));
        }
        updateNode(childrenNodes);
    }

    public abstract T parse(String data);

    public abstract void updateNode(List<T> childrenNodes);
}
