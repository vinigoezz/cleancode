package br.com.nexfleet.architecture;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

import com.tngtech.archunit.ArchConfiguration;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArquiteturaBackendTest {

    static {
        ArchConfiguration.get().setResolveMissingDependenciesFromClassPath(false);
    }

    private final JavaClasses classes = new ClassFileImporter()
            .withImportOption(new ImportOption.DoNotIncludeTests())
            .importPackages("br.com.nexfleet");

    @Test
    void dominioNaoDeveDependerDeFrameworks() {
        noClasses()
                .that().resideInAPackage("..domain..")
                .should().dependOnClassesThat().resideInAnyPackage(
                        "org.springframework..",
                        "jakarta.persistence.."
                )
                .check(classes);
    }

    @Test
    void aplicacaoNaoDeveDependerDeApiOuInfraestrutura() {
        noClasses()
                .that().resideInAPackage("..application..")
                .should().dependOnClassesThat().resideInAnyPackage(
                        "..api..",
                        "..infrastructure.."
                )
                .check(classes);
    }

    @Test
    void apiNaoDeveAcessarInfraestruturaDiretamente() {
        noClasses()
                .that().resideInAPackage("..api..")
                .should().dependOnClassesThat().resideInAPackage("..infrastructure..")
                .check(classes);
    }

    @Test
    void modulosDeNegocioNaoDevemFormarCiclos() {
        slices()
                .matching("br.com.nexfleet.(*)..")
                .should().beFreeOfCycles()
                .check(classes);
    }
}

