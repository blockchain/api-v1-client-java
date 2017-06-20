# Blockchain API library (Java, v2.0.0)

An official Java library for interacting with the Blockchain.info API (Java 1.6 required).

### Getting started

If you prefer building from source:

```
$ git clone https://github.com/blockchain/api-v1-client-java
$ cd api-v1-client-java
$ mvn install
```

We also provide a snapshot Maven repository for users who prefer managing dependencies that way.

Add the following to your pom.xml:
```xml
<project>
    ...
    <repositories>
        ...
        <repository>
            <id>api-v1-client-java-mvn-repo</id>
            <url>https://raw.githubusercontent.com/blockchain/api-v1-client-java/mvn-repo/</url>
            <snapshots>
                <enabled>true</enabled>
                <updatePolicy>always</updatePolicy>
            </snapshots>
        </repository>
    </repositories>
    ...
    <dependencies>
        ...
        <dependency>
  	        <groupId>info.blockchain</groupId>
  	        <artifactId>api</artifactId>
  	        <version>LATEST</version> <!-- for a specific version see the list of tags -->
        </dependency>
    <dependencies>
    ...
</project>
```

The above Maven repository also works with Gradle.

Add this to your `build.gradle`:
```
...
repositories {
    ...
    maven {
        url = 'https://raw.githubusercontent.com/blockchain/api-v1-client-java/mvn-repo'
    }
}
...
dependencies {
    ...
    compile 'info.blockchain:api:1.1.5'
}
...
```

Note that the above procedures require Maven or Gradle. If you do not wish to use Maven or Gradle, please compile the source manually.

The library consists of the following packages:

* `info.blockchain.api.blockexplorer` ([docs](docs/blockexplorer.md)) ([api/blockchain_api][api1])
* `info.blockchain.api.createwallet` ([docs](docs/createwallet.md)) ([api/create_wallet][api2])
* `info.blockchain.api.exchangerates` ([docs](docs/exchangerates.md)) ([api/exchange\_rates\_api][api3])
* `info.blockchain.api.pushtx` ([docs](docs/pushtx.md)) ([pushtx][api7])
* `info.blockchain.api.receive` ([docs](docs/receive.md)) ([api/api_receive][api4])
* `info.blockchain.api.statistics` ([docs](docs/statistics.md)) ([api/charts_api][api5])
* `info.blockchain.api.wallet` ([docs](docs/wallet.md)) ([api/blockchain\_wallet\_api][api6])

In order to use `createwallet` and `wallet` you need to run an instance of [service-my-wallet-v3](https://github.com/blockchain/service-my-wallet-v3).

### Error handling

All methods may throw exceptions caused by incorrectly passed parameters or other problems. If a call is rejected server-side, the `APIException` exception will be thrown. In case of a network error, the `IOException` exception will be thrown.

### Connection timeouts

It is possible to set arbitrary connection timeouts.

```java
info.blockchain.api.HttpClient.TIMEOUT_MS = 2000; // time out after 2000 milliseconds
```

### Request limits and API keys

In order to prevent abuse some API methods require an API key approved with some basic contact information and a description of its intended use. Please request an API key [here](https://blockchain.info/api/api_create_code).

The same API key can be used to bypass the request limiter.

### Code Coverage Report generation

To generate the code coverage report, execute the following command:
```sh
mvn clean verify
```

This will generate a code coverage report in `target/site/jacoco/index.html`.


[api1]: https://blockchain.info/api/blockchain_api
[api2]: https://blockchain.info/api/create_wallet
[api3]: https://blockchain.info/api/exchange_rates_api
[api4]: https://blockchain.info/api/api_receive
[api5]: https://blockchain.info/api/charts_api
[api6]: https://blockchain.info/api/blockchain_wallet_api
[api7]: https://blockchain.info/pushtx
