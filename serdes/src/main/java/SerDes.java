import com.google.protobuf.Descriptors;
import com.google.protobuf.DynamicMessage;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;

public class SerDes {
    public static void main(String[] args) throws Descriptors.DescriptorValidationException {
        ProtobufMessage nestedMessageBuilder = ProtobufMessage.newMessageBuilder("PhoneMsg")
                .addField("required", "string", "mobile", 1, "0773256")
                .addField("required", "string", "home", 2, "4567892")
                .build();

        ProtobufSchemaBuilder schemaBuilder = ProtobufSchemaBuilder.newSchemaBuilder("Student.proto");
        ProtobufMessage messageBuilder = ProtobufMessage.newMessageBuilder("StudentMsg") // message Student
                .addField("required", "int32", "id", 1)        // required int32 id = 1
                .addField("required", "string", "name", 2)    // required string name = 2
                .addNestedMessage(nestedMessageBuilder)
                .build();
//        System.out.println(messageBuilder.getProtobufMessage());

        schemaBuilder.addMessageToProtoSchema(messageBuilder);
        Descriptors.Descriptor schema = schemaBuilder.build();
//        System.out.println(schema.toProto());

        DynamicMessage.Builder newMessageFromSchema = DynamicMessage.newBuilder(schema);
        Descriptors.Descriptor messageDescriptor = newMessageFromSchema.getDescriptorForType();
//        System.out.println(messageDescriptor.findNestedTypeByName("PhoneMsg").getFields());

        /* Testing */

        Descriptors.Descriptor desc = newMessageFromSchema.getDescriptorForType().findNestedTypeByName("PhoneMsg");
        DynamicMessage.Builder subMsg = DynamicMessage.newBuilder(desc);
        Descriptors.Descriptor subMsgDes = subMsg.getDescriptorForType();
        subMsg.setField(subMsgDes.findFieldByName("mobile"), "74848")
                .setField(subMsgDes.findFieldByName("home"), "8745");
//                .build();
//        System.out.println(subMsg);

        /* End Testing */

        DynamicMessage message = newMessageFromSchema
                .setField(messageDescriptor.findFieldByName("id"), 1)
                .setField(messageDescriptor.findFieldByName("name"), "Tharinda Dilshan")
//                .setField(subMsgDes.findFieldByName("mobile"), "789456")
//                .setField(messageDescriptor.findNestedTypeByName("PhoneMsg").findFieldByName("mobile"), subMsg.build())
                .build();

//        System.out.println(message);

        byte[] bytes = message.toByteArray();

        try {
            DynamicMessage des = DynamicMessage.parseFrom(messageDescriptor, bytes);
//            System.out.println(des);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }

    }
}
