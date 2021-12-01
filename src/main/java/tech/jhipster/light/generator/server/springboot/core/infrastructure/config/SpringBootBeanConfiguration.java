package tech.jhipster.light.generator.server.springboot.core.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.light.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.light.generator.project.domain.ProjectRepository;
import tech.jhipster.light.generator.server.springboot.core.domain.SpringBootDomainService;
import tech.jhipster.light.generator.server.springboot.core.domain.SpringBootService;

@Configuration
public class SpringBootBeanConfiguration {

  private final ProjectRepository projectRepository;
  private final BuildToolService buildToolService;

  public SpringBootBeanConfiguration(ProjectRepository projectRepository, BuildToolService buildToolService) {
    this.projectRepository = projectRepository;
    this.buildToolService = buildToolService;
  }

  @Bean
  public SpringBootService springBootService() {
    return new SpringBootDomainService(projectRepository, buildToolService);
  }
}