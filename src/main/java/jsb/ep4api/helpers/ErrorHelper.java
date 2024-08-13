package jsb.ep4api.helpers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorHelper implements ErrorController {

    @RequestMapping("/error")
    public String errorHandling(HttpServletRequest request){
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        int statusCode = Integer.parseInt(status.toString());
        switch (statusCode){
            case 404:
                return "error/404";
            case 403:
                return "error/403";
            case 500:
                return "error/500";
            default:
                return "error/default";
        }
    }
}
