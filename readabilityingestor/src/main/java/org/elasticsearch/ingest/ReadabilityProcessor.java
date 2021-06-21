package org.elasticsearch.ingest;

import org.elasticsearch.ingest.AbstractProcessor;
import org.elasticsearch.ingest.IngestDocument;
import org.elasticsearch.ingest.Processor;
import org.elasticsearch.ingest.ConfigurationUtils;

import java.util.Collections;
import java.util.Map;
import com.google.gson.Gson;

public final class ReadabilityProcessor extends AbstractProcessor {

    public static final String TYPE = "readability_processor";

    //The param we accept as input along with the document
    private String srcField;

    ReadabilityMetrics readmetrics;

    public ReadabilityProcessor(String tag, String description, String srcField) {
        super(tag, description);
        this.srcField = srcField;
        this.readmetrics = new ReadabilityMetrics();
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

            Map<String, Float> metrics = this.readmetrics.computeMetrics(initialValue);

            Gson gson = new Gson(); 
            String jsonStr = gson.toJson(metrics); 

            inputDoc.setFieldValue("_readability", jsonStr);           

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
