package mg.hei.heicorrectorapi.endpoint.mapper;

import mg.hei.heicorrectorapi.repository.entity.Test;
import mg.hei.heicorrectorapi.rest.model.TestInfo;
import org.springframework.stereotype.Component;

@Component
public class TestMapper {

  public TestInfo toRest(Test domain) {
    return new TestInfo()
        .id(domain.getId())
        .kata(domain.getKata())
        .totalPoints(domain.getTotalPoints())
        .passed(domain.getPassed())
        .failed(domain.getFailed());
  }
}
