package com.example.stock.batch;

import com.example.stock.dto.DetailStockDTO;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class DetailStockFieldSetMapper implements FieldSetMapper<DetailStockDTO> {

    @Override
    public DetailStockDTO mapFieldSet(FieldSet fieldSet) throws BindException {
        return DetailStockDTO.builder()
                .date(fieldSet.readString(1))
                .bps(fieldSet.readLong(2))
                .per(fieldSet.readDouble(3))
                .pbr(fieldSet.readDouble(4))
                .eps(fieldSet.readLong(5))
                .divRate(fieldSet.readDouble(6))
                .dps(fieldSet.readLong(7))
                .ticker(fieldSet.readString(8))
                .build();
    }
}
