import com.google.protobuf.DescriptorProtos;
import com.google.protobuf.DescriptorProtos.FileDescriptorSet;

public class ProtobufSchema {

    private FileDescriptorSet fileDescriptorSet;

    // Creates a new schema builder
    public static ProtobufSchemaBuilder newSchemaBuilder() {
        return new ProtobufSchemaBuilder();
    }

}
