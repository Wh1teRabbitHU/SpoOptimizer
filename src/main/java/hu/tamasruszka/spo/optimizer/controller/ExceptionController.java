package hu.tamasruszka.spo.optimizer.controller;

import hu.tamasruszka.spo.optimizer.service.CleanerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * This controller catch and handle all the errors and exceptions. It returns with the HttpStatus number and the
 * error message.
 */
@RestController
@ControllerAdvice
public class ExceptionController implements ErrorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CleanerService.class);

    /**
     * Handling the runtime exceptions
     *
     * @param throwable Runtime exception object
     * @return The simplified error object as a JSON
     */
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleExceptions(Throwable throwable) {
        LOGGER.error(throwable.getMessage(), throwable);

        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, throwable.getMessage());
    }

    /**
     * Instead of using an HTMl error page, it returns a JSON object with the error data
     *
     * @param response The HttpServletResponse object for the request
     * @return The simplified error object as a JSON
     */
    @RequestMapping("/error")
    @ResponseStatus
    public ErrorResponse handleError(HttpServletResponse response) {
        HttpStatus status = HttpStatus.resolve(response.getStatus());

        if (status == null) {
            return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Unknown error!");
        }

        return new ErrorResponse(status, status.getReasonPhrase());
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }

    /**
     * The simplified error object definition.
     */
    @SuppressWarnings("unused")
    private static class ErrorResponse {

        private final int status;

        private final String message;

        private ErrorResponse(HttpStatus status, String message) {
            this.status = status.value();
            this.message = message;
        }

        public int getStatus() {
            return status;
        }

        public String getMessage() {
            return message;
        }

    }

}
