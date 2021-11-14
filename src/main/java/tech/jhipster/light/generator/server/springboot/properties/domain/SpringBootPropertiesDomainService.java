package tech.jhipster.light.generator.server.springboot.properties.domain;

import static tech.jhipster.light.common.domain.FileUtils.getPath;
import static tech.jhipster.light.common.domain.FileUtils.read;
import static tech.jhipster.light.generator.project.domain.Constants.MAIN_RESOURCES;
import static tech.jhipster.light.generator.project.domain.Constants.TEST_RESOURCES;
import static tech.jhipster.light.generator.server.springboot.core.domain.SpringBoot.*;

import java.io.IOException;
import tech.jhipster.light.common.domain.FileUtils;
import tech.jhipster.light.error.domain.GeneratorException;
import tech.jhipster.light.generator.project.domain.Project;
import tech.jhipster.light.generator.project.domain.ProjectRepository;

public class SpringBootPropertiesDomainService implements SpringBootPropertiesService {

  private final ProjectRepository projectRepository;

  public SpringBootPropertiesDomainService(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  @Override
  public void addProperties(Project project, String key, Object value) {
    try {
      String currentApplicationProperties = read(getPath(project.getFolder(), MAIN_RESOURCES, "config", APPLICATION_PROPERTIES));
      String propertiesWithNeedle = key + "=" + value + System.lineSeparator() + NEEDLE_APPLICATION_PROPERTIES;
      String updatedApplicationProperties = FileUtils.replace(
        currentApplicationProperties,
        NEEDLE_APPLICATION_PROPERTIES,
        propertiesWithNeedle
      );

      projectRepository.write(project, updatedApplicationProperties, getPath(MAIN_RESOURCES, "config"), APPLICATION_PROPERTIES);
    } catch (IOException e) {
      throw new GeneratorException("Error when adding properties");
    }
  }

  @Override
  public void addPropertiesTest(Project project, String key, Object value) {
    try {
      String currentApplicationProperties = read(getPath(project.getFolder(), TEST_RESOURCES, "config", APPLICATION_PROPERTIES));
      String propertiesWithNeedle = key + "=" + value + System.lineSeparator() + NEEDLE_APPLICATION_TEST_PROPERTIES;
      String updatedApplicationProperties = FileUtils.replace(
        currentApplicationProperties,
        NEEDLE_APPLICATION_TEST_PROPERTIES,
        propertiesWithNeedle
      );

      projectRepository.write(project, updatedApplicationProperties, getPath(TEST_RESOURCES, "config"), APPLICATION_PROPERTIES);
    } catch (IOException e) {
      throw new GeneratorException("Error when adding properties");
    }
  }
}