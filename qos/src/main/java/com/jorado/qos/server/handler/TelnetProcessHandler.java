package com.jorado.qos.server.handler;

import com.jorado.core.logger.Logger;
import com.jorado.core.logger.LoggerFactory;
import com.jorado.core.util.StringUtils;
import com.jorado.qos.command.CommandContext;
import com.jorado.qos.command.CommandExecutor;
import com.jorado.qos.command.DefaultCommandExecutor;
import com.jorado.qos.command.NoSuchCommandException;
import com.jorado.qos.command.decoder.TelnetCommandDecoder;
import com.jorado.qos.Constants;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Telnet process handler
 */
public class TelnetProcessHandler extends SimpleChannelInboundHandler<String> {

    private static final Logger log = LoggerFactory.getLogger(TelnetProcessHandler.class);
    private static CommandExecutor commandExecutor = new DefaultCommandExecutor();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        if (StringUtils.isBlank(msg)) {
            ctx.writeAndFlush(QosProcessHandler.prompt);
        } else {
            CommandContext commandContext = TelnetCommandDecoder.decode(msg);
            commandContext.setRemote(ctx.channel());

            try {
                String result = commandExecutor.execute(commandContext);
                if (StringUtils.isEquals(Constants.CLOSE, result)) {
                    ctx.writeAndFlush(getByeLabel()).addListener(ChannelFutureListener.CLOSE);
                } else {
                    ctx.writeAndFlush(result + Constants.BR_STR + QosProcessHandler.prompt);
                }
            } catch (NoSuchCommandException ex) {
                ctx.writeAndFlush(msg + " :no such command");
                ctx.writeAndFlush(Constants.BR_STR + QosProcessHandler.prompt);
                log.error("can not found command " + commandContext, ex);
            } catch (Exception ex) {
                ctx.writeAndFlush(msg + " :fail to execute commandContext by " + ex.getMessage());
                ctx.writeAndFlush(Constants.BR_STR + QosProcessHandler.prompt);
                log.error("execute commandContext got exception " + commandContext, ex);
            }
        }
    }

    private String getByeLabel() {
        return "BYE!\n";
    }

}
