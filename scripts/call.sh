#!/bin/bash

# Usage
# ./call.sh

set -euo pipefail

# get access token
TOKEN=$(curl \
            -d "client_id=myoidc-client" \
            -d "client_secret=Z2OcY7TsYQIsQzGF37OpKb7cmhTsvyJ1" \
            -d "username=foo" \
            -d "password=foo" \
            -d "grant_type=password" \
            "http://localhost:8080/realms/myrealm/protocol/openid-connect/token" | jq -r ".access_token")

# call GET /hello with token set
curl -v -H "Authorization: Bearer $TOKEN" "http://localhost:9090/hello"
