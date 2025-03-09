package com.destaxa.payment.api.usecase;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;

import java.io.IOException;

public interface PaymentService {

    ISOMsg authorize(ISOMsg isoMsg) throws IOException, ISOException;

}
