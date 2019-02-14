package com.jorado.logger.dubbo.plugin;

import com.alibaba.dubbo.rpc.RpcContext;
import com.jorado.logger.AutoLoad;
import com.jorado.logger.EventContext;
import com.jorado.logger.dubbo.consts.DataTypes;
import com.jorado.logger.dubbo.data.ProviderInfo;
import com.jorado.logger.plugin.Plugin;

@AutoLoad
public class ProviderContextPlugin implements Plugin {

    @Override
    public void run(EventContext context) {

        try {

            RpcContext rpcContext = RpcContext.getContext();

            if (null == rpcContext.getUrl()) return;

            // 本端是否为提供端
            boolean isProviderSide = rpcContext.isProviderSide();

            if (!isProviderSide) return;

            // 获取调用方IP地址
            String clientIP = rpcContext.getRemoteHost();

            // 获取当前服务配置信息，所有配置信息都将转换为URL的参数
            String application = rpcContext.getUrl().getParameter("application");

            ProviderInfo info = new ProviderInfo();
            info.setClientIP(clientIP);
            info.setApplication(application);
            info.setMethodName(rpcContext.getMethodName());
            info.setUrl(rpcContext.getUrl().toFullString());

            context.getEvent().addTags("dubbo").setReferenceId(context.getEvent().getReferenceId());
            context.getEvent().addData(DataTypes.DUBBO_PROVIDER_INFO, info);

        } catch (Exception ex) {
            context.getLogger().error("Run dubbo ProviderContextPlugin plugin error", ex);
        }
    }
}
