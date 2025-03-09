package com.destaxa.payment.api.application.usecaseimpl;

import com.destaxa.payment.api.usecase.PaymentService;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;

import java.io.IOException;

public class PaymentServiceImpl implements PaymentService {

    @Override
    public ISOMsg authorize(ISOMsg isoMsg) throws IOException, ISOException {


        return null;
    }
}
