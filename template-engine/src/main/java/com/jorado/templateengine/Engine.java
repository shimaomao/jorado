package com.jorado.templateengine;

import com.jorado.templateengine.exception.EngineInitException;
import com.jorado.templateengine.exception.ParsingException;
import com.jorado.templateengine.exception.SyntaxNotAllowedException;

import java.util.Map;

public interface Engine {

    /**
     * 引擎初始化
     *
     * @throws EngineInitException
     */
    void init(boolean cacheEnabled) throws EngineInitException;

    /**
     * 标识符验证
     *
     * @throws SyntaxNotAllowedException
     */
    void validate(String templateBody) throws SyntaxNotAllowedException;

    /**
     * 模板字符串解析
     *
     * @param templateBody
     * @param data
     * @return
     * @throws ParsingException
     */
    String parse(String templateBody, Map<String, Object> data) throws ParsingException;
}
