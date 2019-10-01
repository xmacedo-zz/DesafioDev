package br.com.xmacedo.fileProcessor.model;

import java.util.List;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Sale extends Register {
    private Long           id;
    private List<SaleItem> items;
    private String         salesmanName;
}
