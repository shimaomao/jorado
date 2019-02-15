package com.jorado.search.core;

import com.jorado.search.core.config.BlackConfig;
import com.jorado.search.core.config.Client;
import com.jorado.search.core.config.ClientConfig;
import com.jorado.search.core.consts.ErrorConsts;
import com.jorado.search.core.model.searchinfo.SearchInfo;
import com.jorado.logger.util.StringUtils;
import com.jorado.core.Result;
import com.jorado.core.ResultStatus;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author len.zhang
 * 客户端过滤
 */
public final class ClientFilter {

    private SearchInfo searchInfo;

    public ClientFilter(SearchInfo searchInfo) {
        this.searchInfo = searchInfo;
    }

    /**
     * 验证，过滤，校正
     *
     * @return
     */
    public Result filter() {

        try {

            // 验证是否传递了client参数
            if (StringUtils.isNullOrWhiteSpace(searchInfo.getClient())) {
                return new Result(ResultStatus.FORBIDDEN, "No found client paramter");
            }

            Client client = ClientConfig.getInstance().getClient(searchInfo.getClient());

            // 验证客户端是否授权
            if (null == client || !client.isEnabled()) {
                return new Result(ResultStatus.UNAUTHORIZED, "Client not authorized");
            }
            //是否开启了客户端验证
            if (ClientConfig.getInstance().isVerifyClient()) {

                // 验证是否传递了clientip
                if (StringUtils.isNullOrWhiteSpace(searchInfo.getClientIP())) {
                    return new Result(ResultStatus.FORBIDDEN, "No found client ip paramter");
                }
                // 识别百度爬虫
                if (isBaiduSpider()) {
                    searchInfo.getPayload().put("baiduspider", 1);
                }
                // 验证是否黑名单ip
                if (isBlackIP()) {
                    return new Result(ResultStatus.FORBIDDEN, String.format("Ip [%s] has been forbidden", searchInfo.getClientIP()));
                }
                // 验证是否输入了屏蔽词
                if (!StringUtils.isNullOrWhiteSpace(searchInfo.getKeyword()) && BlackConfig.getInstance().getGenderWords().contains(searchInfo.getKeyword())) {
                    return new Result(ResultStatus.FORBIDDEN, String.format("Keyword [%s] has been forbidden", searchInfo.getKeyword()));
                }
            }

            // 校准关键字
            if (!StringUtils.isNullOrWhiteSpace(searchInfo.getKeyword())) {
                searchInfo.setKeyword(StringUtils.escapeSolrString(searchInfo.getKeyword().trim().toLowerCase()));
            }

            // 校准start
            int start = searchInfo.getStart() < 0 ? 0 : searchInfo.getStart();
            //校准返回记录最小值
            int rows = searchInfo.getRows() < 0 ? 10 : searchInfo.getRows();

            // 搜索引擎越往后查越慢，避免恶意查找
            int maxStart = client.getMaxStart() < 1 ? ClientConfig.getInstance().getMaxStart() : client.getMaxStart();

            if (start > maxStart) {
                start = ThreadLocalRandom.current().nextInt(maxStart);
            }

            // 校准返回记录最大值，避免查询10000条之类的影响系统正常使用(每个client可以自定义配置)
            int maxRows = client.getMaxRows() < 1 ? ClientConfig.getInstance().getMaxRows() : client.getMaxRows();

            if (rows > maxRows) {
                rows = maxRows;
            }

            // 设置显示字段
            String field = searchInfo.getField();

            if (StringUtils.isNullOrWhiteSpace(field)) {
                field = StringUtils.isNullOrWhiteSpace(client.getField()) ? ClientConfig.getInstance().getField() : client.getField();
            }

            searchInfo.setStart(start);
            searchInfo.setRows(rows);
            searchInfo.setField(field);

            return Result.OK;

        } catch (Exception ex) {

            return new Result(ResultStatus.ERROR, ErrorConsts.Search.CLIENT_FILTER);

        }
    }

    /**
     * 是否是黑名单ip
     *
     * @return
     */
    public boolean isBlackIP() {

        if (StringUtils.isNullOrWhiteSpace(searchInfo.getClientIP())) {
            return false;
        }

        if (BlackConfig.getInstance().getBlackIPs().contains(searchInfo.getClientIP())) {
            return true;
        }

        for (String ip : BlackConfig.getInstance().getBlackIPScope()) {
            if (searchInfo.getClientIP().startsWith(ip)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否是白名单ip
     *
     * @return
     */
    public boolean isWhiteIP() {

        if (StringUtils.isNullOrWhiteSpace(searchInfo.getClientIP())) {
            return false;
        }

        if (BlackConfig.getInstance().getWhiteIPs().contains(searchInfo.getClientIP())) {
            return true;
        }

        for (String ip : BlackConfig.getInstance().getWhiteIPScope()) {
            if (searchInfo.getClientIP().startsWith(ip)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否是百度爬虫
     *
     * @return
     */
    public boolean isBaiduSpider() {

        if (StringUtils.isNullOrWhiteSpace(searchInfo.getClientIP())) {
            return false;
        }

        for (String ip : BlackConfig.getInstance().getSpiderScope()) {
            if (searchInfo.getClientIP().startsWith(ip)) {
                return true;
            }
        }
        return false;
    }
}
