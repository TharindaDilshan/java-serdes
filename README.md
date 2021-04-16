# Java SerDes

A Runtime Java Serializer/Deserializer implemented using Proto3

## Example Use Case

```
        // Create a Proto Message
        ProtobufMessage nestedMessageBuilder = ProtobufMessage.newMessageBuilder("Phone")
                .addField("required", "string", "mobile", 1, "0773256")
                .addField("optional", "string", "home", 2, "4567892")
                .build();

        ProtobufSchemaBuilder schemaBuilder = ProtobufSchemaBuilder.newSchemaBuilder("Student.proto");
        ProtobufMessage messageBuilder = ProtobufMessage.newMessageBuilder("StudentMsg")
                .addField("required", "int32", "id", 1)       
                .addField("required", "string", "name", 2) 
                .addNestedMessage(nestedMessageBuilder)
                .addField("optional", "Phone", "phone", 3)
                .addOneofField("address")
                        .addField("string", "work", 4)
                        .addField("string", "home", 5)
                        .oneofMessageBuilder()
                .addOneofField("experience")
                        .addField("string", "student", 6)
                        .addField("string", "employee", 7)
                        .oneofMessageBuilder()
                .build();

        // Add message to proto schema (Add message to .proto file)
        schemaBuilder.addMessageToProtoSchema(messageBuilder);
        Descriptors.Descriptor schema = schemaBuilder.build();

        // Populate the created message
        Descriptors.Descriptor subMessageDescriptor = schema.findNestedTypeByName("Phone");
        DynamicMessage subMessage = DynamicMessage.newBuilder(schema.findNestedTypeByName("Phone"))
                .setField(subMessageDescriptor.findFieldByName("mobile"), "74848")
                .setField(subMessageDescriptor.findFieldByName("home"), "8745")
                .build();

        DynamicMessage.Builder newMessageFromSchema = DynamicMessage.newBuilder(schema);
        Descriptors.Descriptor messageDescriptor = newMessageFromSchema.getDescriptorForType();
        DynamicMessage message = newMessageFromSchema
                .setField(messageDescriptor.findFieldByName("id"), 1)
                .setField(messageDescriptor.findFieldByName("name"), "Tharinda Dilshan")
                .setField(messageDescriptor.findFieldByName("phone"), subMessage)
                .setField(messageDescriptor.findFieldByName("home"), "home address")
                .setField(messageDescriptor.findFieldByName("student"), "ucsc")
                .build();

        // Create a byte array using the populated message
        byte[] bytes = message.toByteArray();
```

