package testapp.andrey.com.testapp.models;

import java.util.Date;
import java.util.List;

/**
 * Created by Андрей on 04.06.2017.
 */

/**
 * Класс, который содержит основные данные о вакансии
 * (нет полного соответствия с форматом JSON-объекта)
 */

public class Vacancy {
    public final int id;
    public final String url;
    public final String position;
    public final Company company;
    public final Contact contact;
    public final String description;
    public final int minSalary;
    public final int maxSalary;
    public final int maxSalaryRub;
    public final int minSalaryRub;
    public final String salary;
    public final String salaryFormatted;
    public final int bonus;
    public final Date addDate;
    public final Date modDate;

    public final String workingType;
    public final String education;
    public final String requirements;
    public final String paymentTypeAlias;
    public final List<String> rubrics;
    public final String schedule;
    public final String experience;

    public Vacancy(int id, String url,
                   String position,
                   Company company, Contact contact,
                   String description,
                   int minSalary, int maxSalary,
                   int maxSalaryRub, int minSalaryRub,
                   String salary, String salaryFormatted,
                   int bonus,
                   Date addDate, Date modDate,
                   String workingType,
                   String education,
                   String requirements,
                   String paymentTypeAlias,
                   List<String> rubrics,
                   String schedule,
                   String experience) {
        this.id = id;
        this.url = url;
        this.position = position;
        this.company = company;
        this.contact = contact;
        this.description = description;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
        this.maxSalaryRub = maxSalaryRub;
        this.minSalaryRub = minSalaryRub;
        this.salary = salary;
        this.salaryFormatted = salaryFormatted;
        this.bonus = bonus;
        this.addDate = addDate;
        this.modDate = modDate;
        this.workingType = workingType;
        this.education = education;
        this.requirements = requirements;
        this.paymentTypeAlias = paymentTypeAlias;
        this.rubrics = rubrics;
        this.schedule = schedule;
        this.experience = experience;
    }
}
