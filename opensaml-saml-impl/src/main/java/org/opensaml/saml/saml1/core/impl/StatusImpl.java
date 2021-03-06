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

package org.opensaml.saml.saml1.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml1.core.Status;
import org.opensaml.saml.saml1.core.StatusCode;
import org.opensaml.saml.saml1.core.StatusDetail;
import org.opensaml.saml.saml1.core.StatusMessage;

/**
 * Concrete Implementation {@link org.opensaml.saml.saml1.core.Status}.
 */
public class StatusImpl extends AbstractSAMLObject implements Status {

    /** Representation of the StatusMessage element. */
    private StatusMessage statusMessage;

    /** Representation of the StatusCode element. */
    private StatusCode statusCode;

    /** Representation of the StatusDetail element. */
    private StatusDetail statusDetail;

    /**
     * Constructor.
     * 
     * @param namespaceURI the namespace the element is in
     * @param elementLocalName the local name of the XML element this Object represents
     * @param namespacePrefix the prefix for the given namespace
     */
    protected StatusImpl(final String namespaceURI, final String elementLocalName, final String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    /** {@inheritDoc} */
    public StatusMessage getStatusMessage() {
        return statusMessage;
    }

    /** {@inheritDoc} */
    public void setStatusMessage(final StatusMessage message) {
        statusMessage = prepareForAssignment(statusMessage, message);
    }

    /** {@inheritDoc} */
    public StatusCode getStatusCode() {
        return statusCode;
    }

    /** {@inheritDoc} */
    public void setStatusCode(final StatusCode code) {
        statusCode = prepareForAssignment(statusCode, code);
    }

    /** {@inheritDoc} */
    public StatusDetail getStatusDetail() {
        return statusDetail;
    }

    /** {@inheritDoc} */
    public void setStatusDetail(final StatusDetail detail) {
        statusDetail = prepareForAssignment(statusDetail, detail);
    }

    /** {@inheritDoc} */
    public List<XMLObject> getOrderedChildren() {
        final ArrayList<XMLObject> children = new ArrayList<>(3);

        if (statusCode != null) {
            children.add(statusCode);
        }

        if (statusMessage != null) {
            children.add(statusMessage);
        }

        if (statusDetail != null) {
            children.add(statusDetail);
        }

        if (children.size() == 0) {
            return null;
        }

        return Collections.unmodifiableList(children);
    }
}