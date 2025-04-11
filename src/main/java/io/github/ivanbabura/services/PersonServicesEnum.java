package io.github.ivanbabura.services;

public enum PersonServicesEnum {
    JPA("JPA"){
        public String getName() {
            return "JPA";
        }
    },
    JDBC("JDBC") {
        public String getName() {
            return "JDBC";
        }
    },
    HIBERNATE("Hibernate") {
        public String getName() {
            return "Hibernate";
        }
    },
    SPRING_JDBC("SpringJDBC") {
        public String getName() {
            return "SpringJDBC";
        }
    },
    ERROR("Error") {
        public String getName() {
            return "Error";
        }
    };
    private String name;

    PersonServicesEnum(String name) {
    }

    public abstract String getName();


    @Override
    public String toString() {
        return "SelectedPersonServicesEnum{" +
                "name='" + name + '\'' +
                '}';
    }
}
