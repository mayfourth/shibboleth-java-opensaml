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

package org.opensaml.saml.metadata.resolver.impl;

import java.io.IOException;
import java.util.Timer;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.shibboleth.utilities.java.support.annotation.ParameterName;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import net.shibboleth.utilities.java.support.resource.Resource;

/**
 * A metadata provider that reads metadata from a {#link {@link Resource}.
 * 
 * @since 2.2
 */
public class ResourceBackedMetadataResolver extends AbstractReloadingMetadataResolver {

    /** Class logger. */
    private final Logger log = LoggerFactory.getLogger(ResourceBackedMetadataResolver.class);

    /** Resource from which metadata is read. */
    private Resource metadataResource;

    /**
     * Constructor.
     * 
     * @param resource resource from which to read the metadata file.
     * @param timer task timer used to schedule metadata refresh tasks
     * 
     * @throws IOException thrown if there is a problem retrieving information about the resource
     */
    public ResourceBackedMetadataResolver(@ParameterName(name="timer") final Timer timer,
            @ParameterName(name="resource") final Resource resource) throws IOException {
        super(timer);

        if (!resource.exists()) {
            throw new IOException("Resource " + resource.getDescription() + " does not exist.");
        }
        metadataResource = resource;
    }
    
    /**
     * Constructor.
     * 
     * @param resource resource from which to read the metadata file.
     * 
     * @throws IOException thrown if there is a problem retrieving information about the resource
     */
    public ResourceBackedMetadataResolver(@ParameterName(name="resource") final Resource resource) throws IOException {

        if (!resource.exists()) {
            throw new IOException("Resource " + resource.getDescription() + " does not exist.");
        }
        metadataResource = resource;
    }

    /** {@inheritDoc} */
    @Override
    protected void doDestroy() {
        metadataResource = null;
        
        super.doDestroy();
    }
    
    /** {@inheritDoc} */
    @Override
    protected String getMetadataIdentifier() {
        return metadataResource.getDescription();
    }

    /** {@inheritDoc} */
    @Override
    protected byte[] fetchMetadata() throws ResolverException {
        try {
            final DateTime metadataUpdateTime = new DateTime(metadataResource.lastModified());
            log.debug("{} Resource {} was last modified {}", 
                    getLogPrefix(), metadataResource.getDescription(), metadataUpdateTime);
            if (getLastRefresh() == null || metadataUpdateTime.isAfter(getLastRefresh())) {
                return inputstreamToByteArray(metadataResource.getInputStream());
            }

            return null;
        } catch (final IOException e) {
            final String errorMsg = "Unable to read metadata file";
            log.error("{} " + errorMsg, getLogPrefix(), e);
            throw new ResolverException(errorMsg, e);
        }
    }
}