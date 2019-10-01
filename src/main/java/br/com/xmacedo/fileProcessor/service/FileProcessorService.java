package br.com.xmacedo.fileProcessor.service;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import br.com.xmacedo.fileProcessor.factories.ClientFactory;
import br.com.xmacedo.fileProcessor.factories.RegisterFactory;
import br.com.xmacedo.fileProcessor.factories.SaleFactory;
import br.com.xmacedo.fileProcessor.factories.SalesmanFactory;
import br.com.xmacedo.fileProcessor.model.Client;
import br.com.xmacedo.fileProcessor.model.Register;
import br.com.xmacedo.fileProcessor.model.Sale;
import br.com.xmacedo.fileProcessor.model.SaleItem;
import br.com.xmacedo.fileProcessor.model.Salesman;

public class FileProcessorService {
    private List<Register> registers;
    private List<String>   VALID_DATA_TYPE = new ArrayList<>(Arrays.asList("001", "002", "003"));

    public FileProcessorService() {
        registers = new ArrayList<>();
    }

    public void validateAndProcessorLinesOfFile(List<String> lines) {
        lines.stream()
            .forEach(line -> {
                    if (validateLine(line)) {
                        processorLine(line);
                    } else {
                        System.out.println("Linha: '" + line + "' descartada, por nao ser o tipo de dado valido.");
                    }
                }
            );
    }

    public String getReportResult() {
        return "Quantidade de clientes no arquivo de entrada: " + getClients().size() + "\n" +
            "Quantidade de vendedor no arquivo de entrada: " + getSalesmen().size() + "\n" +
            "ID da venda mais cara: " + getMostExpensiveSaleId() + "\n" +
            "O pior vendedor: " + getWorstSalesman();
    }

    private List<Client> getClients() {
        return registers.stream()
            .filter(reg -> reg instanceof Client)
            .map(reg -> (Client) reg)
            .collect(Collectors.toList());
    }

    private List<Salesman> getSalesmen() {
        return registers.stream()
            .filter(reg -> reg instanceof Salesman)
            .map(reg -> (Salesman) reg)
            .collect(Collectors.toList());
    }

    private List<Sale> getSales() {
        return registers.stream()
            .filter(reg -> reg instanceof Sale)
            .map(reg -> (Sale) reg)
            .collect(Collectors.toList());
    }

    private Long getMostExpensiveSaleId() {
        BigDecimal mostExpensivePrice = BigDecimal.ZERO;
        Long mostExpensiveSaleId = 0L;
        List<Sale> sales = getSales();
        for (Sale sale : sales) {
            BigDecimal purchaseTotal = purchaseTotal(sale);
            if (mostExpensivePrice.compareTo(purchaseTotal) <= 0) {
                mostExpensiveSaleId = sale.getId();
                mostExpensivePrice = purchaseTotal;
            }
        }
        return mostExpensiveSaleId;
    }

    private String getWorstSalesman() {
        List<Sale> sales = getSales();
        BigDecimal worstSalePrice = purchaseTotal(sales.get(0));
        Sale worstSale = sales.get(0);
        for (Sale sale : sales) {
            if (worstSalePrice.compareTo(purchaseTotal(sale)) < 0) {
            } else {
                worstSalePrice = purchaseTotal(sale);
                worstSale = sale;
            }
        }

        return worstSale.getSalesmanName();
    }

    private BigDecimal purchaseTotal(Sale sale) {
        BigDecimal purchaseTotal = BigDecimal.ZERO;
        for (SaleItem item : sale.getItems()) {
            purchaseTotal = purchaseTotal.add(item.getUnitPrice());
        }
        return purchaseTotal;
    }

    private boolean validateLine(String line) {
        for (String i : VALID_DATA_TYPE) {
            if (line.startsWith(i))
                return true;
        }

        return false;
    }

    private void processorLine(String line) {
        RegisterFactory factory = getCorrespondentFactory(line);
        registers.add(factory.create(line));
    }

    private RegisterFactory getCorrespondentFactory(String line) {
        String type = line.substring(0, 3);
        RegisterFactory factory;

        switch (type) {
            case "001":
                factory = new SalesmanFactory();
                break;
            case "002":
                factory = new ClientFactory();
                break;
            case "003":
                factory = new SaleFactory();
                break;
            default:
                throw new InvalidParameterException("Registro inválido");
        }
        return factory;
    }
}
