package com.group11.cmpt276_project.exception;

public class RepositoryWriteError extends Exception {
    public RepositoryWriteError(String errorMessage) {
        super(errorMessage);
    }
}
