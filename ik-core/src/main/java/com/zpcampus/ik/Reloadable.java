package com.jorado.ik;

import org.apache.lucene.analysis.util.ResourceLoader;
import org.apache.lucene.analysis.util.ResourceLoaderAware;

public interface Reloadable extends ResourceLoaderAware {

    default String getBeanName() {
        return this.getClass().getName();
    }

    String getResourceName();

    default String getRemoteResourceName() {
        return "";
    }

    void update(ResourceLoader loader, String paths);

    default void updateRemote(String paths) {
    }

    default void inform(final ResourceLoader loader) {
        ReloaderRegister.register(this, loader);
    }
}
