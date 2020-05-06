package com.r00ta.telematics.platform.mongo;

import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClientSettings;
import com.r00ta.telematics.platform.SmartDocument;
import org.bson.BsonReader;
import org.bson.BsonString;
import org.bson.BsonValue;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.CollectibleCodec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.json.JsonWriterSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class DocumentCodec<T extends SmartDocument> implements CollectibleCodec<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentCodec.class);

    private final Codec<Document> documentCodec;

    private static final ObjectMapper mapper = new ObjectMapper();

    private final Class<T> clazz;

    private JsonWriterSettings settings;

    public DocumentCodec(Class<T> clazz) {
        this.clazz = clazz;
        this.documentCodec = MongoClientSettings.getDefaultCodecRegistry().get(Document.class);
        settings = JsonWriterSettings.builder()
                .int64Converter((value, writer) -> writer.writeNumber(value.toString())).build();

    }

    @Override
    public void encode(BsonWriter writer, T result, EncoderContext encoderContext) {
        try {
            Document myDoc = Document.parse(mapper.writeValueAsString(result));
            documentCodec.encode(writer, myDoc, encoderContext);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Class<T> getEncoderClass() {
        return clazz;
    }

    @Override
    public T generateIdIfAbsentFromDocument(T document) {
        if (!documentHasId(document)) {
            document.documentId = (UUID.randomUUID().toString());
        }
        return document;
    }

    @Override
    public boolean documentHasId(T document) {
        return document.documentId != null;
    }

    @Override
    public BsonValue getDocumentId(T document) {
        return new BsonString(document.documentId);
    }

    @Override
    public T decode(BsonReader reader, DecoderContext decoderContext) {
        Document document = documentCodec.decode(reader, decoderContext);

        try {
            LOGGER.info("SUCA");
            LOGGER.info(document.toJson(settings));
            return mapper.readValue(document.toJson(settings), clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}