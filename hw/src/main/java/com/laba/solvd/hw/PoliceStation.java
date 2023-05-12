package com.laba.solvd.hw;

import com.laba.solvd.hw.Beast.PoliceDog;
import com.laba.solvd.hw.Case.*;
import com.laba.solvd.hw.Jail.Jail;
import com.laba.solvd.hw.Person.*;
import com.laba.solvd.hw.Exception.PersonTypeException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PoliceStation {

    private final Set<Case> solvedCases = new HashSet<>();
    private static final Set<Person> persons = new HashSet<>();
    public static final List<PoliceDog> dogs = new ArrayList<>();
    public static List<Jail> jails = new ArrayList<>();

    public static void addPerson(Person p) {
        try {
            PersonType type = getTypeOfPerson(p);
            type.addPerson(p);
            persons.add(p);
        } catch (PersonTypeException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static PersonType getTypeOfPerson(Person p) throws PersonTypeException {
        switch (p.getClass().getSimpleName()) {
            case "Officer":
                return PersonType.OFFICER;
            case "Criminal":
                return PersonType.CRIMINAL;
            case "Victim":
                return PersonType.VICTIM;
            default:
                throw new PersonTypeException("Invalid person type.");
        }
    }

    public enum PersonType {
        OFFICER("Officer", new HashSet<Officer>()),
        CRIMINAL("Criminal", new HashSet<Criminal>()),
        VICTIM("Victim", new HashSet<Victim>());

        private final String type;
        private final Set<Person> personSet;

        private PersonType(String type, Set<? extends Person> personSet) {
            this.type = type;
            this.personSet = (Set<Person>) personSet;
        }

        public String getType() {
            return type;
        }

        public Set<Person> getPersonSet() {
            return personSet;
        }

        public void addPerson(Person person) {
            personSet.add(person);
        }

        public void removePerson(Person person) {
            personSet.remove(person);
        }

        public boolean containsPerson(Person person) {
            return personSet.contains(person);
        }

        public <T extends Person> T getPersonByName(String name) {
            return (T) personSet.stream()
                    .filter(p -> p.getName().equals(name))
                    .findFirst()
                    .orElse(null);
        }
    }

    public void addCase(Case c) {
        solvedCases.add(c);
    }

    public void printReport() {
        for (Case c : solvedCases) {
            System.out.println("Case Report:");
            System.out.println(c);
        }
    }
}