
# Income tax subscription eligibility frontend

This is a service for determining whether income tax customers are eligible
to submit their Self Assessment returns using software.

## Running the service locally

You will need [sbt](http://www.scala-sbt.org/)

1) **[Install Service-Manager](https://github.com/hmrc/service-manager/wiki/Install#install-service-manager)**


2) **Start the Income Tax Subscription services:**

   `sm --start ITSA_SUBSC_ALL -f`


3) **Clone the frontend service:**

  - SSH

    `git clone git@github.com:hmrc/income-tax-subscription-eligibility-frontend.git`

  - HTTPS

    `git clone https://github.com/hmrc/income-tax-subscription-eligibility-frontend.git`


4) **Start the frontend service:**

   `sm --stop INCOME_TAX_SUBSCRIPTION_FRONTEND`

   `sbt "run 9561"`


5) **Go to the homepage:**

   http://localhost:9561/report-quarterly/income-and-expenses/sign-up/eligibility

### License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html")

