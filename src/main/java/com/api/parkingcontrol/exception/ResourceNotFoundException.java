package com.api.parkingcontrol.exception;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {

    private final int id;

    public ResourceNotFoundException(int id) {
        super();
        this.id = id;
    }

}
