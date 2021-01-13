import com.google.protobuf.DescriptorProtos;
import com.google.protobuf.DescriptorProtos.FieldDescriptorProto;

import java.util.HashMap;
import java.util.Map;

public class ProtobufMessage {
    // Describes a message type
    private DescriptorProtos.DescriptorProto protobufMessage;

    // Constructor
    private ProtobufMessage(DescriptorProtos.DescriptorProto message) {
        protobufMessage = message;
    }

    public static ProtobufMessageBuilder newMessageBuilder(String messageName) {
        return new ProtobufMessageBuilder(messageName);
    }

    public DescriptorProtos.DescriptorProto getProtobufMessage() {
        return protobufMessage;
    }

    public static class ProtobufMessageBuilder {
        // Describes a message type
        private DescriptorProtos.DescriptorProto.Builder messageBuilder;

        // Constructor with message name as parameter
        private ProtobufMessageBuilder(String messageName) {
            messageBuilder = DescriptorProtos.DescriptorProto.newBuilder();
            messageBuilder.setName(messageName);
        }

        // Add message field without default value
        public ProtobufMessageBuilder addField(String label, String type, String name, int number) {
            FieldDescriptorProto.Label fieldLabel = ProtobufMessageField.getFieldLabel(label);
            addField(fieldLabel, type, name, number, null);
            return this;
        }

        // Add message field with default value
        public ProtobufMessageBuilder addField(String label, String type, String name, int number, String defaultValue) {
            FieldDescriptorProto.Label fieldLabel = ProtobufMessageField.getFieldLabel(label);
            addField(fieldLabel, type, name, number, defaultValue);
            return this;
        }

        public ProtobufMessage build() {
            return new ProtobufMessage(messageBuilder.build());
        }

        // Add a single field to a message
        private void addField(FieldDescriptorProto.Label label, String type, String name, int number, String defaultValue) {
            // Describes a field within a message.
            FieldDescriptorProto.Builder messageFieldBuilder = FieldDescriptorProto.newBuilder();

            messageFieldBuilder.setLabel(label);
            messageFieldBuilder.setName(name);
            messageFieldBuilder.setNumber(number);

            FieldDescriptorProto.Type fieldType = ProtobufMessageField.getFieldType(type);
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

            messageBuilder.addField(messageFieldBuilder.build());
        }
    }

    public static class ProtobufMessageField {

        private static Map<String,FieldDescriptorProto.Type> fieldTypes;
        private static Map<String,FieldDescriptorProto.Label> fieldLabels;

        public static FieldDescriptorProto.Type getFieldType(String type) {
            return fieldTypes.get(type);
        }

        public static FieldDescriptorProto.Label getFieldLabel(String type) {
            return fieldLabels.get(type);
        }

        static {
            fieldTypes = new HashMap<String,FieldDescriptorProto.Type>();
            fieldTypes.put("double", FieldDescriptorProto.Type.TYPE_DOUBLE);
            fieldTypes.put("float", FieldDescriptorProto.Type.TYPE_FLOAT);
            fieldTypes.put("int32", FieldDescriptorProto.Type.TYPE_INT32);
            fieldTypes.put("int64", FieldDescriptorProto.Type.TYPE_INT64);
            fieldTypes.put("uint32", FieldDescriptorProto.Type.TYPE_UINT32);
            fieldTypes.put("uint64", FieldDescriptorProto.Type.TYPE_UINT64);
            fieldTypes.put("sint32", FieldDescriptorProto.Type.TYPE_SINT32);
            fieldTypes.put("sint64", FieldDescriptorProto.Type.TYPE_SINT64);
            fieldTypes.put("fixed32", FieldDescriptorProto.Type.TYPE_FIXED32);
            fieldTypes.put("fixed64", FieldDescriptorProto.Type.TYPE_FIXED64);
            fieldTypes.put("sfixed32", FieldDescriptorProto.Type.TYPE_SFIXED32);
            fieldTypes.put("sfixed64", FieldDescriptorProto.Type.TYPE_SFIXED64);
            fieldTypes.put("bool", FieldDescriptorProto.Type.TYPE_BOOL);
            fieldTypes.put("string", FieldDescriptorProto.Type.TYPE_STRING);
            fieldTypes.put("bytes", FieldDescriptorProto.Type.TYPE_BYTES);

            fieldLabels = new HashMap<String,FieldDescriptorProto.Label>();
            fieldLabels.put("optional", FieldDescriptorProto.Label.LABEL_OPTIONAL);
            fieldLabels.put("required", FieldDescriptorProto.Label.LABEL_REQUIRED);
            fieldLabels.put("repeated", FieldDescriptorProto.Label.LABEL_REPEATED);
        }
    }
}
