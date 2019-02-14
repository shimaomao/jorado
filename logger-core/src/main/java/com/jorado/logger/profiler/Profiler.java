package com.jorado.logger.profiler;

import com.jorado.logger.EventRuntime;
import com.jorado.logger.data.Time;
import com.jorado.logger.util.Stopwatch;
import com.jorado.logger.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.Vector;

/**
 * 分析器
 */
public class Profiler {

    private static final ThreadLocal<Stepping> HEAD = ThreadLocal.withInitial(() -> null);

    private String id;
    private String name;
    private ProfilerOptions options;
    private Stepping root;
    private Stopwatch stopwatch;
    private Stepping lastSetHead;

    public Profiler(String name, ProfilerOptions options) {
        this.id = StringUtils.uuid();
        this.name = name;
        this.options = options;
        this.stopwatch = Stopwatch.begin();
        this.root = new Stepping(this, null, name);
    }

    public static Profiler current() {

        Profiler profiler = EventRuntime.getContext().getProfiler();

        if (null != profiler) return profiler;

        return startNew();
    }

    public static Profiler startNew() {

        return EventRuntime.getContext().startNewProfiler();
    }

    public Stepping step(String name) {
        return new Stepping(this, getHead(), name);
    }

    public Vector<Stepping> getSteppingHierarchy() {

        Stack<Stepping> results = new Stack<>();

        Stack<Stepping> steppings = new Stack<>();

        steppings.push(root);

        while (!steppings.empty()) {

            Stepping stepping = steppings.pop();

            results.push(stepping);

            if (stepping.hasChildren()) {
                List<Stepping> childrens = stepping.getChildren();
                for (int i = childrens.size() - 1; i >= 0; i--) {
                    steppings.push(childrens.get(i));
                }
            }
        }

        return results;
    }

    public void stop() {

        stopwatch.stop();

        for (Stepping stepping : getSteppingHierarchy()) {
            stepping.stop();
        }
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Stepping getRoot() {
        return root;
    }

    public Stepping getHead() {
        Stepping stepping = HEAD.get();
        if (null != stepping)
            return stepping;
        return lastSetHead;
    }

    public void setHead(Stepping head) {
        HEAD.set(head);
        this.lastSetHead = head;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Stepping stepping : getSteppingHierarchy()) {
            int depth = stepping.depth();
            Time time = stepping.getTime();
            String split = String.join("", Collections.nCopies(depth * 5, "-"));
            sb.append(String.format("|--%s step：%s\n", split, stepping.getName()));
            sb.append(String.format("|--%s duration：%s ms\n", split, time.getDuration()));
            sb.append(String.format("|--%s description：%s\n", split, time.getDescription()));
            sb.append(String.format("|--%s ↓\n", split, time.getDescription()));
        }
        return sb.toString();
    }
}
