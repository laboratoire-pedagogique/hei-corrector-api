package mg.hei.heicorrectorapi.endpoint;

import mg.hei.heicorrectorapi.endpoint.converter.PageConverter;
import mg.hei.heicorrectorapi.endpoint.converter.PageSizeConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ConverterConfigurer implements WebMvcConfigurer {
  @Override
  public void addFormatters(FormatterRegistry registry) {
    registry.addConverter(new PageConverter());
    registry.addConverter(new PageSizeConverter());
  }
}
