package tech.jhipster.light.generator.buildtool.maven.application;

import static tech.jhipster.light.TestUtils.*;
import static tech.jhipster.light.generator.buildtool.maven.application.MavenAssertFiles.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.light.IntegrationTest;
import tech.jhipster.light.generator.buildtool.generic.domain.Dependency;
import tech.jhipster.light.generator.buildtool.generic.domain.Parent;
import tech.jhipster.light.generator.buildtool.generic.domain.Plugin;
import tech.jhipster.light.generator.project.domain.Project;

@IntegrationTest
class MavenApplicationServiceIT {

  @Autowired
  MavenApplicationService mavenApplicationService;

  @Test
  void shouldAddParent() {
    Project project = tmpProjectWithPomXml();

    Parent parent = Parent.builder().groupId("org.springframework.boot").artifactId("spring-boot-starter-parent").version("2.5.3").build();
    mavenApplicationService.addParent(project, parent);

    assertFileContent(
      project,
      "pom.xml",
      List.of(
        "<parent>",
        "<groupId>org.springframework.boot</groupId>",
        "<artifactId>spring-boot-starter-parent</artifactId>",
        "<version>2.5.3</version>",
        "<relativePath/>",
        "</parent>"
      )
    );
  }

  @Test
  void shouldAddDependency() {
    Project project = tmpProjectWithPomXml();

    Dependency dependency = Dependency.builder().groupId("org.springframework.boot").artifactId("spring-boot-starter").build();
    mavenApplicationService.addDependency(project, dependency);

    assertFileContent(
      project,
      "pom.xml",
      List.of(
        "<dependency>",
        "<groupId>org.springframework.boot</groupId>",
        "<artifactId>spring-boot-starter</artifactId>",
        "</dependency>"
      )
    );
  }

  @Test
  void shouldAddDependencyWithScopeTest() {
    Project project = tmpProjectWithPomXml();

    Dependency dependency = Dependency
      .builder()
      .groupId("org.springframework.boot")
      .artifactId("spring-boot-starter-test")
      .scope("test")
      .build();
    mavenApplicationService.addDependency(project, dependency);

    assertFileContent(
      project,
      "pom.xml",
      List.of(
        "<dependency>",
        "<groupId>org.springframework.boot</groupId>",
        "<artifactId>spring-boot-starter-test</artifactId>",
        "<scope>test</scope>",
        "</dependency>"
      )
    );
  }

  @Test
  void shouldAddDependencyWithExclusions() {
    Project project = tmpProjectWithPomXml();

    Dependency dependency = Dependency.builder().groupId("org.springframework.boot").artifactId("spring-boot-starter-web").build();
    Dependency dependencyToExclude = Dependency
      .builder()
      .groupId("org.springframework.boot")
      .artifactId("spring-boot-starter-tomcat")
      .build();
    mavenApplicationService.addDependency(project, dependency, List.of(dependencyToExclude));

    assertFileContent(
      project,
      "pom.xml",
      List.of(
        "<dependency>",
        "<groupId>org.springframework.boot</groupId>",
        "<artifactId>spring-boot-starter-web</artifactId>",
        "<exclusions>",
        "<exclusion>",
        "<groupId>org.springframework.boot</groupId>",
        "<artifactId>spring-boot-starter-tomcat</artifactId>",
        "</exclusion>",
        "</exclusions>",
        "</dependency>"
      )
    );
  }

  @Test
  void shouldAddPlugin() {
    Project project = tmpProjectWithPomXml();

    Plugin plugin = Plugin.builder().groupId("org.springframework.boot").artifactId("spring-boot-maven-plugin").build();
    mavenApplicationService.addPlugin(project, plugin);

    assertFileContent(
      project,
      "pom.xml",
      List.of("<plugin>", "<groupId>org.springframework.boot</groupId>", "<artifactId>spring-boot-maven-plugin</artifactId>", "</plugin>")
    );
  }

  @Test
  void shouldAddProperty() {
    Project project = tmpProjectWithPomXml();

    mavenApplicationService.addProperty(project, "testcontainers", "1.16.0");

    assertFileContent(project, "pom.xml", "    <testcontainers.version>1.16.0</testcontainers.version>");
  }

  @Test
  void shouldInit() {
    Project project = tmpProject();

    mavenApplicationService.init(project);

    assertFilesMaven(project);
    assertFileContent(project, "pom.xml", "<name>jhipster</name>");
    assertFileContent(project, "pom.xml", "<description>JHipster Project</description>");
  }

  @Test
  void shouldAddPomXml() {
    Project project = tmpProject();

    mavenApplicationService.addPomXml(project);

    assertFilesPomXml(project);
    assertFileContent(project, "pom.xml", "<name>jhipster</name>");
    assertFileContent(project, "pom.xml", "<description>JHipster Project</description>");
  }

  @Test
  void shouldAddMavenWrapper() {
    Project project = tmpProject();

    mavenApplicationService.addMavenWrapper(project);

    assertFilesMavenWrapper(project);
  }
}