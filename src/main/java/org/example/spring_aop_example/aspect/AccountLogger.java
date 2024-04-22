package org.example.spring_aop_example.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.logging.Logger;

@Aspect
@Component
public class AccountLogger {

    private final Logger logger = Logger.getLogger(getClass().getName());


    /**
     - @Poincut: xác định một điểm cắt
     - within(...): xác định điểm cắt kiểu within là loại đối tượng áp dụng
     - @org.springframework.web.bind.annotation.RestController:là chú thích @CRestController
     - * đại diện cho bất kì tên lớp nào
        => khía cạnh áp dụng cho tất cả các phương thức nằm trong các ớp được chú
     thích bằng @RestController
     */

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void controllerMethods(){}

    /**
     - @Around: xác định điểm cắt là around
     - execution: xác định loại execution là áp dụng cho method
     - * đại diện cho kiểu trả về bất kỳ
     - org.example.spring_aop_example.controller.AccountController: Áp dụng cho lớp AccountController
     - .* biểu thị cho tên method bất kì
     - (..) biểu thị cho bất kì đối số nào
     => Khía cạnh áp dụng cho tất cả các method trong lớp AccountController
     */
    //method áp dụng cho lớp AccountController được chú thích bằng @Rescontroller
    @Around("controllerMethods() && execution(* org.example.spring_aop_example.controller.AccountController.*(..))")
    public Object logUserActivity(ProceedingJoinPoint joinPoint) throws  Throwable {
        //ghi log trước khi thực hiện method
        String methodName = joinPoint.getSignature().getName();
        /**
         - RequestContextHolder: là một lớp trong spring framwork chứa các thông tin về request
         - currentRequestAttributes(): trả về đối tượng RequestAttributes chứa các thuộc tính của request như thông tin về
         sesion, header và thông tin khác
         -
         */
        String remoteAddress = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getRemoteAddr();
        logger.info("User activity started: " + methodName + ",IP address: "+ remoteAddress);

        //thực hiện method gốc
        Object result = joinPoint.proceed();

        //log khi chạy xong method gốc
        logger.info("User activity finished: "+ methodName);
        return result;//trả về kết quả của method gốc để nó vẫn hoạt động đúng
    }

}
