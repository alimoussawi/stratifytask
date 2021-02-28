package com.example.stratifytask;


import com.example.stratifytask.controllers.CustomerController;
import com.example.stratifytask.models.Customer;
import com.example.stratifytask.models.Team;
import com.example.stratifytask.services.CustomerService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.FileInputStream;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @MockBean
    CustomerService customerService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void should_create_customers() throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        ResponseEntity<List<Customer>> responseEntity = new ResponseEntity<>(Prototype.customerList(), httpHeaders, HttpStatus.CREATED);

        when(customerService.saveCustomers(any())).thenReturn(responseEntity);

        String endpoint = "/api/upload";
        String filePath = "C:/Users/alous/Downloads/MOCK_DATA_47 (4).csv";
        FileInputStream fis = new FileInputStream(filePath);

        MockMultipartFile multipartFile = new MockMultipartFile("csvFile", fis.readAllBytes());
        mockMvc.perform(multipart(endpoint).file("csvFile", multipartFile.getBytes())).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void should_return_allCustomer() throws Exception {
        when(customerService.getCustomers(any())).thenReturn(Prototype.customerList());
        String endpoint = "/api/opportunity";
        mockMvc.perform(MockMvcRequestBuilders.get(endpoint)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.jsonPath("$.*", Matchers.hasSize(Prototype.customerList().size())));
    }

    @Test
    public void should_return_team_customers() throws Exception {
        when(customerService.getCustomers(any())).thenReturn(Prototype.teamCustomers(Team.EAST));
        String endpoint = "/api/opportunity?team=EAST";
        mockMvc.perform(MockMvcRequestBuilders.get(endpoint)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.jsonPath("$.*", Matchers.hasSize(1)));
    }

}
