package com.safetynet.safetynetsystem.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PersonUtilTest {

    @Test
    void calculateAgeTest() {
        assertThat(PersonUtil.calculateAge("03/06/1984")).isEqualTo(38);
    }
}