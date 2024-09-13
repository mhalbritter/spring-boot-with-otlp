# OTLP with Spring Boot

## Metrics

Micrometer knows how to export metrics in OTLP format.
For that, add the `io.micrometer:micrometer-registry-otlp` dependency.
This is [documented here](https://docs.micrometer.io/micrometer/reference/implementations/otlp.html).
The `management.otlp.metrics.export.url` property can be used to set the OTLP collector url.

## Traces

When you add "Distributed Tracing" on start.spring.io, you get the Brave tracer by default.
Brave doesn't know how to speak OTLP (yet).
To export traces in OTLP, replace the `io.micrometer:micrometer-tracing-bridge-brave` dependency with `io.opentelemetry:opentelemetry-exporter-otlp`.
This switches the tracer from Brave to OpenTelemetry, which knows how to speak OTLP. 
To export traces in OTLP format, add the `io.opentelemetry:opentelemetry-exporter-otlp` dependency.
This is [documented here](https://docs.spring.io/spring-boot/3.4-SNAPSHOT/reference/actuator/tracing.html#actuator.micrometer-tracing.tracer-implementations.otel-otlp).
The `management.otlp.tracing.endpoint` property must be used to set the OTLP collector url.

## Logs

To export logs via OTLP, you have to first install the OpenTelemetry logback appender.
For that, add the `io.opentelemetry.instrumentation:opentelemetry-logback-appender-1.0:2.7.0-alpha` dependency.
Then add the `io.opentelemetry.instrumentation.logback.appender.v1_0.OpenTelemetryAppender` to the [`logback-spring.xml`](src/main/resources/logback-spring.xml) configuration.
The appender needs to be provided with an `OpenTelemetry` instance.
The class [`OpenTelemetryAppenderInitializer`](src/main/java/com/example/otlp_test/OpenTelemetryAppenderInitializer.java) takes care of that.
This is [documented here](https://docs.spring.io/spring-boot/3.4-SNAPSHOT/reference/actuator/loggers.html#actuator.loggers.opentelemetry).
The `management.otlp.logging.endpoint` property must be used to set the OTLP collector url.

## Testing it yourself

There's a OTLP collector included in this repo, which you can run with Docker Compose:

```shell
cd collector/
docker compose up
```

and then run the application:

```
./gradlew bootRun
```

And open http://localhost:8080/ in the browser.

You should now see some logs show up in the `collector/file-exporter` folder and in the output of Docker Compose.
