package com.example.moremeal;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    AddToCartActivity ob = new AddToCartActivity();

    @Before
    public void setupObject(){

        ob = new AddToCartActivity();
    }

    @Test // (radio id == -1) = not Selected any of radio button
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test //for id -1 this is for unselected radio button radio id = -1 & quantity = 3
    public void textCalculationAddToCart() {

       int result = ob.addToCartTest(3, -1, "medium", "900.00", "1500.00", "2300.00");

        assertEquals(1, result);

    }

    @Test //radio id selected but user entered quantity = 0, radio id = normal(! = -1)
    public void textCalculationAddToCart2() {

        int result = ob.addToCartTest(0, 2, "medium", "900.00", "1500.00", "2300.00");

        assertEquals(2, result);

    }

    @Test //get total ,, now focus to skip the first if and second else if to return the total price
    public void textCalculationAddToCart3() {  // so q = normal(!= 0) and radio id = normal/// q = 2 && radio id = 3 && size = medium

        //so medium price = 900 and it convert to integer after
        //after this will return int total = 900 * 2 = 1800


        int result = ob.addToCartTest(2, 2, "Medium", "900", "1500", "2300");

        assertEquals(1800, result);

    }


    @Test
    public void textCalculationAddToCart4() {  // so q = normal(!= 0) and radio id = normal(!=-1)/// q = 3 && radio id = 3 && size = Large

        //so Large price = 1500 and it convert to integer after
        //after this will return int total = 1500 * 3 = 4500


        int result = ob.addToCartTest(3, 2, "Large", "900", "1500", "2300");

        assertEquals(4500, result);

    }

    @Test
    public void textCalculationAddToCart5() {  // so q = normal(!= 0) and radio id = normal(!= -1)/// q = 4 && radio id = 3 && size = premium

        //so premium price = 2300 and it convert to integer after
        //after this will return int total = 2300 * 4 = 9200


        int result = ob.addToCartTest(4, 2, "Premium", "900", "1500", "2300");

        assertEquals(9200, result);

    }


}