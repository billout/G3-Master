package com.billoutdgr.g3;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.billoutdgr.g3");

        noClasses()
            .that()
            .resideInAnyPackage("com.billoutdgr.g3.service..")
            .or()
            .resideInAnyPackage("com.billoutdgr.g3.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..com.billoutdgr.g3.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
