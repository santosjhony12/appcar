package com.santosjhony.demo.park.api.web.dto;

import java.math.BigDecimal;

import com.santosjhony.demo.park.api.entity.Car;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public record CarCreateDto(String modelo, String placa, String cor, Integer ano, BigDecimal valor, String montadora, String imagemBase64) {
    public Car toCar(CarCreateDto car){
        Car newCar = new Car();
        newCar.setAno(car.ano);
        newCar.setCor(car.cor);
        newCar.setModelo(car.modelo);
        newCar.setMontadora(car.montadora);
        newCar.setPlaca(car.placa);
        newCar.setValor(car.valor);

        if (car.imagemBase64 != null && !car.imagemBase64.isEmpty()) {
            try {
                // Remove o prefixo 'data:image/jpeg;base64,' ou similar
                String base64Image = car.imagemBase64.replaceFirst("^data:image/[^;]+;base64,", "");

                byte[] imagem = java.util.Base64.getDecoder().decode(base64Image);
                newCar.setImagem(imagem);
            } catch (IllegalArgumentException e) {
                log.error("Erro ao decodificar imagem Base64: {}", e.getMessage());
            }
        } else {
            log.error("Imagem nula");
        }
        return newCar;
    }
}
