package com.codbex.helios.integration.tests;

import org.eclipse.dirigible.integration.tests.api.SecurityIT;
import org.eclipse.dirigible.integration.tests.api.java.messaging.MessagingFacadeIT;
import org.eclipse.dirigible.integration.tests.api.javascript.cms.CmsSuiteIT;
import org.eclipse.dirigible.integration.tests.ui.tests.*;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({//
        CmsSuiteIT.class, //
        CreateNewProjectIT.class, //
        CsvimIT.class, //
        CustomSecurityIT.class, //
        DatabasePerspectiveIT.class, //
        GitPerspectiveIT.class, //
        HomepageRedirectIT.class, //
        MailIT.class, //
        MessagingFacadeIT.class, //
        SecurityIT.class, //
        TerminalIT.class//
})
public class DirigibileCommonTestSuiteIT {
}
