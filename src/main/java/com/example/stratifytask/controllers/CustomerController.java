package com.example.stratifytask.controllers;


import com.example.stratifytask.repositories.CustomerRepository;
import com.example.stratifytask.models.Customer;

import com.example.stratifytask.services.CustomerService;
import net.kaczmarzyk.spring.data.jpa.domain.Between;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
public class CustomerController {

    @Autowired
    CustomerService customerService;
    @Autowired
    CustomerRepository customerRepository;
    @PostMapping("/upload")
    public ResponseEntity<List<Customer>> uploadCSVFile(@RequestParam("csvFile") MultipartFile csvFile) {
        return customerService.saveCustomers(csvFile);
    }

    @GetMapping("/opportunity")
    public ResponseEntity<List<Customer>> getCustomers(@And({@Spec(path = "team", params = "team", spec = Equal.class), @Spec(path = "product", params = "product", spec = Equal.class), @Spec(path = "bookingType", params = "bookingType", spec = Equal.class), @Spec(path = "bookingDate", params = {"startDate", "endDate"}, spec = Between.class)}) Specification<Customer> spec) {
        return customerService.getCustomers(spec);
    }


}

