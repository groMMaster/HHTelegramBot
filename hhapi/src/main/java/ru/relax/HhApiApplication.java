package ru.relax;

import ru.relax.controller.HhApiController;

public class HhApiApplication {

    public static void main(String[] args) {
        for (var a : HhApiController.GetVacanciesByTag("Программист")) {
            System.out.println(a);
        }
    }
}
