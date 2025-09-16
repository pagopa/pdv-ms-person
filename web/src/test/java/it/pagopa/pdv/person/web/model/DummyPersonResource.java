package it.pagopa.pdv.person.web.model;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

public class DummyPersonResource extends PersonResource {

  public DummyPersonResource() {
    setId(UUID.randomUUID());
    setName(new DummyCertifiedFieldResource<>(String.class));
    setFamilyName(new DummyCertifiedFieldResource<>(String.class));
    setEmail(new DummyCertifiedFieldResource<>(String.class));
    setBirthDate(new DummyCertifiedFieldResource<>(LocalDate.class));
    setWorkContacts(Map.of("inst-1", new DummyWorkContactResource()));

    setSpidCode(new DummyCertifiedFieldResource<>(String.class));
    setPlaceOfBirth(new DummyCertifiedFieldResource<>(String.class));
    setCountyOfBirth(new DummyCertifiedFieldResource<>(String.class));
    setGender(new DummyCertifiedFieldResource<>(String.class));
    setCompanyName(new DummyCertifiedFieldResource<>(String.class));
    setRegisteredOffice(new DummyCertifiedFieldResource<>(String.class));
    setIvaCode(new DummyCertifiedFieldResource<>(String.class));
    setIdCard(new DummyCertifiedFieldResource<>(String.class));
    setMobilePhone(new DummyCertifiedFieldResource<>(String.class));
    setAddress(new DummyCertifiedFieldResource<>(String.class));
    setExpirationDate(new DummyCertifiedFieldResource<>(LocalDate.class));
    setDigitalAddress(new DummyCertifiedFieldResource<>(String.class));
    setDomicileAddress(new DummyCertifiedFieldResource<>(String.class));
    setDomicilePlace(new DummyCertifiedFieldResource<>(String.class));
    setDomicilePostalCode(new DummyCertifiedFieldResource<>(String.class));
    setDomicileProvince(new DummyCertifiedFieldResource<>(String.class));
    setDomicileCountry(new DummyCertifiedFieldResource<>(String.class));
    setQualification(new DummyCertifiedFieldResource<>(String.class));
    setCommonName(new DummyCertifiedFieldResource<>(String.class));
    setSurname(new DummyCertifiedFieldResource<>(String.class));
    setGivenName(new DummyCertifiedFieldResource<>(String.class));
    setPreferredUsername(new DummyCertifiedFieldResource<>(String.class));
    setTitle(new DummyCertifiedFieldResource<>(String.class));
    setUserCertificate(new DummyCertifiedFieldResource<>(String.class));
    setEmployeeNumber(new DummyCertifiedFieldResource<>(String.class));
    setOrgUnitName(new DummyCertifiedFieldResource<>(String.class));
    setPreferredLanguage(new DummyCertifiedFieldResource<>(String.class));
    setCountry(new DummyCertifiedFieldResource<>(String.class));
    setStateOrProvince(new DummyCertifiedFieldResource<>(String.class));
    setCity(new DummyCertifiedFieldResource<>(String.class));
    setPostalCode(new DummyCertifiedFieldResource<>(String.class));
    setStreet(new DummyCertifiedFieldResource<>(String.class));
  }

  public static class DummyWorkContactResource extends WorkContactResource {

    public DummyWorkContactResource() {
      setEmail(new DummyCertifiedFieldResource<>(String.class));
      setMobilePhone(new DummyCertifiedFieldResource<>(String.class));
      setTelephone(new DummyCertifiedFieldResource<>(String.class));
    }
  }
}
