import com.google.protobuf.DescriptorProtos.FileDescriptorSet;
import com.google.protobuf.Descriptors;
import com.google.protobuf.DynamicMessage;

import java.util.HashMap;
import java.util.Map;

public class ProtobufSchema {

    private FileDescriptorSet fileDescriptorSet;

    // Constructor
    public FileDescriptorSet ProtobufSchema(FileDescriptorSet fileDescriptorSet) {
//        System.out.println(fileDescriptorSet);
        return fileDescriptorSet;
    }

    // Creates a new schema builder
    public static ProtobufSchemaBuilder newSchemaBuilder() {
        return new ProtobufSchemaBuilder();
    }

    public DynamicMessage.Builder newMessageBuilder(String msgTypeName) {
        Descriptors.Descriptor msgType = getMessageDescriptor(msgTypeName);
        if (msgType == null) return null;
        return DynamicMessage.newBuilder(msgType);
    }

    public Descriptors.Descriptor getMessageDescriptor(String msgTypeName) {
        Descriptors.Descriptor msgType = mMsgDescriptorMapShort.get(msgTypeName);
        if (msgType == null) msgType = mMsgDescriptorMapFull.get(msgTypeName);
        return msgType;
    }

    private Map<String, Descriptors.Descriptor> mMsgDescriptorMapFull = new HashMap<String, Descriptors.Descriptor>();
    private Map<String, Descriptors.Descriptor> mMsgDescriptorMapShort = new HashMap<String, Descriptors.Descriptor>();
    private Map<String, Descriptors.EnumDescriptor> mEnumDescriptorMapFull = new HashMap<String, Descriptors.EnumDescriptor>();
    private Map<String, Descriptors.EnumDescriptor> mEnumDescriptorMapShort = new HashMap<String, Descriptors.EnumDescriptor>();

}
