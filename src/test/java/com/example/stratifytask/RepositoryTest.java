package com.example.stratifytask;

import com.example.stratifytask.models.Customer;
import com.example.stratifytask.models.Team;
import com.example.stratifytask.repositories.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RepositoryTest {

    @Autowired
    CustomerRepository customerRepository;


    @Test
    public void should_save_customer() {
        Customer customer = Prototype.customer();
        customerRepository.save(customer);
        Optional<Customer> foundCustomer = customerRepository.findById(customer.getId());
        assertEquals(customer.getOpportunityID(), foundCustomer.get().getOpportunityID());
    }

    @Test
    public void should_save_customer_list() {
        List<Customer> customers = Prototype.customerList();
        customerRepository.saveAll(customers);
        List<Customer> list = customerRepository.findAll();
        assertTrue(list.size() > 0);
    }

    @Test
    public void should_not_save_duplicate_opportunity() {
        Customer customer1 = Prototype.anotherCustomer();
        Customer customer2 = Prototype.anotherCustomer();
        customerRepository.save(customer1);
        assertThrows(DataIntegrityViolationException.class, () -> customerRepository.save(customer2));
    }

    @Test
    public void should_return_valid_customers_by_team() {
        List<Customer> customers = Prototype.customerList();
        customerRepository.saveAll(customers);
        List<Customer> foundCustomers = customerRepository.findAllByTeamEquals(Team.EAST);
        foundCustomers.forEach(customer -> {
            assertEquals(customer.getTeam(), Team.EAST);
        });
    }

    @Test
    public void should_return_valid_customers_by_dateBetween() {
        List<Customer> customers = Prototype.customerList();
        customerRepository.saveAll(customers);
        List<Customer> foundCustomers = customerRepository.findAllByBookingDateBetween(LocalDate.of(2020, 9, 30), LocalDate.of(2021, 5, 30));
        foundCustomers.forEach(customer -> {
            assertTrue(customer.getBookingDate().isBefore(LocalDate.of(2021, 5, 30)));
            assertTrue(customer.getBookingDate().isAfter(LocalDate.of(2020, 9, 30)));
        });
    }
}
