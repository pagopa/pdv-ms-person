package it.pagopa.pdv.person.web.model.mapper;

import it.pagopa.pdv.person.connector.model.CertifiableField;
import it.pagopa.pdv.person.connector.model.PersonDetailsOperations;
import it.pagopa.pdv.person.connector.model.PersonDto;
import it.pagopa.pdv.person.connector.model.PersonIdDto;
import it.pagopa.pdv.person.web.model.CertifiableFieldResource;
import it.pagopa.pdv.person.web.model.PersonResource;
import it.pagopa.pdv.person.web.model.SavePersonDto;
import it.pagopa.pdv.person.web.model.SavePersonNamespaceDto;
import it.pagopa.pdv.person.web.model.WorkContactResource;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PersonMapper {

  public static PersonIdDto assembles(UUID id, String namespace,
      SavePersonNamespaceDto savePersonNamespaceDto) {
    PersonIdDto personIdDto = null;
    if (id != null) {
      personIdDto = new PersonIdDto();
      personIdDto.setGlobalId(id.toString());
      personIdDto.setNamespace(namespace);
      if (savePersonNamespaceDto != null && savePersonNamespaceDto.getNamespacedId() != null) {
        personIdDto.setNamespacedId(savePersonNamespaceDto.getNamespacedId().toString());
      }
    }
    return personIdDto;
  }


  public static PersonDto assembles(UUID id, SavePersonDto savePersonDto) {
    PersonDto personDto = null;
    if (id != null) {
      personDto = new PersonDto();
      personDto.setId(id.toString());
      if (savePersonDto != null) {
        personDto.setName(savePersonDto.getName());
        personDto.setFamilyName(savePersonDto.getFamilyName());
        personDto.setEmail(savePersonDto.getEmail());
        personDto.setBirthDate(savePersonDto.getBirthDate());
        personDto.setSpidCode(savePersonDto.getSpidCode());
        personDto.setPlaceOfBirth(savePersonDto.getPlaceOfBirth());
        personDto.setCountyOfBirth(savePersonDto.getCountyOfBirth());
        personDto.setGender(savePersonDto.getGender());
        personDto.setCompanyName(savePersonDto.getCompanyName());
        personDto.setRegisteredOffice(savePersonDto.getRegisteredOffice());
        personDto.setIvaCode(savePersonDto.getIvaCode());
        personDto.setIdCard(savePersonDto.getIdCard());
        personDto.setMobilePhone(savePersonDto.getMobilePhone());
        personDto.setAddress(savePersonDto.getAddress());
        personDto.setExpirationDate(savePersonDto.getExpirationDate());
        personDto.setDigitalAddress(savePersonDto.getDigitalAddress());
        personDto.setDomicileAddress(savePersonDto.getDomicileAddress());
        personDto.setDomicilePlace(savePersonDto.getDomicilePlace());
        personDto.setDomicilePostalCode(savePersonDto.getDomicilePostalCode());
        personDto.setDomicileProvince(savePersonDto.getDomicileProvince());
        personDto.setDomicileCountry(savePersonDto.getDomicileCountry());
        personDto.setQualification(savePersonDto.getQualification());
        personDto.setCommonName(savePersonDto.getCommonName());
        personDto.setSurname(savePersonDto.getSurname());
        personDto.setGivenName(savePersonDto.getGivenName());
        personDto.setPreferredUsername(savePersonDto.getPreferredUsername());
        personDto.setTitle(savePersonDto.getTitle());
        personDto.setUserCertificate(savePersonDto.getUserCertificate());
        personDto.setEmployeeNumber(savePersonDto.getEmployeeNumber());
        personDto.setOrgUnitName(savePersonDto.getOrgUnitName());
        personDto.setPreferredLanguage(savePersonDto.getPreferredLanguage());
        personDto.setCountry(savePersonDto.getCountry());
        personDto.setStateOrProvince(savePersonDto.getStateOrProvince());
        personDto.setCity(savePersonDto.getCity());
        personDto.setPostalCode(savePersonDto.getPostalCode());
        personDto.setStreet(savePersonDto.getStreet());
        if (savePersonDto.getWorkContacts() != null) {
          personDto.setWorkContacts(savePersonDto.getWorkContacts().entrySet().stream()
              .map(entry -> Map.entry(entry.getKey(), toDto(entry.getValue())))
              .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
        }
      }
    }
    return personDto;
  }


  static PersonDto.WorkContactDto toDto(WorkContactResource workContactResource) {
    PersonDto.WorkContactDto workContactDto = null;
    if (workContactResource != null) {
      workContactDto = new PersonDto.WorkContactDto();
      if (workContactResource.getEmail() != null) {
        workContactDto.setEmail(workContactResource.getEmail());
      }
      if (workContactResource.getMobilePhone() != null) {
        workContactDto.setMobilePhone(workContactResource.getMobilePhone());
      }
      if (workContactResource.getTelephone() != null) {
        workContactDto.setTelephone(workContactResource.getTelephone());
      }
    }
    return workContactDto;
  }


  public static PersonResource toResource(PersonDetailsOperations personDetailsOperations) {
    PersonResource personResource = null;
    if (personDetailsOperations != null) {
      personResource = new PersonResource();
      personResource.setId(UUID.fromString(personDetailsOperations.getId()));
      if (personDetailsOperations.getName() != null) {
        personResource.setName(new CertifiableFieldResource<>(personDetailsOperations.getName()));
      }
      if (personDetailsOperations.getFamilyName() != null) {
        personResource.setFamilyName(
            new CertifiableFieldResource<>(personDetailsOperations.getFamilyName()));
      }
      if (personDetailsOperations.getEmail() != null) {
        personResource.setEmail(new CertifiableFieldResource<>(personDetailsOperations.getEmail()));
      }
      if (personDetailsOperations.getBirthDate() != null) {
        personResource.setBirthDate(
            new CertifiableFieldResource<>(personDetailsOperations.getBirthDate()));
      }
      if (personDetailsOperations.getSpidCode() != null) {
        personResource.setSpidCode(
            new CertifiableFieldResource<>(personDetailsOperations.getSpidCode()));
      }
      if (personDetailsOperations.getPlaceOfBirth() != null) {
        personResource.setPlaceOfBirth(
            new CertifiableFieldResource<>(personDetailsOperations.getPlaceOfBirth()));
      }
      if (personDetailsOperations.getCountyOfBirth() != null) {
        personResource.setCountyOfBirth(
            new CertifiableFieldResource<>(personDetailsOperations.getCountyOfBirth()));
      }
      if (personDetailsOperations.getGender() != null) {
        personResource.setGender(
            new CertifiableFieldResource<>(personDetailsOperations.getGender()));
      }
      if (personDetailsOperations.getCompanyName() != null) {
        personResource.setCompanyName(
            new CertifiableFieldResource<>(personDetailsOperations.getCompanyName()));
      }
      if (personDetailsOperations.getRegisteredOffice() != null) {
        personResource.setRegisteredOffice(
            new CertifiableFieldResource<>(personDetailsOperations.getRegisteredOffice()));
      }
      if (personDetailsOperations.getIvaCode() != null) {
        personResource.setIvaCode(
            new CertifiableFieldResource<>(personDetailsOperations.getIvaCode()));
      }
      if (personDetailsOperations.getIdCard() != null) {
        personResource.setIdCard(
            new CertifiableFieldResource<>(personDetailsOperations.getIdCard()));
      }
      if (personDetailsOperations.getMobilePhone() != null) {
        personResource.setMobilePhone(
            new CertifiableFieldResource<>(personDetailsOperations.getMobilePhone()));
      }
      if (personDetailsOperations.getAddress() != null) {
        personResource.setAddress(
            new CertifiableFieldResource<>(personDetailsOperations.getAddress()));
      }
      if (personDetailsOperations.getExpirationDate() != null) {
        personResource.setExpirationDate(
            new CertifiableFieldResource<>(personDetailsOperations.getExpirationDate()));
      }
      if (personDetailsOperations.getDigitalAddress() != null) {
        personResource.setDigitalAddress(
            new CertifiableFieldResource<>(personDetailsOperations.getDigitalAddress()));
      }
      if (personDetailsOperations.getDomicileAddress() != null) {
        personResource.setDomicileAddress(
            new CertifiableFieldResource<>(personDetailsOperations.getDomicileAddress()));
      }
      if (personDetailsOperations.getDomicilePlace() != null) {
        personResource.setDomicilePlace(
            new CertifiableFieldResource<>(personDetailsOperations.getDomicilePlace()));
      }
      if (personDetailsOperations.getDomicilePostalCode() != null) {
        personResource.setDomicilePostalCode(
            new CertifiableFieldResource<>(personDetailsOperations.getDomicilePostalCode()));
      }
      if (personDetailsOperations.getDomicileProvince() != null) {
        personResource.setDomicileProvince(
            new CertifiableFieldResource<>(personDetailsOperations.getDomicileProvince()));
      }
      if (personDetailsOperations.getDomicileCountry() != null) {
        personResource.setDomicileCountry(
            new CertifiableFieldResource<>(personDetailsOperations.getDomicileCountry()));
      }
      if (personDetailsOperations.getQualification() != null) {
        personResource.setQualification(
            new CertifiableFieldResource<>(personDetailsOperations.getQualification()));
      }
      if (personDetailsOperations.getCommonName() != null) {
        personResource.setCommonName(
            new CertifiableFieldResource<>(personDetailsOperations.getCommonName()));
      }
      if (personDetailsOperations.getSurname() != null) {
        personResource.setSurname(
            new CertifiableFieldResource<>(personDetailsOperations.getSurname()));
      }
      if (personDetailsOperations.getGivenName() != null) {
        personResource.setGivenName(
            new CertifiableFieldResource<>(personDetailsOperations.getGivenName()));
      }
      if (personDetailsOperations.getPreferredUsername() != null) {
        personResource.setPreferredUsername(
            new CertifiableFieldResource<>(personDetailsOperations.getPreferredUsername()));
      }
      if (personDetailsOperations.getTitle() != null) {
        personResource.setTitle(new CertifiableFieldResource<>(personDetailsOperations.getTitle()));
      }
      if (personDetailsOperations.getUserCertificate() != null) {
        personResource.setUserCertificate(
            new CertifiableFieldResource<>(personDetailsOperations.getUserCertificate()));
      }
      if (personDetailsOperations.getEmployeeNumber() != null) {
        personResource.setEmployeeNumber(
            new CertifiableFieldResource<>(personDetailsOperations.getEmployeeNumber()));
      }
      if (personDetailsOperations.getOrgUnitName() != null) {
        personResource.setOrgUnitName(
            new CertifiableFieldResource<>(personDetailsOperations.getOrgUnitName()));
      }
      if (personDetailsOperations.getPreferredLanguage() != null) {
        personResource.setPreferredLanguage(
            new CertifiableFieldResource<>(personDetailsOperations.getPreferredLanguage()));
      }
      if (personDetailsOperations.getCountry() != null) {
        personResource.setCountry(
            new CertifiableFieldResource<>(personDetailsOperations.getCountry()));
      }
      if (personDetailsOperations.getStateOrProvince() != null) {
        personResource.setStateOrProvince(
            new CertifiableFieldResource<>(personDetailsOperations.getStateOrProvince()));
      }
      if (personDetailsOperations.getCity() != null) {
        personResource.setCity(new CertifiableFieldResource<>(personDetailsOperations.getCity()));
      }
      if (personDetailsOperations.getPostalCode() != null) {
        personResource.setPostalCode(
            new CertifiableFieldResource<>(personDetailsOperations.getPostalCode()));
      }
      if (personDetailsOperations.getStreet() != null) {
        personResource.setStreet(
            new CertifiableFieldResource<>(personDetailsOperations.getStreet()));
      }
      if (personDetailsOperations.getWorkContacts() != null) {
        personResource.setWorkContacts(personDetailsOperations.getWorkContacts().entrySet().stream()
            .map(entry -> Map.entry(entry.getKey(), toResource(entry.getValue())))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
      }
    }
    return personResource;
  }


  static WorkContactResource toResource(
      PersonDetailsOperations.WorkContactOperations workContactOperations) {
    WorkContactResource workContactResource = null;
    if (workContactOperations != null) {
      workContactResource = new WorkContactResource();
      if (workContactOperations.getEmail() != null) {
        if (workContactOperations.getEmail().getValue() != null) {
          workContactResource.setEmail(
              new CertifiableFieldResource<>(workContactOperations.getEmail()));
        } else {
          CertifiableField<String> emptyEmail = workContactOperations.getEmail();
          emptyEmail.setValue("");
          workContactResource.setEmail(new CertifiableFieldResource<>(emptyEmail));
        }
      }

      CertifiableField<String> mobilePhone = workContactOperations.getMobilePhone();
      if (mobilePhone != null) {
        if (mobilePhone.getValue() != null) {
          workContactResource.setMobilePhone(new CertifiableFieldResource<>(mobilePhone));
        } else {
          mobilePhone.setValue("");
          workContactResource.setMobilePhone(new CertifiableFieldResource<>(mobilePhone));
        }
      }

      CertifiableField<String> telephone = workContactOperations.getTelephone();
      if (telephone != null) {
        if (telephone.getValue() != null) {
          workContactResource.setTelephone(new CertifiableFieldResource<>(telephone));
        } else {
          telephone.setValue("");
          workContactResource.setTelephone(new CertifiableFieldResource<>(telephone));
        }
      }
    }
    return workContactResource;
  }

}
