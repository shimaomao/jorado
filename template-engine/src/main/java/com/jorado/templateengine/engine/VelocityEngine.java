package com.jorado.templateengine.engine;

import com.jorado.templateengine.Engine;
import com.jorado.templateengine.exception.EngineInitException;
import com.jorado.templateengine.exception.ParsingException;
import com.jorado.templateengine.exception.SyntaxNotAllowedException;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.StringWriter;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class VelocityEngine implements Engine {

    private static Set<String> notAllowedSyntax;

    public VelocityEngine() {

        notAllowedSyntax = new HashSet<>();
        notAllowedSyntax.add("#macro");
        notAllowedSyntax.add("#include");
        notAllowedSyntax.add("#parse");
    }

    @Override
    public void init(boolean cacheEnabled) throws EngineInitException {
        try {
            if (cacheEnabled) {
                Velocity.setProperty("velocity.resource.loader.cache", true);
            }
            Velocity.init();


        } catch (Exception ex) {

            throw new EngineInitException(ex);
        }
    }

    @Override
    public void validate(String templateBody) throws SyntaxNotAllowedException {
        for (String syntax : notAllowedSyntax) {
            if (templateBody.contains(syntax)) {
                throw new SyntaxNotAllowedException(syntax);
            }
        }
    }

    @Override
    public String parse(String templateBody, Map<String, Object> data) throws ParsingException {
        try {

            VelocityContext context = new VelocityContext();
            for (Map.Entry<String, Object> entry : data.entrySet()) {
                context.put(entry.getKey(), entry.getValue());
            }

            StringWriter sw = new StringWriter();
            Velocity.evaluate(context, sw, "", templateBody);
            String output = sw.toString();
            return output;

        } catch (Exception ex) {

            throw new ParsingException(ex);
        }
    }
}
