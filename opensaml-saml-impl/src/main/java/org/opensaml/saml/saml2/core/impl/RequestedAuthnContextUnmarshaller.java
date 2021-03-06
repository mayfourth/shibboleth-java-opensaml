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

/**
 * 
 */

package org.opensaml.saml.saml2.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.saml2.core.AuthnContextClassRef;
import org.opensaml.saml.saml2.core.AuthnContextComparisonTypeEnumeration;
import org.opensaml.saml.saml2.core.AuthnContextDeclRef;
import org.opensaml.saml.saml2.core.RequestedAuthnContext;
import org.w3c.dom.Attr;

/**
 * A thread-safe Unmarshaller for {@link org.opensaml.saml.saml2.core.RequestedAuthnContext} objects.
 */
public class RequestedAuthnContextUnmarshaller extends AbstractSAMLObjectUnmarshaller {

    /** {@inheritDoc} */
    protected void processChildElement(final XMLObject parentSAMLObject, final XMLObject childSAMLObject)
            throws UnmarshallingException {
        final RequestedAuthnContext rac = (RequestedAuthnContext) parentSAMLObject;
        
        if (childSAMLObject instanceof AuthnContextClassRef) {
            rac.getAuthnContextClassRefs().add((AuthnContextClassRef) childSAMLObject);
        } else if (childSAMLObject instanceof AuthnContextDeclRef) {
            rac.getAuthnContextDeclRefs().add((AuthnContextDeclRef) childSAMLObject);
        } else {
            super.processChildElement(parentSAMLObject, childSAMLObject);
        }
    }

    /** {@inheritDoc} */
    protected void processAttribute(final XMLObject samlObject, final Attr attribute) throws UnmarshallingException {
        final RequestedAuthnContext rac = (RequestedAuthnContext) samlObject;

        if (attribute.getLocalName().equals(RequestedAuthnContext.COMPARISON_ATTRIB_NAME)
                && attribute.getNamespaceURI() == null) {
            if ("exact".equals(attribute.getValue())) {
                rac.setComparison(AuthnContextComparisonTypeEnumeration.EXACT);
            } else if ("minimum".equals(attribute.getValue())) {
                rac.setComparison(AuthnContextComparisonTypeEnumeration.MINIMUM);
            } else if ("maximum".equals(attribute.getValue())) {
                rac.setComparison(AuthnContextComparisonTypeEnumeration.MAXIMUM);
            } else if ("better".equals(attribute.getValue())) {
                rac.setComparison(AuthnContextComparisonTypeEnumeration.BETTER);
            } else {
                throw new UnmarshallingException("Saw an invalid value for Comparison attribute: "
                        + attribute.getValue());
            }
        } else {
            super.processAttribute(samlObject, attribute);
        }
    }
}