package com.jaramgroupware.jaramgateway.domain.Error;



import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Component
public class ErrorInfoMapper implements BiFunction<Row, RowMetadata, ErrorInfo> {
    @Override
    public ErrorInfo apply(Row row, RowMetadata rowMetadata) {

        return ErrorInfo.builder()
                .id(row.get("ERROR_PK",Integer.class))
                .name(row.get("ERROR_NM",String.class))
                .code(row.get("ERROR_HTTP_CODE",String.class))
                .index(row.get("ERROR_INDEX",String.class))
                .title(row.get("ERROR_TITLE",String.class))
                .build();

    }
}
