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

package org.opensaml.saml.saml2.metadata.impl;

import net.shibboleth.utilities.java.support.xml.DOMTypeSupport;

import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.saml2.common.CacheableSAMLObject;
import org.opensaml.saml.saml2.metadata.Extensions;
import org.opensaml.saml.saml2.common.TimeBoundSAMLObject;
import org.opensaml.saml.saml2.metadata.AdditionalMetadataLocation;
import org.opensaml.saml.saml2.metadata.AffiliationDescriptor;
import org.opensaml.saml.saml2.metadata.ContactPerson;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml.saml2.metadata.Organization;
import org.opensaml.saml.saml2.metadata.RoleDescriptor;
import org.opensaml.xmlsec.signature.Signature;
import org.w3c.dom.Attr;

import com.google.common.base.Strings;

/**
 * A thread safe Unmarshaller for {@link org.opensaml.saml.saml2.metadata.EntityDescriptor}s.
 */
public class EntityDescriptorUnmarshaller extends AbstractSAMLObjectUnmarshaller {

    /** {@inheritDoc} */
    protected void processChildElement(final XMLObject parentSAMLObject, final XMLObject childSAMLObject)
            throws UnmarshallingException {
        final EntityDescriptor entityDescriptor = (EntityDescriptor) parentSAMLObject;

        if (childSAMLObject instanceof Extensions) {
            entityDescriptor.setExtensions((Extensions) childSAMLObject);
        } else if (childSAMLObject instanceof Signature) {
            entityDescriptor.setSignature((Signature) childSAMLObject);
        } else if (childSAMLObject instanceof RoleDescriptor) {
            entityDescriptor.getRoleDescriptors().add((RoleDescriptor) childSAMLObject);
        } else if (childSAMLObject instanceof AffiliationDescriptor) {
            entityDescriptor.setAffiliationDescriptor((AffiliationDescriptor) childSAMLObject);
        } else if (childSAMLObject instanceof Organization) {
            entityDescriptor.setOrganization((Organization) childSAMLObject);
        } else if (childSAMLObject instanceof ContactPerson) {
            entityDescriptor.getContactPersons().add((ContactPerson) childSAMLObject);
        } else if (childSAMLObject instanceof AdditionalMetadataLocation) {
            entityDescriptor.getAdditionalMetadataLocations().add((AdditionalMetadataLocation) childSAMLObject);
        } else {
            super.processChildElement(parentSAMLObject, childSAMLObject);
        }
    }

    /** {@inheritDoc} */
    protected void processAttribute(final XMLObject samlObject, final Attr attribute) throws UnmarshallingException {
        final EntityDescriptor entityDescriptor = (EntityDescriptor) samlObject;

        if (attribute.getNamespaceURI() == null) {
            if (attribute.getLocalName().equals(EntityDescriptor.ENTITY_ID_ATTRIB_NAME)) {
                entityDescriptor.setEntityID(attribute.getValue());
            } else if (attribute.getLocalName().equals(EntityDescriptor.ID_ATTRIB_NAME)) {
                entityDescriptor.setID(attribute.getValue());
                attribute.getOwnerElement().setIdAttributeNode(attribute, true);
            } else if (attribute.getLocalName().equals(TimeBoundSAMLObject.VALID_UNTIL_ATTRIB_NAME)
                    && !Strings.isNullOrEmpty(attribute.getValue())) {
                entityDescriptor.setValidUntil(new DateTime(attribute.getValue(), ISOChronology.getInstanceUTC()));
            } else if (attribute.getLocalName().equals(CacheableSAMLObject.CACHE_DURATION_ATTRIB_NAME)) {
                entityDescriptor.setCacheDuration(DOMTypeSupport.durationToLong(attribute.getValue()));
            } else {
                super.processAttribute(samlObject, attribute);
            }
        } else {
            processUnknownAttribute(entityDescriptor, attribute);
        }
    }
}