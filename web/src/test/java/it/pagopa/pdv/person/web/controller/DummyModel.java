package it.pagopa.pdv.person.web.controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
public class DummyModel {

    @NotNull
    String value;

}
