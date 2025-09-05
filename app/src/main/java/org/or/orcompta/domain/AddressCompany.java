package org.or.orcompta.domain;

import java.util.HashMap;
import java.util.Map;

public class AddressCompany {

    private final String numero;
    private final String address;
    private final String address2;
    private final String postalCode;
    private final String city;

    public AddressCompany(String numero, String address, String address2, String postalCode, String city) {
        this.numero = numero;
        this.address = address;
        this.address2 = address2;
        this.postalCode = postalCode;
        this.city = city;
    }

    @Override
    public String toString() {
        return numero + " " + address + " " + address2 + " " + postalCode + " " + city;
    }

    public Map<String, String> getAddressMap() {
        Map<String, String> addressMap = new HashMap<>();
        addressMap.put("numero", numero);
        addressMap.put("address", address);
        addressMap.put("address2", address2);
        addressMap.put("postalCode", postalCode);
        addressMap.put("city", city);
        return addressMap;
    }

}
