import com.google.protobuf.DescriptorProtos;
import com.google.protobuf.DescriptorProtos.OneofDescriptorProto;

public class ProtobufMessageBuilder {
    // Describes a message type
    private DescriptorProtos.DescriptorProto.Builder messageBuilder;
    private int oneofFieldIndex = 0;

    // Constructor with message name as parameter
    ProtobufMessageBuilder(String messageName) {
        messageBuilder = DescriptorProtos.DescriptorProto.newBuilder();
        messageBuilder.setName(messageName);
    }

    // Add message field without default value
    public ProtobufMessageBuilder addField(String label, String type, String name, int number) {
        DescriptorProtos.FieldDescriptorProto.Label fieldLabel = ProtobufMessageField.getFieldLabel(label);
        addField(fieldLabel, type, name, number, null, null);
        return this;
    }

    // Add message field with default value
    public ProtobufMessageBuilder addField(String label, String type, String name, int number, String defaultValue) {
        DescriptorProtos.FieldDescriptorProto.Label fieldLabel = ProtobufMessageField.getFieldLabel(label);
        addField(fieldLabel, type, name, number, defaultValue, null);
        return this;
    }

    public ProtobufMessageBuilder addNestedMessage(ProtobufMessage nestedMessage){
        messageBuilder.addNestedType(nestedMessage.getProtobufMessage());
        return this;
    }

    public OneofFieldBuilder addOneofField(String oneofFieldName) {
        messageBuilder.addOneofDecl(OneofDescriptorProto.newBuilder().setName(oneofFieldName).build());
        return new OneofFieldBuilder(this, oneofFieldIndex++);
    }

    public ProtobufMessage build() {

        return new ProtobufMessage(messageBuilder.build());
    }

    // Add a single field to a message
    public void addField(DescriptorProtos.FieldDescriptorProto.Label label, String type, String name, int number, String defaultValue, OneofFieldBuilder oneofFieldBuilder) {
        // Describes a field within a message.
        DescriptorProtos.FieldDescriptorProto.Builder messageFieldBuilder = DescriptorProtos.FieldDescriptorProto.newBuilder();

        messageFieldBuilder.setLabel(label);
        messageFieldBuilder.setName(name);
        messageFieldBuilder.setNumber(number);

        DescriptorProtos.FieldDescriptorProto.Type fieldType = ProtobufMessageField.getFieldType(type);
        if (fieldType != null) {
            // Primitive types
         messageFieldBuilder.setType(fieldType);
       } else {
            // TYPE_ENUM, TYPE_MESSAGE or TYPE_GROUP
            messageFieldBuilder.setTypeName(type);
        }

        if (defaultValue != null) {
            messageFieldBuilder.setDefaultValue(defaultValue);
        }

        if (oneofFieldBuilder != null) {
            messageFieldBuilder.setOneofIndex(oneofFieldBuilder.getMessageIndex());
        }

        messageBuilder.addField(messageFieldBuilder.build());
    }
}
