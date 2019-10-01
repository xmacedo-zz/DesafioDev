package br.com.xmacedo.fileProcessor.model;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Salesman extends Register {
    private String     cpf;
    private String     name;
    private BigDecimal salary;
}
