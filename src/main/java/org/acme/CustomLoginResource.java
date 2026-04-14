package org.acme;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Path("/custom-login")
public class CustomLoginResource {

    @Inject
    Template customLogin;

    @ConfigProperty(name = "actual.provider.authorization.url")
    String actualProviderAuthUrl;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getCustomLoginPage(@Context UriInfo uriInfo) {
        String queryParams = uriInfo.getRequestUri().getRawQuery();
        String targetUrl = actualProviderAuthUrl + (queryParams != null ? "?" + queryParams : "");
        return customLogin.data("targetUrl", targetUrl);
    }
}
