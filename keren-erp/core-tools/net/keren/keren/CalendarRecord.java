//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.03.13 at 09:33:01 AM GMT+01:00 
//


package net.keren.keren;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
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
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="label" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="parent" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="action_ref" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="titlefield" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="startfield" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="endfield" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="allday" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "calendarRecord")
public class CalendarRecord {

    @XmlAttribute(name = "id")
    protected String id;
    @XmlAttribute(name = "label")
    protected String label;
    @XmlAttribute(name = "parent")
    protected String parent;
    @XmlAttribute(name = "action_ref")
    protected String actionRef;
    @XmlAttribute(name = "titlefield")
    protected String titlefield;
    @XmlAttribute(name = "startfield")
    protected String startfield;
    @XmlAttribute(name = "endfield")
    protected String endfield;
    @XmlAttribute(name = "allday")
    protected Boolean allday;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the label property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets the value of the label property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLabel(String value) {
        this.label = value;
    }

    /**
     * Gets the value of the parent property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParent() {
        return parent;
    }

    /**
     * Sets the value of the parent property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParent(String value) {
        this.parent = value;
    }

    /**
     * Gets the value of the actionRef property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActionRef() {
        return actionRef;
    }

    /**
     * Sets the value of the actionRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActionRef(String value) {
        this.actionRef = value;
    }

    /**
     * Gets the value of the titlefield property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitlefield() {
        return titlefield;
    }

    /**
     * Sets the value of the titlefield property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitlefield(String value) {
        this.titlefield = value;
    }

    /**
     * Gets the value of the startfield property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStartfield() {
        return startfield;
    }

    /**
     * Sets the value of the startfield property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStartfield(String value) {
        this.startfield = value;
    }

    /**
     * Gets the value of the endfield property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndfield() {
        return endfield;
    }

    /**
     * Sets the value of the endfield property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndfield(String value) {
        this.endfield = value;
    }

    /**
     * Gets the value of the allday property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAllday() {
        return allday;
    }

    /**
     * Sets the value of the allday property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAllday(Boolean value) {
        this.allday = value;
    }

}
