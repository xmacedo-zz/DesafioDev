package br.com.xmacedo.fileProcessor.factories;

import java.util.ArrayList;
import java.util.List;

import br.com.xmacedo.fileProcessor.model.Register;
import br.com.xmacedo.fileProcessor.model.Sale;
import br.com.xmacedo.fileProcessor.model.SaleItem;

public class SaleFactory implements RegisterFactory {

    private final String LINE_SEPARATOR = "ç";

    @Override
    public Register create(String register) {
        String[] splitedRegistries = register.split(LINE_SEPARATOR);
        return Sale.builder()
            .id(new Long(splitedRegistries[1]))
            .items(createSaleItems(splitedRegistries[2]))
            .salesmanName(splitedRegistries[3])
            .build();
    }

    private List<SaleItem> createSaleItems(String itemsRegister) {
        List<SaleItem> saleItems = new ArrayList<>();
        String[] items = itemsRegister.replace("[", "").replace("]", "").split(",");
        RegisterFactory saleItemsFactory = new SaleItemFactory();
        for (String item : items) {
            saleItems.add((SaleItem) saleItemsFactory.create(item));
        }
        return saleItems;
    }
}
