package io.dimoffon.sn.dto;

import io.dimoffon.sn.entity.Interest;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

public class InterestFormatter implements Formatter<Interest> {
    @Override
    public Interest parse(final String s, final Locale locale) throws ParseException {
        return Interest.builder().id(Long.valueOf(s)).build();
    }

    @Override
    public String print(final Interest interest, final Locale locale) {
        return interest.getId().toString();
    }
}
