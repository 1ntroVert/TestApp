package testapp.andrey.com.testapp.models;

import java.util.List;

/**
 * Created by Андрей on 04.06.2017.
 */

/**
 * Класс, который содержит основные контакты
 */
public class Contact {
    public final String city;
    public final String building;
    public final String street;
    public final String firstName;
    public final String lastName;
    public final String url;
    public final String address;
    public final String addressDesc;
    public final String email;
    public final String name;

    public Contact(String city, String address, String addressDesc,
                   String street, String building,
                   String name, String firstName, String lastName,
                   String url, String email) {
        this.city = city;
        this.building = building;
        this.street = street;
        this.firstName = firstName;
        this.lastName = lastName;
        this.url = url;
        this.address = address;
        this.addressDesc = addressDesc;
        this.email = email;
        this.name = name;
    }
}
