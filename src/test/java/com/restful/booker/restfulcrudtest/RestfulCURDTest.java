package com.restful.booker.restfulcrudtest;

import com.restful.booker.model.BookingPojo;
import com.restful.booker.restfulinfo.RestfulSteps;
import com.restful.booker.testbase.TestBase;
import com.restful.booker.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

public class RestfulCURDTest  extends TestBase {

    static String firstname = "vivek" + TestUtils.getRandomValue();
    static String lastname = "vaghani" + TestUtils.getRandomValue();
    static int totalprice = Integer.parseInt(1 + TestUtils.getRandomValue());
    static boolean depositpaid = true;
    static String additionalneeds = "pillow";

    static String token;
    static int id;

    @Steps
    RestfulSteps restfulSteps;

    @Title("This method will create a Tokken")
    @org.junit.Test
    public void test001() {
        ValidatableResponse response = restfulSteps.getToken().statusCode(200);
        token = response.extract().path("token");
    }

    @Title("This method will create a booking")
    @org.junit.Test
    public void test002() {
        BookingPojo.BookingDates bookingdates = new BookingPojo.BookingDates();
        bookingdates.setCheckin("2022-10-01");
        bookingdates.setCheckout("2022-12-01");
        ValidatableResponse response = restfulSteps.createBooking(firstname, lastname, totalprice, depositpaid, bookingdates, additionalneeds).statusCode(200);
        id = response.extract().path("bookingid");
    }

    @Title("This method will verify new Booking ID creation")
    @Test
    public void test003() {
        ValidatableResponse response = restfulSteps.getBookingInfoByID();
        ArrayList<?> booking = response.extract().path("bookingid");
        Assert.assertTrue(booking.contains(id));
    }
    @Title("This method will get booking with Id")
    @org.junit.Test
    public void test004() {
        restfulSteps.getSingleBookingIDs(id).statusCode(200);
    }

    @Title("This method will updated a booking with ID")
    @Test
    public void test005() {
        additionalneeds = "lunch";
        BookingPojo.BookingDates bookingdates = new BookingPojo.BookingDates();
        bookingdates.setCheckin("2022-10-01");
        bookingdates.setCheckout("2022-12-01");
        restfulSteps.updateBookingWithID(id, token, firstname, lastname, totalprice, depositpaid, bookingdates, additionalneeds);
        ValidatableResponse response = restfulSteps.getSingleBookingIDs(id);
        HashMap<String, ?> update = response.extract().path("");
        Assert.assertThat(update, hasValue("lunch"));
    }

    @Title("This method will delete a booking with ID")
    @Test
    public void test006() {
        restfulSteps.deleteABookingID(id, token).statusCode(201);
        restfulSteps.getSingleBookingIDs(id).statusCode(404);
    }




}
