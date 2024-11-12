package es.santander.scib.corporatetradeparties.servicetasks;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.ProcessingException;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import es.santander.scib.corporatetradeparties.rest.client.ApiException;
import es.santander.scib.corporatetradeparties.rest.client.PartiesApi;
import es.santander.scib.corporatetradeparties.rest.dto.WrapperCreatePartyResponse;

@ApplicationScoped
public class PartiesServiceTask {

    @Inject
    @RestClient
    PartiesApi partiesRestClient;

    Logger logger = Logger.getLogger(PartiesServiceTask.class);

    public void createParty () {

        WrapperCreatePartyResponse response = null;

        try {
            response = partiesRestClient.createParty(null, null, null);

        } catch (ProcessingException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        } catch (ApiException e) {
            int httpResponseCode = e.getResponse().getStatus();
            String httpResponseMessage = e.getMessage();
            e.printStackTrace();
        }

        logger.info("PARTY CREATED: " + response.toString());
    }
}
