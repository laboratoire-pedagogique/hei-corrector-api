package mg.hei.heicorrectorapi.model;

import lombok.Getter;
import mg.hei.heicorrectorapi.model.exception.BadRequestException;

public class PageFromOne {

  @Getter
  private final int value;

  public PageFromOne(int value) {
    if (value < 1) {
      throw new BadRequestException("page value must be >=1");
    }
    this.value = value;
  }
}
