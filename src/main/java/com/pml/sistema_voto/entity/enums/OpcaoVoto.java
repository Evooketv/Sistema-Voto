package com.pml.sistema_voto.entity.enums;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.pml.sistema_voto.deserializer.OpcaoVotoDeserializer;

@JsonDeserialize(using = OpcaoVotoDeserializer.class)
public enum OpcaoVoto {
    SIM,
    NAO;

}
