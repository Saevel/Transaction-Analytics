package org.dmcs.transaction.analytics.gradle;

import org.gradle.api.Project;
import org.gradle.api.Task;

public class SubprojectTaskCall {

    public static void callTaskIfExists(Project project, String name){
        project.getSubprojects().forEach(subproject -> {
            Task task = subproject.getTasks().findByName(name);
            if(task != null) {
                task.getActions().forEach(action -> action.execute(task));
            }
        });
    }
}
