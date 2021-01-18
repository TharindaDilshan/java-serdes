import com.google.protobuf.DescriptorProtos;
import com.google.protobuf.Descriptors;
import com.google.protobuf.DynamicMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SerDes {
    public static void main(String[] args) throws Descriptors.DescriptorValidationException {
        ProtobufSchemaBuilder schemaBuilder = ProtobufSchema.newSchemaBuilder();
        ProtobufMessage messageBuilder = ProtobufMessage.newMessageBuilder("Student") // message Person
                .addField("required", "int32", "id", 1)        // required int32 id = 1
                .addField("required", "string", "name", 2)    // required string name = 2
                .build();

        schemaBuilder.addMessageToProtoSchema(messageBuilder);
//      ProtobufSchema schema =
        DescriptorProtos.FileDescriptorSet schema = schemaBuilder.build();
        Descriptors.FileDescriptor fdd = null;
        for (DescriptorProtos.FileDescriptorProto fdProto : schema.getFileList()) {
            List<String> dependencyList = fdProto.getDependencyList();
            List<Descriptors.FileDescriptor> resolvedFdList = new ArrayList<Descriptors.FileDescriptor>();
//            resolvedFdList.add(fd);
            Map<String, Descriptors.FileDescriptor> resolvedFileDescMap = new HashMap<String, Descriptors.FileDescriptor>();
            for (String depName : dependencyList) {
                Descriptors.FileDescriptor fd = resolvedFileDescMap.get(depName);
                if (fd != null) resolvedFdList.add(fd);
            }
            Descriptors.FileDescriptor[] fds = new Descriptors.FileDescriptor[resolvedFdList.size()];
            fdd = Descriptors.FileDescriptor.buildFrom(fdProto,resolvedFdList.toArray(fds));
        }
        Descriptors.Descriptor msgBuild = null;
        for (Descriptors.Descriptor msgType : fdd.getMessageTypes()){
            msgBuild = msgType;
        }

        DynamicMessage.Builder msg = DynamicMessage.newBuilder(msgBuild);
//        System.out.println(msg.getDescriptorForType());
        Descriptors.Descriptor msgDesc = msg.getDescriptorForType();
//
//        System.out.println(msgDesc.getFields());
        DynamicMessage msg2 = msg
                .setField(msgDesc.findFieldByName("id"), 1)
                .setField(msgDesc.findFieldByName("name"), "Alan Turing")
                .build();
        System.out.println(msg2);


//        System.out.println(messageBuilder.toString());
    }
}
