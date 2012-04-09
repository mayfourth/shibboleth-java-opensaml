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

package org.opensaml.saml.saml2.metadata.provider;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.Assert;
import org.testng.Assert;
import java.io.File;

import org.opensaml.core.xml.XMLObjectBaseTestCase;
import org.opensaml.saml.saml2.metadata.EntitiesDescriptor;
import org.opensaml.saml.saml2.metadata.provider.FileBackedHTTPMetadataProvider;
import org.opensaml.saml.saml2.metadata.provider.HTTPMetadataProvider;
import org.opensaml.saml.saml2.metadata.provider.MetadataProviderException;

/**
 * Test case for {@link FileBackedHTTPMetadataProvider}.
 */
public class FileBackedHTTPMetadataProviderTest extends XMLObjectBaseTestCase {

    private String mdUrl;

    private String badMDURL;

    private String backupFilePath;

    private FileBackedHTTPMetadataProvider metadataProvider;

    /** {@inheritDoc} */
    @BeforeMethod
    protected void setUp() throws Exception {
        //mdUrl = "http://wayf.incommonfederation.org/InCommon/InCommon-metadata.xml";
        mdUrl="http://metadata.ukfederation.org.uk/ukfederation-metadata.xml";
        badMDURL = "http://www.google.com/";
        backupFilePath = System.getProperty("java.io.tmpdir") + "metadata.xml";
        metadataProvider = new FileBackedHTTPMetadataProvider(mdUrl, 1000 * 5, backupFilePath);
        metadataProvider.setParserPool(parserPool);
        metadataProvider.initialize();
    }

    /** {@inheritDoc} */
    @AfterMethod
    protected void tearDown() {
        File backupFile = new File(backupFilePath);
        backupFile.delete();
    }

    /**
     * Tests the {@link HTTPMetadataProvider#getMetadata()} method.
     */
    @Test
    public void testGetMetadata() throws MetadataProviderException {
        EntitiesDescriptor descriptor = (EntitiesDescriptor) metadataProvider.getMetadata();
        Assert.assertNotNull(descriptor, "Retrieved metadata was null");

        File backupFile = new File(backupFilePath);
        Assert.assertTrue(backupFile.exists(), "Backup file was not created");
        Assert.assertTrue(backupFile.length() > 0, "Backup file contains no data");

        // Test pulling it from the backup file
        FileBackedHTTPMetadataProvider badProvider = new FileBackedHTTPMetadataProvider(badMDURL, 1000 * 5,
                backupFilePath);
        badProvider.setParserPool(parserPool);
        
        try{
            badProvider.initialize();
            Assert.fail("metadata provider claims to have parsed known invalid data");
        }catch(MetadataProviderException e){
            //expected this
        }
        
        badProvider = new FileBackedHTTPMetadataProvider(badMDURL, 1000 * 5,
                backupFilePath);
        badProvider.setParserPool(parserPool);
        badProvider.setFailFastInitialization(false);
        badProvider.initialize();
    }
}