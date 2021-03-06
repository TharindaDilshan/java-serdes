import com.google.protobuf.DescriptorProtos.FieldDescriptorProto.Label;

public class OneofFieldBuilder {
    private ProtobufMessageBuilder messageBuilder;
    private int messageIndex;

    public OneofFieldBuilder(ProtobufMessageBuilder protobufMessageBuilder, int index) {
        messageBuilder = protobufMessageBuilder;
        messageIndex = index;
    }

    public int getMessageIndex() {
        return messageIndex;
    }

    public OneofFieldBuilder addField(String type, String name, int number) {
        return addField(type, name, number, null); 
    }

    public OneofFieldBuilder addField(String type, String name, int number, String defaultValue) {
        messageBuilder.addField(Label.LABEL_OPTIONAL, type, name, number, defaultValue, this);
        return this;
    }

    public ProtobufMessageBuilder oneofMessageBuilder() {
        return messageBuilder;
    }
}
