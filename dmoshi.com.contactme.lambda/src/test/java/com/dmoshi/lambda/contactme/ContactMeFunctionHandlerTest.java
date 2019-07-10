package com.dmoshi.lambda.contactme;

import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.dmoshi.lambda.contactme.ContactMeFunctionHandler.Response;
import com.dmoshi.lambda.contactme.model.ContactMe;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class ContactMeFunctionHandlerTest {

    private static ContactMe input;

    @BeforeClass
    public static void createInput() throws IOException {
        input = new ContactMe();
        input.setMessage("Hello Daniel");
        input.setSendersEmail("visitor@domain.com");
        System.setProperty("SNS_TOPIC_ARN", "<<arn:yours>>");
        System.setProperty("REGION_NAME", "<<region>>");

    }

    private Context createContext() {
        TestContext ctx = new TestContext();
        ctx.setFunctionName("DMoshicontactMe");
        return ctx;
    }

    @Test
    public void testContactMeFunctionHandler() {
        ContactMeFunctionHandler handler = new ContactMeFunctionHandler();
        Context ctx = createContext();
        Response output = (Response) handler.handleRequest(input, ctx);
        Assert.assertEquals(200, output.getStatusCode());
    }
}
