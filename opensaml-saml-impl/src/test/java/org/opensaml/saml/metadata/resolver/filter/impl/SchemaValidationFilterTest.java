/*
 * Licensed to the University Corporation for Advanced Internet Development,
 * Inc. (UCAID) under one or more contributor license agreements.  See the
 * NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The UCAID licenses this file to You under the Apache
 * License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.opensaml.saml.metadata.resolver.filter.impl;

import org.apache.http.client.params.AllClientPNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.opensaml.core.xml.XMLObjectBaseTestCase;
import org.opensaml.saml.common.xml.SAMLSchemaBuilder;
import org.opensaml.saml.common.xml.SAMLSchemaBuilder.SAML1Version;
import org.opensaml.saml.metadata.resolver.impl.HTTPMetadataResolver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import net.shibboleth.utilities.java.support.repository.RepositorySupport;

/**
 * Unit tests for {@link SchemaValidationFilter}.
 */
@SuppressWarnings("deprecation")
public class SchemaValidationFilterTest extends XMLObjectBaseTestCase {
    
    private DefaultHttpClient httpClient;

    /** URL to InCommon metadata. */
    private String inCommonMDURL;

    @BeforeMethod
    protected void setUp() throws Exception {
        httpClient = new DefaultHttpClient();
        httpClient.getParams().setIntParameter(AllClientPNames.CONNECTION_TIMEOUT, 1000 * 5);
        
        inCommonMDURL = RepositorySupport.buildHTTPSResourceURL("java-opensaml", "opensaml-saml-impl/src/test/resources/org/opensaml/saml/saml2/metadata/InCommon-metadata.xml");
    }

    @Test
    public void test() throws Exception {
        HTTPMetadataResolver metadataProvider = new HTTPMetadataResolver(httpClient, inCommonMDURL);
        metadataProvider.setParserPool(parserPool);
        metadataProvider.setId("test");
        metadataProvider.setMetadataFilter(new SchemaValidationFilter(new SAMLSchemaBuilder(SAML1Version.SAML_11)));
        metadataProvider.initialize();
    }
}