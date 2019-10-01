package br.com.xmacedo.fileProcessor.factories;

import br.com.xmacedo.fileProcessor.model.Client;
import br.com.xmacedo.fileProcessor.model.Register;

public class ClientFactory implements RegisterFactory {
    private final String LINE_SEPARATOR = "ç";

    @Override
    public Register create(String register) {
        String[] splitedRegistries = register.split(LINE_SEPARATOR);
        return Client.builder()
            .cnpj(splitedRegistries[1])
            .name(splitedRegistries[2])
            .businessArea(splitedRegistries[3])
            .build();
    }

}
