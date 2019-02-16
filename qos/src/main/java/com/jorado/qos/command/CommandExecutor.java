package com.jorado.qos.command;

public interface CommandExecutor {
    /**
     * Execute one command and return the execution result
     *
     * @param commandContext command context
     * @return command execution result
     * @throws NoSuchCommandException
     */
    String execute(CommandContext commandContext) throws NoSuchCommandException;
}
