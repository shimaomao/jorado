package com.jorado.templateengine;

import com.jorado.logger.EventBuilder;
import com.jorado.logger.EventClient;
import com.jorado.logger.util.IOUtils;
import com.jorado.templateengine.engine.VelocityEngine;
import com.jorado.templateengine.exception.EngineInitException;
import com.jorado.templateengine.exception.ParsingException;
import com.jorado.templateengine.exception.SyntaxNotAllowedException;

import java.util.Map;

/**
 * 模板引擎代理
 */
public class EngineProxy {

    private Engine engine;
    private boolean engineInited;
    private boolean validateEnabled;
    private boolean cacheEnabled;

    public EngineProxy() {
        this.engine = new VelocityEngine();
    }

    public EngineProxy(Engine engine) {
        this.engine = engine;
    }

    /**
     * 解析字符串模板
     *
     * @param templateBody
     * @param data
     * @return
     */
    public String parse(String templateBody, Map<String, Object> data) {

        EventBuilder eventBuilder = EventClient.getDefault().createEvent();
        eventBuilder.addTags("template-engine");

        String result = "";

        try {

            if (!this.isEngineInited()) {
                this.engine.init(this.isCacheEnabled());
                this.engineInited = true;
            }

            if (this.isValidateEnabled()) {
                this.engine.validate(templateBody);
            }

            result = this.engine.parse(templateBody, data);

        } catch (EngineInitException ex) {
            eventBuilder.setError().setException(ex);
        } catch (SyntaxNotAllowedException ex) {
            eventBuilder.setError().addData("template-not-allowed-syntax", ex.getNotAllowedSyntax()).setException(ex);
        } catch (ParsingException ex) {
            eventBuilder.setError().setException(ex);
        } finally {
            if (eventBuilder.getTarget().isError()) {
                eventBuilder.addData("template-engine", this.engine.getClass().getSimpleName())
                        .addData("template-body", templateBody)
                        .addData("template-data", data).asyncSubmit();
            }
        }
        return result;
    }

    /**
     * 解析资源文件
     *
     * @param name
     * @param data
     * @return
     */
    public String parseResource(String name, Map<String, Object> data) {
        String templateBody = IOUtils.readResource(name);
        return parse(templateBody, data);
    }

    public Engine getEngine() {
        return engine;
    }

    public EngineProxy setEngine(Engine engine) {
        this.engine = engine;
        return this;
    }

    public boolean isValidateEnabled() {
        return validateEnabled;
    }

    public EngineProxy setValidateEnabled(boolean validateEnabled) {
        this.validateEnabled = validateEnabled;
        return this;
    }

    public boolean isEngineInited() {
        return engineInited;
    }

    public void setEngineInited(boolean engineInited) {
        this.engineInited = engineInited;
    }

    public boolean isCacheEnabled() {
        return cacheEnabled;
    }

    public EngineProxy setCacheEnabled(boolean cacheEnabled) {
        this.cacheEnabled = cacheEnabled;
        return this;
    }
}
