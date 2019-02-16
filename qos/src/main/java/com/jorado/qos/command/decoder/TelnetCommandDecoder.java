package com.jorado.qos.command.decoder;

import com.jorado.core.util.StringUtils;
import com.jorado.qos.command.CommandContext;
import com.jorado.qos.command.CommandContextFactory;


public class TelnetCommandDecoder {
    public static final CommandContext decode(String str) {
        CommandContext commandContext = null;
        if (!StringUtils.isBlank(str)) {
            String[] array = str.split("(?<![\\\\]) ");
            if (array.length > 0) {
                String name = array[0];
                String[] targetArgs = new String[array.length - 1];
                System.arraycopy(array, 1, targetArgs, 0, array.length - 1);
                commandContext = CommandContextFactory.newInstance( name, targetArgs,false);
                commandContext.setOriginRequest(str);
            }
        }

        return commandContext;
    }

}
