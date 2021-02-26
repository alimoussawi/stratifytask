package com.example.stratifytask.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String customerName;
    private LocalDate bookingDate;
    private String opportunityID;
    private BookingType bookingType;
    private double total;
    private String accountExecutive;
    private String salesOrganization;
    private Team team;
    private Product product;
    private boolean renewable;

    public Customer(String customerName, LocalDate bookingDate, String opportunityID, BookingType bookingType, double total, String accountExecutive, String salesOrganization, Team team, Product product, boolean renewable) {
        this.customerName = customerName;
        this.bookingDate = bookingDate;
        this.opportunityID = opportunityID;
        this.bookingType = bookingType;
        this.total = total;
        this.accountExecutive = accountExecutive;
        this.salesOrganization = salesOrganization;
        this.team = team;
        this.product = product;
        this.renewable = renewable;
    }
}

