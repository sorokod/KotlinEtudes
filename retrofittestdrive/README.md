Configure a basic `retrofit2` client as well as a client that combines  `retrofit2` + `resilience4j`.

* Resilience4j docs: https://resilience4j.readme.io/docs 
* Retrofit docs: https://square.github.io/retrofit/

The combined client is [retrying](https://resilience4j.readme.io/docs/retry) on 5XX errors and exceptions. See [RetrofitTest](src/test/kotlin/retrofittestdrive/RetrofitTest.kt)

