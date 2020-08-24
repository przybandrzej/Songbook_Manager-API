package com.lazydev.stksongbook.mailer.client.api;

import com.lazydev.stksongbook.mailer.client.invoker.ApiClient;

import com.lazydev.stksongbook.mailer.client.model.Email;
import com.lazydev.stksongbook.mailer.client.model.MailSentResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-08-25T00:05:28.841+02:00")
@Component("com.lazydev.stksongbook.mailer.client.api.MailOperationsApi")
public class MailOperationsApi {
    private ApiClient apiClient;

    public MailOperationsApi() {
        this(new ApiClient());
    }

    @Autowired
    public MailOperationsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * 
     * Send email
     * <p><b>200</b> - Email was sent
     * @param mail  (required)
     * @return MailSentResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public MailSentResponse sendMail(Email mail) throws RestClientException {
        return sendMailWithHttpInfo(mail).getBody();
    }

    /**
     * 
     * Send email
     * <p><b>200</b> - Email was sent
     * @param mail  (required)
     * @return ResponseEntity&lt;MailSentResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<MailSentResponse> sendMailWithHttpInfo(Email mail) throws RestClientException {
        Object postBody = mail;
        
        // verify the required parameter 'mail' is set
        if (mail == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'mail' when calling sendMail");
        }
        
        String path = UriComponentsBuilder.fromPath("/mail/send").build().toUriString();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = { 
            "application/json"
        };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "application/json"
        };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<MailSentResponse> returnType = new ParameterizedTypeReference<MailSentResponse>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
