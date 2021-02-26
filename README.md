# Stratify Task
Rest Api where you can upload a csv file and retrieve filtered data

### External dependencies:
* h2 Database
* project Lombok
* openCSV
* specification-arg-resolver
    * https://github.com/tkaczmarzyk/specification-arg-resolver

### Endpoints:

* /api/upload
    * Request Params: csvFile
    * Demo request:
      ![image](https://user-images.githubusercontent.com/34392211/109321717-26e71400-785a-11eb-97c4-796467bc629d.png)

* /api/opportunity
    * Query Params: team, product, bookingType, bookingDate -No query params return all customers

* Link to a postman collection containing some simple test cases:
  [![Run in Postman](https://run.pstmn.io/button.svg)](https://app.getpostman.com/run-collection/3bea8906be1db4b9bde7)
  