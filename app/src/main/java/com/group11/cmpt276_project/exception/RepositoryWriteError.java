package com.group11.cmpt276_project.exception;

/**
 * This class reads a repo write error message
 */
public class RepositoryWriteError extends Exception {
    public RepositoryWriteError(String errorMessage) {
        super(errorMessage);
    }
}
