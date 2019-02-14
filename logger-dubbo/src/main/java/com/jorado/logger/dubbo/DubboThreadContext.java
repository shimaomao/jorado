package com.jorado.logger.dubbo;

import com.alibaba.dubbo.rpc.RpcContext;
import com.jorado.logger.concurrent.threadcontext.DefaultThreadContext;
import com.jorado.logger.concurrent.threadcontext.ThreadContext;
import com.jorado.logger.util.StringUtils;

public class DubboThreadContext implements ThreadContext {

    private ThreadContext innerThreadContext;

    public DubboThreadContext() {
        innerThreadContext = new DefaultThreadContext();
    }

    @Override
    public void setData(String key, String value) {
        if (StringUtils.isNotNullOrWhiteSpace(value)) {
            RpcContext.getContext().setAttachment(key, value);
            // innerThreadContext.setData(key, value);
        }
    }

    @Override
    public void removeData(String key) {
        RpcContext.getContext().removeAttachment(key);
        //innerThreadContext.removeData(key);
    }

    @Override
    public void clearData() {
        RpcContext.getContext().clearAttachments();
        // innerThreadContext.clearData();
    }

    @Override
    public String getData(String key) {
        String data = RpcContext.getContext().getAttachment(key);
        if (StringUtils.isNotNullOrWhiteSpace(data)) return data;
        return innerThreadContext.getData(key);
    }
}
