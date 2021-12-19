package com.jmgits.transactionreport.base.controller;

import com.jmgits.transactionreport.base.exception.GeneralException;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@Controller
@RequestMapping({"${server.error.path:${error.path:/error}}"})
public class CustomErrorController extends AbstractErrorController {

    @Configuration
    @ConfigurationProperties(prefix = "server.error")
    public static class CustomErrorProperties extends ErrorProperties {

    }

    private final ErrorAttributes errorAttributes;
    private final ErrorProperties errorProperties;

    public CustomErrorController(ErrorAttributes errorAttributes, ErrorProperties errorProperties) {
        super(errorAttributes);

        this.errorAttributes = errorAttributes;
        this.errorProperties = errorProperties;
    }

    @DeleteMapping
    public ResponseEntity<Map<String, Object>> deleteError(HttpServletRequest request) {
        return errorInternal(request);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getError(HttpServletRequest request) {
        return errorInternal(request);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> postError(HttpServletRequest request) {
        return errorInternal(request);
    }

    @PutMapping
    public ResponseEntity<Map<String, Object>> putError(HttpServletRequest request) {
        return errorInternal(request);
    }

    @Override
    protected Map<String, Object> getErrorAttributes(HttpServletRequest request, ErrorAttributeOptions options) {

        var values = super.getErrorAttributes(request, options);

        var error = this.errorAttributes.getError(new ServletWebRequest(request));

        if (error instanceof GeneralException) {

            GeneralException generalException = (GeneralException) error;

            values.put("errorCode", generalException.getCode());
            values.put("errorDescription", generalException.getDescription());
        }

        return values;
    }

    //
    // private

    private ResponseEntity<Map<String, Object>> errorInternal(HttpServletRequest request) {

        var status = this.getStatus(request);

        if (status == NO_CONTENT) {
            return new ResponseEntity<>(status);
        } else {

            var body = this.getErrorAttributes(request, this.getErrorAttributeOptions(request));

            return new ResponseEntity<>(body, status);
        }
    }

    private ErrorAttributeOptions getErrorAttributeOptions(HttpServletRequest request) {

        var options = ErrorAttributeOptions.defaults();

        if (this.errorProperties.isIncludeException()) {
            options = options.including(ErrorAttributeOptions.Include.EXCEPTION);
        }

        if (this.isIncludeStackTrace(request)) {
            options = options.including(ErrorAttributeOptions.Include.STACK_TRACE);
        }

        if (this.isIncludeMessage(request)) {
            options = options.including(ErrorAttributeOptions.Include.MESSAGE);
        }

        if (this.isIncludeBindingErrors(request)) {
            options = options.including(ErrorAttributeOptions.Include.BINDING_ERRORS);
        }

        return options;
    }

    private boolean isIncludeStackTrace(HttpServletRequest request) {
        switch (this.errorProperties.getIncludeStacktrace()) {
            case ALWAYS:
                return true;
            case ON_PARAM:
                return this.getTraceParameter(request);
            default:
                return false;
        }
    }

    private boolean isIncludeMessage(HttpServletRequest request) {
        switch (this.errorProperties.getIncludeMessage()) {
            case ALWAYS:
                return true;
            case ON_PARAM:
                return this.getMessageParameter(request);
            default:
                return false;
        }
    }

    private boolean isIncludeBindingErrors(HttpServletRequest request) {
        switch (this.errorProperties.getIncludeBindingErrors()) {
            case ALWAYS:
                return true;
            case ON_PARAM:
                return this.getErrorsParameter(request);
            default:
                return false;
        }
    }
}
