package com.zucisystems.camel.SpringBootCamelAPI.typeConverter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zucisystems.camel.SpringBootCamelAPI.dto.Order;
import org.apache.camel.Converter;
import org.apache.camel.Exchange;
import org.apache.camel.TypeConversionException;
import org.apache.camel.converter.stream.InputStreamCache;
import org.apache.camel.support.TypeConverterSupport;

import java.io.IOException;

//@Converter(generateLoader = true)
//public class OrderTypeConverter extends TypeConverterSupport {
//
//    private final ObjectMapper objectMapper=new ObjectMapper();
//
//    @Converter
//    @Override
//    public <T> T convertTo(Class<T> type, Exchange exchange, Object value) throws TypeConversionException {
//        if(type.isAssignableFrom(Order.class)){
//            try{
//                Order order=objectMapper.readValue(exchange.getContext().getTypeConverter().convertTo(String.class,value),Order.class);
//                return type.cast(order);
//            }catch (Exception e)
//            {
//                throw new TypeConversionException(value, type, e);
//            }
//        }
//        try {
//            throw new InvalidPayloadException(exchange, type);
//        } catch (InvalidPayloadException e) {
//            throw new RuntimeException(e);
//        }
//
//    }
//}

@Converter(generateLoader = true)
public class OrderTypeConverter extends TypeConverterSupport {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T convertTo(Class<T> type, Exchange exchange, Object value) throws TypeConversionException {
        if (type.isAssignableFrom(Order.class) && value instanceof InputStreamCache) {
            try {
                InputStreamCache inputStreamCache = (InputStreamCache) value;
                return type.cast(objectMapper.readValue(inputStreamCache, Order.class));
            } catch (IOException e) {
                throw new TypeConversionException(value, type, e);
            }
        }
        return null;
    }
}