import com.google.protobuf.DescriptorProtos;
import com.google.protobuf.DescriptorProtos.FieldDescriptorProto;

import java.util.HashMap;
import java.util.Map;

public class ProtobufMessage {
    // Describes a message type
    private DescriptorProtos.DescriptorProto protobufMessage;

    // Constructor
    public ProtobufMessage(DescriptorProtos.DescriptorProto message) {
        protobufMessage = message;
    }

    public static ProtobufMessageBuilder newMessageBuilder(String messageName) {
        return new ProtobufMessageBuilder(messageName);
    }

    public DescriptorProtos.DescriptorProto getProtobufMessage() {
        return protobufMessage;
    }

}
