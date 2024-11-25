package doa_bookstore.entity;

/* The code below its just an example on how can you
   use Address as a strong entity

@Entity
public class Address {

    @Id
    private long id;

    @OneToOne(mappedBy = "address")
    private Author author;

    private String country;
    private String city;
    private String street;
    private String zipcode;

}
*/

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Address {
    private String country;
    private String city;
    private String street;
    private String zipcode;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(country, address.country) && Objects.equals(city, address.city) && Objects.equals(street, address.street) && Objects.equals(zipcode, address.zipcode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, city, street, zipcode);
    }
}
