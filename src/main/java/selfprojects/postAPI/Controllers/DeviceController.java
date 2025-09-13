package selfprojects.postAPI.Controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import selfprojects.postAPI.Services.DeviceTokenService;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/devices")
public class DeviceController {

    private final DeviceTokenService deviceTokenService;

    public DeviceController(DeviceTokenService deviceTokenService) {
        this.deviceTokenService = deviceTokenService;


    }


    @PostMapping("/register")
    public ResponseEntity<String> registerDevice(
            @RequestBody Map<String, String> device) {
        String deviceToken = device.get("deviceToken");

        return deviceTokenService.sendDeviceToken(deviceToken);

    }

}
