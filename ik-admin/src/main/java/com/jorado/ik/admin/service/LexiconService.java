package com.jorado.ik.admin.service;

import com.jorado.ik.admin.repository.LexiconRepository;
import com.jorado.core.Result;
import com.jorado.core.ResultList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * Created by len.zhang on 2018/4/17.
 *
 */
@Service
public class LexiconService {

    @Autowired
    LexiconRepository lexiconRepository;

    public Result add(int type, String word) {
        if (lexiconRepository.exist(type, word) > 0) {
            return Result.ofFail("要添加的词已经存在");
        }
        lexiconRepository.add(type, word);
        return Result.ofSuccess("添加成功");
    }

    public Result delete(int type, String word) {
        lexiconRepository.delete(type, word);
        return Result.ofSuccess("删除成功");
    }

    public Result count(int type) {
        long count = lexiconRepository.count(type);
        return Result.ofSuccess(count);
    }

    public ResultList getAll(int type) {
        List<String> datas = lexiconRepository.getAll(type);
        return new ResultList<>(datas);
    }

    public Result getLastVersion(int type) {
        int version = lexiconRepository.getLastVersion(type);
        return Result.ofSuccess(version);
    }

    public Result publish(int type) {
        int version = lexiconRepository.getLastVersion(type);
        version += 1;
        int rows = lexiconRepository.publish(type, version);
        return Result.ofSuccess(rows);
    }

    public Result publishWithVersion(int type, int version) {
        int rows = lexiconRepository.publish(type, version);
        return Result.ofSuccess(rows);
    }

    public Result clear(int type) {
        int rows = lexiconRepository.clear(type);
        return Result.ofSuccess(rows);
    }

    public Result clearVersion(int type) {
        int rows = lexiconRepository.clearVersion(type);
        return Result.ofSuccess(rows);
    }
}