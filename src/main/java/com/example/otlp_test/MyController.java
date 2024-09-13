package com.example.otlp_test;

import io.micrometer.tracing.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Moritz Halbritter
 */
@RestController
class MyController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyController.class);

    private final Tracer tracer;

    MyController(Tracer tracer) {
        this.tracer = tracer;
    }

    @GetMapping("/")
    String index() {
        LOGGER.info("index()");
        String traceId = this.tracer.currentTraceContext().context().traceId();
        return "Hello World, trace id " + traceId;
    }
}
