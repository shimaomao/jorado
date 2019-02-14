package com.jorado.logger.dubbo.filter;

import com.alibaba.dubbo.rpc.*;
import com.jorado.logger.util.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class LogFilter implements Filter {

    private static final String REFERENCE_ID = "ReferenceId";

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {

        String uuid = StringUtils.uuid();

        String referenceId = RpcContext.getContext().getAttachment(REFERENCE_ID);

        if (StringUtils.isNullOrWhiteSpace(referenceId)) {
            referenceId = uuid;
            RpcContext.getContext().setAttachment(REFERENCE_ID, referenceId);
        }

        Result result = invoker.invoke(invocation);

        RpcContext.getContext().setAttachment(REFERENCE_ID, referenceId);

        return result;
    }
}
