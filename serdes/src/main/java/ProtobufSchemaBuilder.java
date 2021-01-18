import com.google.protobuf.DescriptorProtos;

public class ProtobufSchemaBuilder {
    // Describes a complete .proto file
    private DescriptorProtos.FileDescriptorProto.Builder fileDescriptorProtoBuilder;
    // FileDescriptorSet containing the .proto files the compiler parses.
    private DescriptorProtos.FileDescriptorSet.Builder fileDescriptorSetBuilder;

    // Constructor
    public ProtobufSchemaBuilder() {
        fileDescriptorProtoBuilder = DescriptorProtos.FileDescriptorProto.newBuilder();
        fileDescriptorSetBuilder = DescriptorProtos.FileDescriptorSet.newBuilder();
        fileDescriptorProtoBuilder.setName("DynamicSchema.proto");
    }

    // Add message to proto schema
    public ProtobufSchemaBuilder addMessageToProtoSchema(ProtobufMessage message) {
        DescriptorProtos.DescriptorProto protobufMessage = message.getProtobufMessage();
        fileDescriptorProtoBuilder.addMessageType(protobufMessage);
        return this;
    }

    // Compiles the .proto file and outputs a FileDescriptorSet
    public void build() {
        DescriptorProtos.FileDescriptorSet.Builder newFileDescriptorSetBuilder = DescriptorProtos.FileDescriptorSet.newBuilder();
        newFileDescriptorSetBuilder.addFile(fileDescriptorProtoBuilder.build());
        newFileDescriptorSetBuilder.mergeFrom(fileDescriptorSetBuilder.build());
//        return new ProtobufSchema(newFileDescriptorSetBuilder.build());
    }
}
