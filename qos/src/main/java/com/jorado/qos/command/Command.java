package com.jorado.qos.command;

public interface Command {
    String execute(CommandContext commandContext,String[] args);
}
