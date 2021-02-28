package com.example.stratifytask;

import com.example.stratifytask.models.BookingType;
import com.example.stratifytask.models.Customer;
import com.example.stratifytask.models.Product;
import com.example.stratifytask.models.Team;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class Prototype {

    public static List<Customer> customerList() {
        Customer customer = new Customer("name", LocalDate.of(2020, 10, 30), "12345", BookingType.NEW, 150.0145, "ali", "org", Team.EAST, Product.ALLOY, true);
        Customer customer2 = new Customer("name2", LocalDate.of(2021, 4, 30), "124124234235", BookingType.NEW, 150.0145, "ali", "org", Team.WEST, Product.BRONZE, true);
        Customer customer3 = new Customer("name3", LocalDate.of(2021, 7, 30), "8452005", BookingType.EXPANSION, 150.0145, "ali", "org", Team.SOUTH, Product.GOLD, true);
        Customer customer4 = new Customer("name4", LocalDate.of(2021, 10, 30), "7541247", BookingType.RENEWAL, 150.0145, "ali", "org", Team.NORTH, Product.PLATINUM, true);
        return List.of(customer, customer2, customer3, customer4);
    }

    public static List<Customer> teamCustomers(Team team) {
        return customerList().stream().filter(customer -> {
            return customer.getTeam() == team;
        }).collect(Collectors.toList());
    }

    public static Customer customer() {
        return new Customer("name", LocalDate.now(), "12345", BookingType.NEW, 150.0145, "ali", "org", Team.EAST, Product.ALLOY, true);
    }

    public static Customer anotherCustomer() {
        return new Customer("name2", LocalDate.now(), "12345", BookingType.NEW, 150.0145, "ali", "org", Team.EAST, Product.ALLOY, true);
    }

}
