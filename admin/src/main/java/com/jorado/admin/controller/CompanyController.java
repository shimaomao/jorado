package com.jorado.admin.controller;

import com.jorado.admin.config.RemoteSettings;
import com.jorado.admin.model.DictType;
import com.jorado.admin.service.LexiconService;
import com.jorado.ik.util.StringUtils;
import com.jorado.core.Result;
import com.jorado.core.ResultList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*
 * Created by len.zhang on 2018/4/17.
 *
 */
@RestController
@EnableAutoConfiguration
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    LexiconService lexiconService;

    @RequestMapping(value = "/add/{word}", method = RequestMethod.GET)
    public Result add(@PathVariable("word") String word) {
        return lexiconService.add(DictType.COMPANY.getValue(), word);
    }

    @RequestMapping(value = "/delete/{word}", method = RequestMethod.GET)
    public Result delete(@PathVariable("word") String word) {
        return lexiconService.delete(DictType.COMPANY.getValue(), word);
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public Result count() {
        return lexiconService.count(DictType.COMPANY.getValue());
    }

    @RequestMapping(value = "/data", method = RequestMethod.GET, produces = "text/plain")
    public String data() {
        ResultList<String> result = lexiconService.getAll(DictType.COMPANY.getValue());
        List<String> datas = result.getData();
        return StringUtils.joinString(datas, "\r\n");
    }

    @RequestMapping(value = "/last", method = RequestMethod.GET)
    public Result last() {
        return lexiconService.getLastVersion(DictType.COMPANY.getValue());
    }

    @RequestMapping(value = "/publish", method = RequestMethod.GET)
    public Result publish() {
        return lexiconService.publish(DictType.COMPANY.getValue());
    }

    @RequestMapping(value = "/publish/{version}", method = RequestMethod.GET)
    public Result publishWithVersion(@PathVariable("version") int version) {
        return lexiconService.publishWithVersion(DictType.COMPANY.getValue(), version);
    }

    @RequestMapping(value = "/clear", method = RequestMethod.GET)
    public Result clear() {
        return lexiconService.clear(DictType.COMPANY.getValue());
    }

    @RequestMapping(value = "/clearversion", method = RequestMethod.GET)
    public Result clearVersion() {
        return lexiconService.clearVersion(DictType.COMPANY.getValue());
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = "text/plain")
    public String index() {
        int version = Integer.MIN_VALUE;
        Result<Integer> result = lexiconService.getLastVersion(DictType.COMPANY.getValue());
        if (result.isOk()) {
            version = result.getData();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("lastupdate=" + version);
        sb.append("\r\n");
        sb.append("files=" + RemoteSettings.host() + "/company/data");
        return sb.toString();
    }
}