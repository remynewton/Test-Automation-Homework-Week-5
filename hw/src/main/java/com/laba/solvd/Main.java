package com.laba.solvd;
import com.laba.solvd.hw.*;
import com.laba.solvd.hw.Beast.PoliceDog;
import com.laba.solvd.hw.Case.*;
import com.laba.solvd.hw.Jail.Jail;
import com.laba.solvd.hw.Person.*;
import org.apache.log4j.PropertyConfigurator;
import java.io.*;
import java.util.logging.Logger;
import java.util.*;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    public static void main(String[] args) throws InterruptedException, IOException {
        PropertyConfigurator.configure("src/main/resources/log4j.properties");
        Officer officer1 = new Officer("John Doe", "06/12/1981", "123 Main St", 12345, "Patrol");
        List<String> trainings1 = Arrays.asList("Patrol");
        PoliceDog patroldog1 = new PoliceDog("Fido", "05/17/2019", true, "German Shepherd", trainings1);
        ArrayList<ICrime> crimes1 = new ArrayList<>();
        crimes1.add(new Crime(Crime.Type.JAVA_INSTR));
        Criminal criminal1 = new Criminal("Andrei Trukhanovich", "07/17/1991", "456 Elm St", crimes1);
        Victim victim1 = new Victim("Remy Newton", "05/22/1997", "789 Oak Ave", "9876");
        Case case1 = new Case("repeated Java instruction", officer1, criminal1, victim1, false);
        PoliceStation station = new PoliceStation();
        UnsolvedCases<Case> unsolvedCases = new UnsolvedCases<>();
        unsolvedCases.add(case1);
        Jail jail1 = new Jail(50);
        PoliceStation.addPerson(officer1);
        PoliceStation.addPerson(criminal1);
        PoliceStation.addPerson(victim1);

        logger.info(String.format("Officer %s from %s department is investigating a case of %s. That's %s.", officer1.getName(), officer1.getRank(), case1.getDescription(), officer1.getProfile()));
        logger.info(String.format("The victim of the crime is %s.", victim1.getProfile()));
        logger.info(String.format("Officer %s uses his trusty police dog %s to patrol the area for the criminal.", officer1.getName(), patroldog1.getName()));
        logger.info(patroldog1.patrol());
        logger.info(String.format("The officer has apprehended the criminal. %s", criminal1.getProfile()));
        jail1.addInmate(criminal1);
        logger.info(String.format("%s gets a treat.", patroldog1.getName()));
        String verb = jail1.getInmates().size() > 1 ? "are" : "is";
        logger.info(String.format("There %s now %d inmate(s) in the jail. Number of jails: %d.", verb, jail1.getInmates().size(), Jail.getTotalJails()));
        LogReader logReader = new LogReader();
        logReader.countUniqueWords("src/main/java/com/laba/solvd/hw/police_station.log");
    }
}