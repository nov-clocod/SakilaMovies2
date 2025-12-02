package com.pluralsight.db;

import com.pluralsight.models.Actor;
import com.pluralsight.models.Film;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private DataSource dataSource;

    public DataManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Actor> getActorsByName(String firstName, String lastName) {
        List<Actor> matchingActors = new ArrayList<>();

        try {
            String query = """
                    SELECT Actor_ID, First_Name, Last_Name
                    FROM actor
                    WHERE First_Name = ? AND Last_Name = ?
                    """;
            try (Connection connection = dataSource.getConnection();

                 PreparedStatement preparedStatement = connection.prepareStatement(query)
            ) {
                preparedStatement.setString(1, firstName);
                preparedStatement.setString(2, lastName);

                try (ResultSet results = preparedStatement.executeQuery()) {
                    if (results.next()) {
                        do {
                            int actorID = results.getInt(1);
                            String actorFirstName = results.getString(2);
                            String actorLastName = results.getString(3);

                            matchingActors.add(new Actor(actorID, actorFirstName, actorLastName));
                        } while (results.next());
                    } else {
                        System.out.println("Sorry, there are no matching actors");
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("Error occurred!");
            System.out.println(ex.getMessage());
        }
        return matchingActors;
    }

    public List<Film> getFilmByActorID(int actorID) {
        List<Film> matchingFilms = new ArrayList<>();

        try {
            String query = """
                    SELECT f.Film_ID, Title, Description, Release_Year, Length
                    FROM film as f
                    INNER JOIN film_actor as fa ON f.film_id = fa.film_id
                    WHERE Actor_ID = ?
                    """;
            try (Connection connection = dataSource.getConnection();

                 PreparedStatement preparedStatement = connection.prepareStatement(query);
            ) {
                preparedStatement.setInt(1, actorID);
                try (ResultSet results = preparedStatement.executeQuery()) {
                    if (results.next()) {
                        do {
                            int filmID = results.getInt(1);
                            String filmTitle = results.getString(2);
                            String filmDescription = results.getString(3);
                            int filmYear = results.getInt(4);
                            int filmLength = results.getInt(5);

                            matchingFilms.add(new Film(filmID, filmTitle, filmDescription, filmYear, filmLength));
                        } while (results.next());
                    } else {
                        System.out.println("Sorry, there are no films for that actor");
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("Error occurred!");
            System.out.println(ex.getMessage());
        }
        return matchingFilms;
    }
}
