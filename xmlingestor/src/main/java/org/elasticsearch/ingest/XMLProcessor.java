package org.elasticsearch.ingest;

import org.elasticsearch.ingest.AbstractProcessor;
import org.elasticsearch.ingest.IngestDocument;
import org.elasticsearch.ingest.Processor;
import org.elasticsearch.ingest.ConfigurationUtils;

import org.json.JSONObject;
import org.json.XML;
import org.json.JSONException;

import java.util.Map;


public final class XMLProcessor extends AbstractProcessor {

    public static final String TYPE = "xml_processor";

    //The param we accept as input along with the document
    private String srcField;

    public XMLProcessor(String tag, String description, String srcField) {
        super(tag, description);
        this.srcField = srcField;
    }

    @Override
    public String getType(){
        return TYPE;
    }

    @Override
    public IngestDocument execute(IngestDocument ingestDoc) throws Exception {

        IngestDocument inputDoc = ingestDoc;

        if (inputDoc.hasField(srcField)) {
            String initialXML = inputDoc.getFieldValue(this.srcField, String.class);

            JSONObject convertedJSON = XML.toJSONObject(initialXML);
            
            inputDoc.setFieldValue("_converted_json", convertedJSON.toString());           

        }
        return inputDoc;

    }


    public static final class Factory implements Processor.Factory {
        @Override
        public Processor create(Map<String, Processor.Factory> registry, 
                                String tag, String description,
                                Map<String, Object> config) throws Exception {
            String srcField = ConfigurationUtils.readStringProperty(TYPE, tag, config, "srcfield");
            return new XMLProcessor(tag, description, srcField); 
        }

    }
    

}
