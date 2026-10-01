package org.openapitools.codegen.templating;

import org.openapitools.codegen.api.TemplatingExecutor;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Map;

import static org.testng.Assert.assertEquals;

public class HandlebarsEngineAdapterIntegrationTest_testHandlePartialTemplate {

    @Test(description = "verify https://github.com/jknack/handlebars.java/issues/940#issue-1111612043 is fixed")
    public void testHandlePartialTemplate() throws IOException {
        HandlebarsEngineAdapter adapter = new HandlebarsEngineAdapter();
        TemplatingExecutor executorMock_proxy = new TemplatingExecutor_Proxy(<--TemplatingExecutor_REAL_CONFIGURED_INSTANCE-->);
        executorMock_proxy.getFullTemplateContents("outerTemplate.hbs");
        executorMock_proxy.getFullTemplateContents("innerTemplate.hbs");

        String generatedFile = adapter.compileTemplate(executorMock_proxy, Map.of(), "outerTemplate.hbs");

        assertEquals(generatedFile, "Contents: 'Specific contents'");
    }
}