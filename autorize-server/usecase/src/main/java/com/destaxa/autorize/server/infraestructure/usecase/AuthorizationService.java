package com.destaxa.autorize.server.infraestructure.usecase;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;

public interface AuthorizationService {
    ISOMsg authorize(ISOMsg isoMsg) throws ISOException;
}
