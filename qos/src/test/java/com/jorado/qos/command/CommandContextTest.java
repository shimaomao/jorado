package com.jorado.qos.command;

import io.netty.channel.Channel;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandContextTest {
    @Test
    public void test() throws Exception {
        CommandContext context = new CommandContext("test", new String[]{"hello"}, true);
        Object request = new Object();
        context.setOriginRequest(request);
        Channel channel = Mockito.mock(Channel.class);
        context.setRemote(channel);
        assertThat(context.getCommandName(), equalTo("test"));
        assertThat(context.getArgs(), arrayContaining("hello"));
        assertThat(context.getOriginRequest(), is(request));
        assertTrue(context.isHttp());
        assertThat(context.getRemote(), is(channel));

        context = new CommandContext("command");
        context.setRemote(channel);
        context.setOriginRequest(request);
        context.setArgs(new String[]{"world"});
        context.setHttp(false);
        assertThat(context.getCommandName(), equalTo("command"));
        assertThat(context.getArgs(), arrayContaining("world"));
        assertThat(context.getOriginRequest(), is(request));
        assertFalse(context.isHttp());
        assertThat(context.getRemote(), is(channel));
    }
}
