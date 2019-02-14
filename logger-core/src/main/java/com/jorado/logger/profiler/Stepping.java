package com.jorado.logger.profiler;

import com.jorado.logger.data.Time;
import com.jorado.logger.util.Stopwatch;
import com.jorado.logger.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 步骤
 */
public class Stepping {

    private final Object syncRoot = new Object();

    private String id;
    private String name;
    private String profilerId;
    private Profiler profiler;
    private Stepping parent;
    private List<Stepping> children;
    private Stopwatch stopwatch;

    public Stepping(Profiler profiler, Stepping parent, String name) {
        this.id = StringUtils.uuid();
        this.name = name;
        this.profiler = profiler;
        this.profiler.setHead(this);
        this.children = new ArrayList<>();
        if (null != parent) {
            parent.addChild(this);
        }
        stopwatch = Stopwatch.begin();
    }

    public void addChild(Stepping stepping) {
        synchronized (syncRoot) {
            this.children.add(stepping);
        }
        stepping.setProfiler(null == stepping.getProfiler() ? this.profiler : stepping.getProfiler());
        stepping.setParent(this);
        if (null != this.profiler)
            stepping.setProfilerId(this.profiler.getId());
    }

    public void removeChild(Stepping stepping) {
        synchronized (this.children) {
            this.children.remove(stepping);
        }
    }

    public boolean hasChildren() {
        return null != children && children.size() > 0;
    }

    public boolean isRoot() {
        return this.equals(this.profiler.getRoot());
    }

    public short depth() {

        short result = 0;
        Stepping parent = this.parent;

        while (parent != null) {
            parent = parent.getParent();
            result++;
        }
        return result;
    }

    public void stop() {
        stopwatch.stop();
        this.profiler.setHead(parent);
    }

    public String getId() {
        return id;
    }

    public Profiler getProfiler() {
        return profiler;
    }

    public void setProfiler(Profiler profiler) {
        this.profiler = profiler;
    }

    public String getName() {
        return name;
    }

    public List<Stepping> getChildren() {
        return children;
    }

    public void setParent(Stepping parent) {
        this.parent = parent;
    }

    public String getProfilerId() {
        return profilerId;
    }

    public void setProfilerId(String profilerId) {
        this.profilerId = profilerId;
    }

    public Stepping getParent() {
        return parent;
    }

    public Time getTime() {
        return new Time(this.stopwatch.getDuration(), this.stopwatch.toString());
    }

    @Override
    public String toString() {
        return name;
    }
}
