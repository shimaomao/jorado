package com.jorado.logger.data;

import com.jorado.logger.data.collection.ParameterCollection;

public class Method extends BaseData {
    private String declaringPackage;
    private String declaringType;
    private String name;
    private String libraryId;
    private ParameterCollection parameters;

    public String getDeclaringPackage() {
        return declaringPackage;
    }

    public void setDeclaringPackage(String declaringPackage) {
        this.declaringPackage = declaringPackage;
    }

    public String getDeclaringType() {
        return declaringType;
    }

    public void setDeclaringType(String declaringType) {
        this.declaringType = declaringType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(String libraryId) {
        this.libraryId = libraryId;
    }

    public ParameterCollection getParameters() {
        return parameters;
    }

    public void setParameters(ParameterCollection parameters) {
        this.parameters = parameters;
    }
}
