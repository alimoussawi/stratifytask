package com.example.stratifytask.services;

import com.example.stratifytask.models.Customer;
import com.example.stratifytask.models.CustomerDTO;
import com.example.stratifytask.repositories.CustomerRepository;
import com.example.stratifytask.utils.CustomerMapper;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public ResponseEntity<List<Customer>> saveCustomers(MultipartFile csvFile) {
        if (csvFile.isEmpty()||!csvFile.getContentType().equals("text/csv")) {
            log.error("file not provided or does not match the predefined structure");
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.BAD_REQUEST);
        } else {
            // parse CSV file to create a list of `CustomerDTO` objects
            try (Reader reader = new BufferedReader(new InputStreamReader(csvFile.getInputStream()))) {
                // create csv bean reader
                CsvToBean csvToBean = new CsvToBeanBuilder<>(reader).withType(CustomerDTO.class)
                        .withIgnoreLeadingWhiteSpace(true)
                        .withFilter((String[] strings) -> {
                            for (String one : strings) {
                                if (one != null && one.length() > 0) {
                                    return true;
                                }
                            }
                            return false;
                        }).build();

                // convert `CsvToBean` object to list of CustomerDTO
                List<CustomerDTO> customerDTOS = csvToBean.parse();

                // Map CustomerDTO to Customer Objects with appropriate data types to be saved in the DB
                List<Customer> customers = customerDTOS.stream().map(CustomerMapper::toCustomer).filter(distinctByKey(Customer::getOpportunityID)).collect(Collectors.toList());
                return new ResponseEntity<>(customerRepository.saveAll(customers),HttpStatus.CREATED);
            } catch (Exception ex) {
                log.error("An error occurred while processing the CSV file.");
                log.error(ex.getMessage());
                return new ResponseEntity<>(Collections.emptyList(),HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    public ResponseEntity<List<Customer>> getCustomers(Specification<Customer> spec){
        return new ResponseEntity<>(customerRepository.findAll(spec), HttpStatus.OK);
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
