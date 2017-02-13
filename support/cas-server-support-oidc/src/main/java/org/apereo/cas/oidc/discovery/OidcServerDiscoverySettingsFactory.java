package org.apereo.cas.oidc.discovery;

import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.configuration.model.support.oidc.OidcProperties;
import org.apereo.cas.support.oauth.OAuth20GrantTypes;
import org.apereo.cas.support.oauth.OAuth20ResponseTypes;
import org.springframework.beans.factory.FactoryBean;

import java.util.Arrays;
import java.util.Collections;

/**
 * This is {@link OidcServerDiscoverySettingsFactory}.
 *
 * @author Misagh Moayyed
 * @since 5.1.0
 */
public class OidcServerDiscoverySettingsFactory implements FactoryBean<OidcServerDiscoverySettings> {
    private final CasConfigurationProperties casProperties;

    public OidcServerDiscoverySettingsFactory(final CasConfigurationProperties casProperties) {
        this.casProperties = casProperties;
    }

    @Override
    public OidcServerDiscoverySettings getObject() throws Exception {
        final OidcProperties oidc = casProperties.getAuthn().getOidc();
        final OidcServerDiscoverySettings discoveryProperties =
                new OidcServerDiscoverySettings(casProperties.getServer().getPrefix(),
                        oidc.getIssuer());

        discoveryProperties.setClaimsSupported(oidc.getClaims());
        discoveryProperties.setScopesSupported(oidc.getScopes());
        discoveryProperties.setResponseTypesSupported(
                Arrays.asList(OAuth20ResponseTypes.CODE.getType(),
                        OAuth20ResponseTypes.TOKEN.getType(),
                        OAuth20ResponseTypes.IDTOKEN_TOKEN.getType()));

        discoveryProperties.setSubjectTypesSupported(oidc.getSubjectTypes());
        discoveryProperties.setClaimTypesSupported(Collections.singletonList("normal"));

        discoveryProperties.setGrantTypesSupported(
                Arrays.asList(OAuth20GrantTypes.AUTHORIZATION_CODE.getType(),
                        OAuth20GrantTypes.PASSWORD.getType(),
                        OAuth20GrantTypes.REFRESH_TOKEN.getType()));

        discoveryProperties.setIdTokenSigningAlgValuesSupported(Arrays.asList("none", "RS256"));
        return discoveryProperties;
    }

    @Override
    public Class<?> getObjectType() {
        return OidcServerDiscoverySettings.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}