package com.jorado.qos.command.impl;

import com.jorado.qos.command.Command;
import com.jorado.qos.command.CommandContext;
import com.jorado.qos.command.annotation.Cmd;
import com.jorado.qos.Constants;

@Cmd(name = "quit",summary = "quit telnet console")
public class Quit implements Command {
    @Override
    public String execute(CommandContext commandContext, String[] args) {
        return Constants.CLOSE;
    }
}
