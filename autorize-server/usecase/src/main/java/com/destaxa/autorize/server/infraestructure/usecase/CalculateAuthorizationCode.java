package com.destaxa.autorize.server.infraestructure.usecase;

import org.jpos.iso.ISOMsg;

public interface CalculateAuthorizationCode {
    String calculate(String responseCode);
}
