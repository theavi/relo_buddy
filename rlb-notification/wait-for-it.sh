#!/bin/sh

host=$1
port=$2
shift 2
cmd="$@"

if [ -z "$host" ] || [ -z "$port" ]; then
  echo "Usage: $0 host port -- command"
  exit 1
fi

echo "Waiting for $host:$port..."

while ! nc -z "$host" "$port"; do
  sleep 1
done

echo "$host:$port is available, running command: $cmd"

exec $cmd
