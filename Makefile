.PHONY: run
run:
	mvn spring-boot:run

.PHONY: keycloak
keycloak:
	docker run -d -p 8080:8080 --name keycloak -v ./keycloak:/opt/keycloak/data/import -e KC_BOOTSTRAP_ADMIN_USERNAME=admin -e KC_BOOTSTRAP_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:26.1.2 start-dev --import-realm

.PHONY: keycloak-stop
keycloak-stop:
	docker kill keycloak
