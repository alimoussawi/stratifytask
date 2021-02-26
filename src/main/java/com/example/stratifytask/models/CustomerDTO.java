package com.example.stratifytask.models;


import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter @Setter @NoArgsConstructor @ToString
public class CustomerDTO {

    @CsvBindByName(column = "CustomerName")
    private String customerName;
    @CsvBindByName(column = "BookingDate")
    private String bookingDate;
    @CsvBindByName(column = "OpportunityID")
    private String opportunityID;
    @CsvBindByName(column = "BookingType")
    private String bookingType;//enum
    @CsvBindByName(column = "Total")
    private String total;
    @CsvBindByName(column = "AccountExecutive")
    private String accountExecutive;
    @CsvBindByName(column = "SalesOrganization")
    private String salesOrganization;
    @CsvBindByName(column = "Team")
    private String team;
    @CsvBindByName(column = "Product")
    private String product;
    @CsvBindByName(column = "Renewable")
    private String renewable;

    public CustomerDTO(String customerName, String bookingDate, String opportunityID, String bookingType, String total, String accountExecutive, String salesOrganization, String team, String product, String renewable) {
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
