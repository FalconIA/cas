package org.jasig.cas.mgmt.services.web.beans;


/**
 * Defines logout types.
 * @author Misagh Moayyed
 * @since 4.1
 */
public enum RegisteredServiceLogoutTypeEditBean {

    /**
     * No logout.
     */
    NONE("none"),

    /**
     * Front-channel logout.
     */
    FRONT("front"),

    /**
     * Back-channel type.
     */
    BACK("back");

    private String value;

    /**
     * Instantiates a new AlgorithmTypes.
     *
     * @param value the value
     */
    RegisteredServiceLogoutTypeEditBean(final String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

}