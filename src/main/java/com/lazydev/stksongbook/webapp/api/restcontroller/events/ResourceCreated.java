package com.lazydev.stksongbook.webapp.api.restcontroller.events;

import org.springframework.context.ApplicationEvent;

import javax.servlet.http.HttpServletResponse;

public class ResourceCreated extends ApplicationEvent {

    private HttpServletResponse response;
    private long idOfNewResource;

    public ResourceCreated(Object source, HttpServletResponse response, long idOfNewResource) {
        super(source);

        this.response = response;
        this.idOfNewResource = idOfNewResource;
    }

    public HttpServletResponse getResponse() {
        return response;
    }
    public long getIdOfNewResource() {
        return idOfNewResource;
    }
}
