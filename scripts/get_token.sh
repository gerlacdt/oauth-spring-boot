#!/bin/bash

set -euo pipefail

TOKEN=$(curl \
    -d "client_id=myoidc-client" \
    -d "client_secret=Z2OcY7TsYQIsQzGF37OpKb7cmhTsvyJ1" \
    -d "username=foo" \
    -d "password=foo" \
    -d "grant_type=password" \
    "http://localhost:8080/realms/myrealm/protocol/openid-connect/token" | jq ".access_token")

echo $TOKEN
