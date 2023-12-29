FROM quay.io/keycloak/keycloak:latest as builder

COPY ./Configuration/conf/*.conf /opt/keycloak/conf
COPY ./Configuration/providers/custom-event-listener.jar /opt/keycloak/providers

RUN /opt/keycloak/bin/kc.sh build

FROM quay.io/keycloak/keycloak:latest

COPY --from=builder /opt/keycloak/ /opt/keycloak/

RUN mkdir -p /opt/keycloak/certs

COPY ./Configuration/certs/* /opt/keycloak/certs

ENTRYPOINT ["/opt/keycloak/bin/kc.sh"]

