package inc.evil.testing.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;

@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
@Slf4j
public class GlobalExceptionHandler {
	private final MessageSource messageSource;

	public GlobalExceptionHandler(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> onException(Exception e, HttpServletRequest request) {
		log.error("Exception while handling request", e);
		var errorMessages = List.of(e.getMessage());
		ErrorResponse errorModel = ErrorResponse.builder()
				.messages(errorMessages)
				.path(request.getServletPath())
				.build();
		return ResponseEntity.internalServerError()
				.body(errorModel);
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<ErrorResponse> onMissingServletRequestParameterException(
			MissingServletRequestParameterException e,
			HttpServletRequest request) {
		String message = "Parameter: '" + e.getParameterName() + "' of type " + e.getParameterType() +
				" is required but is missing";
		log.error("Exception while handling request: " + message, e);
		var errorMessages = List.of(message);
		ErrorResponse errorModel = ErrorResponse.builder()
				.messages(errorMessages)
				.path(request.getServletPath())
				.build();
		return ResponseEntity.badRequest()
				.body(errorModel);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> onMethodArgumentNotValidException(MethodArgumentNotValidException e,
			HttpServletRequest request) {
		log.error("Exception while handling request.", e);
		BindingResult bindingResult = e.getBindingResult();
		List<String> errorMessages = new ArrayList<>();
		for (ObjectError error : bindingResult.getAllErrors()) {
			String resolvedMessage = messageSource.getMessage(error, Locale.US);
			if (error instanceof FieldError fieldError) {
				errorMessages.add(
						String.format("Field '%s' %s but value was '%s'", fieldError.getField(), resolvedMessage,
								fieldError.getRejectedValue()));
			}
			else {
				errorMessages.add(resolvedMessage);
			}
		}
		ErrorResponse errorModel = ErrorResponse.builder()
				.messages(errorMessages)
				.path(request.getServletPath())
				.build();
		return ResponseEntity.badRequest()
				.body(errorModel);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErrorResponse> onConstraintViolationException(ConstraintViolationException e,
			HttpServletRequest request) {
		log.error("Exception while handling request.", e);
		Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
		List<String> errorMessages = new ArrayList<>();
		for (ConstraintViolation<?> violation : constraintViolations) {
			errorMessages.add(
					String.format("Field '%s' %s but value was '%s'", violation.getPropertyPath(),
							violation.getMessage(),
							violation.getInvalidValue()));
		}
		ErrorResponse errorModel = ErrorResponse.builder()
				.messages(errorMessages)
				.path(request.getServletPath())
				.build();
		return ResponseEntity.badRequest()
				.body(errorModel);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorResponse> onMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e,
			HttpServletRequest request) {
		MethodParameter parameter = e.getParameter();
		String message = "Parameter: '" + parameter.getParameterName() + "' is not valid. " +
				"Value '" + e.getValue() + "' could not be bound to type: '" + parameter.getParameterType() + "'";
		log.error("Exception while handling request: " + message, e);
		var errorMessages = List.of(message);
		ErrorResponse errorModel = ErrorResponse.builder()
				.messages(errorMessages)
				.path(request.getServletPath())
				.build();
		return ResponseEntity.badRequest()
				.body(errorModel);
	}
}
