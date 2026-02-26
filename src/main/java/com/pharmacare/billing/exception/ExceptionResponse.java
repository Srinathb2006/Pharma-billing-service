package com.pharmacare.billing.exception;

import java.time.LocalDateTime;

public class ExceptionResponse {

    private String message;
    private int status;
    private LocalDateTime timestamp;
    private String path;

    
    public ExceptionResponse() {}

   
    public ExceptionResponse(String message, int status,
                             LocalDateTime timestamp, String path) {
        this.message = message;
        this.status = status;
        this.timestamp = timestamp;
        this.path = path;
    }

   
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String message;
        private int status;
        private LocalDateTime timestamp;
        private String path;

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder status(int status) {
            this.status = status;
            return this;
        }

        public Builder timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder path(String path) {
            this.path = path;
            return this;
        }

        public ExceptionResponse build() {
            return new ExceptionResponse(message, status, timestamp, path);
        }
    }

    

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
