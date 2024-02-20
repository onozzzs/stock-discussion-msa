package com.example.batch.config;

import com.example.batch.dto.StockDTO;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class StockFieldSetMapper implements FieldSetMapper<StockDTO> {
    @Override
    public StockDTO mapFieldSet(FieldSet fieldSet) throws BindException {
        return StockDTO.builder()
                .index(fieldSet.readInt(0))
                .date(fieldSet.readString(1))
                .open(fieldSet.readLong(2))
                .high(fieldSet.readLong(3))
                .low(fieldSet.readLong(4))
                .close(fieldSet.readLong(5))
                .volume(fieldSet.readLong(6))
                .fluctuationRate(fieldSet.readDouble(7))
                .ticker(fieldSet.readString(8))
                .stock_name(fieldSet.readString(9))
                .market(fieldSet.readString(10))
                .build();
    }
}
