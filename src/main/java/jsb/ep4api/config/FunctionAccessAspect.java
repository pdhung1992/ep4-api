package jsb.ep4api.config;

import jsb.ep4api.securities.service.AdminDetailsImp;
import jsb.ep4api.services.RoleFunctionService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import java.util.List;

@Aspect
@Component
public class FunctionAccessAspect {
    @Autowired
    private RoleFunctionService roleFunctionService;

    @Around("@within(hasFunctionAccess)")
    public Object checkFunctionAccess(ProceedingJoinPoint joinPoint, HasFunctionAccess hasFunctionAccess) throws Throwable {
        Long functionId = hasFunctionAccess.value();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof AdminDetailsImp)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
        AdminDetailsImp adminDetails = (AdminDetailsImp) authentication.getPrincipal();
        List<Long> functionIds = roleFunctionService.functionIdsByRole(adminDetails.getRole().getId());
        if (functionIds.contains(functionId)) {
            return joinPoint.proceed();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You don't have permission to access this function");
        }
    }
}
