package com.jorado.templateengine.exception;

/**
 * Created by Administrator on 15-5-20.
 */
public class SyntaxNotAllowedException extends Exception {

    private String notAllowedSyntax;

    public SyntaxNotAllowedException(String notAllowedSyntax) {

        super(String.format("Find not allowed syntax [{}]", notAllowedSyntax));

        this.setNotAllowedSyntax(notAllowedSyntax);
    }

    public String getNotAllowedSyntax() {
        return notAllowedSyntax;
    }

    public void setNotAllowedSyntax(String notAllowedSyntax) {
        this.notAllowedSyntax = notAllowedSyntax;
    }

}
