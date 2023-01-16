[![Apache-2.0 license](http://img.shields.io/badge/license-Apache-brightgreen.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)
[![Build Status](https://travis-ci.org/hmrc/income-tax-subscription-eligibility-frontend.svg)](https://travis-ci.org/hmrc/income-tax-subscription-eligibility-frontend)
[![Download](https://api.bintray.com/packages/hmrc/releases/income-tax-subscription-eligibility-frontend/images/download.svg)](https://bintray.com/hmrc/releases/income-tax-subscription-eligibility-frontend/_latestVersion)


# Income tax subscription eligibility frontend

This is a Scala/Play frontend web UI providing a service for determining whether income tax customers are eligible
to submit their Self Assessment returns using software.

1. [Quick start](#Quick-start)
    - [Prerequisites](#Prerequisites)
    - [How to start](#How-to-start)
    - [How to use](#How-to-use)
    - [How to test](#How-to-test)
2. [Persistence](#Persistence)

# Quick start

## Prerequisites

* [sbt](http://www.scala-sbt.org/)
* MongoDB (*[See Persistence](#Persistence)*)
* HMRC Service manager (*[Install Service-Manager](https://github.com/hmrc/service-manager/wiki/Install#install-service-manager)*)

## How to start

**Run the service with `ITSA_SUBSC_ALL`:**  
```
./scripts/start
```

**Run the service with mininal downstreams:**  
```
./scripts/start --minimal
```

## How to use

### Local

* Login via: [http://localhost:9949/auth-login-stub/gg-sign-in](http://localhost:9949/auth-login-stub/gg-sign-in)
* Entry page: [http://localhost:9589/report-quarterly/income-and-expenses/sign-up/eligibility](http://localhost:9589/report-quarterly/income-and-expenses/sign-up/eligibility)
* Feature switches: [http://localhost:9589/report-quarterly/income-and-expenses/sign-up/eligibility/test-only/feature-switch](http://localhost:9589/report-quarterly/income-and-expenses/sign-up/eligibility/test-only/feature-switch)

### Staging

*Requires HMRC VPN*

* Login via: [https://www.staging.tax.service.gov.uk/auth-login-stub/gg-sign-in](https://www.staging.tax.service.gov.uk/auth-login-stub/gg-sign-in)
* Entry page : [https://www.staging.tax.service.gov.uk/report-quarterly/income-and-expenses/sign-up/eligibility](https://www.staging.tax.service.gov.uk/report-quarterly/income-and-expenses/sign-up/eligibility)
* Feature switches: [https://www.staging.tax.service.gov.uk/report-quarterly/income-and-expenses/sign-up/eligibility/test-only/feature-switch](https://www.staging.tax.service.gov.uk/report-quarterly/income-and-expenses/sign-up/eligibility/test-only/feature-switch)

## How to test

* Run unit tests: `sbt clean test`
* Run integration tests: `sbt clean it:test`
* Run performance tests: provided in the repo [income-tax-subscription-performance-tests](https://github.com/hmrc/income-tax-subscription-performance-tests)
* Run acceptance tests: provided in the repo [income-tax-subscription-acceptance-tests](https://github.com/hmrc/income-tax-subscription-acceptance-tests)

### License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html")

# Persistence

Data is stored as key/value in Mongo DB. See json reads/writes implementations (especially tests) for details.

To connect to the mongo db provided by docker (recommended) please use

```
docker exec -it mongo-db mongosh
```

Various commands are available.  Start with `show dbs` to see which databases are populated.

### License.
 
This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html")
