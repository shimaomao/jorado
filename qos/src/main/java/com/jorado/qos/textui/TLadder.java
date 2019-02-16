package com.jorado.qos.textui;

import java.util.ArrayList;
import java.util.List;

import static com.jorado.core.util.StringUtils.repeat;


/**
 * Ladder
 */
public class TLadder implements TComponent {

    // separator
    private static final String LADDER_CHAR = "`-";

    // tab
    private static final String STEP_CHAR = " ";

    // indent length
    private static final int INDENT_STEP = 2;

    private final List<String> items = new ArrayList<String>();


    @Override
    public String rendering() {
        final StringBuilder ladderSB = new StringBuilder();
        int deep = 0;
        for (String item : items) {

            // no separator is required for the first item
            if (deep == 0) {
                ladderSB
                        .append(item)
                        .append(System.lineSeparator());
            }

            // need separator for others
            else {
                ladderSB
                        .append(repeat(STEP_CHAR, deep * INDENT_STEP))
                        .append(LADDER_CHAR)
                        .append(item)
                        .append(System.lineSeparator());
            }

            deep++;

        }
        return ladderSB.toString();
    }

    /**
     * add one item
     */
    public TLadder addItem(String item) {
        items.add(item);
        return this;
    }

}
