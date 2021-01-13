import com.google.protobuf.DescriptorProtos;

public class ProtobufSchema {

    public static SchemaBuilder newSchemaBuilder() {
        return new SchemaBuilder();
    }

    public static class SchemaBuilder {
        // Describes a complete .proto file
        private DescriptorProtos.FileDescriptorProto.Builder fileDescriptorProtoBuilder;
        // FileDescriptorSet containing the .proto files the compiler parses.
        private DescriptorProtos.FileDescriptorSet.Builder fileDescriptorSetBuilder;

        // Constructor
        private SchemaBuilder() {
            fileDescriptorProtoBuilder = DescriptorProtos.FileDescriptorProto.newBuilder();
            fileDescriptorSetBuilder = DescriptorProtos.FileDescriptorSet.newBuilder();
            fileDescriptorProtoBuilder.setName("DynamicSchema.proto");
        }
    }
}
