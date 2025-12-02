package com.pluralsight;

import com.pluralsight.db.DataManager;
import com.pluralsight.models.Actor;
import com.pluralsight.models.Film;
import org.apache.commons.dbcp2.BasicDataSource;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Error on required application details to run");
            System.exit(1);
        }

        String username = args[0];
        String password = args[1];

        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setUrl("jdbc:mysql://localhost:3306/sakila");
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        try (Scanner myScanner = new Scanner(System.in)) {
            DataManager dataManager = new DataManager(dataSource);

            System.out.println("\nEnter the first name of the actor you want to search for:");
            String userInputMovieActorFirstName = myScanner.nextLine().trim();

            System.out.println("\nEnter the last name of the actor you want to search for:");
            String userInputMovieActorLastName = myScanner.nextLine().trim();

            List<Actor> actorList = dataManager.getActorsByName(userInputMovieActorFirstName, userInputMovieActorLastName);
            if (!actorList.isEmpty()) {
                System.out.println("\nActor ID " + "First Name" + " ".repeat(36) + "Last Name" + " ".repeat(37));
                System.out.println("-------- " + "-".repeat(45) + " " + "-".repeat(45));
                actorList.forEach(System.out::println);
            } else {
                System.out.println();
            }

            System.out.println("Enter the actor's id to display films the actor has played a role in:");
            int userInputActorID = myScanner.nextInt();
            myScanner.nextLine();

            List<Film> filmList = dataManager.getFilmByActorID(userInputActorID);
            if (!filmList.isEmpty()) {
                System.out.println("\nFilm ID " + "Title" + " ".repeat(26) + "Description" + " ".repeat(110) + "Release Year" + " " + "Movie Length");
                System.out.println("------- " + "-".repeat(30) + " " + "-".repeat(120) + " " + "-".repeat(12) + " " + "-".repeat(12));
                filmList.forEach(System.out::println);
            }
        } catch (Exception ex) {
            System.out.println("Please check your inputs!");
            System.out.println(ex.getMessage());
        }
    }
}
