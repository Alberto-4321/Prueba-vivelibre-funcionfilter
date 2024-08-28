package org.prueba;

import org.prueba.services.PruebaLambdasService;

public class PruebaLambdasController {

    PruebaLambdasService pruebaLambdaService = new PruebaLambdasService();

    public static void main(String[] args) {
        PruebaLambdasController pruebaLambdasController=new PruebaLambdasController();
        pruebaLambdasController.prueba1();
        pruebaLambdasController.prueba2();
        pruebaLambdasController.prueba3();
    }

    public void prueba1() {
        pruebaLambdaService.prueba1();
    }

    public void prueba2() {
        pruebaLambdaService.prueba2();
    }

    public void prueba3() {
        pruebaLambdaService.prueba3();
    }

}