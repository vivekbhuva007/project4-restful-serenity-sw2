package com.restful.booker.restfulinfo;

import com.restful.booker.constant.EndPoint;
import com.restful.booker.model.AuthPojo;
import com.restful.booker.model.BookingPojo;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

public class RestfulSteps {

    @Step
    public ValidatableResponse getToken(){
        AuthPojo authPojo = new AuthPojo();
        authPojo.setUsername("admin");
        authPojo.setPassword("password123");
        return SerenityRest.given().log().all()
                .header("Content-Type","application/json")
                .when()
                .body(authPojo)
                .post("https://restful-booker.herokuapp.com/auth")
                .then().log().all();
    }
    @Step("Creating new booking with firstname : {0}, lastname {1}, email: {2}, totalprice:{3}, depositpaid:{4},bookingdates:{5}, and additionalneeds:{6}")

    public ValidatableResponse createBooking(String firstname, String lastname, int totalprice, boolean depositpaid, BookingPojo.BookingDates bookingdates, String additionalneeds){
        BookingPojo bookingPojo = new BookingPojo();
        bookingPojo.setFirstname(firstname);
        bookingPojo.setLastname(lastname);
        bookingPojo.setTotalprice(totalprice);
        bookingPojo.setDepositpaid(depositpaid);
        bookingPojo.setBookingDates(bookingdates);
        bookingPojo.setAdditionalneeds(additionalneeds);
        return SerenityRest.given()
                .header("Content-Type","application/json")
                .when()
                .body(bookingPojo)
                .post()
                .then().log().all();
    }
    @Step("Getting existing single booking with id: {0}")
    public ValidatableResponse getSingleBookingIDs(int id) {
        return SerenityRest.given()
                .pathParam("id", id)
                .when().
                get(EndPoint.GET_SINGLE_BOOKING_BY_ID)
                .then().log().all();
    }

    @Step("Updating record with id:{0}, token{1}, firstName: {2}, lastName: {3}, email: {4}, totalprice: {5} depositpaid: {6}, bookingdates: {7} and additonalneeds: {8} ")
    public ValidatableResponse updateBookingWithID(int id, String token, String firstname, String lastname, int totalprice, boolean depositpaid, BookingPojo.BookingDates bookingdates, String additionalneeds) {

        BookingPojo bookingPojo = new BookingPojo();
        bookingPojo.setFirstname(firstname);
        bookingPojo.setLastname(lastname);
        bookingPojo.setTotalprice(totalprice);
        bookingPojo.setDepositpaid(depositpaid);
        bookingPojo.setBookingDates(bookingdates);
        bookingPojo.setAdditionalneeds(additionalneeds);
        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Cookie", "token=" + token)
                .pathParam("id", id)
                .body(bookingPojo)
                .when().put(EndPoint.UPDATE_BOOKING_BY_ID)
                .then().log().all().statusCode(200);
    }

    @Step("Deleting existing booking with id: {0} and token: {1}")
    public ValidatableResponse deleteABookingID(int id, String token) {
        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .header("Cookie", "token=" + token)
                .pathParam("id", id)
                .when().delete(EndPoint.DELETE_BOOKING_BY_ID)
                .then().log().all();

    }

    @Step("Getting booking info by ID")
    public ValidatableResponse getBookingInfoByID() {
        return SerenityRest.given()
                .when()
                .get()
                .then().statusCode(200);
    }

}





