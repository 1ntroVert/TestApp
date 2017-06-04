package testapp.andrey.com.testapp.models;

import java.util.List;

/**
 * Created by Андрей on 04.06.2017.
 */

/**
 * Класс, который содержит основные данные о компании
 * (нет полного соответствия с форматом JSON-объекта)
 */
public class Company {
    public final String logoUrl;
    public final String title;
    public final String employeesCountAlias;

    public Company(String logoUrl,
                   String title,
                   String employeesCountAlias) {
        this.logoUrl = logoUrl;
        this.title = title;
        this.employeesCountAlias = employeesCountAlias;
    }
}
