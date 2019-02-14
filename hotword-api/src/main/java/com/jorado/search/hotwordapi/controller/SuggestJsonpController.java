package com.jorado.search.hotwordapi.controller;

import com.jorado.search.hotword.config.RemoteSettings;
import com.jorado.search.hotword.model.enums.HotWordType;
import com.jorado.search.hotword.model.suggest.SuggestResult;
import com.jorado.search.hotword.model.suggest.SuggestWord;
import com.jorado.search.hotword.service.SuggestService;
import com.jorado.logger.util.JsonUtils;
import com.jorado.core.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/*
 * Created by len.zhang on 2018/4/2.
 *
 */
@RestController
@EnableAutoConfiguration
@RequestMapping(value = "/suggest/jsonp", produces = "application/json;charset=UTF-8")
public class SuggestJsonpController {

    @Autowired
    private SuggestService suggestService;

    @RequestMapping("/full")
    String full(@RequestParam("callback") String callback, @RequestParam("kw") String kw, @RequestParam(value = "count", required = false, defaultValue = "10") int count, @RequestParam(value = "debug", required = false, defaultValue = "0") int debug) {

        count = count < 0 ? 10 : count;

        //如果启用了分组,职位与公司各取一半
        if (RemoteSettings.groupEnabled()) {

            //两次查询，各取count条
            Result<SuggestResult> result = suggestService.dataSuggest(HotWordType.POSITION, kw, count, debug > 0);
            Result<SuggestResult> result_company = suggestService.dataSuggest(HotWordType.COMPANY, kw, count, debug > 0);

            result.getData().setFlag(HotWordType.FULL_INDEX);

            for (SuggestWord word : result_company.getData().getWords()) {
                result.getData().getWords().add(word);
            }
            for (String debugInfo : result_company.getDebugInfo()) {
                result.getDebugInfo().add(debugInfo);
            }
            return String.format("%s(%s)", callback, JsonUtils.toJson(result));

        } else {
            Result result = suggestService.dataSuggest(HotWordType.FULL_INDEX, kw, count, debug > 0);
            return String.format("%s(%s)", callback, JsonUtils.toJson(result));
        }
    }


    @RequestMapping("/position")
    String position(@RequestParam("callback") String callback, @RequestParam("kw") String kw, @RequestParam(value = "count", required = false, defaultValue = "10") int count, @RequestParam(value = "debug", required = false, defaultValue = "0") int debug) {
        Result result = suggestService.dataSuggest(HotWordType.POSITION, kw, count, debug > 0);
        return String.format("%s(%s)", callback, JsonUtils.toJson(result));
    }

    @RequestMapping("/school")
    String school(@RequestParam("callback") String callback, @RequestParam("kw") String kw, @RequestParam(value = "count", required = false, defaultValue = "10") int count, @RequestParam(value = "debug", required = false, defaultValue = "0") int debug) {
        Result result = suggestService.dataSuggest(HotWordType.SCHOOL, kw, count, debug > 0);
        return String.format("%s(%s)", callback, JsonUtils.toJson(result));
    }

    @RequestMapping("/company")
    String company(@RequestParam("callback") String callback, @RequestParam("kw") String kw, @RequestParam(value = "count", required = false, defaultValue = "10") int count, @RequestParam(value = "debug", required = false, defaultValue = "0") int debug) {
        Result result = suggestService.dataSuggest(HotWordType.COMPANY, kw, count, debug > 0);
        return String.format("%s(%s)", callback, JsonUtils.toJson(result));
    }

    @RequestMapping("/city")
    String city(@RequestParam("callback") String callback, @RequestParam("kw") String kw, @RequestParam(value = "count", required = false, defaultValue = "10") int count, @RequestParam(value = "debug", required = false, defaultValue = "0") int debug) {
        Result result = suggestService.dataSuggest(HotWordType.CITY, kw, count, debug > 0);
        return String.format("%s(%s)", callback, JsonUtils.toJson(result));
    }
}