# Project

This is a learning project in order to understand the OAuth2 _Authorization Code
Flow_. The projects consists of a Spring Boot application which protect its
endpoints via JWT token validation. Additionally, the app functions as a OAuth
client that is able to go through the whole OAuth login process. Keycloak is
used as Authorization Server. Some scripts are provided to start-up a
preconfigured server with an oauth client, some users with roles.

# Development

### Development guide

```bash
# start keycloak as container
make keycloak

# start Sprint Boot app
make

# keycloak runs at localhost:8080
# spring boot runs at localhost:9090

# now you login via Browser and get a JWT token
# http://localhost:9090/login
# you will be redirected to Keycloak and after successful authenication you will be redcirected to the app homepage which contains a link to the JWT token

# with the JWT token, you can make http requests
TOKEN="<put your JWT token here>"
curl -v -H "Authorization: Bearer $TOKEN" "http://localhost:9090/hello
curl -v -H "Authorization: Bearer $TOKEN" "http://localhost:9090/greeting

# after work, you can kill the keycloak container
make keycloak-stop
```

### Export data from Keycloak container

Keycloak as container start blank, i.e. no clients, no users no roles. In order
to have to start a preconfigured Keycloak, one has to export the data and use
the JSON files as import during startup. The following describes how to export
"all" data. It's cumbersome and you have to do a _container dance_ since
Keycloak does not offer a complete export at runtime.

Preconfigured users:

- Keycloak user: admin/admin
- oauth user: foo/foo
- oauth user: bar/bar

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
# get a fresh access token and call GET /hello
./scripts/call.sh
```
