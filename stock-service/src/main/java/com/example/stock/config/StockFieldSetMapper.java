package com.example.stock.config;

import com.example.stock.dto.StockDTO;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class StockFieldSetMapper implements FieldSetMapper<StockDTO> {
    @Override
    public StockDTO mapFieldSet(FieldSet fieldSet) throws BindException {
        return StockDTO.builder()
                .index(fieldSet.readInt(0))
                .date(fieldSet.readString(1))
                .open(readLongWithDefault(fieldSet, 2))
                .high(readLongWithDefault(fieldSet, 3))
                .low(readLongWithDefault(fieldSet, 4))
                .close(readLongWithDefault(fieldSet, 5))
                .volume(readLongWithDefault(fieldSet, 6))
                .fluctuationRate(readDoubleWithDefault(fieldSet, 7))
                .ticker(fieldSet.readString(8))
                .stock_name(fieldSet.readString(9))
                .market(fieldSet.readString(10))
                .build();
    }

    private Long readLongWithDefault(FieldSet fieldSet, int index) {
        try {
            String value = fieldSet.readString(index);
            return value.isEmpty() ? 0L : Long.parseLong(value);
        } catch (NumberFormatException e) {
            return 0L;
        }
    }

    private Double readDoubleWithDefault(FieldSet fieldSet, int index) {
        try {
            String value = fieldSet.readString(index);
            return value.isEmpty() ? 0 : Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return Double.valueOf(0);
        }
    }
}
