package com.jorado.qos.command;

import com.jorado.core.util.ArrayUtils;
import com.jorado.qos.command.annotation.Cmd;


@Cmd(name = "greeting", summary = "greeting message", example = {"greeting dubbo",})
public class GreetingCommand implements Command {
    @Override
    public String execute(CommandContext commandContext, String[] args) {
        return ArrayUtils.isNotEmpty(args) ? "greeting " + args[0] : "greeting";
    }
}
