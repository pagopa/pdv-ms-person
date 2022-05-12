package it.pagopa.pdv.person.web.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import(WebConfig.class)
public class WebTestConfig {
}