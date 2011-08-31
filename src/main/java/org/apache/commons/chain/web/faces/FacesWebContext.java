/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.chain.web.faces;


import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import org.apache.commons.chain.web.WebContext;


/**
 * <p>Concrete implementation of {@link WebContext} suitable for use in
 * JavaServer Faces apps.  The abstract methods are mapped to the appropriate
 * collections of the underlying <code>FacesContext</code> instance
 * that is passed to the constructor (or the initialize method).</p>
 *
 * @author Craig R. McClanahan
 * @version $Revision$ $Date$
 */

public class FacesWebContext extends WebContext {


    // ------------------------------------------------------------ Constructors


    /**
     * <p>Construct an uninitialized {@link FacesWebContext} instance.</p>
     */
    public FacesWebContext() {
    }


    /**
     * <p>Construct a {@link FacesWebContext} instance that is initialized
     * with the specified JavaServer Faces API objects.</p>
     *
     * @param context The <code>FacesContext</code> for this request
     */
    public FacesWebContext(FacesContext context) {

        initialize(context);

    }


    // ------------------------------------------------------ Instance Variables


    /**
     * <p>The <code>FacesContext</code> instance for the request represented
     * by this {@link WebContext}.</p>
     */
    private FacesContext context = null;


    // ---------------------------------------------------------- Public Methods


    /**
     * <p>Return the <code>FacesContext</code> instance for the request
     * associated with this {@link FacesWebContext}.</p>
     *
     * @return The <code>FacesContext</code> for this request
     */
    public FacesContext getContext() {

    return (this.context);

    }


    /**
     * <p>Initialize (or reinitialize) this {@link FacesWebContext} instance
     * for the specified JavaServer Faces API objects.</p>
     *
     * @param context The <code>FacesContext</code> for this request
     */
    public void initialize(FacesContext context) {

        this.context = context;

    }


    /**
     * <p>Release references to allocated resources acquired in
     * <code>initialize()</code> of via subsequent processing.  After this
     * method is called, subsequent calls to any other method than
     * <code>initialize()</code> will return undefined results.</p>
     */
    public void release() {

        context = null;

    }



    // ------------------------------------------------------ WebContext Methods


    /**
     * See the {@link WebContext}'s Javadoc.
     *
     * @return Application scope Map.
     */
    public Map<String, Object> getApplicationScope() {

    return (context.getExternalContext().getApplicationMap());

    }


    /**
     * See the {@link WebContext}'s Javadoc.
     *
     * @return Header values Map.
     */
    public Map<String, String> getHeader() {

    return (context.getExternalContext().getRequestHeaderMap());

    }


    /**
     * See the {@link WebContext}'s Javadoc.
     *
     * @return Header values Map.
     */
    public Map<String, String[]> getHeaderValues() {

    return (context.getExternalContext().getRequestHeaderValuesMap());

    }


    /**
     * See the {@link WebContext}'s Javadoc.
     *
     * @return Initialization parameter Map.
     */
    public Map<String, String> getInitParam() {

    return (context.getExternalContext().getInitParameterMap());

    }


    /**
     * See the {@link WebContext}'s Javadoc.
     *
     * @return Request parameter Map.
     */
    public Map<String, String> getParam() {

    return (context.getExternalContext().getRequestParameterMap());

    }


    /**
     * See the {@link WebContext}'s Javadoc.
     *
     * @return Request parameter Map.
     */
    public Map<String, String[]> getParamValues() {

    return (context.getExternalContext().getRequestParameterValuesMap());

    }


    /**
     * See the {@link WebContext}'s Javadoc.
     *
     * @return Map of Cookies.
     * @since Chain 1.1
     */
    public Map<String, Cookie> getCookies() {

        Map<String, Object> facesCookieMap =
                context.getExternalContext().getRequestCookieMap();

        /* This ugly hack was done because the faces API implements
         * the cookie collection using <String, Object> instead of <String, Cookie>.
         * So we painstakingly check for type safety and cast it as a Map<String, Cookie>.
         */
        Iterator<Object> itr = facesCookieMap.values().iterator();

        if (itr.hasNext()) {
            Object cookieObj = itr.next();

            if (cookieObj instanceof Cookie) {
                Map<String, Cookie> cookieMap = Collections.checkedMap(
                        (Map)facesCookieMap, String.class, Cookie.class);

                return cookieMap;
            } else {
                String msg = "Could not cast cookie Map into <String, Cookie>. " +
                        "Actual Cookie type is: " + cookieObj.getClass().toString();
                throw new ClassCastException(msg);
            }
        } else {
            return Collections.EMPTY_MAP;
        }
    }


    /**
     * See the {@link WebContext}'s Javadoc.
     *
     * @return Request scope Map.
     */
    public Map<String, Object> getRequestScope() {

    return (context.getExternalContext().getRequestMap());

    }


    /**
     * See the {@link WebContext}'s Javadoc.
     *
     * @return Session scope Map.
     */
    public Map<String, Object> getSessionScope() {

    return (context.getExternalContext().getSessionMap());

    }



}