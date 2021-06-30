package com.santiago.response.hypermedia.controller;

import com.santiago.response.hypermedia.util.Constants;
import com.santiago.response.hypermedia.model.Customer;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.toedter.spring.hateoas.jsonapi.JsonApiModelBuilder.jsonApiModel;
import static com.toedter.spring.hateoas.jsonapi.MediaTypes.JSON_API_VALUE;

@RestController
@RequestMapping(value = "/api", produces = JSON_API_VALUE)
public class CustomersController {

    @GetMapping(value = "/customers")
    ResponseEntity<RepresentationModel> getCustomers(){

        Customer customer1=new Customer("123","customers","test", "one");
        Customer customer2=new Customer("987","customers","test", "two");
        final RepresentationModel<?> jsonApiModel1 =
                jsonApiModel()
                        .model(customer1)
                        .build();

        final RepresentationModel<?> jsonApiModel2 =
                jsonApiModel()
                        .model(customer2)
                        .build();

        List<RepresentationModel<?>> customers = new ArrayList<>();
        customers.add(jsonApiModel1);
        customers.add(jsonApiModel2);
        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(2, 1, 50, 25);
        Link selfLink = Link.of(Constants.BASE_URL).withSelfRel();

        final PagedModel<RepresentationModel<?>> pagedModel = PagedModel.of(customers, pageMetadata, selfLink);

        RepresentationModel<?> pagedJasonApiModel =
                jsonApiModel()
                        .model(pagedModel)
                        .pageMeta()
                        .pageLinks(Constants.BASE_URL)
                        .build();

        return ResponseEntity.ok(pagedJasonApiModel);
    }
}
