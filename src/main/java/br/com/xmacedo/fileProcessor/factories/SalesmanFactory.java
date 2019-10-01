package br.com.xmacedo.fileProcessor.factories;

import java.math.BigDecimal;

import br.com.xmacedo.fileProcessor.model.Register;
import br.com.xmacedo.fileProcessor.model.Salesman;


public class SalesmanFactory implements RegisterFactory {

    private final String LINE_SEPARATOR = "ç";

    @Override
    public Register create(String register) {
        String[] splitedRegister = register.split(LINE_SEPARATOR);
        return Salesman.builder()
            .cpf(splitedRegister[1])
            .name(splitedRegister[2])
            .salary(new BigDecimal(splitedRegister[3]))
            .build();
    }

}
