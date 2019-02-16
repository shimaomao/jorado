package com.jorado.qos.command;

public class DefaultCommandExecutor implements CommandExecutor {
    @Override
    public String execute(CommandContext commandContext) throws NoSuchCommandException {
        Command command = null;
        try {
            command = (Command) CommandLoader.getCommandClass(commandContext.getCommandName()).newInstance();
        } catch (Throwable throwable) {
            //can't find command
        }
        if (command == null) {
            throw new NoSuchCommandException(commandContext.getCommandName());
        }
        return command.execute(commandContext, commandContext.getArgs());
    }
}
