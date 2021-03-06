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

import java.util.List;

import javax.xml.namespace.QName;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml1.core.AuthorityBinding;

/**
 * A concrete implementation of the {@link org.opensaml.saml.saml1.core.SubjectLocality} interface.
 */
public class AuthorityBindingImpl extends AbstractSAMLObject implements AuthorityBinding {

    /** The AuthorityKind. */
    private QName authorityKind;

    /** The Location. */
    private String location;

    /** The Binding. */
    private String binding;

    /**
     * Constructor.
     * 
     * @param namespaceURI the namespace the element is in
     * @param elementLocalName the local name of the XML element this Object represents
     * @param namespacePrefix the prefix for the given namespace
     */
    protected AuthorityBindingImpl(final String namespaceURI, final String elementLocalName,
            final String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
   }
    
    /** {@inheritDoc} */
    public QName getAuthorityKind() {
        return authorityKind;
    }

    /** {@inheritDoc} */
    public void setAuthorityKind(final QName kind) {
        authorityKind = prepareAttributeValueForAssignment(AuthorityBinding.AUTHORITYKIND_ATTRIB_NAME, 
                authorityKind, kind);
    }

    /** {@inheritDoc} */
    public String getLocation() {
        return location;
    }

    /** {@inheritDoc} */
    public void setLocation(final String loc) {
        location = prepareForAssignment(location, loc);
    }

    /** {@inheritDoc} */
    public String getBinding() {
        return binding;
    }

    /** {@inheritDoc} */
    public void setBinding(final String newBinding) {
        binding = prepareForAssignment(binding, newBinding);
    }

    /** {@inheritDoc} */
    public List<XMLObject> getOrderedChildren() {
        // No children
        return null;
    }
}