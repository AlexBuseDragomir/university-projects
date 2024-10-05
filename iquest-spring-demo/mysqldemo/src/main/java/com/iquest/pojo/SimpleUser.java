package com.iquest.pojo;

import javax.persistence.Column;  
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name="simple_user")
@XmlRootElement
public class SimpleUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "salary")
    private Integer salary;

    public Integer getId() {
        return id;
    }

    @XmlAttribute
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    public Integer getSalary() {
        return salary;
    }

    @XmlElement
    public void setSalary(Integer salary) {
        this.salary = salary;
    }
    
    @Override
    public String toString() {
    	return "Person id: " + id + "\n" +
    			"Person name: " + name + "\n" +
    			"Person salary: " + salary + "\n";
    }
}
