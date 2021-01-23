import com.google.protobuf.Descriptors;
import com.google.protobuf.DynamicMessage;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;

public class SerDes {
    public static void main(String[] args) throws Descriptors.DescriptorValidationException {

        ProtobufMessage nestedMessageBuilder = ProtobufMessage.newMessageBuilder("Phone")
                .addField("required", "string", "mobile", 1, "0773256")
                .addField("optional", "string", "home", 2, "4567892")
                .build();

        ProtobufSchemaBuilder schemaBuilder = ProtobufSchemaBuilder.newSchemaBuilder("Student.proto");
        ProtobufMessage messageBuilder = ProtobufMessage.newMessageBuilder("StudentMsg") // message Student
                .addField("required", "int32", "id", 1)        // required int32 id = 1
                .addField("required", "string", "name", 2)    // required string name = 2
                .addNestedMessage(nestedMessageBuilder)
                .addField("optional", "Phone", "phone", 3)
                .build();

        schemaBuilder.addMessageToProtoSchema(messageBuilder);
        Descriptors.Descriptor schema = schemaBuilder.build();

        DynamicMessage.Builder newMessageFromSchema = DynamicMessage.newBuilder(schema);
        Descriptors.Descriptor messageDescriptor = newMessageFromSchema.getDescriptorForType();
//        System.out.println(messageDescriptor.getFields());

        Descriptors.Descriptor desc = schema.findNestedTypeByName("Phone");
        DynamicMessage subMsg = DynamicMessage.newBuilder(schema.findNestedTypeByName("Phone"))
                .setField(desc.findFieldByName("mobile"), "74848")
                .setField(desc.findFieldByName("home"), "8745")
                .build();

        DynamicMessage message = newMessageFromSchema
                .setField(messageDescriptor.findFieldByName("id"), 1)
                .setField(messageDescriptor.findFieldByName("name"), "Tharinda Dilshan")
                .setField(messageDescriptor.findFieldByName("phone"), subMsg)
                .build();

        byte[] bytes = message.toByteArray();

        try {
            DynamicMessage des = DynamicMessage.parseFrom(messageDescriptor, bytes);
            System.out.println(des);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }

    }
}
