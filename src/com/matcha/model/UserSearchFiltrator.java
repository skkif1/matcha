package com.matcha.model;

import com.matcha.entity.User;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class UserSearchFiltrator {

    public static List<User> sortByAge(List<User> listOfUsers)
    {
        listOfUsers = listOfUsers.stream()
                            .sorted(Comparator.comparingInt(o -> o.getInformation()
                            .getAge()))
                            .collect(Collectors.toList());
        return listOfUsers;
    }

    public static List<User> sortByLocation(List<User> listOfUsers, User userWhoSort)
    {
        DistanceCalculator calculator = new DistanceCalculator();
        listOfUsers = listOfUsers.stream()
                .sorted((o1, o2) ->
                {
                    DistanceCalculator.Point user1Location =
                            new DistanceCalculator.Point(o1.getInformation().getLatitude(), o2.getInformation().getLongitude());
                    DistanceCalculator.Point user2Location =
                            new DistanceCalculator.Point(o2.getInformation().getLatitude(), o2.getInformation().getLongitude());
                    DistanceCalculator.Point userWhoSortLocation =
                            new DistanceCalculator.Point(userWhoSort.getInformation().getLatitude(), userWhoSort.getInformation().getLongitude());

                    return Double.compare(calculator.calculateDistanceTo(user1Location, userWhoSortLocation),
                                            calculator.calculateDistanceTo(user2Location, userWhoSortLocation));

                })
                .collect(Collectors.toList());
        return listOfUsers;
    }

    public static  List<User> sortByRate(List<User> listOfUsers)
    {
        listOfUsers = listOfUsers.stream()
                .sorted(Comparator.comparingInt(o -> o.getInformation().getRate()))
                .collect(Collectors.toList());
        return listOfUsers;
    }

    public static  List<User> sortByTags(List<User> listOfUsers, User userWhoSearch)
    {
        List<String> userWhoSearchTags = userWhoSearch.getInformation().getInterests();

        listOfUsers = listOfUsers.stream()
                .sorted((o1, o2) ->
                {
                    List<String> intersectionUser1 = new ArrayList<>(o1.getInformation().getInterests());
                    List<String> intersectionUser2 = new ArrayList<>(o2.getInformation().getInterests());
                    intersectionUser1.retainAll(userWhoSearchTags);
                    System.out.println(intersectionUser1);
                    intersectionUser2.retainAll(userWhoSearchTags);
                    System.out.println(intersectionUser2);
                    return intersectionUser2.size() - intersectionUser1.size();
                })
                .collect(Collectors.toList());
        return listOfUsers;
    }
}
