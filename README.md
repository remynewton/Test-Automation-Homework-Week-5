# Test-Automation-Homework-Week-5

The assignment for the first part of the homework was:
Practical task:
Read text from the file and calculate the numbers of the unique words. Write the result to the file. The main requirement is: using StringUtils and FileUtils to implement it with minimum lines of code.

As such, I modified LogReader.java to accomplish exactly that:
```
package com.laba.solvd.hw;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class LogReader {
    public void countUniqueWords(String logFilePath) {
        File file = new File(logFilePath);
        try {
            String text = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
            String[] words = StringUtils.split(text);
            Set<String> uniqueWords = new HashSet<>();
            Collections.addAll(uniqueWords, words);
            int numUniqueWords = uniqueWords.size();
            FileUtils.writeStringToFile(file, "Number of unique words: " + numUniqueWords, StandardCharsets.UTF_8, true);
            System.out.println("Result written to file.");
        } catch (IOException e) {
            System.err.println("Failed to read or write to file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
```

I also fixed a number of issues with my code as a result of suggestions from my mentor.

# Part 2

The prompt for part 2 was:
Practical tasks:
Use at least 5 lambda functions from the java.util.function package.
Create 3 custom Lambda functions with generics.
Create 5 complex Enums(with fields, methods, blocks).

I've done most of it, but I still need to make one more custom enum and another custom lambda function with generics.

I primarily edited PoliceStation.java:
```
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
```

I did quite a bit of stuff in PoliceDog.java too:
```
package com.laba.solvd.hw.Beast;

import java.util.List;
import java.util.function.Predicate;

public class PoliceDog extends Beast implements SearchAndRescue, Detection, Patrol, Cadaver {
    private Breed breed;
    private List<String> trainings;

    public PoliceDog(String name, String DOB, boolean furry, Breed breed, List<String> trainings) {
        super(name, DOB, furry);
        this.breed = breed;
        this.trainings = trainings;
    }

    public List<String> getTrainings() {
        return trainings;
    }

    public void setTrainings(List<String> trainings) {
        this.trainings = trainings;
    }

    public Breed getBreed() {
        return breed;
    }

    public void setBreed(Breed breed) {
        this.breed = breed;
    }

    public static PoliceDog findElement(List<PoliceDog> list, Predicate<PoliceDog> predicate) {
        for (PoliceDog element : list) {
            if (predicate.test(element)) {
                return element;
            }
        }
        return null;
    }

    @Override
    public void searchAndRescue() {
        if (trainings.contains("Search and Rescue")) {
            System.out.println("Performing search and rescue operation...");
        } else {
            System.out.println("This dog is not trained for search and rescue.");
        }
    }

    @Override
    public void detect() {
        if (trainings.contains("Detection")) {
            System.out.println("Detecting for drugs and explosives...");
        } else {
            System.out.println("This dog is not trained for detection.");
        }
    }

    @Override
    public String patrol() {
        if (trainings.contains("Patrol")) {
            return "Patrolling the area...";
        } else {
            return "This dog is not trained for patrol.";
        }
    }

    @Override
    public void findCadaver() {
        if (trainings.contains("Cadaver")) {
            System.out.println("Searching for cadaver...");
        } else {
            System.out.println("This dog is not trained for cadaver detection.");
        }
    }

    public enum Breed {
        BELGIAN_MALINOIS,
        BLOODHOUND,
        BORDER_COLLIE,
        BOXER,
        DOBERMAN_PINSCHER,
        ENGLISH_SPRINGER_SPANIEL,
        GERMAN_SHEPHERD,
        LABRADOR_RETRIEVER,
        OTHER,
        ROTWEILLER
    }
}
```

I changed a lot of other files too, but those were the big edits. I also fixed all the problems my mentor pointed out, such as:
1. log4j not working properly
2. Insufficient number of custom Exceptions
3. Log file not in wrong directory
4. Recursion issue in Jail class
5. Existence of jails list in Jail class
