package org.openapitools.codegen.templating;

import java.nio.file.Path;
import org.openapitools.codegen.api.TemplatingExecutor;

public class TemplatingExecutor_EmptyProxy implements TemplatingExecutor {

    protected final TemplatingExecutor dependency;

    public TemplatingExecutor_EmptyProxy(TemplatingExecutor dependency) {
        this.dependency = dependency;
    }

    @Override
    public String getFullTemplateContents(String arg0) {
        return dependency.getFullTemplateContents(arg0);
    }

    @Override
    public Path getFullTemplatePath(String arg0) {
        return dependency.getFullTemplatePath(arg0);
    }

}
