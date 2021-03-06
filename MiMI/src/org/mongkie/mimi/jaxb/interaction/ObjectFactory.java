//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.10.30 at 10:07:32 AM KST 
//


package org.mongkie.mimi.jaxb.interaction;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.mongkie.mimi.jaxb package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Provenance_QNAME = new QName("", "Provenance");
    private final static QName _Source_QNAME = new QName("", "Source");
    private final static QName _Target_QNAME = new QName("", "Target");
    private final static QName _Function_QNAME = new QName("", "Function");
    private final static QName _Process_QNAME = new QName("", "Process");
    private final static QName _Component_QNAME = new QName("", "Component");
    private final static QName _InteractionType_QNAME = new QName("", "InteractionType");
    private final static QName _PubMed_QNAME = new QName("", "PubMed");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.mongkie.mimi.jaxb
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Interaction }
     * 
     */
    public Interaction createInteraction() {
        return new Interaction();
    }

    /**
     * Create an instance of {@link Result }
     * 
     */
    public Result createResult() {
        return new Result();
    }

    /**
     * Create an instance of {@link ResultSet }
     * 
     */
    public ResultSet createResultSet() {
        return new ResultSet();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Provenance")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    public JAXBElement<String> createProvenance(String value) {
        return new JAXBElement<String>(_Provenance_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Source")
    public JAXBElement<Integer> createSource(Integer value) {
        return new JAXBElement<Integer>(_Source_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Target")
    public JAXBElement<Integer> createTarget(Integer value) {
        return new JAXBElement<Integer>(_Target_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Function")
    public JAXBElement<String> createFunction(String value) {
        return new JAXBElement<String>(_Function_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Process")
    public JAXBElement<String> createProcess(String value) {
        return new JAXBElement<String>(_Process_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Component")
    public JAXBElement<String> createComponent(String value) {
        return new JAXBElement<String>(_Component_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "InteractionType")
    public JAXBElement<String> createInteractionType(String value) {
        return new JAXBElement<String>(_InteractionType_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "PubMed")
    public JAXBElement<Integer> createPubMed(Integer value) {
        return new JAXBElement<Integer>(_PubMed_QNAME, Integer.class, null, value);
    }

}
