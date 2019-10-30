package org.nuxeo.training.contractsmanagement;

import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.adapter.DocumentAdapterFactory;

public class ContractAdapterFactory implements DocumentAdapterFactory {

    @Override
    public Object getAdapter(DocumentModel doc, Class<?> itf) {
        if ("Contract".equals(doc.getType()) && doc.hasSchema("dublincore")){
            return new ContractAdapter(doc);
        }else{
            return null;
        }
    }
}
