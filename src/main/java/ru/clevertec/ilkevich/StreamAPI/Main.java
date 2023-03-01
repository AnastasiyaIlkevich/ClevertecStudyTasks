package ru.clevertec.ilkevich.StreamAPI;


import ru.clevertec.ilkevich.StreamAPI.model.*;
import ru.clevertec.ilkevich.StreamAPI.util.Util;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
   private static final String ORIGIN_ANIMAL_JAPANESE = "Japanese";
    private static final String ORIGIN_ANIMAL_HUNGARIAN = "Hungarian";
    private static final String GENDER_FEMALE = "Female";
    private static final String GENDER_MALE = "Male";
    private static final String ORIGIN_ANIMAL_OCEANIA = "Oceania";
    private static final String ORIGIN_ANIMAL_INDONESIAN = "Indonesian";
    private static final String BUILDING_TYPE_HOSPITAL = "Hospital";
    private static final String BUILDING_TYPE_CIVIL_BUILDING = "Civil building";

    public static void main(String[] args) throws IOException {
        task1();
        task2();
        task3();
        task4();
        task5();
        task6();
        task7();
        task8();
        task9();
        task10();
        task11();
        task12();
        task13();
        task14();
        task15();
        task16();
    }


    private static void task1() throws IOException {
        System.out.println("--------------------------Task 1-----------------------");
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(animal -> animal.getAge() > 9 && animal.getAge() < 20)
                .sorted(Comparator.comparing(Animal::getAge))
                .skip(14)
                .limit(7)
                .forEach(System.out::println);
    }


    private static void task2() throws IOException {
        System.out.println("--------------------------Task 2-----------------------");
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(animal -> ORIGIN_ANIMAL_JAPANESE.equals(animal.getOrigin()))
                .map(animal -> GENDER_FEMALE.equals(animal.getGender()) ? animal.getBread().toUpperCase() : animal.getBread())
                .forEach(System.out::println);

    }

    private static void task3() throws IOException {
        System.out.println("--------------------------Task 3-----------------------");
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(animal -> animal.getAge() > 30)
                .map(Animal::getOrigin)
                .filter(origin -> origin.charAt(0) == 'A')
                .distinct()
                .forEach(System.out::println);
    }

    private static void task4() throws IOException {
        System.out.println("--------------------------Task 4-----------------------");
        List<Animal> animals = Util.getAnimals();
        Long countFemaleGender = animals.stream()
                .filter(animal -> GENDER_FEMALE.equals(animal.getGender()))
                .count();
        System.out.println(countFemaleGender);

    }

    private static void task5() throws IOException {
        System.out.println("--------------------------Task 5-----------------------");
        List<Animal> animals = Util.getAnimals();
        boolean isExistsAnimal = animals.stream()
                .filter(animal -> animal.getAge() >= 20 && animal.getAge() <= 30)
                .anyMatch(animal -> ORIGIN_ANIMAL_HUNGARIAN.equals(animal.getOrigin()));
        System.out.println(isExistsAnimal);

    }

    private static void task6() throws IOException {
        System.out.println("--------------------------Task 6-----------------------");
        List<Animal> animals = Util.getAnimals();
        boolean b = animals.stream()
                .allMatch(animal -> GENDER_FEMALE.equals(animal.getGender())
                        || GENDER_MALE.equals(animal.getGender()));
        System.out.println(b);
    }

    private static void task7() throws IOException {
        System.out.println("--------------------------Task 7-----------------------");
        List<Animal> animals = Util.getAnimals();
        boolean b = animals.stream()
                .noneMatch(animal -> ORIGIN_ANIMAL_OCEANIA.equals(animal.getOrigin()));
        System.out.println(b);
    }

    private static void task8() throws IOException {
        System.out.println("--------------------------Task 8-----------------------");
        List<Animal> animals = Util.getAnimals();
        animals.stream().sorted(Comparator.comparing(Animal::getBread))
                .limit(100)
                .max(Comparator.comparing(Animal::getAge))
                .ifPresent(System.out::println);
    }

    private static void task9() throws IOException {
        System.out.println("--------------------------Task 9-----------------------");
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .map(Animal::getBread)
                .map(String::toCharArray)
                .min(Comparator.comparing(chars -> chars.length))
                .ifPresent(chars -> System.out.println(chars.length));
    }

    private static void task10() throws IOException {
        System.out.println("--------------------------Task 10-----------------------");
        List<Animal> animals = Util.getAnimals();
        int sumAge = animals.stream()
                .mapToInt(Animal::getAge)
                .sum();
        System.out.println(sumAge);
    }

    private static void task11() throws IOException {
        System.out.println("--------------------------Task 11-----------------------");
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(animal -> ORIGIN_ANIMAL_INDONESIAN.equals(animal.getOrigin()))
                .mapToDouble(Animal::getAge)
                .average()
                .ifPresent(System.out::println);
    }

    private static void task12() throws IOException {
        System.out.println("--------------------------Task 12-----------------------");
        List<Person> people = Util.getPersons();
        people.stream()
                .filter(person -> GENDER_MALE.equals(person.getGender()))
                .filter(person -> Period.between(person.getDateOfBirth(), LocalDate.now()).getYears() >= 18)
                .filter(person -> Period.between(person.getDateOfBirth(), LocalDate.now()).getYears() < 27)
                .sorted(Comparator.comparing(Person::getRecruitmentGroup))
                .limit(200)
                .forEach(System.out::println);
    }

    private static void task13() throws IOException {
        System.out.println("--------------------------Task 13-----------------------");
        List<House> houses = Util.getHouses();
        List<Person> firstGroupHospitalPersons = new ArrayList<>(houses.stream()
                .filter(house -> BUILDING_TYPE_HOSPITAL.equals(house.getBuildingType()))
                .flatMap(house -> house.getPersonList().stream())
                .collect(Collectors.toList()));

        List<Person> buildingType = new ArrayList<>(houses.stream()
                .filter(house -> BUILDING_TYPE_CIVIL_BUILDING.equals(house.getBuildingType()))
                .flatMap(house -> house.getPersonList().stream())
                .collect(Collectors.toList()));

        List<Person> secondGroupPersons = new ArrayList<>(buildingType.stream()
                .filter(person -> (Period.between(person.getDateOfBirth(), LocalDate.now()).getYears() <= 18)
                        || (Period.between(person.getDateOfBirth(), LocalDate.now()).getYears() > 58) && (GENDER_FEMALE.equals(person.getGender()))
                        || (Period.between(person.getDateOfBirth(), LocalDate.now()).getYears() > 63) && (GENDER_MALE.equals(person.getGender())))
                .collect(Collectors.toList()));

        Stream.of(firstGroupHospitalPersons, secondGroupPersons, buildingType)
                .flatMap(Collection::stream)
                .distinct()
                .limit(500)
                .forEach(System.out::println);
    }

    private static void task14() throws IOException {
        System.out.println("--------------------------Task 14-----------------------");
        List<Car> cars = Util.getCars();

        List<Car> carsForTurkmenistan = cars.stream()
                .filter(car -> "Jaguar".equals(car.getCarMake())
                        || "White".equals(car.getColor()))
                .toList();
        cars = remainingVehiclesForDistribution(cars, carsForTurkmenistan);

        List<Car> carsForUzbekistan = cars.stream()
                .filter(car -> (car.getMass() < 1500)
                        || "BMW".equals(car.getCarMake())
                        || "Lexus".equals(car.getCarMake())
                        || "Chrysler".equals(car.getCarMake())
                        || "Toyota".equals(car.getCarMake()))
                .toList();
        cars = remainingVehiclesForDistribution(cars, carsForUzbekistan);

        List<Car> carsForKazahstan = cars.stream()
                .filter(car -> ("Black".equals(car.getColor()) && (car.getMass() > 4000))
                        || "GMC".equals(car.getCarMake())
                        || "Dodge".equals(car.getCarMake()))
                .toList();
        cars = remainingVehiclesForDistribution(cars, carsForKazahstan);

        List<Car> carsForKargustan = cars.stream()
                .filter(car -> (car.getReleaseYear() < 1982)
                        || "Civic".equals(car.getCarModel())
                        || "Cherokee".equals(car.getCarModel()))
                .toList();
        cars = remainingVehiclesForDistribution(cars, carsForKargustan);

        List<Car> carsForRussian = cars.stream()
                .filter(car -> (!"Yellow".equals(car.getColor())
                        && !"Red".equals(car.getColor())
                        && !"Green".equals(car.getColor())
                        && !"Blue".equals(car.getColor())
                        || car.getPrice() > 40000))
                .toList();
        cars = remainingVehiclesForDistribution(cars, carsForRussian);

        List<Car> carsForMongolian = cars.stream()
                .filter(car -> car.getVin().contains("59"))
                .toList();

        System.out.printf("Logistics costs to Turkmenistan -> %.2f \n", totalCostLogistics(carsForRussian));
        System.out.printf("Logistics costs to Uzbekistan -> %.2f \n", totalCostLogistics(carsForUzbekistan));
        System.out.printf("Logistics costs to Kazahstan -> %.2f \n", totalCostLogistics(carsForKazahstan));
        System.out.printf("Logistics costs to Kargustan -> %.2f \n", totalCostLogistics(carsForKargustan));
        System.out.printf("Logistics costs to Russia -> %.2f \n", totalCostLogistics(carsForRussian));
        System.out.printf("Logistics costs to Mongolian -> %.2f \n", totalCostLogistics(carsForMongolian));

        List<Car> listCars = Stream.of(carsForKargustan, carsForKazahstan, carsForRussian,
                carsForMongolian, carsForTurkmenistan, carsForUzbekistan).flatMap(Collection::stream).toList();

        double totalSumLogistic = totalCostLogistics(listCars);
        System.out.printf("Sum of all logistics -> %.2f \n", totalSumLogistic);

        double amountOfProfit = listCars.stream()
                .map(Car::getPrice)
                .reduce(0, Integer::sum);

        double logisticsCompanyProfit = amountOfProfit - totalSumLogistic;
        System.out.printf("Logistics company of profit -> %.2f \n", logisticsCompanyProfit);
    }

    private static double totalCostLogistics(List<Car> cars) {
        final double logisticCost = 7.14;
        return cars.stream().map(Car::getMass).reduce(0, Integer::sum) * logisticCost;
    }

    private static List<Car> remainingVehiclesForDistribution(List<Car> cars, List<Car> distributedCars) {
        return cars.stream()
                .filter(car -> !distributedCars.contains(car)).toList();
    }

    private static void task15() throws IOException {
        System.out.println("--------------------------Task 15-----------------------");
        List<Flower> flowers = Util.getFlowers();
        double plantCost = flowers.stream()
                .sorted(Comparator.comparing(Flower::getOrigin)
                        .reversed()
                        .thenComparing(Flower::getPrice)
                        .thenComparing(Flower::getWaterConsumptionPerDay)
                        .reversed())
                .filter(flower -> flower.getCommonName().matches("^[CDEFGHIJKLMNOPQRS].{0,}"))
                .filter(flower -> flower.isShadePreferred()
                        && flower.getFlowerVaseMaterial().contains("Glass")
                        && flower.getFlowerVaseMaterial().contains("Aluminum")
                        && flower.getFlowerVaseMaterial().contains("Steel"))
                .mapToDouble(flower -> flower.getPrice()
                        + flower.getWaterConsumptionPerDay() * (5 * 365) * 1.39)
                .reduce(0.0d, Double::sum);
        System.out.printf("Cost of plant maintenance -> %.2f \n", plantCost);
    }

    private static void task16() throws IOException {


        System.out.println("--------------------------Task 16-----------------------");

        List<Car> cars = Util.getCars();

        List<Flower> flowers = Util.getFlowers();
        Map<String, List<Flower>> flowerMap = flowers.stream()
                .sorted(Comparator.comparing(Flower::getPrice).reversed())
                .filter(flower -> !flower.getFlowerVaseMaterial().contains("Aluminum")
                        && !flower.getFlowerVaseMaterial().contains("Brass"))
                .limit(100)
                .collect(Collectors.groupingBy(Flower::getPlantFamily));

        flowerMap.entrySet().stream().filter(plantFamily -> "Rubiaceae".equals(plantFamily.getKey()))
                .findFirst()
                .ifPresent(System.out::println);
        flowerMap.entrySet().forEach(System.out::println);

        double totalSumCost = flowerMap.values()
                .stream()
                .mapToDouble(flower -> flower.stream()
                        .mapToDouble(Flower::getPrice)
                        .sum())
                .reduce(0.0d, Double::sum);
        System.out.println("TotalSumCost -> " + totalSumCost);
    }

}
