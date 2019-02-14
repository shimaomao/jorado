package com.jorado.logger.dubbo.plugin;

import com.alibaba.dubbo.rpc.RpcContext;
import com.jorado.logger.AutoLoad;
import com.jorado.logger.EventContext;
import com.jorado.logger.dubbo.consts.DataTypes;
import com.jorado.logger.dubbo.data.ConsumerInfo;
import com.jorado.logger.plugin.Plugin;

@AutoLoad
public class ConsumerContextPlugin implements Plugin {

    @Override
    public void run(EventContext context) {

        try {

            RpcContext rpcContext = RpcContext.getContext();

            if (null == rpcContext.getUrl()) return;

            // 本端是否为消费端
            boolean isConsumerSide = rpcContext.isConsumerSide();

            if (!isConsumerSide) return;

            // 获取最后一次调用的提供方IP地址
            String serverIP = rpcContext.getRemoteHost();

            // 获取当前服务配置信息，所有配置信息都将转换为URL的参数
            String application = rpcContext.getUrl().getParameter("application");

            ConsumerInfo info = new ConsumerInfo();
            info.setServerIP(serverIP);
            info.setApplication(application);
            info.setMethodName(rpcContext.getMethodName());
            info.setUrl(rpcContext.getUrl().toFullString());

            context.getEvent().addTags("dubbo").setReferenceId(context.getEvent().getReferenceId());
            context.getEvent().addData(DataTypes.DUBBO_CONSUMER_INFO, info);

        } catch (Exception ex) {
            context.getLogger().error("Run dubbo ConsumerContextPlugin plugin error", ex);
        }
    }
}
