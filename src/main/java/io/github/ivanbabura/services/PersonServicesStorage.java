package io.github.ivanbabura.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersonServicesStorage {
    private final PersonService pSJdbc;
    private final PersonService pSJpa;
    private final PersonService pSHibernate;
    private final PersonService pSSpringJdbc;
    private PersonServicesEnum typeOfService;

    @Autowired
    public PersonServicesStorage(PersonService personServiceJdbc,
                                 PersonService personServiceJpa,
                                 PersonService personServiceHibernate,
                                 PersonService personServiceSpringJdbc) {
        this.pSJdbc = personServiceJdbc;
        this.pSJpa = personServiceJpa;
        this.pSHibernate = personServiceHibernate;
        this.pSSpringJdbc = personServiceSpringJdbc;
        typeOfService = PersonServicesEnum.JDBC;
    }

    public PersonService getSelectedService() {
        return switch (typeOfService) {
            case JDBC -> pSJdbc;
            case JPA -> pSJpa;
            case HIBERNATE -> pSHibernate;
            case SPRING_JDBC -> pSSpringJdbc;
            default -> null;
        };
    }

    public void setTypeOfService(String nameOfTypeOfService) {
        this.typeOfService = PersonServicesEnum.ERROR;
        for(PersonServicesEnum servicesEnum : PersonServicesEnum.values()){
            if (servicesEnum.getName().equals(nameOfTypeOfService)) {
                this.typeOfService = servicesEnum;
                System.out.printf("Selected %s.\n", servicesEnum.getName());
                break;
            }
        }
    }

    public String getNameOfSelectedService() {
        return typeOfService.getName();
    }

    public boolean isNotNull(){
        return !(typeOfService == null || typeOfService.equals(PersonServicesEnum.ERROR));
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
