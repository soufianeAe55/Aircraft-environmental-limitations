package com.example.aircraftenvlimitations.exception;

import com.example.aircraftenvlimitations.dto.CommonResponseError;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.MessageSource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class ExceptionGlobalHandlerTest {

    @InjectMocks
    private ExceptionGlobalHandler exceptionGlobalHandler;
    @Mock
    private MessageSource messageSource;

    @Test
    public void when_handling_business_exception_should_return_right_messages() throws Exception {

        //GIVEN
        MessageList messages = new MessageList();
        messages.add(new Message("error1"));
        //WHEN
        when(messageSource.getMessage("error1",null, null)).thenReturn("Message1");

        CommonResponseError result = exceptionGlobalHandler.handle(new BusinessException(messages));
        //THEN
        assertNotNull(result);
        assertEquals(1, result.getMessageErrorList().size());
        assertEquals("Message1", result.getMessageErrorList().get(0).getMessage());

    }

    @Test
    public void when_handling_technical_exception_should_return_right_messages() {
        //WHEN
        when(messageSource.getMessage(anyString(),any(), any())).thenReturn("technical exception occurred");

        CommonResponseError result = exceptionGlobalHandler.handle(new TechnicalException(""));
        //THEN
        assertNotNull(result);
        assertEquals(1, result.getMessageErrorList().size());
        assertEquals("technical exception occurred", result.getMessageErrorList().get(0).getMessage());

    }
}