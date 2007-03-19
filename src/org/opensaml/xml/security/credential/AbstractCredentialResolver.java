/*
 * Copyright [2007] [University Corporation for Advanced Internet Development, Inc.]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.opensaml.xml.security.credential;

import java.security.Key;

import org.opensaml.xml.security.SecurityException;

/**
 * Abstract base class for {@link CredentialResolver} implementations.
 * 
 * @param <ContextType> the type of {@link CredentialContext} associated with the resolver
 */
public abstract class AbstractCredentialResolver<ContextType extends CredentialContext>
    implements CredentialResolver<ContextType> {
    
    /** The Class of the credential context that will be produced by the resolver. */
    private Class<? extends ContextType> contextClass;
    
    /** {@inheritDoc} */
    public Class<? extends ContextType> getContextClass() {
        return contextClass;
    }

    /** {@inheritDoc} */
    public void setContextClass(Class<? extends ContextType> newContextClass) {
       contextClass = newContextClass; 
    }

    /** {@inheritDoc} */
    public Credential resolveCredential(CredentialCriteria criteria) throws SecurityException {
        Iterable<Credential> creds = resolveCredentials(criteria);
        if (creds.iterator().hasNext()) {
            return creds.iterator().next();
        } else {
            return null;
        }
    }
    
    /**
     * Create and return a new instance of the {@link CredentialContext} type currently defined
     * for this class instance.
     * 
     * @return a new instance of CredentialContext
     * @throws SecurityException thrown if the context object can not be instantiated
     */
    public ContextType newCredentialContext() throws SecurityException {
        ContextType context = null;
        
        try {
            context = contextClass.newInstance();
        } catch (InstantiationException e) {
            throw new SecurityException("Could not instantiate instance of CredentialContext", e);
        } catch (IllegalAccessException e) {
            throw new SecurityException("Error creating instance of CredentialContext", e);
        }
        
        return context;
    }
    
    /**
     * Check whether the resolved key meets the specified key criteria.
     * 
     * @param key the Key to evaluate 
     * @param criteria the credential criteria to use to evaluate the key
     * @return true if the key meets the criteria, otherwise false
     */
    protected boolean evaluateKey(Key key, CredentialCriteria criteria) {
        // If key was null, or no key algo specified, define this to be 'true'
        if (criteria.getKeyAlgorithm() == null || key == null) {
            return true;
        }
        if (criteria.getKeyAlgorithm().equals(key.getAlgorithm())) {
            return true;
        }
        return false;
    }

    

    /** {@inheritDoc} */
    public abstract Iterable<Credential> resolveCredentials(CredentialCriteria criteria) throws SecurityException;

}
