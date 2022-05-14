package pl.artur.zaczek.car.mechanic.rest.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.MockMvc
import pl.artur.zaczek.car.mechanic.BaseControllerITSpec
import pl.artur.zaczek.car.mechanic.jpa.VehicleRepository

class VehicleControllerTest extends BaseControllerITSpec{
    private static final String VEHICLE_URI = "/api/vehicle"

    @Autowired
    VehicleRepository vehicleRepository

    @Autowired
    private MockMvc mockMvc
}
