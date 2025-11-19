package br.com.senac.ado.zoologico.exception;

public class MaxCapacityReachedException extends RuntimeException {
    public MaxCapacityReachedException(String message) {
        super(message);
    }
}
