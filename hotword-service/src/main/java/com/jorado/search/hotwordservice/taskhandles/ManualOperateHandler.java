package com.jorado.search.hotwordservice.taskhandles;



import com.jorado.search.core.task.BaseHandler;
import com.jorado.search.hotword.config.RemoteSettings;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Service;

/**
 * @author len.zhang
 */
@Service
@Description("手动操作监听")
public final class ManualOperateHandler extends BaseHandler {

    @Override
    protected void exec(Object... args) {

        if (RemoteSettings.manualUpdatePositionMatchRows()) {
            new MatchRowsUpdateHandler().handle(1);
        }
        if (RemoteSettings.manualUpdateCompanyMatchRows()) {
            new MatchRowsUpdateHandler().handle(2);
        }
        if (RemoteSettings.manualSyncOnlineCompany()) {
            new CompanyNameSyncHandler().handle();
        }
    }
}
