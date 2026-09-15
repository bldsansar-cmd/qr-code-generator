package com.qrazy.validator.order;

import jakarta.validation.GroupSequence;

@GroupSequence({

		ValidOrder1.class, ValidOrder2.class, ValidOrder3.class, ValidOrder4.class, ValidOrder5.class,

		ValidOrder6.class, ValidOrder7.class, ValidOrder8.class, ValidOrder9.class, ValidOrder10.class

})

public interface ValidOrderGroup {

}
