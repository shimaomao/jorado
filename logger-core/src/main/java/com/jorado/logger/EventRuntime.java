package com.jorado.logger;

import com.jorado.logger.profiler.Profiler;
import com.jorado.logger.profiler.ProfilerOptions;

public class EventRuntime {

    private static final ThreadLocal<EventRuntime> LOCAL = ThreadLocal.withInitial(() -> new EventRuntime());

    private Profiler profiler;
    private EventClient eventClient;

    private EventRuntime() {
    }

    public static EventRuntime getContext() {
        return LOCAL.get();
    }

    public static void removeContext() {
        LOCAL.remove();
    }

    public Profiler startNewProfiler() {

        Profiler profiler = new Profiler("TimeProfiler", new ProfilerOptions());

        this.profiler = profiler;

        return profiler;
    }

    public EventClient startNewClient() {

        EventClient client = new EventClient();

        this.eventClient = client;

        return client;
    }

    public Profiler getProfiler() {
        if (null == profiler) {
            this.startNewProfiler();
        }
        return profiler;
    }

    public EventClient getEventClient() {
        if (null == eventClient) {
            this.startNewClient();
        }
        return eventClient;
    }
}
