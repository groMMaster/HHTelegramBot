package ru.relax;

import ru.relax.controller.HhApiController;

public class HhApi17Application {

    public static void main(String[] args) {
        for (var a : HhApiController.GetVacanciesByTag("Программист")) {
            System.out.println(a);
        }
    }
}
