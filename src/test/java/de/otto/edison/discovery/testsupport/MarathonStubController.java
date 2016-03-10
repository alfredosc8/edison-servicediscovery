package de.otto.edison.discovery.testsupport;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class MarathonStubController {

    @RequestMapping(
            value = "/marathon/stub/v2/apps",
            produces = "application/json"
    )
    public Resource getApps() throws IOException {
        return new ClassPathResource("marathon/marathon-apps-response.json");
    }

    @RequestMapping(
            value = "/marathon/stub/v2/apps/ci/order/shoppingcart",
            produces = "application/json"
    )
    public Resource getShoppingcartCi() throws IOException {
        return new ClassPathResource("marathon/marathon-shoppingcart-ci-response.json");
    }

    @RequestMapping(
            value = "/marathon/stub/v2/apps/live/order/shoppingcart",
            produces = "application/json"
    )
    public Resource getShoppingCartLive() throws IOException {
        return new ClassPathResource("marathon/marathon-shoppingcart-live-response.json");
    }
}
