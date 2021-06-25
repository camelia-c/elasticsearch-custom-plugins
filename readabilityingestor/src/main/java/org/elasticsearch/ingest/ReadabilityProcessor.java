package org.elasticsearch.ingest;

import org.elasticsearch.ingest.AbstractProcessor;
import org.elasticsearch.ingest.IngestDocument;
import org.elasticsearch.ingest.Processor;
import org.elasticsearch.ingest.ConfigurationUtils;

import org.elasticsearch.SpecialPermission;
import java.security.AccessController;
import java.security.PrivilegedAction;

import java.util.Collections;
import java.util.Map;
import com.google.gson.Gson;

public final class ReadabilityProcessor extends AbstractProcessor {

    public static final String TYPE = "readability_processor";

    //The param we accept as input along with the document
    private String srcField;

    private SecurityManager sec;

    ReadabilityMetrics readmetrics;

    public ReadabilityProcessor(String tag, String description, String srcField) {
        super(tag, description);
        this.srcField = srcField;
        this.readmetrics = new ReadabilityMetrics();

        this.sec = System.getSecurityManager();
        if (this.sec != null) {
	    //silently passses if permission is granted
	    this.sec.checkPermission(new SpecialPermission());
	}
    }


    @Override
    public String getType(){
        return TYPE;
    }


    @Override
    public IngestDocument execute(IngestDocument ingestDoc) throws Exception {

        IngestDocument inputDoc = ingestDoc;

        if (inputDoc.hasField(srcField)) {
            String initialValue = inputDoc.getFieldValue(this.srcField, String.class);

            MetricsResult metrics = this.readmetrics.end2endProcessing(initialValue);

            //gson requires priviledged mode to be used in ES plugin
            AccessController.doPrivileged(new PrivilegedAction<Void>() {

                public Void run() {
                     Gson gson = new Gson(); 
                     String jsonStr = gson.toJson(metrics); 

                     inputDoc.setFieldValue("_readability", jsonStr);   

                     return null; // nothing to return
                }
            });        

        }
        return inputDoc;
    }


    public static final class Factory implements Processor.Factory {
        @Override
        public Processor create(Map<String, Processor.Factory> registry, 
                                String tag, String description,
                                Map<String, Object> config) throws Exception {
            String srcField = ConfigurationUtils.readStringProperty(TYPE, tag, config, "srcfield");
            return new ReadabilityProcessor(tag, description, srcField); 
        }

    }

}
