import com.google.protobuf.DescriptorProtos;
import com.google.protobuf.Descriptors;
import com.google.protobuf.DynamicMessage;

public class SerDes {
    public static void main(String[] args) throws Descriptors.DescriptorValidationException {
        ProtobufSchemaBuilder schemaBuilder = ProtobufSchemaBuilder.newSchemaBuilder();
        ProtobufMessage messageBuilder = ProtobufMessage.newMessageBuilder("Student") // message Person
                .addField("required", "int32", "id", 1)        // required int32 id = 1
                .addField("required", "string", "name", 2)    // required string name = 2
                .build();

        schemaBuilder.addMessageToProtoSchema(messageBuilder);
        Descriptors.Descriptor schema = schemaBuilder.build();

        DynamicMessage.Builder newMessageFromSchema = DynamicMessage.newBuilder(schema);
        Descriptors.Descriptor messageDescriptor = newMessageFromSchema.getDescriptorForType();
        DynamicMessage message = newMessageFromSchema
                .setField(messageDescriptor.findFieldByName("id"), 1)
                .setField(messageDescriptor.findFieldByName("name"), "Tharinda Dilshan")
                .build();
        System.out.println(message);

    }
}
