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

-   Docker installed (to run Keycloak)
-   Java 17+
-   Maven

### Running Keycloak

If you don't have an OIDC provider, you can start a Keycloak container:

```bash
docker run --name keycloak -p 8180:8080 \
  -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin \
  quay.io/keycloak/keycloak:24.0.0 start-dev
```

1. Access Keycloak at `http://localhost:8180`.
2. Create a realm named `myrealm`.
3. Create a client named `myclient`.
4. Set "Client authentication" to `On`.
5. Add `http://localhost:8080/*` to "Valid redirect URIs".
6. In the "Credentials" tab, copy the "Client secret" and update `src/main/resources/application.properties`.

## Running the POC

```bash
mvn quarkus:dev
```

Try accessing `http://localhost:8080/hello` to trigger the OIDC flow.
