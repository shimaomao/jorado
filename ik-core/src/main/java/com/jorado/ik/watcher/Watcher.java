package com.jorado.ik.watcher;

import java.util.Properties;

public interface Watcher {

    Properties changed(String conf);
}
