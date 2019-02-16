package com.jorado.qos.command.impl;

import com.jorado.qos.command.Command;
import com.jorado.qos.command.CommandContext;
import com.jorado.qos.command.annotation.Cmd;
import com.jorado.qos.command.CommandLoader;
import com.jorado.qos.textui.TTable;

import java.util.Collections;
import java.util.List;

@Cmd(name = "help", summary = "help command", example = {
        "help",
        "help online"
})
public class Help implements Command {
    @Override
    public String execute(CommandContext commandContext, String[] args) {
        if (args != null && args.length > 0) {
            return commandHelp(args[0]);
        } else {
            return mainHelp();
        }

    }


    private String commandHelp(String commandName) {

        if (!CommandLoader.hasCommand(commandName)) {
            return "no such command:" + commandName;
        }

        Class<?> clazz = CommandLoader.getCommandClass(commandName);

        final Cmd cmd = clazz.getAnnotation(Cmd.class);
        final TTable tTable = new TTable(new TTable.ColumnDefine[]{
                new TTable.ColumnDefine(TTable.Align.RIGHT),
                new TTable.ColumnDefine(80, false, TTable.Align.LEFT)
        });

        tTable.addRow("COMMAND NAME", commandName);

        if (null != cmd.example()) {
            tTable.addRow("EXAMPLE", drawExample(cmd));
        }

        return tTable.padding(1).rendering();
    }

    private String drawExample(Cmd cmd) {
        final StringBuilder drawExampleStringBuilder = new StringBuilder();
        for (String example : cmd.example()) {
            drawExampleStringBuilder.append(example).append("\n");
        }
        return drawExampleStringBuilder.toString();
    }

    /*
     * output main help
     */
    private String mainHelp() {

        final TTable tTable = new TTable(new TTable.ColumnDefine[]{
                new TTable.ColumnDefine(TTable.Align.RIGHT),
                new TTable.ColumnDefine(80, false, TTable.Align.LEFT)
        });

        final List<Class<?>> classes = CommandLoader.getAllCommandClass();

        Collections.sort(classes, (o1, o2) -> {
            final Integer o1s = o1.getAnnotation(Cmd.class).sort();
            final Integer o2s = o2.getAnnotation(Cmd.class).sort();
            return o1s.compareTo(o2s);
        });
        for (Class<?> clazz : classes) {

            if (clazz.isAnnotationPresent(Cmd.class)) {
                final Cmd cmd = clazz.getAnnotation(Cmd.class);
                tTable.addRow(cmd.name(), cmd.summary());
            }

        }

        return tTable.padding(1).rendering();
    }
}
