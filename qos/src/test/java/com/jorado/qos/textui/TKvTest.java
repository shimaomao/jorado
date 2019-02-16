package com.jorado.qos.textui;

import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class TKvTest {
    @Test
    public void test1() {
        TKv tKv = new TKv(new TTable.ColumnDefine(TTable.Align.RIGHT), new TTable.ColumnDefine(10, false, TTable.Align.LEFT));
        tKv.add("KEY-1", "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        tKv.add("KEY-2", "1234567890");
        tKv.add("KEY-3", "1234567890");

        TTable tTable = new TTable(new TTable.ColumnDefine[]{
                new TTable.ColumnDefine(),
                new TTable.ColumnDefine(20, false, TTable.Align.LEFT)
        });

        String kv = tKv.rendering();
        assertThat(kv, containsString("ABCDEFGHIJ" + System.lineSeparator()));
        assertThat(kv, containsString("KLMNOPQRST" + System.lineSeparator()));
        assertThat(kv, containsString("UVWXYZ" + System.lineSeparator()));

        tTable.addRow("OPTIONS", kv);
        String table = tTable.rendering();
        assertThat(table, containsString("|OPTIONS|"));
        assertThat(table, containsString("|KEY-3"));

        System.out.println(table);
    }

    @Test
    public void test2() throws Exception {
        TKv tKv = new TKv();
        tKv.add("KEY-1", "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        tKv.add("KEY-2", "1234567890");
        tKv.add("KEY-3", "1234567890");
        String kv = tKv.rendering();
        assertThat(kv, containsString("ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
        System.out.println(kv);
    }
}
