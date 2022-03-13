package ru.nsu.fit.oop.maximov;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import ru.nsu.fit.oop.maximov.pizzeria.Pizzeria;
import ru.nsu.fit.oop.maximov.pizzeria.PizzeriaConfig;
import ru.nsu.fit.oop.maximov.pizzeria.order.Order;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Random;

public class Main {
    private final static String path = "Task_2_2_1/src/test/java/ru/nsu/fit/oop/maximov/pizzeria/testdata/";

    public static void main(String[] args) {
        try {
            var pizzeriaConfig = new Gson().fromJson(new FileReader(path + "config.json"), PizzeriaConfig.class);

            var pizzeria = new Pizzeria(pizzeriaConfig);

            for (int orderID = 0; orderID < 5; ++orderID) {
                try {
                    pizzeria.putOrder(new Order(orderID));
                    Thread.sleep(new Random().nextInt(1000));
                } catch (InterruptedException ignored) {
                }
            }

            pizzeria.stop();
        } catch (FileNotFoundException e) {
            System.err.println("File not found");
        } catch (JsonSyntaxException e) {
            System.err.println("Json syntax error");
        } catch (Exception e) {
            System.err.println("Oops, something went wrong");
        }
    }
}
