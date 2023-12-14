package it.vodafone.dxl.utils;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StringToLocalDateConverter implements Converter<LocalDate, String> {

    private static final List<String> SUPPORTED_FORMATS = Arrays.asList("dd-MM-yyyy", "yyyy-MM-dd");
    private static final List<DateTimeFormatter> DATE_TIME_FORMATTERS = SUPPORTED_FORMATS
            .stream()
            .map(DateTimeFormatter::ofPattern)
            .collect(Collectors.toList());

    /**
     * Convert the source object of type {@code S} to target type {@code T}.
     *
     * @param localDate the source object to convert, which must be an instance of {@code S} (never {@code null})
     * @return the converted object, which must be an instance of {@code T} (potentially {@code null})
     * @throws DateTimeException if the source cannot be converted to the desired target type
     */
    @Override
    public String convert(LocalDate localDate) {

        for (DateTimeFormatter dateTimeFormatter : DATE_TIME_FORMATTERS) {
            try {
                return localDate.format(dateTimeFormatter);
            } catch (DateTimeParseException ex) {
                // deliberate empty block so that all parsers run
            }
        }

        throw new DateTimeException(String.format("unable to format (%s) supported formats are %s",
                localDate, String.join(", ", SUPPORTED_FORMATS)));
    }
}