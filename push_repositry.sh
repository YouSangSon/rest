#!/bin/bash

file_path="buildConfig.json"
local_repository=$(jq -r '.base_path' "$file_path")

echo "Choice to delete rest-server tag in Local Docker Repository (tag name / n)"
curl $local_repository/v2/rest/tags/list
read -p "Enter version to deleteTag[tag name / n]:"
if [ -n "$REPLY" ]; then deleteTag="${REPLY}"; fi

case $deleteTag in
n | No | N)
    echo "Skip Delete tag in Local Docker Repository..."
    ;;
*)
    echo "Delete tag in Local Docker Repository..."
    DIGEST=$(curl -v --silent -H "Accept: application/vnd.docker.distribution.manifest.v2+json" -X GET "$local_repository/v2/lotto-rest/manifests/$deleteTag" 2>&1 | grep Docker-Content-Digest | awk '{print $3}' | tr -d '\n' | tr -d '\r')
    curl -X DELETE "$local_repository/v2/lotto-rest/manifests/$DIGEST"
    ;;
esac

read -p "Please enter the image tag:"
if [ -n "$REPLY" ]; then tag="${REPLY}"; fi

echo "Pushing to Local Docker Repository..."
./gradlew jib -PjibTag=$tag