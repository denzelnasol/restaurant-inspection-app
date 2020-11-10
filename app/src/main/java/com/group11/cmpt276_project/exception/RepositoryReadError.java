package com.group11.cmpt276_project.exception;

public class RepositoryReadError extends  Exception{
    public RepositoryReadError(String errorMessage) {
        super(errorMessage);
    }
}
