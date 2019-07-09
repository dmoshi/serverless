package com.dmoshi.lambda.contactme;

import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.dmoshi.lambda.contactme.model.ContactMe;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class ContactMeFunctionHandlerTest {

    private static APIGatewayProxyRequestEvent input;
    private static final ObjectMapper mapper = new ObjectMapper();

    @BeforeClass
    public static void createInput() throws IOException {
        ContactMe inputModel = new ContactMe();
        inputModel.setMessage("Hello Daniel");
        inputModel.setSendersEmail("visitor@domain.com");
        String body = mapper.writeValueAsString(inputModel);
        input = new APIGatewayProxyRequestEvent();
        input.withBody(body);
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
        APIGatewayProxyResponseEvent output = handler.handleRequest(input, ctx);
        Assert.assertEquals(Integer.valueOf(200), output.getStatusCode());
    }
}
