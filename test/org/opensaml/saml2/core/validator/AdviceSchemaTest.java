/*
 * Copyright [2005] [University Corporation for Advanced Internet Development, Inc.]
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

package org.opensaml.saml2.core.validator;

import javax.xml.namespace.QName;

import org.opensaml.common.SAMLObjectBaseTestCase;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.Advice;
import org.opensaml.saml2.core.validator.AdviceSchemaValidator;
import org.opensaml.xml.validation.ValidationException;

public class AdviceSchemaTest extends SAMLObjectBaseTestCase {

    /**
     * Tests the correct case.
     * 
     * @throws ValidationException
     */
    public void testProper() throws ValidationException {
        QName qname = new QName(SAMLConstants.SAML20_NS, Advice.LOCAL_NAME, SAMLConstants.SAML20_PREFIX);
        Advice advice = (Advice) buildXMLObject(qname);

        AdviceSchemaValidator adviceValidator = new AdviceSchemaValidator();
        adviceValidator.validate(advice);
    }
    
    public void testSingleElementUnmarshall() {
        // TODO Auto-generated method stub
        
    }

    public void testSingleElementMarshall() {
        // TODO Auto-generated method stub
        
    }

}
