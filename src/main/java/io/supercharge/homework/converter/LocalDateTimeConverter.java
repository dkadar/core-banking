package io.supercharge.homework.converter;

import java.time.LocalDateTime;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LocalDateTimeConverter implements Converter<String, LocalDateTime> {
    @Override
    public LocalDateTime convert(String date) {
        return LocalDateTime.parse(date);
    }
}
