package com.jorado.qos.command;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class DefaultCommandExecutorTest {
    @Test
    public void testExecute1() throws Exception {
        Assertions.assertThrows(NoSuchCommandException.class, () -> {
            DefaultCommandExecutor executor = new DefaultCommandExecutor();
            executor.execute(CommandContextFactory.newInstance("not-exit"));
        });
    }

    @Test
    public void testExecute2() throws Exception {
        DefaultCommandExecutor executor = new DefaultCommandExecutor();
        String result = executor.execute(CommandContextFactory.newInstance("greeting", new String[]{"dubbo"}, false));
        assertThat(result, equalTo("greeting dubbo"));
    }
}
