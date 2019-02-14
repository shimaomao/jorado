package com.jorado.logger.data;

import com.jorado.logger.data.collection.LibraryCollection;

public class Error extends InnerError {

    private LibraryCollection libraries;

    private boolean unhandledError;


    public Error() {
        libraries = new LibraryCollection();
    }

    public LibraryCollection getLibraries() {
        return libraries;
    }

    public boolean isUnhandledError() {

        return unhandledError;
    }

    public void setUnhandledError(boolean unhandledError) {

        this.unhandledError = unhandledError;
    }
}
