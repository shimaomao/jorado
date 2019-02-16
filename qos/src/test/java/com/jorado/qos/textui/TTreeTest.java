package com.jorado.qos.textui;

import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class TTreeTest {
    @Test
    public void test() throws Exception {
        TTree tree = new TTree(false, "root");
        tree.begin("one").begin("ONE").end().end();
        tree.begin("two").begin("TWO").end().begin("2").end().end();
        tree.begin("three").end();
        String result = tree.rendering();
        String expected = "`---+root\n" +
                "    +---+one\n" +
                "    |   `---ONE\n" +
                "    +---+two\n" +
                "    |   +---TWO\n" +
                "    |   `---2\n" +
                "    `---three\n";
        assertThat(result, equalTo(expected));
        System.out.println(result);
    }
}
