package com.azad.ecommerce.app.apis.inventory;

import com.azad.ecommerce.app.apis.inventory.models.StoreRequest;
import com.azad.ecommerce.app.apis.inventory.models.StoreResponse;
import com.azad.ecommerce.app.commons.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/inventoryservice")
public class InventoryResourceV1 {

    @Value("{inventory_service_base_url}")
    private String INVENTORY_SERVICE_BASE_URL;

    @Autowired
    private AppUtils appUtils;

    @PostMapping(path = "/stores")
    public ResponseEntity<StoreResponse> createStore(@Valid @RequestBody StoreRequest request) {

        String createStoreApiUrl = INVENTORY_SERVICE_BASE_URL + "/api/v1/stores";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<StoreRequest> requestEntity = new HttpEntity<>(request, headers);

        ResponseEntity<StoreResponse> response = restTemplate.exchange(createStoreApiUrl, HttpMethod.POST, requestEntity, StoreResponse.class);

        return response;
    }
}
