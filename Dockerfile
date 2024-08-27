ARG ALPINE_VERSION

FROM alpine:${ALPINE_VERSION}

RUN apk add --update-cache \
  fortune

ENTRYPOINT ["/usr/bin/fortune"]
