package com.jorado.qos.command;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandContextFactoryTest {
    @Test
    public void testNewInstance() throws Exception {
        CommandContext context = CommandContextFactory.newInstance("test");
        assertThat(context.getCommandName(), equalTo("test"));
        context = CommandContextFactory.newInstance("command", new String[]{"hello"}, true);
        assertThat(context.getCommandName(), equalTo("command"));
        assertThat(context.getArgs(), Matchers.arrayContaining("hello"));
        assertTrue(context.isHttp());
    }
}
