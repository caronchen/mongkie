//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.09.25 at 05:11:47 PM KST 
//


package org.mongkie.mimi.jaxb.official;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element ref="{}MiMI"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "miMI"
})
@XmlRootElement(name = "NCIBI")
public class NCIBI {

    @XmlElement(name = "MiMI", required = true)
    protected MiMI miMI;

    /**
     * Gets the value of the miMI property.
     * 
     * @return
     *     possible object is
     *     {@link MiMI }
     *     
     */
    public MiMI getMiMI() {
        return miMI;
    }

    /**
     * Sets the value of the miMI property.
     * 
     * @param value
     *     allowed object is
     *     {@link MiMI }
     *     
     */
    public void setMiMI(MiMI value) {
        this.miMI = value;
    }

}
