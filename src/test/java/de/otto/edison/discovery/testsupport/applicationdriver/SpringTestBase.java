package de.otto.edison.discovery.testsupport.applicationdriver;

import de.otto.edison.discovery.testsupport.TestServer;
import org.springframework.context.ApplicationContext;

public class SpringTestBase {

    static {
        TestServer.main(new String[0]);
    }

    public static ApplicationContext applicationContext() {
        return TestServer.applicationContext();
    }
}
