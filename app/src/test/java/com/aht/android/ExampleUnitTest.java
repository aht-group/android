package com.aht.android;

import com.aht.android.rest.RestConnection;

import org.jeesl.model.json.system.status.JsonContainer;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void HttpConnection() throws Exception {
        RestConnection rc = new RestConnection();
        rc.connect();

        JsonContainer json = rc.getRest();
		Assert.assertEquals("abc", json.getStatus().get(0).getCode());
    }
}