# Quarkus Custom OIDC Login POC

This POC demonstrates how to use a local, branded custom login page with Quarkus OIDC.

## How it works

The goal is to provide a seamless branded login experience while still leveraging the security of the actual OIDC provider (like Keycloak).

1.  **Authorization Path Override**: In `application.properties`, we've set `quarkus.oidc.authorization-path` to our local `/custom-login` endpoint.
2.  **Disabled Proactive Auth**: To ensure Quarkus-OIDC doesn't intercept the initial request to `/custom-login` (which will contain the OIDC `state` parameter), we've set `quarkus.http.auth.proactive=false`.
3.  **Local Redirection**: The `CustomLoginResource` captures all query parameters and appends them to the actual OIDC provider's authorization URL, which is then injected into our Qute template.
4.  **Handoff**: The user clicks the button on our custom page and is redirected to the actual OIDC provider to authenticate.
5.  **Callback**: After a successful login, the provider redirects the user back to the app's callback URL, where Quarkus OIDC verifies the token and completes the authentication.

## Prerequisites

-   A running OIDC provider (e.g., Keycloak)
-   Configured `actual.provider.authorization.url` in `application.properties`

## Running the POC

```bash
mvn quarkus:dev
```

Try accessing `http://localhost:8080/hello` to trigger the OIDC flow.
