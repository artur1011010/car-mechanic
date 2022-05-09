package pl.artur.zaczek.car.mechanic

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(["test"])
abstract class BaseControllerITSpec extends Specification {
}