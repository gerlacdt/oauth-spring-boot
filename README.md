# Development Instructions

### Export data from keycloak container

```bash
docker ps
b0279392d83c   quay.io/keycloak/keycloak:19.0.3   "/opt/keycloak/bin/k…"

docker stop b0279392d83c
docker commit b0279392d83c debug/keycloak
docker run -it --rm --entrypoint sh debug/keycloak

# in the container
/opt/keycloak/bin/kc.sh export --users realm_file --dir /tmp --realm myrealm

# don't stop container, use docker cp to copy the files on the local machine
docker cp <container_id>:/tmp/myrealm-realm.json .
```

### Get direct token

**Prerequisites**:

- Keycloak is started
- Spring-Boot app is running

Getting a token directly from Keycloak is only possible because **Direct Access
Grant** is enabled for our client. It should not be done in production, it's
only for convenient development purpose.

Get a token:

```bash
curl \
  -d "client_id=myoidc-client" \
  -d "client_secret=Z2OcY7TsYQIsQzGF37OpKb7cmhTsvyJ1" \
  -d "username=foo" \
  -d "password=foo" \
  -d "grant_type=password" \
  "http://localhost:8080/realms/myrealm/protocol/openid-connect/token"

# or use:
./scripts/get_token.sh
```

### Make a http call

With the above in place, an authenticated call can be done via:

```bash
./scripts/call.sh
```
