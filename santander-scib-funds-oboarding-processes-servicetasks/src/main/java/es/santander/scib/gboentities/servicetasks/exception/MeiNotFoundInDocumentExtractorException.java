package es.santander.scib.gboentities.servicetasks.exception;

import org.kie.kogito.process.workitem.WorkItemExecutionException;

public class MeiNotFoundInDocumentExtractorException extends WorkItemExecutionException {

    public MeiNotFoundInDocumentExtractorException(String message) {
        super(MeiNotFoundInDocumentExtractorException.class.getClass().getSimpleName(), message);
    }
}
