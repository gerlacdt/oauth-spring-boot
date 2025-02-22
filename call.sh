#!/bin/bash

# Usage

# export TOKEN
# ./call.sh

# or
# TOKEN="value" ./call.sh

set -euo pipefail

curl -v -H "Authorization: Bearer $TOKEN" "http://localhost:9090/hello"
