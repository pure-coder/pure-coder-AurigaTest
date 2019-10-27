package com.aurigainterviews.aurigainterview;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class DataHolderSerializer extends StdSerializer<DataHolder> {

    protected DataHolderSerializer(Class<DataHolder> t) {
        super(t);
    }

    public void serialize(DataHolder dataHolder, JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider)
            throws IOException {

        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("name", dataHolder.getName());
        jsonGenerator.writeStringField("agentVersion", dataHolder.getAgentVersion());
        jsonGenerator.writeNumberField("howManyAlerts", dataHolder.getAlertIds());
        jsonGenerator.writeStringField("architecture", dataHolder.getArchitecture());
        jsonGenerator.writeStringField("collector", dataHolder.getCollector());
        jsonGenerator.writeStringField("cpuModel", dataHolder.getCpuModel());
        jsonGenerator.writeStringField("description", dataHolder.getDescription());
        jsonGenerator.writeStringField("discoveryDate", dataHolder.getDiscoveryDate());
        jsonGenerator.writeObjectField("ipAddresses", dataHolder.getIpAddresses());
        jsonGenerator.writeEndObject();
    }
}
