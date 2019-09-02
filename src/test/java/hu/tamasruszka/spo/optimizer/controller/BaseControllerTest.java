package hu.tamasruszka.spo.optimizer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import hu.tamasruszka.spo.optimizer.model.http.HttpResponseObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class BaseControllerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseControllerTest.class);

    private static final String LOCALHOST_URL_PREFIX = "http://localhost:";

    @LocalServerPort
    private int port;

    private ObjectMapper objectMapper;

    @PostConstruct
    public void init() {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * This is a POST type http request. It returns with the response data casted to the given type as list.
     *
     * @param url          The request URL
     * @param postParam    The body of the request
     * @param responseType The response data's type
     * @return The response object, which contains the retrieved data
     */
    <S, R> HttpResponseObject<List<R>> postJsonReturnsList(String url, S postParam, Class<R> responseType) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);

            if (postParam != null) {
                HttpEntity httpEntity = new StringEntity(serialize(postParam), ContentType.APPLICATION_JSON);

                httpPost.setEntity(httpEntity);
            }

            return httpclient.execute(httpPost, getListResponseHandler(responseType));
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return null;
    }

    /**
     * Its a response handler implementation used by the apache http client to execute the http request
     *
     * @param responseType The response data's type
     * @return The response handler implementation for the http client
     */
    private <T> ResponseHandler<HttpResponseObject<List<T>>> getListResponseHandler(Class<T> responseType) {
        return response -> {
            HttpResponseObject<List<T>> httpResponseObject = new HttpResponseObject<>();

            int status = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            String responseBodyString = entity != null ? EntityUtils.toString(entity) : null;

            httpResponseObject.setResponseAsString(responseBodyString);

            if (status >= 200 && status < 300) {
                if (responseType != null) {
                    httpResponseObject.setResponse(deserializeList(responseBodyString, responseType));
                }
            } else {
                LOGGER.error("[{}] Error during HTTP request: {}", status, responseBodyString);
            }

            httpResponseObject.setStatusCode(status);

            return httpResponseObject;
        };
    }

    private <T> String serialize(T object) {
        if (object == null) {
            return "";
        }

        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage(), e);

            return "";
        }
    }

    /**
     * Deserialize the given json string to the provided target class. If the input string is null, then it returns null
     *
     * @param jsonString This string contains the JSOn data
     * @param <T>        Data type of the class parameter and the return value
     * @return The deserialization result object
     */
    private <T> List<T> deserializeList(String jsonString, Class<T> responseType) {
        if (jsonString == null) {
            return null;
        }

        try {
            CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(List.class, responseType);

            return objectMapper.readValue(jsonString, listType);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);

            return null;
        }
    }

    String getServerRootUrl() {
        return LOCALHOST_URL_PREFIX + port;
    }
}
