package org.dmcs.transaction.analytics.swarm;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;

public class SwarmDeployment {

    public static void generateDescriptor(Stack stack) throws IOException {
       ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
       mapper.writer().writeValue(new File("stack.yml"), stack);
    }
}
