import com.google.protobuf.DescriptorProtos;
import com.google.protobuf.DescriptorProtos.FileDescriptorSet;
import com.google.protobuf.Descriptors;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ProtobufSchema {

    private FileDescriptorSet fileDescriptorSet;

    // Creates a new schema builder
    public static SchemaBuilder newSchemaBuilder() {
        return new SchemaBuilder();
    }


    public static class SchemaBuilder {
        // Describes a complete .proto file
        private DescriptorProtos.FileDescriptorProto.Builder fileDescriptorProtoBuilder;
        // FileDescriptorSet containing the .proto files the compiler parses.
        private FileDescriptorSet.Builder fileDescriptorSetBuilder;

        // Constructor
        private SchemaBuilder() {
            fileDescriptorProtoBuilder = DescriptorProtos.FileDescriptorProto.newBuilder();
            fileDescriptorSetBuilder = DescriptorProtos.FileDescriptorSet.newBuilder();
            fileDescriptorProtoBuilder.setName("DynamicSchema.proto");
        }

        // Add message to proto schema
        public SchemaBuilder addMessageToProtoSchema(ProtobufMessage message) {
            DescriptorProtos.DescriptorProto protobufMessage = message.getProtobufMessage();
            fileDescriptorProtoBuilder.addMessageType(protobufMessage);
            return this;
        }

        // Compiles the .proto file and outputs a FileDescriptorSet
        public ProtobufSchema build() {
            FileDescriptorSet.Builder newFileDescriptorSetBuilder = FileDescriptorSet.newBuilder();
            newFileDescriptorSetBuilder.addFile(fileDescriptorProtoBuilder.build());
            newFileDescriptorSetBuilder.mergeFrom(fileDescriptorSetBuilder.build());
            return new ProtobufSchema(newFileDescriptorSetBuilder.build());
        }
    }
}
