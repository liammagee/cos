package edu.rmit.sustainability.model;

import com.clarkparsia.empire.annotation.Namespaces;
import com.clarkparsia.empire.annotation.RdfProperty;
import com.clarkparsia.empire.annotation.RdfsClass;
import edu.rmit.sustainability.data.CoSEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;


@Namespaces({"frbr", "http://vocab.org/frbr/core#",
        "dc", "http://purl.org/dc/terms/",
        "cos", "http://circlesofsustainability.org/ontology#",
        "foaf", "http://xmlns.com/foaf/0.1/"})
@RdfsClass("cos:Subdomain")
@Entity
@EntityListeners(CoSEntityListener.class)
public class Subdomain extends AbstractEmpireModel {

    /* The ID of the subdomain */
    @RdfProperty("cos:subdomainID")
    private int subdomainID;

    /* The name of the subdomain */
    @RdfProperty("cos:hasName")
    private String name;

    /* A description of the subdomain */
    @RdfProperty("cos:hasDescription")
    private String description;

    /* The associated parent domain */
    @RdfProperty("cos:hasParentDomain")
    private Domain parentDomain;


    public Subdomain() {
    }

    public Subdomain(String name) {
        setName(name);
    }

    public Subdomain(int subdomainID, String name) {
        setSubdomainID(subdomainID);
        setName(name);
    }


    public int getSubdomainID() {
        return subdomainID;
    }

    public void setSubdomainID(int subdomainID) {
        this.subdomainID = subdomainID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Domain getParentDomain() {
        return parentDomain;
    }

    public void setParentDomain(Domain parentDomain) {
        this.parentDomain = parentDomain;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Subdomain subdomain = (Subdomain) o;

        if (subdomainID != subdomain.subdomainID) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
