package br.com.xmacedo.fileProcessor.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Client extends Register {
    private String cnpj;
    private String name;
    private String businessArea;
}
