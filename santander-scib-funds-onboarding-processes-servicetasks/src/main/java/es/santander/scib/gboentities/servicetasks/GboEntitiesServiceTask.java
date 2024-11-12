package es.santander.scib.gboentities.servicetasks;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;
import org.kie.kogito.internal.process.workitem.WorkItemExecutionException;

import es.santander.scib.gboentities.rest.client.ApiException;
import es.santander.scib.gboentities.rest.client.EntitiesApi;
import es.santander.scib.gboentities.rest.dto.Entity;
import es.santander.scib.gboentities.servicetasks.exception.GboEntityNotFoundException;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.ProcessingException;

@ApplicationScoped
public class GboEntitiesServiceTask {

    @Inject
    @RestClient
    EntitiesApi entitiesRestClient;

    Logger logger = Logger.getLogger(GboEntitiesServiceTask.class);

    public Entity getEntitiesByGlobalLimitCodeSystem(String glcs, String mei, String lei) {

        Entity entity = new Entity();

        String authorization = "";
        String xSantanderClientId = "";
        String xSantanderDevice = "";
        String acceptLanguage = "";
        String ifNoneMatch = "";
        String ifMatch = "";
        String xB3Traceid = "";
        String xB3Parentspanid = "";
        String xB3Spanid = "";
        String xB3Sampled = "";

        logger.debug("Invoking getEntitiesByGlobalLimitCodeSystem glcs value: " + glcs);

        logger.debug("Invoking getEntitiesByGlobalLimitCodeSystem service");

        try {

            entity = entitiesRestClient.retrieveEntities(authorization, xSantanderClientId, xSantanderDevice, 
                acceptLanguage, ifNoneMatch, ifMatch, xB3Traceid, xB3Parentspanid, xB3Spanid, xB3Sampled,
                glcs, mei, lei);
            
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
