package org.nuxeo.training.contractsmanagement;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.event.EventService;
import org.nuxeo.ecm.core.event.impl.EventListenerDescriptor;
import org.nuxeo.ecm.platform.test.PlatformFeature;
import org.nuxeo.runtime.test.runner.Deploy;
import org.nuxeo.runtime.test.runner.Features;
import org.nuxeo.runtime.test.runner.FeaturesRunner;

import com.google.inject.Inject;

@RunWith(FeaturesRunner.class)
@Features({ PlatformFeature.class })
@Deploy({"studio.extensions.esteiner-SANDBOX","org.nuxeo.training.contractsmanagement.contractsmanagement-core"})
public class TestCustomCreationHandler {

    protected final List<String> events = Arrays.asList("emptyDocumentModelCreated");

    @Inject
    CoreSession session;

    @Inject
    protected EventService s;

    @Test
    public void listenerRegistration() {
        EventListenerDescriptor listener = s.getEventListener("customcreationhandler");
        assertNotNull(listener);
        assertTrue(events.stream().allMatch(listener::acceptEvent));
    }

    @Test
    public void checkContractTitle() {
        DocumentModel contract = session.createDocumentModel("/", "Contract", "Contract");
        contract.setPropertyValue("contract:contract-schema", "test");
        contract.setPropertyValue("required", true);
        contract = session.createDocument(contract);
        session.save();
        Assert.assertEquals("Title set from Java code", contract.getPropertyValue("dc:title"));
    }
}
