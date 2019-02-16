package com.jorado.qos.textui;

import com.jorado.core.util.StringUtils;

import java.util.Scanner;

/**
 * KV
 */
public class TKv implements TComponent {

    private final TTable tTable;

    public TKv() {
        this.tTable = new TTable(new TTable.ColumnDefine[]{
                new TTable.ColumnDefine(TTable.Align.RIGHT),
                new TTable.ColumnDefine(TTable.Align.RIGHT),
                new TTable.ColumnDefine(TTable.Align.LEFT)
        })
                .padding(0);
        this.tTable.getBorder().set(TTable.Border.BORDER_NON);
    }

    public TKv(TTable.ColumnDefine keyColumnDefine, TTable.ColumnDefine valueColumnDefine) {
        this.tTable = new TTable(new TTable.ColumnDefine[]{
                keyColumnDefine,
                new TTable.ColumnDefine(TTable.Align.RIGHT),
                valueColumnDefine
        })
                .padding(0);
        this.tTable.getBorder().set(TTable.Border.BORDER_NON);
    }

    public TKv add(final Object key, final Object value) {
        tTable.addRow(key, " : ", value);
        return this;
    }

    @Override
    public String rendering() {
        return filterEmptyLine(tTable.rendering());
    }

    private String filterEmptyLine(String content) {
        final StringBuilder sb = new StringBuilder();
        try (Scanner scanner = new Scanner(content)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line != null) {
                    // remove extra space at line's end
                    line = StringUtils.stripEnd(line, " ");
                    if (line.isEmpty()) {
                        line = " ";
                    }
                }
                sb.append(line).append(System.lineSeparator());
            }
        }

        return sb.toString();
    }
}
