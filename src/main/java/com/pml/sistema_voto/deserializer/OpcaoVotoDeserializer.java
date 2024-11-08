package com.pml.sistema_voto.deserializer;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.pml.sistema_voto.entity.enums.OpcaoVoto; // Ajuste o caminho para o seu enum

import java.io.IOException;

public class OpcaoVotoDeserializer extends JsonDeserializer<OpcaoVoto> {
    @Override
    public OpcaoVoto deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException, JsonProcessingException {
        String value = jsonParser.getText().toUpperCase(); // Converte para maiúsculas para comparação

        try {
            return OpcaoVoto.valueOf(value);
        } catch (IllegalArgumentException e) {
            // Lança uma exceção com a mensagem desejada
            throw new JsonProcessingException("Opção de voto inválida: " + value) {};
        }
    }
}