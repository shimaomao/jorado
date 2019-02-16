package com.jorado.qos.textui;

import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class TTableTest {
    @Test
    public void test1() throws Exception {
        TTable table = new TTable(4);
        table.addRow(1, "one", "uno", "un");
        table.addRow(2, "two", "dos", "deux");
        String result = table.rendering();
        String expected = "+-+---+---+----+" + System.lineSeparator() +
                "|1|one|uno|un  |" + System.lineSeparator() +
                "+-+---+---+----+" + System.lineSeparator() +
                "|2|two|dos|deux|" + System.lineSeparator() +
                "+-+---+---+----+" + System.lineSeparator();
        assertThat(result, equalTo(expected));
        System.out.println(result);
    }

    @Test
    public void test2() throws Exception {
        TTable table = new TTable(new TTable.ColumnDefine[]{
                new TTable.ColumnDefine(5, true, TTable.Align.LEFT),
                new TTable.ColumnDefine(10, false, TTable.Align.MIDDLE),
                new TTable.ColumnDefine(10, false, TTable.Align.RIGHT)
        });
        table.addRow(1, "abcde", "ABCDE");
        String result = table.rendering();
        String expected = "+-+----------+----------+" + System.lineSeparator() +
                "|1|   abcde  |     ABCDE|" + System.lineSeparator() +
                "+-+----------+----------+" + System.lineSeparator();
        assertThat(result, equalTo(expected));
        System.out.println(result);
    }
}
