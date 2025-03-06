package com.codbex.helios.ui.tests;

import org.eclipse.dirigible.tests.UserInterfaceIntegrationTest;
import org.springframework.context.annotation.Import;

@Import(TestConfigurations.class)
public abstract class HeliosIntegrationTest extends UserInterfaceIntegrationTest {
}
