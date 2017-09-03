package org.dmcs.transaction.analytics.docker;

import java.io.File;


public class DockerfileScout {

    Boolean hasDockerDeployables(String subprojectName, String dockerfilePath) {
        File possibleDockerfile = new File(subprojectName.replaceFirst(":", "").replace(":", "/") + "/" + dockerfilePath + "/" + "Dockerfile");
        return possibleDockerfile.exists();
    }

    Boolean hasDockerDeployables(String subprojectName){
        return hasDockerDeployables(subprojectName, "src/main/docker");
    }
}
