package com.jorado.qos.textui;

import org.junit.jupiter.api.Test;

import static org.hamcrest.JMock1Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class TLadderTest {
    @Test
    public void testRendering() throws Exception {
        TLadder ladder = new TLadder();
        ladder.addItem("1");
        ladder.addItem("2");
        ladder.addItem("3");
        ladder.addItem("4");
        String result = ladder.rendering();
        String expected = "1" + System.lineSeparator() +
                "  `-2" + System.lineSeparator() +
                "    `-3" + System.lineSeparator() +
                "      `-4" + System.lineSeparator();
        assertThat(result, equalTo(expected));
        System.out.println(result);
    }
}
