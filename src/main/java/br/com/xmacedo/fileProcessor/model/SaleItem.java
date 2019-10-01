package br.com.xmacedo.fileProcessor.model;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SaleItem extends Register {
    private Long       id;
    private Long       quantity;
    private BigDecimal unitPrice;
}
