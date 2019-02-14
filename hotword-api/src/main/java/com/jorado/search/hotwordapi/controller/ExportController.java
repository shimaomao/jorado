package com.jorado.search.hotwordapi.controller;

import com.jorado.search.core.service.ExportService;
import com.jorado.search.hotword.service.exportimpl.BaseWordExporter;
import com.jorado.search.hotword.service.exportimpl.ExportHotWordImpl;
import com.jorado.logger.util.JsonUtils;
import com.jorado.core.Result;
import com.jorado.core.ResultStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by len.zhang on 2018/4/17.
 *
 */

@RestController
@EnableAutoConfiguration
@RequestMapping(value = "/export", produces = "application/json;charset=UTF-8")
public class ExportController {

    @Autowired
    @Qualifier("positionWordExporter")
    private BaseWordExporter positionWordExporter;

    @Autowired
    @Qualifier("schoolWordExporter")
    private BaseWordExporter schoolWordExporter;

    @Autowired
    @Qualifier("companyWordExporter")
    private BaseWordExporter companyWordExporter;

    @Autowired
    @Qualifier("cityWordExporter")
    private BaseWordExporter cityWordExporter;

    @RequestMapping("/all")
    Result all() {

        List<Result> resultList = new ArrayList<>();

        resultList.add(position());

        resultList.add(company());

        resultList.add(city());

        resultList.add(school());

        return new Result(ResultStatus.OK, JsonUtils.toJson(resultList));
    }

    @RequestMapping("/position")
    Result position() {
        ExportService exportService = new ExportHotWordImpl(positionWordExporter);
        Result result = exportService.dataExport();
        return result;
    }

    @RequestMapping("/school")
    Result school() {
        ExportService exportService = new ExportHotWordImpl(schoolWordExporter);
        Result result = exportService.dataExport();
        return result;
    }

    @RequestMapping("/company")
    Result company() {
        ExportService exportService = new ExportHotWordImpl(companyWordExporter);
        Result result = exportService.dataExport();
        return result;
    }

    @RequestMapping("/city")
    Result city() {
        ExportService exportService = new ExportHotWordImpl(cityWordExporter);
        Result result = exportService.dataExport();
        return result;
    }
}