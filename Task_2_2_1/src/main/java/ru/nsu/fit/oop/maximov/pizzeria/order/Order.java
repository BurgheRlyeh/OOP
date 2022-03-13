package ru.nsu.fit.oop.maximov.pizzeria.order;

public record Order(int id) {
    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
