package es.santander.scib.gboentities.servicetasks;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.ProcessingException;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;
import org.kie.kogito.process.workitem.WorkItemExecutionException;

import es.santander.scib.dummy.rest.client.DummyApi;
import es.santander.scib.gboentities.rest.client.ApiException;

@ApplicationScoped
public class DummyServiceTask {

    @Inject
    @RestClient
    DummyApi dummyRestClient;

    Logger logger = Logger.getLogger(DummyServiceTask.class);

    public void callDummy() {

        try {
            dummyRestClient.getDummy();
        } catch (ProcessingException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        } catch (ApiException e) {
            logger.error(e.getMessage());
            throw new WorkItemExecutionException(String.valueOf(e.getResponse().getStatus()), e.getMessage());
        }
    }
}
