package webapps.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "user", propOrder = {"Id", "Name", "Lastname", "Personal_id", "Balance"})
public class User {
    private String Id;
    private String Name;
    private String Lastname;
    private Double Balance;

    private String Personal_id;

    public String getPersonal_id() {
        return Personal_id;
    }

    public void setPersonal_id(String personal_id) {
        Personal_id = personal_id;
    }

    public Double getBalance() {
        return Balance;
    }

    public void setBalance(Double balance) {
        Balance = balance;
    }

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String lastname) {
        Lastname = lastname;
    }

    public User(String Id, String Name, String Lastname, String Personal_id, Double Balance) {
        this.Id = Id;
        this.Name = Name;
        this.Lastname = Lastname;
        this.Personal_id = Personal_id;
        this.Balance = Balance;
    }
    public User(String Name, String Lastname, Double Balance) {
        this.Name = String.valueOf(Name.charAt(0));
        this.Lastname = String.valueOf(Lastname.charAt(0));
        this.Balance = Balance;
    }

    public User() {
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{Id=");
        sb.append(Id);
        sb.append(", Name=");
        sb.append(Name);
        sb.append(", Lastname=");
        sb.append(Lastname);
        sb.append(", Personal_id=");
        sb.append(Personal_id);
        sb.append(", Balance=");
        sb.append(Balance);
        sb.append("}");
        return sb.toString();
    }
}
