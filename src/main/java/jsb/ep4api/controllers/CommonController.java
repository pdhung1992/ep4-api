package jsb.ep4api.controllers;

import jsb.ep4api.services.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/common")
public class CommonController {
    @Autowired
    CommonService commonService;

    @GetMapping("/slug")
    public String checkAndCreateSlug(
            @RequestParam String slugCase,
            @RequestParam String title,
            @RequestParam(required = false) String additionalIfExist
    ) {
        System.out.println("additionalIfExist = " + additionalIfExist);
        return commonService.checkAndCreateSlug(slugCase, title, additionalIfExist);
    }
}
