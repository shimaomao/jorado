package com.jorado.qos.command;

import io.netty.channel.Channel;

public class CommandContext {

    private String commandName;
    private String[] args;
    private Channel remote;
    private boolean isHttp;
    private Object originRequest;

    public CommandContext(String commandName) {
        this.commandName = commandName;
    }

    public CommandContext(String commandName, String[] args, boolean isHttp) {
        this.commandName = commandName;
        this.args = args;
        this.isHttp = isHttp;
    }

    public String getCommandName() {
        return commandName;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public Channel getRemote() {
        return remote;
    }

    public void setRemote(Channel remote) {
        this.remote = remote;
    }

    public boolean isHttp() {
        return isHttp;
    }

    public void setHttp(boolean http) {
        isHttp = http;
    }

    public Object getOriginRequest() {
        return originRequest;
    }

    public void setOriginRequest(Object originRequest) {
        this.originRequest = originRequest;
    }
}
