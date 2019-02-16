package com.jorado.qos.command.decoder;

import com.jorado.qos.command.CommandContext;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TelnetCommandDecoderTest {
    @Test
    public void testDecode() throws Exception {
        CommandContext context = TelnetCommandDecoder.decode("test a b");
        assertThat(context.getCommandName(), equalTo("test"));
        assertThat(context.isHttp(), is(false));
        assertThat(context.getArgs(), arrayContaining("a", "b"));
    }
}
