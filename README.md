# transaction-report

This project is about an application to check for financial transactions.

It contains the following parts.

* REST API
* PostgreSQL
* Kafka
* Zookepper

All of them are configured in helm charts to allow for a kubernetes deployment.

Prerequisites
===

The application requires of:

* Java 11
* Maven > 3.1
* Docker (when using containerization)
* kubectl and helm (when using containerization)

Build
===

The application can be built as follows:

```
mvn clean install
```

We can also build without tests:

```
mvn clean install -DskipTests
```
Test
===

The tests can be run with:

```
mvn test
```

Docker
===

We can build an image with:

```
docker build -t com.jmgits/transaction-report .
```

Then run it later with:

```
docker run -e xxx -p 8080:8080 com.jmgits/transaction-report
```

where xxx represents pairs of environment variable name and value.

For example to run with "dev" env variables:

```
docker run -e spring.profiles.active=dev -p 8080:8080 com.jmgits/transaction-report
```

The application's API would be exposed under http://localhost:8080/transaction-report

Kubernetes
===

The application can be deployed in a kubernetes cluster by means of helm.

Before that we need to push an image to the container registry.

That's usually done in a pipeline (eg GitLab) but it can also be pushed manually to a local container registry:

```
docker tag com.jmgits/transaction-report localhost:5000/com.jmgits/transaction-report:1.0.0-SNAPSHOT
docker push localhost:5000/com.jmgits/transaction-report:1.0.0-SNAPSHOT
```

Then we can use helm to install a new release:

```
helm upgrade --install transaction-report helm -f helm/values.yaml
```
If we're using minikube we can get the application URL executing this:

```
minikube service transaction-report --url
```

The application's API would be exposed under urlValue/transaction-report/
