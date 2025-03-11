package com.destaxa.payment.api.usecase;

import org.jpos.iso.ISOMsg;

public interface MessageEmptyUseCase {

    Boolean isEmpty(ISOMsg msg);

}
