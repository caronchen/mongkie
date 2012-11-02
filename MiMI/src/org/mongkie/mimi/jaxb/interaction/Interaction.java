//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.10.30 at 10:07:32 AM KST 
//


package org.mongkie.mimi.jaxb.interaction;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}PubMed" maxOccurs="unbounded"/>
 *         &lt;element ref="{}Provenance" maxOccurs="unbounded"/>
 *         &lt;element ref="{}Process" maxOccurs="unbounded"/>
 *         &lt;element ref="{}InteractionType" maxOccurs="unbounded"/>
 *         &lt;element ref="{}Function" maxOccurs="unbounded"/>
 *         &lt;element ref="{}Component" maxOccurs="unbounded"/>
 *         &lt;element ref="{}Target"/>
 *         &lt;element ref="{}Source"/>
 *       &lt;/sequence>
 *       &lt;attribute name="ID" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "pubMed",
    "provenance",
    "process",
    "interactionType",
    "function",
    "component",
    "target",
    "source"
})
@XmlRootElement(name = "Interaction")
public class Interaction {

    @XmlElement(name = "PubMed", required = true)
    protected List<String> pubMed;
    @XmlElement(name = "Provenance", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected List<String> provenance;
    @XmlElement(name = "Process", required = true)
    protected List<String> process;
    @XmlElement(name = "InteractionType", required = true)
    protected List<String> interactionType;
    @XmlElement(name = "Function", required = true)
    protected List<String> function;
    @XmlElement(name = "Component", required = true)
    protected List<String> component;
    @XmlElement(name = "Target")
    protected int target;
    @XmlElement(name = "Source")
    protected int source;
    @XmlAttribute(name = "ID", required = true)
    protected int id;

    /**
     * Gets the value of the pubMed property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pubMed property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPubMed().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Integer }
     * 
     * 
     */
    public List<String> getPubMed() {
        if (pubMed == null) {
            pubMed = new ArrayList<String>();
        }
        return this.pubMed;
    }

    /**
     * Gets the value of the provenance property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the provenance property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProvenance().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getProvenance() {
        if (provenance == null) {
            provenance = new ArrayList<String>();
        }
        return this.provenance;
    }

    /**
     * Gets the value of the process property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the process property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProcess().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getProcess() {
        if (process == null) {
            process = new ArrayList<String>();
        }
        return this.process;
    }

    /**
     * Gets the value of the interactionType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the interactionType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInteractionType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getInteractionType() {
        if (interactionType == null) {
            interactionType = new ArrayList<String>();
        }
        return this.interactionType;
    }

    /**
     * Gets the value of the function property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the function property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFunction().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getFunction() {
        if (function == null) {
            function = new ArrayList<String>();
        }
        return this.function;
    }

    /**
     * Gets the value of the component property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the component property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getComponent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getComponent() {
        if (component == null) {
            component = new ArrayList<String>();
        }
        return this.component;
    }

    /**
     * Gets the value of the target property.
     * 
     */
    public int getTarget() {
        return target;
    }

    /**
     * Sets the value of the target property.
     * 
     */
    public void setTarget(int value) {
        this.target = value;
    }

    /**
     * Gets the value of the source property.
     * 
     */
    public int getSource() {
        return source;
    }

    /**
     * Sets the value of the source property.
     * 
     */
    public void setSource(int value) {
        this.source = value;
    }

    /**
     * Gets the value of the id property.
     * 
     */
    public int getID() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     */
    public void setID(int value) {
        this.id = value;
    }

}
