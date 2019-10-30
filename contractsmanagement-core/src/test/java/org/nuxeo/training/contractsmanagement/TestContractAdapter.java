package org.nuxeo.training.contractsmanagement;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.lifecycle.LifeCycleService;
import org.nuxeo.ecm.core.test.CoreFeature;
import org.nuxeo.runtime.test.runner.*;
import org.nuxeo.training.contractsmanagement.ContractAdapter;

@RunWith(FeaturesRunner.class)
@Features(CoreFeature.class)
@Deploy({"org.nuxeo.training.contractsmanagement.contractsmanagement-core"})
@Deploy("studio.extensions.esteiner-SANDBOX")

public class TestContractAdapter {
  @Inject
  CoreSession session;

  @Test
  public void shouldCallTheAdapter() {
    String doctype = "Contract";
    String testTitle = "My Adapter Title";

    DocumentModel doc = session.createDocumentModel("/", "test-adapter", doctype);
    ContractAdapter adapter = doc.getAdapter(ContractAdapter.class);
    adapter.setTitle(testTitle);
    adapter.setContractSchema("test");
    adapter.setRequired(true);

    doc = session.createDocument(doc);

    session.save();

    adapter.toNegotiating(doc);

    Assert.assertEquals(doc.getCurrentLifeCycleState(), "negotiating");

  }
}
