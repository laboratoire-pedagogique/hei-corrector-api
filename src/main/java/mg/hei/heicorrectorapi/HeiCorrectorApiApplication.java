package mg.hei.heicorrectorapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class HeiCorrectorApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(HeiCorrectorApiApplication.class, args);
  }

}
