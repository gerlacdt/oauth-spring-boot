# Export data from keycloak container

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
