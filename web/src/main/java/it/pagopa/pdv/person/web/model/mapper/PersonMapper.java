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

    public static PersonIdDto assembles(UUID id, String namespace, SavePersonNamespaceDto savePersonNamespaceDto) {
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
                personResource.setFamilyName(new CertifiableFieldResource<>(personDetailsOperations.getFamilyName()));
            }
            if (personDetailsOperations.getEmail() != null) {
                personResource.setEmail(new CertifiableFieldResource<>(personDetailsOperations.getEmail()));
            }
            if (personDetailsOperations.getBirthDate() != null) {
                personResource.setBirthDate(new CertifiableFieldResource<>(personDetailsOperations.getBirthDate()));
            }
            if (personDetailsOperations.getWorkContacts() != null) {
                personResource.setWorkContacts(personDetailsOperations.getWorkContacts().entrySet().stream()
                        .map(entry -> Map.entry(entry.getKey(), toResource(entry.getValue())))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
            }
        }
        return personResource;
    }


    static WorkContactResource toResource(PersonDetailsOperations.WorkContactOperations workContactOperations) {
        WorkContactResource workContactResource = null;
        if (workContactOperations != null) {
            workContactResource = new WorkContactResource();
            if (workContactOperations.getEmail() != null) {
                if(workContactOperations.getEmail().getValue() != null) {
                    workContactResource.setEmail(new CertifiableFieldResource<>(workContactOperations.getEmail()));
                }
                else{
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
