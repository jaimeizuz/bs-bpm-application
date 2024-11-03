package es.santander.scib.gboentities.servicetasks.exception;

import org.kie.kogito.process.workitem.WorkItemExecutionException;

public class GboEntityNotFoundException extends WorkItemExecutionException {

    public GboEntityNotFoundException(String message) {
        super("GboEntityNotFoundException", message);
    }
}
