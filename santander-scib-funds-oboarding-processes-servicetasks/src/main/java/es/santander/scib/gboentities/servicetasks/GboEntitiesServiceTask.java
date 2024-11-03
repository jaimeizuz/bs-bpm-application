package es.santander.scib.gboentities.servicetasks;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.ProcessingException;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;
import org.kie.kogito.process.workitem.WorkItemExecutionException;

import es.santander.scib.gboentities.rest.client.ApiException;
import es.santander.scib.gboentities.rest.client.EntitiesApi;
import es.santander.scib.gboentities.rest.dto.Entity;
import es.santander.scib.gboentities.servicetasks.exception.GboEntityNotFoundException;
import io.quarkus.logging.Log;

@ApplicationScoped
public class GboEntitiesServiceTask {

    @Inject
    @RestClient
    EntitiesApi entitiesRestClient;

    Logger logger = Logger.getLogger(GboEntitiesServiceTask.class);

    public Entity getEntitiesByGlobalLimitCodeSystem(String globalLimitCodeSystemId) {

        Entity entity = new Entity();

        String authToken = "";
        String x_santanderClientId = "";
        String acceptHeader = "";
        String x_santanderDevice = "";
        String x_acceptLanguage = "";
        String x_ifNoneMatch = "";
        String x_ifMatch = "";
        String x_b3TraceId = "";
        String x_b3ParentSpanId = "";
        String x_b3SpanId = "";
        String xB3Sampled = "";

        logger.debug("Invoking getEntitiesByGlobalLimitCodeSystem glcs value: " + globalLimitCodeSystemId);

        logger.debug("Invoking getEntitiesByGlobalLimitCodeSystem service");

        try {

            entity = entitiesRestClient.retrieveEntities(authToken, x_santanderClientId, globalLimitCodeSystemId,
                    acceptHeader, x_santanderDevice, x_acceptLanguage, x_ifNoneMatch, x_ifMatch, x_b3TraceId,
                    x_b3ParentSpanId, x_b3SpanId, xB3Sampled);
            
            logger.debug("getEntitiesByGlobalLimitCodeSystem invoked");

            return entity;
        } catch (ProcessingException e) {
            Log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        } catch (ApiException e) {
            int httpResponseCode = e.getResponse().getStatus();
            String httpResponseMessage = e.getMessage();
            Log.error("Error calling Gbo Entity: [" + httpResponseCode + "] - [" + httpResponseMessage + "]");

            if(httpResponseCode == 404) {
                GboEntityNotFoundException gboEntityNotFoundException = new GboEntityNotFoundException(httpResponseMessage);
                Log.error("Throwin BPMN exception code: [" + gboEntityNotFoundException.getErrorCode() + "]");

                throw new GboEntityNotFoundException(httpResponseMessage);
            }
            else {
                throw new WorkItemExecutionException(String.valueOf(httpResponseCode), httpResponseMessage);
            }
        }
    }
}
