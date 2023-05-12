package com.laba.solvd.hw.Jail;
import com.laba.solvd.hw.Person.*;
import com.laba.solvd.hw.Exception.*;
import com.laba.solvd.hw.PoliceStation;

import java.util.Scanner;
import java.util.ArrayList;
import java.lang.IllegalArgumentException;

public class Jail implements IJail {
    private ArrayList<Criminal> inmates = new ArrayList<>();
    private int capacity;
    protected static int totalJails;
    HoldingCell holdingCell = HoldingCell.getInstance();

    public Jail(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("Capacity must be greater than zero!");
        }
        this.capacity = capacity;
        PoliceStation.jails.add(this);
        totalJails = PoliceStation.jails.size();
    }

    public static int getTotalJails() {
        return totalJails;
    }

    @Override
    public ArrayList<Criminal> getInmates() {
        return inmates;
    }

    @Override
    public void setInmates(ArrayList<Criminal> inmates) {
        this.inmates = inmates;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public void addInmate(Criminal criminal) {
        try {
            if (inmates.size() < capacity) {
                inmates.add(criminal);
            } else {
                throw new JailFullException();
            }
        } catch (JailFullException e) {
            System.out.println(e.getMessage());
            holdingCell.addInmate(criminal);
        }
    }    

    @Override
    public boolean removeInmate(Criminal criminal) throws InmateNotFoundException {
        if (inmates.remove(criminal)) {
            return true;
        }
    
        System.out.println("The specified inmate was not found in this jail.");
        System.out.print("Do you want to remove that inmate from all jails, including the holding cell? (yes/no): ");
    
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().trim().toLowerCase();
        scanner.close();
    
        if (input.equals("yes")) {
            for (Jail jail : PoliceStation.jails) {
                if (jail.getInmates().contains(criminal)) {
                    jail.inmates.remove(criminal);
                }
            }
            holdingCell.removeInmate(criminal);
            throw new InmateNotFoundException("The inmate has been removed from all jails, including the holding cell.");
        } else if (input.equals("no")) {
            return false;
        } else {
            System.out.println("Invalid input. Please enter either 'yes' or 'no'.");
            return removeInmate(criminal);
        }
    }
}