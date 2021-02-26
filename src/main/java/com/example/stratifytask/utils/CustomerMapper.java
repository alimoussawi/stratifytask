package com.example.stratifytask.utils;

import com.example.stratifytask.models.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


/** a helper class to make CustomerDTO mapping to Customer easier*/
public class CustomerMapper {
    public static Customer toCustomer(CustomerDTO customerDTO) {
        return new Customer(customerDTO.getCustomerName(),
                getDate(customerDTO.getBookingDate()),
                customerDTO.getOpportunityID(),
                getBookingType(customerDTO.getBookingType()),
                getTotal(customerDTO.getTotal()),
                customerDTO.getAccountExecutive(),
                customerDTO.getSalesOrganization(),
                getTeam(customerDTO.getTeam()),
                getProduct(customerDTO.getProduct()),
                getRenewable(customerDTO.getRenewable())
                );
    }

    private static LocalDate getDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("M/d/y"));
    }

    private static double getTotal(String total) {
        String formatted = total.replace("$", "").replace(",", "");
        return Double.parseDouble(formatted);
    }

    private static BookingType getBookingType(String booking) {
        BookingType bookingType;
        switch (booking) {
            case "new":
                bookingType = BookingType.NEW;
                break;
            case "renewal":
                bookingType = BookingType.RENEWAL;
                break;
            case "expansion":
                bookingType = BookingType.EXPANSION;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + booking);

        }
        return bookingType;
    }

    private static Team getTeam(String customerTeam) {
        Team team;
        switch (customerTeam) {
            case "EAST":
                team = Team.EAST;
                break;
            case "WEST":
                team = Team.WEST;
                break;
            case "NORTH":
                team = Team.NORTH;
                break;
            case "SOUTH":
                team = Team.SOUTH;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + customerTeam);

        }
        return team;
    }

    private static Product getProduct(String product) {
        Product productType;
        switch (product) {
            case "ALLOY":
                productType = Product.ALLOY;
                break;
            case "GOLD":
                productType = Product.GOLD;
                break;
            case "BRONZE":
                productType = Product.BRONZE;
                break;
            case "PLATINUM":
                productType = Product.PLATINUM;
                break;
            case "SILVER":
                productType = Product.SILVER;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + product);

        }
        return productType;
    }

    private static boolean getRenewable(String renewable) {
        return renewable.equals("YES");
    }
}
