package com.santiago.response.hypermedia.controller;


import com.santiago.response.hypermedia.util.Constants;
import com.santiago.response.hypermedia.model.Account;
import com.santiago.response.hypermedia.model.Customer;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.toedter.spring.hateoas.jsonapi.MediaTypes.JSON_API_VALUE;
import static com.toedter.spring.hateoas.jsonapi.JsonApiModelBuilder.jsonApiModel;

@RestController
@RequestMapping(value = "/api",produces = JSON_API_VALUE)
public class AccountsPerCustomerController {



    @GetMapping(value = "/customers/{id}/accounts")
    ResponseEntity<RepresentationModel> getCustomers(@PathVariable String id){

        Account acct1 = new Account("09876","accounts","my-acct-test-1");
        Account acct2 = new Account("12345","accounts","my-acct-test-2");
        List<Account> accounts = new ArrayList<>();
        accounts.add(acct1);
        accounts.add(acct2);

        Customer customer=new Customer("123","customers","test", "one");

        final RepresentationModel<?> jsonApiModel =
                jsonApiModel()
                        .model(customer)
                        .relationship("accounts", accounts)
                        .included(accounts)
                        .build();

        List<RepresentationModel<?>> cus = new ArrayList<>();
        cus.add(jsonApiModel);
        PagedModel.PageMetadata pageMetadata =  new PagedModel.PageMetadata(2, 1, 2, 1);
        Link selfLink = Link.of(Constants.BASE_URL+id).withSelfRel();
        final PagedModel<RepresentationModel<?>> pagedModel = PagedModel.of(cus, pageMetadata, selfLink);

        RepresentationModel<?> pagedJasonApiModel =
                jsonApiModel()
                        .model(pagedModel)
                        .included(accounts)
                        .pageMeta()
                        .pageLinks(Constants.BASE_URL+id)
                        .build();

        return ResponseEntity.ok(pagedJasonApiModel);
    }
}
