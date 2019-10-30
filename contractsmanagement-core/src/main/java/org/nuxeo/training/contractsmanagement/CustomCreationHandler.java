package org.nuxeo.training.contractsmanagement;


import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.event.Event;
import org.nuxeo.ecm.core.event.EventContext;
import org.nuxeo.ecm.core.event.EventListener;

import org.nuxeo.ecm.core.event.impl.DocumentEventContext;

public class CustomCreationHandler implements EventListener {
  

    @Override
    public void handleEvent(Event event) {
        EventContext ctx = event.getContext();
        if (!(ctx instanceof DocumentEventContext)) {
          return;
        }

        DocumentEventContext docCtx = (DocumentEventContext) ctx;
        DocumentModel doc = docCtx.getSourceDocument();

        if ("Contract".equals(doc.getType()) && !doc.isVersion() && !doc.isImmutable()) {
            doc.setPropertyValue("dc:title", "Title set from Java code");
        }

    }
}
