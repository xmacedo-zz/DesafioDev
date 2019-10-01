package br.com.xmacedo.fileProcessor.factories;

import java.math.BigDecimal;

import br.com.xmacedo.fileProcessor.model.Register;
import br.com.xmacedo.fileProcessor.model.SaleItem;


public class SaleItemFactory implements RegisterFactory {

    private final String LINE_SEPARATOR_FOR_SALE_ITEM = "-";

    @Override
    public Register create(String register) {
        String[] splitedRegistries = register.split(LINE_SEPARATOR_FOR_SALE_ITEM);
        return SaleItem.builder()
            .id(new Long(splitedRegistries[0]))
            .quantity(new Long(splitedRegistries[1]))
            .unitPrice(new BigDecimal(splitedRegistries[2]))
            .build();
    }
}
