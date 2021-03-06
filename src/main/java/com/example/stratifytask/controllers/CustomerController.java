package com.example.stratifytask.controllers;

import com.example.stratifytask.models.Customer;
import com.example.stratifytask.services.CustomerService;
import net.kaczmarzyk.spring.data.jpa.domain.Between;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class CustomerController {

    private CustomerService customerService;


    @Autowired
    public CustomerController(CustomerService customerService){
        this.customerService=customerService;
    }
    @PostMapping("/upload")
    public ResponseEntity<List<Customer>> uploadCSVFile(@RequestParam("csvFile") MultipartFile csvFile) {
        return customerService.saveCustomers(csvFile);
    }

    @GetMapping("/opportunity")
    public ResponseEntity<List<Customer>> getCustomers(@And({@Spec(path = "team", params = "team", spec = Equal.class), @Spec(path = "product", params = "product", spec = Equal.class), @Spec(path = "bookingType", params = "bookingType", spec = Equal.class), @Spec(path = "bookingDate", params = {"startDate", "endDate"}, spec = Between.class)}) Specification<Customer> spec) {
        HttpHeaders responseHeaders = new HttpHeaders();
        return ResponseEntity.ok().headers(responseHeaders).body(customerService.getCustomers(spec));
    }


}

