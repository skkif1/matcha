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
                            new DistanceCalculator.Point(o1.getInformation().getLatitude(), o1.getInformation().getLongitude());
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
        listOfUsers.sort((o1, o2) -> {
            return o2.getInformation().getRate() - o1.getInformation().getRate();
        });
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

    // /*
    //           all filter methods return new allocated list
    // */

    public static List<User> filterByLocation(List<User> listOfUsers, Double locationRange, User userWhoSearch)
    {
        DistanceCalculator calculator = new DistanceCalculator();
        List<User> res;

        DistanceCalculator.Point userWhoSearchLocation =
                new DistanceCalculator.Point(userWhoSearch.getInformation().getLatitude(), userWhoSearch.getInformation().getLongitude());
        res = listOfUsers.stream().filter(user ->
        {
            DistanceCalculator.Point userPoint =
                    new DistanceCalculator.Point(userWhoSearch.getInformation().getLatitude(), user.getInformation().getLongitude());
            return calculator.calculateDistanceTo(userPoint, userWhoSearchLocation) <= locationRange;
        }).collect(Collectors.toList());
        return res;
    }


    public static List<User> filterByAge(List<User> listOfUsers, Integer minAge, Integer maxAge)
    {
        List<User> res = new ArrayList<>(listOfUsers);
        res.removeIf(user ->
                minAge > user.getInformation().getAge() || user.getInformation().getAge() > maxAge);
        return res;
    }


    public static List<User> filterByRating(List<User>listOfUsers, Integer rate)
    {
        List<User> res = new ArrayList<>(listOfUsers);
        res.removeIf(user -> user.getInformation().getRate() < rate);
        return res;
    }


    public static List<User> filterByTag(List<User> listOfUsers, List<String> tagList)
    {
        if (tagList == null)
            return listOfUsers;

        List<User> res = new ArrayList<User>(listOfUsers);

        res.removeIf(user ->
                !user.getInformation().getInterests().containsAll(tagList));
        return res;
    }

    public static void main(String[] args) {

        List<Integer> res = new ArrayList<>();

        long now = System.currentTimeMillis();

        for (int i = 0; i < 1000; i++) {
               res.add(produceAndAdd());
        }

        long time = System.currentTimeMillis() - now;
        System.out.println(time);
    }

    static Integer produceAndAdd()
    {
        Integer res = new Integer(0);

        for (int i = 0; i < 100_000_000; i++) {
            res++;
        }
        return res;
    }
}


