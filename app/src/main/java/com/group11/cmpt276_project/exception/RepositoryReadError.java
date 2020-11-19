package com.group11.cmpt276_project.exception;

/**
 * This class reads a repo read error message
 */
public class RepositoryReadError extends  Exception{
    public RepositoryReadError(String errorMessage) {
        super(errorMessage);
    }
}
