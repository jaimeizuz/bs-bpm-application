package es.santander.scib.gboentities.servicetasks.exception;

import org.kie.kogito.process.workitem.WorkItemExecutionException;

public class MeiAlreadyExistsException extends WorkItemExecutionException {

    public MeiAlreadyExistsException(String message) {
        super(MeiAlreadyExistsException.class.getClass().getSimpleName(), message);
    }
}
