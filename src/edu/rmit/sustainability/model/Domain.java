package edu.rmit.sustainability.model;

import com.clarkparsia.empire.annotation.Namespaces;
import com.clarkparsia.empire.annotation.RdfProperty;
import com.clarkparsia.empire.annotation.RdfsClass;
import edu.rmit.sustainability.data.CoSEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Namespaces({"frbr", "http://vocab.org/frbr/core#",
        "dc", "http://purl.org/dc/terms/",
        "cos", "http://circlesofsustainability.org/ontology#",
        "foaf", "http://xmlns.com/foaf/0.1/"})
@RdfsClass("cos:Domain")
@Entity
@EntityListeners(CoSEntityListener.class)
public class Domain extends AbstractEmpireModel {
    /* The name of the domain */
    @RdfProperty("cos:hasName")
    private String name;

    /* A description of the domain */
    @RdfProperty("cos:hasDescription")
    private String description;

    /* A list of associated subdomains */
    @OneToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @RdfProperty("cos:hasSubdomain")
    private List<Subdomain> subdomains = new ArrayList<Subdomain>();


    public Domain() {
    }

    public Domain(String name) {
        setName(name);
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

    public List<Subdomain> getSubdomains() {
        return subdomains;
    }

    public void setSubdomains(List<Subdomain> subdomains) {
        this.subdomains = subdomains;
    }

    public void addSubdomain(Subdomain subdomain) {
        subdomain.setParentDomain(this);
        this.subdomains.add(subdomain);
    }


    public Subdomain getSubdomain(String name) {
        for (Iterator<Subdomain> iterator = subdomains.iterator(); iterator.hasNext(); ) {
            Subdomain next = iterator.next();
            if (next.getName().equals(name))
                return next;
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Domain domain = (Domain) o;

        if (description != null ? !description.equals(domain.description) : domain.description != null) return false;
        if (name != null ? !name.equals(domain.name) : domain.name != null) return false;

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
