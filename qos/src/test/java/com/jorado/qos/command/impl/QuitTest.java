package com.jorado.qos.command.impl;

import com.jorado.qos.command.CommandContext;
import com.jorado.qos.Constants;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class QuitTest {
    @Test
    public void testExecute() throws Exception {
        Quit quit = new Quit();
        String output = quit.execute(Mockito.mock(CommandContext.class), null);
        assertThat(output, equalTo(Constants.CLOSE));
    }
}
