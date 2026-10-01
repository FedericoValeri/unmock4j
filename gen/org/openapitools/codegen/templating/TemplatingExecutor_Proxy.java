package org.openapitools.codegen.templating;

import org.mockito.Mockito;
import org.openapitools.codegen.api.TemplatingExecutor;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Map;

import static org.testng.Assert.assertEquals;

public class TemplatingExecutor_Proxy extends TemplatingExecutor_EmptyProxy {

    public TemplatingExecutor_Proxy(TemplatingExecutor dependency) {
        super(dependency);
    }

    @Override
    public String getFullTemplateContents(String name) {
        String result = dependency.getFullTemplateContents(name);
        if ("outerTemplate.hbs".equals(name)) {
            assertEquals("Contents: {{>innerTemplate}}", result);
        }
        if ("innerTemplate.hbs".equals(name)) {
            assertEquals("'Specific contents'", result);
        }
        return result;
    }
}